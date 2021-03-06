package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.Integral;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
public interface IIntegralService extends IService<Integral> {

    /**
     * 获取用户积分
     * @param clientId
     * @return
     */
    Integral getIntegralByClientId(String clientId);

    /**
     * 更新积分
     * @param integral
     * @return
     */
    boolean updateIntegral(Integral integral);
}
