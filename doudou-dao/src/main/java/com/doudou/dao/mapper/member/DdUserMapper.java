package com.doudou.dao.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.dao.entity.member.DdUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface DdUserMapper extends BaseMapper<DdUser> {

    List<DdUser> listPage(Page page, @Param("searchString") String searchString);
}
