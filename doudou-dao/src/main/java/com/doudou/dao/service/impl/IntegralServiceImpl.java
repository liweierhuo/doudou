package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.Integral;
import com.doudou.dao.mapper.IntegralMapper;
import com.doudou.dao.service.IIntegralService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 用户积分表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class IntegralServiceImpl extends ServiceImpl<IntegralMapper, Integral> implements IIntegralService {

    @Override
    public Integral getIntegralByClientId(String clientId) {
        return getOne(new QueryWrapper<Integral>().eq("client_id", clientId));
    }

    @Override
    public boolean updateIntegral(Integral integral) {
        Assert.notNull(integral,"更新对象不能为空");
        Assert.hasText(integral.getClientId(),"clientId不能为空");
        Integral updateIntegral = new Integral();
        updateIntegral.setRemark(integral.getRemark());
        updateIntegral.setUserIntegral(integral.getUserIntegral());
        return update(updateIntegral,new QueryWrapper<Integral>().eq("client_id", integral.getClientId()));
    }
}
