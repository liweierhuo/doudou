package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.ResourceStatusEnum;
import com.doudou.core.constant.ResourceTypeEnum;
import com.doudou.core.util.RedisUtil;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IResourceService;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.vo.ResourceVO;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/api/resource")
@Slf4j
public class ResourceController extends BaseController{

    @Resource
    private IResourceService resourceService;
    @Resource
    private IUserService userService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/list")
    public ApiResponse getPageResource(PageRequestVO pageRequestVO,ResourceVO resourceVO) {
        IPage<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        IPage<DataResource> result = resourceService.page(pageQuery,buildResourceWrapper(resourceVO));
        return new ApiResponse<>(result);
    }

    @PostMapping("/add")
    public ApiResponse addResource(@SessionId String clientId, @RequestBody ResourceVO resourceVO) {
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户不存在");
        checkParam(resourceVO);
        resourceVO.setClientId(clientId);
        resourceVO.setStatus(ResourceStatusEnum.PENDING.name());
        resourceVO.setResType(ResourceTypeEnum.ONLINE.name());
        resourceVO.setRemainingNum(resourceVO.getTotalNum());
        resourceVO.setResourceId(redisUtil.genericUniqueId("R"));
        boolean result = resourceService.save(resourceVO);
        if (!result){
            return ApiResponse.error();
        }
        return ApiResponse.success();
    }

    @GetMapping("/detail/{resourceId}")
    public ApiResponse getResourceById(@PathVariable String resourceId) {
        Assert.hasText(resourceId,"resourceId 不能为空");
        DataResource dataResource = resourceService.getResource(resourceId);
        Assert.notNull(dataResource,"资源不能为空");
        ResourceVO resourceVO = new ResourceVO();
        BeanUtils.copyProperties(dataResource,resourceVO);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        resourceVO.setPublishDate(dataResource.getCreated().format(dateTimeFormatter));
        User userInfo = userService.queryByClientId(dataResource.getClientId());
        Assert.notNull(userInfo,"用户不存在");
        resourceVO.setPublisher(userInfo.getNickName());
        resourceVO.setPublisherIcon(userInfo.getIcon());
        //更新查看次数
        threadPoolTaskExecutor.execute(() -> {
            log.info("异步更新资源[{}]浏览次数",dataResource.getResourceId());
            DataResource updateBean = new DataResource();
            updateBean.setResourceId(dataResource.getResourceId());
            updateBean.setViewNum((dataResource.getViewNum() == null ? 0 : dataResource.getViewNum()) + 1);
            resourceService.updateResource(updateBean);
        });
        return new ApiResponse<>(resourceVO);
    }

    @GetMapping("/publish")
    public ApiResponse getPublishUserResourceList(@SessionId String clientId, PageRequestVO pageRequestVO) {
        return getUserPublishList(clientId,pageRequestVO);
    }

    @GetMapping("/{clientId}/publish")
    public ApiResponse getUserPublishList(@PathVariable("clientId") String clientId, PageRequestVO pageRequestVO) {
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"user info is null");
        IPage<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        pageQuery = resourceService.pageResource(clientId, pageQuery);
        IPage<ResourceVO> pageResult = new Page<>(pageQuery.getCurrent(),pageQuery.getSize());
        pageResult.setTotal(pageQuery.getTotal());
        pageResult.setPages(pageQuery.getPages());
        pageResult.setRecords(buildResourceList(pageQuery.getRecords()));
        return new ApiResponse<>(pageResult);
    }

    private List<ResourceVO> buildResourceList(List<DataResource> records) {
        return records.stream().map(dataResource -> {
            ResourceVO resourceVO = new ResourceVO();
            BeanUtils.copyProperties(dataResource,resourceVO);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            resourceVO.setPublishDate(dataResource.getCreated().format(dateTimeFormatter));
            return resourceVO;
        }).collect(Collectors.toList());
    }

    private Wrapper<DataResource> buildResourceWrapper(ResourceVO resourceVO) {
        Assert.notNull(resourceVO,"request is required");
        QueryWrapper<DataResource> wrapper = new QueryWrapper<>();
        wrapper.eq("status",ResourceStatusEnum.NORMAL.name());
        if (StringUtils.isNotBlank(resourceVO.getKeywords())) {
            wrapper.like("title",resourceVO.getKeywords())
                .or()
                .like("subtitle",resourceVO.getKeywords())
                .orderByDesc("id");
        }
        return wrapper;
    }

    private void checkParam(ResourceVO resourceVO) {
        Assert.notNull(resourceVO,"request is required");
        Assert.hasText(resourceVO.getTitle(),"title is required");
        Assert.notNull(resourceVO.getTotalNum(),"total num is required");
    }
}
