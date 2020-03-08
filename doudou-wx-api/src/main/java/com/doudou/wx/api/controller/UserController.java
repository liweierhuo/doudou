package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.ErrorMsgEnum;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.service.WebOrderService;
import com.doudou.wx.api.service.WebUserService;
import com.doudou.wx.api.util.ResourceConvert;
import com.doudou.wx.api.vo.ResourceVO;
import com.doudou.wx.api.vo.UserInfoVO;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;
    @Resource
    private WebOrderService webOrderService;
    @Resource
    private WebUserService webUserService;

    @PostMapping("/signIn")
    public ApiResponse signInGetIntegral(@SessionId String clientId) {
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户信息不存在");
        if (webUserService.userSignInStatus(clientId)) {
            return ApiResponse.error(ErrorMsgEnum.REPEAT_SIGN_IN.getCode(), ErrorMsgEnum.REPEAT_SIGN_IN.getErrorMsg());
        }
        //领取积分
        webUserService.userSignIn(clientId);
        return ApiResponse.success();
    }

    @GetMapping("/info")
    public ApiResponse getUserInfo(@SessionId String clientId) {
        return getUserInfoByClientId(clientId);
    }

    @GetMapping("/data/{clientId}")
    public ApiResponse getUserInfoByClientId(@PathVariable("clientId") String clientId) {
        return new ApiResponse<>(webUserService.getUserInfo(clientId));
    }

    @PostMapping("/update")
    public ApiResponse updateUser(@SessionId String clientId,@RequestBody UserInfoVO userInfoVO) {
        Assert.notNull(userInfoVO,"request is required");
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户信息不存在");
        User updateBean = new User();
        if (StringUtils.isNotBlank(userInfoVO.getBackImageUrl())) {
            updateBean.setBackgroundImageUrl(userInfoVO.getBackImageUrl());
        }
        if (StringUtils.isNotBlank(userInfoVO.getPhoneNo())) {
            updateBean.setPhoneNo(userInfoVO.getPhoneNo());
        }
        if (StringUtils.isNotBlank(userInfoVO.getEmail())) {
            updateBean.setEmail(userInfoVO.getEmail());
        }
        if (StringUtils.isNotBlank(userInfoVO.getSignature())) {
            updateBean.setSignature(userInfoVO.getSignature());
        }
        userService.updateUserByClientId(updateBean,clientId);
        return ApiResponse.success();
    }

    @GetMapping("resource")
    public ApiResponse getAllUserResourceList(@SessionId String clientId, PageRequestVO pageRequestVO) {
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"user info is null");
        Page<ResourceVO> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        pageQuery.setRecords(ResourceConvert.convertResourceVO(webOrderService.pageUserResource(clientId,pageQuery)));
        return new ApiResponse<>(pageQuery);
    }

}
