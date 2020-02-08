package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Order;
import java.util.List;

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

    /**
     * 获取用户资源数量
     * @param clientId
     * @return
     */
    int countUserResource(String clientId);

    /**
     * 分页查询
     * @param clientId
     * @param pageQuery
     * @return
     */
    List<DataResource> pageUserResource(String clientId, Page pageQuery);

    /**
     * 查询
     * @param clientId
     * @param pageQuery
     * @return
     */
    List<DataResource> pageUserOrderResource(String clientId, Page pageQuery);
	
}
