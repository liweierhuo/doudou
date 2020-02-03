package com.doudou.wx.api.service;

import com.doudou.core.constant.IntegralTypeEnum;
import com.doudou.core.util.RedisUtil;
import com.doudou.dao.entity.Integral;
import com.doudou.dao.entity.IntegralDetail;
import com.doudou.dao.entity.Record;
import com.doudou.dao.entity.UserSignIn;
import com.doudou.dao.service.IIntegralDetailService;
import com.doudou.dao.service.IIntegralService;
import com.doudou.dao.service.IRecordService;
import com.doudou.dao.service.IUserSignInService;
import java.time.LocalDateTime;
import javax.annotation.Resource;
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
public class IntegralService {
    @Resource
    private IIntegralService integralService;
    @Resource
    private IIntegralDetailService integralDetailService;
    @Resource
    private IRecordService recordService;
    @Resource
    private IUserSignInService userSignInService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 签到领积分
     * @param clientId
     * @param signInIntegral
     */
    @Transactional(rollbackFor = Throwable.class)
    public void signInGetIntegral(String clientId,Integer signInIntegral){
        Assert.isTrue(userSignInService.save(buildUserSignIn(clientId)), "保存数据失败");
        //保存用户积分
        Integral integral = integralService.getIntegralByClientId(clientId);
        if (integral != null) {
            integral.setUserIntegral(integral.getUserIntegral() + signInIntegral);
            Assert.isTrue(integralService.updateIntegral(integral),"更新数据失败");
        } else {
            Integral saveIntegral = new Integral();
            saveIntegral.setClientId(clientId);
            saveIntegral.setUserIntegral(signInIntegral);
            Assert.isTrue(integralService.save(saveIntegral),"保存数据失败");
        }
        //保存积分明细
        IntegralDetail integralDetail = integralDetailService.getIntegralDetailByClientId(clientId,IntegralTypeEnum.SIGN_IN.getValue());
        if (integralDetail != null) {
            integralDetail.setUserIntegral(integralDetail.getUserIntegral() + signInIntegral);
            Assert.isTrue(integralDetailService.updateIntegralDetail(integralDetail),"更新数据失败");
        } else {
            Integral integralResult = integralService.getIntegralByClientId(clientId);
            IntegralDetail saveIntegralDetail = new IntegralDetail();
            saveIntegralDetail.setClientId(clientId);
            saveIntegralDetail.setUserIntegral(signInIntegral);
            saveIntegralDetail.setUserIntegralId(integralResult.getId().intValue());
            saveIntegralDetail.setType(IntegralTypeEnum.SIGN_IN.getValue());
            Assert.isTrue(integralDetailService.save(saveIntegralDetail),"保存数据失败");
        }
        //保存记录记录
        Record record = new Record();
        record.setClientId(clientId);
        record.setIntegral(signInIntegral);
        record.setType(IntegralTypeEnum.SIGN_IN.getValue());
        Assert.isTrue(recordService.saveRecord(record),"保存数据失败");
    }

    private UserSignIn buildUserSignIn(String clientId) {
        UserSignIn userSignIn = new UserSignIn();
        userSignIn.setClientId(clientId);
        userSignIn.setTxId(redisUtil.genericUniqueId("S"));
        userSignIn.setSignInDate(LocalDateTime.now());
        return userSignIn;
    }
}
