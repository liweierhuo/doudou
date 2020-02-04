package com.doudou.dao.service.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.member.DdUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface IDdUserService extends IService<DdUser> {


    DdUser getUserById(String userId);

    List<DdUser> listPage(Page page, String searchString);

    DdUser getUserByOpenId(String openId);
}
