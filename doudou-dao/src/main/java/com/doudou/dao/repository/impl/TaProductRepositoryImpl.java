package com.doudou.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.TaProduct;
import com.doudou.dao.mapper.TaProductMapper;
import com.doudou.dao.repository.TaProductRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2019-10-13
 */
@Repository
public class TaProductRepositoryImpl extends ServiceImpl<TaProductMapper, TaProduct> implements TaProductRepository {

}
