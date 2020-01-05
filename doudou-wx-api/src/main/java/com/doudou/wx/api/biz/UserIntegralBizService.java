package com.doudou.wx.api.biz;

import com.doudou.core.constant.MemberConstant;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.entity.resources.DdUserIntegral;
import com.doudou.dao.service.member.IDdUserService;
import com.doudou.dao.service.resources.IDdUserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserIntegralBizService
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/5 00:32
 **/
@Service
public class UserIntegralBizService {
    @Autowired
    private IDdUserIntegralService integralService;

    @Autowired
    private IDdUserService userService;

    public DdUserIntegral addIntegral(String userId) {
        DdUserIntegral userIntegral = new DdUserIntegral();
        userIntegral.setType(MemberConstant.INTEGRAL_TYPE_SIGN);
        userIntegral.setUserId(userId);
        userIntegral.setUserIntegral(MemberConstant.INTEGRAL_SCORE);

        integralService.save(userIntegral);

        //修改总积分
        DdUser userById = userService.getUserById(userId);
        Integer userTotalIntegral = userById.getUserTotalIntegral();
        userById.setUserTotalIntegral(userTotalIntegral+MemberConstant.INTEGRAL_SCORE);
        userService.saveOrUpdate(userById);
        return userIntegral;
    }
}
