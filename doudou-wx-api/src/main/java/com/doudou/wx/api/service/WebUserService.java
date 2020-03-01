package com.doudou.wx.api.service;

import com.doudou.core.constant.IntegralTypeEnum;
import com.doudou.core.service.WebIntegralService;
import com.doudou.core.util.RedisUtil;
import com.doudou.dao.entity.Integral;
import com.doudou.dao.entity.User;
import com.doudou.dao.entity.UserSignIn;
import com.doudou.dao.service.IUserService;
import com.doudou.dao.service.IUserSignInService;
import com.doudou.wx.api.util.MyDateUtils;
import com.doudou.wx.api.vo.UserInfoVO;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.annotation.Resource;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-03
 */
@Service
public class WebUserService {

    @Resource
    private WebIntegralService integralService;

    @Resource
    private IUserSignInService userSignInService;

    @Resource
    private WebOrderService webOrderService;

    @Resource
    private IUserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Value("${signIn.integral:10}")
    private int signInIntegral;

    @Transactional(rollbackFor = Throwable.class)
    public void userSignIn(String clientId) {
        Assert.isTrue(userSignInService.save(buildUserSignIn(clientId)), "保存数据失败");
        integralService.saveIntegral(clientId,signInIntegral, IntegralTypeEnum.SIGN_IN);
    }

    public UserInfoVO getUserInfo(String clientId) {
        Assert.hasText(clientId,"clientId is required");
        User userInfo = userService.queryByClientId(clientId);
        Assert.notNull(userInfo,"用户信息不存在");
        UserInfoVO userInfoVO = UserInfoVO.builder().build();
        BeanUtils.copyProperties(userInfo,userInfoVO);

        Integral integral = integralService.getIntegralByClientId(clientId);
        userInfoVO.setUserIntegral(integral == null ? 0 : integral.getUserIntegral());
        long days = ChronoUnit.DAYS.between(MyDateUtils.date2LocalDate(userInfo.getCreated()), LocalDateTime.now());
        userInfoVO.setRegisteredDays(days);
        userInfoVO.setResourceNum(webOrderService.countUserResourceNum(clientId));
        userInfoVO.setSignInStatus(userSignInStatus(clientId));
        return userInfoVO;
    }

    public User getSimpleUserInfo(String clientId) {
        Assert.hasText(clientId,"clientId is required");
        return userService.queryByClientId(clientId);
    }

    public boolean userSignInStatus(String clientId) {
        Assert.hasText(clientId,"clientId is required");
        int count = userSignInService
            .countSignIn(clientId, DateTime.now().withTimeAtStartOfDay().toDate(), DateTime.now().plusDays(1).minusSeconds(1).toDate());
        return count > 0;
    }

    private UserSignIn buildUserSignIn(String clientId) {
        UserSignIn userSignIn = new UserSignIn();
        userSignIn.setClientId(clientId);
        userSignIn.setTxId(redisUtil.genericUniqueId("S"));
        userSignIn.setSignInDate(new Date());
        return userSignIn;
    }
}
