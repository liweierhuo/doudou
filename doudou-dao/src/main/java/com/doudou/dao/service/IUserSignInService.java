package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.UserSignIn;
import java.util.Date;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liwei
 * @since 2020-02-02
 */
public interface IUserSignInService extends IService<UserSignIn> {

    /**
     * 查询当前记录条数
     * @param clientId
     * @param startTime
     * @param endTime
     * @return
     */
    int countSignIn(String clientId, Date startTime, Date endTime);
}
