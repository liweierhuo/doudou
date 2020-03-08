package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Order;
import com.doudou.dao.mapper.OrderMapper;
import com.doudou.dao.service.IOrderService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public Order getOrder(String clientId, String resourceId) {
        return getOne(new QueryWrapper<Order>()
            .eq("client_id",clientId)
            .eq("resource_id",resourceId));
    }

    @Override
    public int countUserResource(String clientId) {
        return getBaseMapper().countUserResource(clientId);
    }


    @Override
    public List<DataResource> pageUserResource(String clientId, Page pageQuery) {
        return getBaseMapper().pageUserResource(pageQuery, clientId);
    }

    @Override
    public List<DataResource> pageUserOrderResource(String clientId, String status, Page pageQuery) {
        return getBaseMapper().pageUserOrderResource(pageQuery,clientId,status);
    }
}
