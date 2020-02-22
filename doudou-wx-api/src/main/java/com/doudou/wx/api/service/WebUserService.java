package com.doudou.wx.api.service;

import com.doudou.core.constant.IntegralTypeEnum;
import com.doudou.core.service.WebIntegralService;
import com.doudou.core.util.RedisUtil;
import com.doudou.dao.entity.UserSignIn;
import com.doudou.dao.service.IUserSignInService;
import java.time.LocalDateTime;
import javax.annotation.Resource;
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
    private RedisUtil redisUtil;

    @Value("${signIn.integral:10}")
    private int signInIntegral;

    @Transactional(rollbackFor = Throwable.class)
    public void userSignIn(String clientId) {
        Assert.isTrue(userSignInService.save(buildUserSignIn(clientId)), "保存数据失败");
        integralService.saveIntegral(clientId,signInIntegral, IntegralTypeEnum.SIGN_IN);
    }

    private UserSignIn buildUserSignIn(String clientId) {
        UserSignIn userSignIn = new UserSignIn();
        userSignIn.setClientId(clientId);
        userSignIn.setTxId(redisUtil.genericUniqueId("S"));
        userSignIn.setSignInDate(LocalDateTime.now());
        return userSignIn;
    }
}
