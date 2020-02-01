package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.mapper.ResourceMapper;
import com.doudou.dao.service.IResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, DataResource> implements IResourceService {
	
}
