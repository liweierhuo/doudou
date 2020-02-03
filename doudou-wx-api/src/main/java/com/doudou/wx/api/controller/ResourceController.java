package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public ApiResponse getPageResource(PageRequestVO<ResourceVO> pageRequestVO) {
        IPage<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        IPage<DataResource> result = resourceService.page(pageQuery);
        return new ApiResponse<>(result);
    }

    @PostMapping("/add")
    public ApiResponse addResource(@SessionId String sessionId, @RequestBody ResourceVO resourceVO) {
        User userInfo = userService.queryByOpenId(sessionId);
        Assert.notNull(userInfo,"用户不存在");
        resourceVO.setClientId(userInfo.getClientId());
        resourceVO.setResourceId(redisUtil.genericUniqueId("R"));
        boolean result = resourceService.save(resourceVO);
        if (!result){
            return ApiResponse.error();
        }
        return ApiResponse.success();
    }

    @GetMapping("/user")
    public ApiResponse userResource(PageRequestVO<ResourceVO> pageRequestVO, @SessionId String sessionId) {
        User userInfo = userService.queryByOpenId(sessionId);
        IPage<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        IPage<DataResource> result = resourceService.page(pageQuery,new QueryWrapper<DataResource>().eq("clientId",userInfo.getClientId()));
        return new ApiResponse<>(result);
    }
}
