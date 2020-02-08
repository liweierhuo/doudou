package com.doudou.wx.api.service;

import com.doudou.core.constant.IntegralOperateTypeEnum;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Service("webIntegralService")
public class WebIntegralService {
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
     * 获取积分
     * @param clientId
     * @param userIntegral
     * @param integralTypeEnum
     */
    @Transactional(rollbackFor = Throwable.class)
    public void saveIntegral(String clientId,Integer userIntegral,IntegralTypeEnum integralTypeEnum){
        businessProcess(clientId,integralTypeEnum);
        //保存用户积分
        Integral integral = integralService.getIntegralByClientId(clientId);
        if (integral != null) {
            integral.setUserIntegral(integral.getUserIntegral() + userIntegral);
            Assert.isTrue(integralService.updateIntegral(integral),"更新数据失败");
        } else {
            Integral saveIntegral = new Integral();
            saveIntegral.setClientId(clientId);
            saveIntegral.setUserIntegral(userIntegral);
            Assert.isTrue(integralService.save(saveIntegral),"保存数据失败");
        }
        //保存积分明细
        IntegralDetail integralDetail = integralDetailService.getIntegralDetailByClientId(clientId,integralTypeEnum.name());
        if (integralDetail != null) {
            integralDetail.setUserIntegral(integralDetail.getUserIntegral() + userIntegral);
            Assert.isTrue(integralDetailService.updateIntegralDetail(integralDetail),"更新数据失败");
        } else {
            Integral integralResult = integralService.getIntegralByClientId(clientId);
            IntegralDetail saveIntegralDetail = new IntegralDetail();
            saveIntegralDetail.setClientId(clientId);
            saveIntegralDetail.setUserIntegral(userIntegral);
            saveIntegralDetail.setUserIntegralId(integralResult.getId().intValue());
            saveIntegralDetail.setType(integralTypeEnum.name());
            Assert.isTrue(integralDetailService.save(saveIntegralDetail),"保存数据失败");
        }
        saveOperateRecord(clientId,userIntegral,IntegralOperateTypeEnum.valueOf(integralTypeEnum.name()));
    }

    /**
     * 花费积分
     * 扣减顺序 签到积分
     * @param clientId
     * @param integral
     */
    @Transactional(rollbackFor = Throwable.class)
    public void expendIntegral(String clientId,Integer integral) {
        Assert.notNull(integral,"积分不能为空");
        Integral userIntegral = integralService.getIntegralByClientId(clientId);
        Assert.notNull(userIntegral,"您的积分不足");
        Assert.isTrue(userIntegral.getUserIntegral().compareTo(integral) >= 0 ,"您的积分不足");
        //扣总积分
        userIntegral.setUserIntegral(userIntegral.getUserIntegral() - integral);
        Assert.isTrue(integralService.updateIntegral(userIntegral),"更新数据失败");
        //扣积分明细
        List<IntegralDetail> integralDetailList = integralDetailService.getIntegralDetailByClientId(clientId);
        integralDetailList.sort((o1, o2) -> {
            if (o1.getType().equalsIgnoreCase(o2.getType())) {
                return 0;
            }
            return IntegralTypeEnum.valueOf(o1.getType()).getSortNum() > IntegralTypeEnum.valueOf(o1.getType()).getSortNum() ? -1 : 1;

        });
        int detailTotal = 0;
        List<IntegralDetail> updateDetailList = new ArrayList<>(4);
        Map<Long,Integer> detailIdMap = new HashMap<>(4);
        for (IntegralDetail integralDetail : integralDetailList) {
            if (detailTotal >= integral) {
                break;
            }
            int originTotal = detailTotal;
            detailTotal += integralDetail.getUserIntegral();
            if (detailTotal >= integral) {
                updateDetailList.add(integralDetail);
                detailIdMap.put(integralDetail.getId(),integral - originTotal);
                break;
            } else {
                updateDetailList.add(integralDetail);
                detailIdMap.put(integralDetail.getId(),integralDetail.getUserIntegral());
            }
        }
        for (IntegralDetail integralDetail : updateDetailList) {
            integralDetail.setUserIntegral(integralDetail.getUserIntegral() - detailIdMap.get(integralDetail.getId()));
            Assert.isTrue(integralDetailService.updateIntegralDetail(integralDetail),"更新数据失败");
        }
        saveOperateRecord(clientId,integral,IntegralOperateTypeEnum.EXCHANGE_RESOURCES);
    }

    public Integral getIntegralByClientId(String clientId) {
        return integralService.getIntegralByClientId(clientId);
    }

    private void businessProcess(String clientId,IntegralTypeEnum integralTypeEnum) {
        switch (integralTypeEnum) {
            case SIGN_IN:
                Assert.isTrue(userSignInService.save(buildUserSignIn(clientId)), "保存数据失败");
                break;
            case EARN:
                break;
            default:

        }
    }

    private UserSignIn buildUserSignIn(String clientId) {
        UserSignIn userSignIn = new UserSignIn();
        userSignIn.setClientId(clientId);
        userSignIn.setTxId(redisUtil.genericUniqueId("S"));
        userSignIn.setSignInDate(LocalDateTime.now());
        return userSignIn;
    }

    private void saveOperateRecord(String clientId,Integer integral, IntegralOperateTypeEnum operateTypeEnum) {
        //保存记录记录
        Record record = new Record();
        record.setClientId(clientId);
        record.setIntegral(integral);
        record.setType(operateTypeEnum.name());
        Assert.isTrue(recordService.saveRecord(record),"保存数据失败");
    }
}
