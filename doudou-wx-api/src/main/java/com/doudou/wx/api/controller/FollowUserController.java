package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.wx.api.service.WebFollowUserService;
import com.doudou.wx.api.vo.FollowUserVO;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 关注表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-03-01
 */
@RestController
@RequestMapping("/api/follow")
public class FollowUserController extends BaseController {

    @Resource
    private WebFollowUserService webFollowUserService;

    @PostMapping
    public ApiResponse followUser(@SessionId String clientId, @RequestBody FollowUserVO followUserVO) {
        followUserVO.setClientId(clientId);
        webFollowUserService.addFollowUser(followUserVO);
        return ApiResponse.success();
    }

    @PostMapping("check")
    public ApiResponse followUserCheck(@SessionId String clientId, @RequestBody FollowUserVO followUserVO) {
        followUserVO.setClientId(clientId);
        return new ApiResponse<>(webFollowUserService.followUserCheck(followUserVO));
    }

    @GetMapping
    public ApiResponse getUserFollowInfo(@SessionId String clientId) {
        return new ApiResponse<>(webFollowUserService.getUserFollowInfo(clientId));
    }

    @GetMapping("/list")
    public ApiResponse pageMyFollow(@SessionId String clientId, PageRequestVO pageRequestVO) {
        return new ApiResponse<>(webFollowUserService.pageMyFollow(clientId,pageRequestVO));
    }

    @GetMapping("/my/list")
    public ApiResponse pageFollowMe(@SessionId String clientId, PageRequestVO pageRequestVO) {
        return new ApiResponse<>(webFollowUserService.pageFollowMe(clientId,pageRequestVO));
    }

    @DeleteMapping("/cancel")
    public ApiResponse cancelFollow(@SessionId String clientId, @RequestBody FollowUserVO followUserVO) {
        followUserVO.setClientId(clientId);
        webFollowUserService.cancelFollow(followUserVO);
        return ApiResponse.success();
    }
	
}
