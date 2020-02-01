package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.Order;
import com.doudou.dao.mapper.OrderMapper;
import com.doudou.dao.service.IOrderService;
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
	
}
