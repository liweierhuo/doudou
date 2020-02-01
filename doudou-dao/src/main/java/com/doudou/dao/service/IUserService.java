package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.User;
import javax.jws.soap.SOAPBinding.Use;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
public interface IUserService extends IService<User> {

    /**
     * 根据openId查询
     * @param openId
     * @return
     */
    User queryByOpenId(String openId);


    /**
     * 更新用户信息
     * @param openId
     * @param user
     * @return
     */
    void updateUserByOpenId(User user,String openId);
	
}
