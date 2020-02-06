package com.doudou.wx.api.controller;

import com.doudou.core.constant.ErrorMsgEnum;
import com.doudou.core.constant.IntegralTypeEnum;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IUserService;
import com.doudou.dao.service.IUserSignInService;
import com.doudou.wx.api.service.IntegralService;
import javax.annotation.Resource;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
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
    private IntegralService integralService;

    @Value("${signIn.integral:10}")
    private int signInIntegral;

    @PostMapping("/signIn")
    public ApiResponse signInGetIntegral(@SessionId String sessionId) {
        User userInfo = userService.queryByOpenId(sessionId);
        int count = userSignInService
            .countSignIn(userInfo.getClientId(), DateTime.now().withTimeAtStartOfDay().toDate(), DateTime.now().plusDays(1).minusSeconds(1).toDate());
        if (count > 0) {
            return ApiResponse.error(ErrorMsgEnum.REPEAT_SIGN_IN.getCode(), ErrorMsgEnum.REPEAT_SIGN_IN.getErrorMsg());
        }
        //领取积分
        integralService.saveIntegral(userInfo.getClientId(), signInIntegral, IntegralTypeEnum.SIGN_IN);
        return ApiResponse.success();
    }

}
