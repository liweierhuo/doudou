package com.doudou.dao.service.resources;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.resources.DdUserIntegral;

import java.util.List;

/**
 * <p>
 * 用户积分表 服务类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface IDdUserIntegralService extends IService<DdUserIntegral> {

    List<DdUserIntegral> getList(Page page, String userId);
}
