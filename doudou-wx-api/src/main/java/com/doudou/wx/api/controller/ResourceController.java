package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.ResourceStatusEnum;
import com.doudou.core.util.RedisUtil;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IResourceService;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.vo.ResourceVO;
import javax.annotation.Resource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ResourceController extends BaseController{

    @Resource
    private IResourceService resourceService;
    @Resource
    private IUserService userService;
    @Resource
    private RedisUtil redisUtil;
    @GetMapping("/list")
    public ApiResponse getPageResource(PageRequestVO pageRequestVO,ResourceVO resourceVO) {
        IPage<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        IPage<DataResource> result = resourceService.page(pageQuery);
        return new ApiResponse<>(result);
    }

    @PostMapping("/add")
    public ApiResponse addResource(@SessionId String clientId, @RequestBody ResourceVO resourceVO) {
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户不存在");
        checkParam(resourceVO);
        resourceVO.setClientId(clientId);
        resourceVO.setStatus(ResourceStatusEnum.PENDING.name());
        resourceVO.setResourceId(redisUtil.genericUniqueId("R"));
        boolean result = resourceService.save(resourceVO);
        if (!result){
            return ApiResponse.error();
        }
        return ApiResponse.success();
    }

    @GetMapping("/user")
    public ApiResponse userResource(PageRequestVO pageRequestVO,ResourceVO resourceVO, @SessionId String sessionId) {
        User userInfo = userService.queryByOpenId(sessionId);
        IPage<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        IPage<DataResource> result = resourceService.page(pageQuery,new QueryWrapper<DataResource>().eq("client_id",userInfo.getClientId()));
        return new ApiResponse<>(result);
    }

    private void checkParam(ResourceVO resourceVO) {
        Assert.notNull(resourceVO,"request is required");
        Assert.hasText(resourceVO.getTitle(),"title is required");
    }
}
