package com.doudou.dao.mapper.resources;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.dao.entity.resources.DdUserIntegral;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户积分表 Mapper 接口
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface DdUserIntegralMapper extends BaseMapper<DdUserIntegral> {

    List<DdUserIntegral> getList(@Param("page") Page page, @Param("userId") String userId);
}
