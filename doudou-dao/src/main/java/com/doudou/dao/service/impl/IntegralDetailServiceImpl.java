package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.IntegralDetail;
import com.doudou.dao.mapper.IntegralDetailMapper;
import com.doudou.dao.service.IIntegralDetailService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 用户积分明细表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class IntegralDetailServiceImpl extends ServiceImpl<IntegralDetailMapper, IntegralDetail> implements IIntegralDetailService {

    @Override
    public IntegralDetail getIntegralDetailByClientId(String clientId, String type) {
        return getOne(new QueryWrapper<IntegralDetail>()
            .eq("client_id",clientId)
            .eq("type",type));
    }

    @Override
    public List<IntegralDetail> getIntegralDetailByClientId(String clientId) {
        return list(new QueryWrapper<IntegralDetail>()
            .eq("client_id",clientId));
    }

    @Override
    public boolean updateIntegralDetail(IntegralDetail integralDetail) {
        Assert.notNull(integralDetail,"更新对象不能为空");
        Assert.hasText(integralDetail.getClientId(),"clientId不能为空");
        Assert.hasText(integralDetail.getType(),"类型不能为空");
        Assert.notNull(integralDetail.getUserIntegralId(),"积分表Id不能为空");
        IntegralDetail updateIntegralDetail = new IntegralDetail();
        updateIntegralDetail.setRemark(integralDetail.getRemark());
        updateIntegralDetail.setUserIntegral(integralDetail.getUserIntegral());
        return update(updateIntegralDetail,new QueryWrapper<IntegralDetail>()
            .eq("client_id",integralDetail.getClientId())
            .eq("type",integralDetail.getType())
            .eq("user_integral_id",integralDetail.getUserIntegralId()));
    }
}
