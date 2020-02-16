package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.ErrorMsgEnum;
import com.doudou.core.constant.IntegralTypeEnum;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Integral;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IUserService;
import com.doudou.dao.service.IUserSignInService;
import com.doudou.wx.api.service.WebIntegralService;
import com.doudou.wx.api.service.WebOrderService;
import com.doudou.wx.api.vo.UserInfoVO;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
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
    private IUserSignInService userSignInService;
    @Resource
    private IUserService userService;
    @Resource
    private WebIntegralService integralService;
    @Resource
    private WebOrderService webOrderService;

    @Value("${signIn.integral:10}")
    private int signInIntegral;

    @PostMapping("/signIn")
    public ApiResponse signInGetIntegral(@SessionId String clientId) {
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户信息不存在");
        int count = userSignInService
            .countSignIn(userInfo.getClientId(), DateTime.now().withTimeAtStartOfDay().toDate(), DateTime.now().plusDays(1).minusSeconds(1).toDate());
        if (count > 0) {
            return ApiResponse.error(ErrorMsgEnum.REPEAT_SIGN_IN.getCode(), ErrorMsgEnum.REPEAT_SIGN_IN.getErrorMsg());
        }
        //领取积分
        integralService.saveIntegral(userInfo.getClientId(), signInIntegral, IntegralTypeEnum.SIGN_IN);
        return ApiResponse.success();
    }

    @GetMapping("/info")
    public ApiResponse getUserInfo(@SessionId String clientId) {
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户信息不存在");
        UserInfoVO userInfoVO = UserInfoVO.builder().build();
        BeanUtils.copyProperties(userInfo,userInfoVO);

        Integral integral = integralService.getIntegralByClientId(clientId);
        userInfoVO.setUserIntegral(integral == null ? 0 : integral.getUserIntegral());
        long days = ChronoUnit.DAYS.between(userInfo.getCreated(), LocalDateTime.now());
        userInfoVO.setRegisteredDays(days);
        userInfoVO.setResourceNum(webOrderService.countUserResourceNum(clientId));
        return new ApiResponse<>(userInfoVO);
    }

    @PostMapping("/update")
    public ApiResponse updateUser(@SessionId String clientId,@RequestBody UserInfoVO userInfoVO) {
        Assert.notNull(userInfoVO,"request is required");
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户信息不存在");
        User updateBean = new User();
        if (StringUtils.isNotBlank(userInfoVO.getBackgroundImageUrl())) {
            updateBean.setBackgroundImageUrl(userInfoVO.getBackgroundImageUrl());
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
        Page<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        pageQuery.setRecords(webOrderService.pageUserResource(clientId,pageQuery));
        return new ApiResponse<>(pageQuery);
    }

}
