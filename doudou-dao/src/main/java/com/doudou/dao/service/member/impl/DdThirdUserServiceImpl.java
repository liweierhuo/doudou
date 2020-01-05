package com.doudou.dao.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.member.DdThirdUser;
import com.doudou.dao.mapper.member.DdThirdUserMapper;
import com.doudou.dao.service.member.IDdThirdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方用户表 服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdThirdUserServiceImpl extends
        ServiceImpl<DdThirdUserMapper, DdThirdUser> implements IDdThirdUserService {

    @Autowired
    private DdThirdUserMapper thirdUserMapper;

    @Override
    public DdThirdUser queryByOpenId(String openId) {
        QueryWrapper<DdThirdUser> queryWrapper = new QueryWrapper<DdThirdUser>();
        queryWrapper.eq("open_id",openId);
        DdThirdUser thirdUser = thirdUserMapper.selectOne(queryWrapper);
        return thirdUser;
    }

    @Override
    public DdThirdUser queryByUnionId(String unionId) {
        QueryWrapper<DdThirdUser> queryWrapper = new QueryWrapper<DdThirdUser>();
        queryWrapper.eq("union_id",unionId);
        DdThirdUser thirdUser = thirdUserMapper.selectOne(queryWrapper);
        return thirdUser;
    }

    @Override
    public DdThirdUser getByUnionIdType(String unionId, Integer type) {
        QueryWrapper<DdThirdUser> queryWrapper = new QueryWrapper<DdThirdUser>();
        queryWrapper.eq("union_id",unionId);
        queryWrapper.eq("type",type);
        DdThirdUser thirdUser = thirdUserMapper.selectOne(queryWrapper);
        return thirdUser;
    }

}