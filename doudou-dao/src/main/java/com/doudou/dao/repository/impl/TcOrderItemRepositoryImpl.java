package com.doudou.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.TcOrderItem;
import com.doudou.dao.mapper.TcOrderItemMapper;
import com.doudou.dao.repository.TcOrderItemRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2019-10-13
 */
@Repository
public class TcOrderItemRepositoryImpl extends ServiceImpl<TcOrderItemMapper, TcOrderItem> implements TcOrderItemRepository {

}
