package com.doudou.dao.service.resources.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdUserResource;
import com.doudou.dao.mapper.resources.DdUserResourceMapper;
import com.doudou.dao.service.resources.IDdUserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户兑换资源表 服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdUserResourceServiceImpl extends
        ServiceImpl<DdUserResourceMapper, DdUserResource> implements IDdUserResourceService {

    @Autowired
    private DdUserResourceMapper userResourceMapper;

    @Override
    public Map<String, Object> getList(Page page,String userId,Integer type) {
        return userResourceMapper.getList(page,userId,type);
    }
}
