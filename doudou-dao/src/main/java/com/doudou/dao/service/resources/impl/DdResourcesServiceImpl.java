package com.doudou.dao.service.resources.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdResources;
import com.doudou.dao.mapper.resources.DdResourcesMapper;
import com.doudou.dao.service.resources.IDdResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private DdResourcesMapper resourcesMapper;

    @Override
    public List<DdResources> listPage(Page page, String searchString) {
        return resourcesMapper.listPage(page,searchString);
    }
}
