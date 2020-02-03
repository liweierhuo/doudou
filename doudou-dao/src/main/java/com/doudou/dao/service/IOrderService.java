package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.Order;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
public interface IOrderService extends IService<Order> {

    /**
     * 获取订单
     * @param clientId
     * @param resourceId
     * @return
     */
    Order getOrder(String clientId,String resourceId);
	
}
