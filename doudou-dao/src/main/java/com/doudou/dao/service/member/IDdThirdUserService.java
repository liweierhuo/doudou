package com.doudou.dao.service.member;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.member.DdThirdUser;

/**
 * <p>
 * 第三方用户表 服务类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
public interface IDdThirdUserService extends IService<DdThirdUser> {

    DdThirdUser queryByOpenId(String openId);

    DdThirdUser queryByUnionId(String unionId);

    DdThirdUser getByUnionIdType(String unionId, Integer type);
}
