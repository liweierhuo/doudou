package com.doudou.dao.repository.resources.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdResources;
import com.doudou.dao.mapper.resources.DdResourcesMapper;
import com.doudou.dao.repository.resources.IDdResourcesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据资源表 服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdResourcesServiceImpl extends
        ServiceImpl<DdResourcesMapper, DdResources> implements IDdResourcesService {

}
