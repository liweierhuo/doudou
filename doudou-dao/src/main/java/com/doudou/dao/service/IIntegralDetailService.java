package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.Integral;
import com.doudou.dao.entity.IntegralDetail;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
public interface IIntegralDetailService extends IService<IntegralDetail> {

    /**
     * 获取用户积分明细
     * @param clientId
     * @param type
     * @return
     */
    IntegralDetail getIntegralDetailByClientId(String clientId,String type);

    /**
     * 获取积分明细
     * @param clientId
     * @return
     */
    List<IntegralDetail> getIntegralDetailByClientId(String clientId);

    /**
     * 更新积分明细
     * @param integralDetail
     * @return
     */
    boolean updateIntegralDetail(IntegralDetail integralDetail);
}
