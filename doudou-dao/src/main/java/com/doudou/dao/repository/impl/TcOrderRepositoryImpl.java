package com.doudou.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.TcOrder;
import com.doudou.dao.mapper.TcOrderMapper;
import com.doudou.dao.repository.TcOrderRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2019-10-13
 */
@Repository
public class TcOrderRepositoryImpl extends ServiceImpl<TcOrderMapper, TcOrder> implements TcOrderRepository {

}
