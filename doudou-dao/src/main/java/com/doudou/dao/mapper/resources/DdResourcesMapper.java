package com.doudou.dao.mapper.resources;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.dao.entity.resources.DdResources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据资源表 Mapper 接口
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface DdResourcesMapper extends BaseMapper<DdResources> {

    List<DdResources> listPage(Page page, @Param("searchString") String searchString);
}
