package com.doudou.dao.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.resources.DdResources;

import java.util.List;

/**
 * <p>
 * 数据资源表 服务类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface IDdResourcesService extends IService<DdResources> {

    List<DdResources> listPage(Page page, String searchString);
}
