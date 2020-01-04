package com.doudou.dao.mapper.resources;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.dao.entity.resources.DdUserResource;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 用户兑换资源表 Mapper 接口
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface DdUserResourceMapper extends BaseMapper<DdUserResource> {

    Map<String, Object> getList(@Param("page") Page page, @Param("userId") String userId,
                                @Param("type") Integer type);
}
