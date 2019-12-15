package com.doudou.dao.repository.resources.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdUserResource;
import com.doudou.dao.mapper.resources.DdUserResourceMapper;
import com.doudou.dao.repository.resources.IDdUserResourceService;
import org.springframework.stereotype.Service;

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

}
