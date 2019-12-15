package com.doudou.dao.repository.member;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.member.DdUser;

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

}
