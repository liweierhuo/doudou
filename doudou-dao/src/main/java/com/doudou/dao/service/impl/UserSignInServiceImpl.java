package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.UserSignIn;
import com.doudou.dao.mapper.UserSignInMapper;
import com.doudou.dao.service.IUserSignInService;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户签到表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-02-02
 */
@Service
public class UserSignInServiceImpl extends ServiceImpl<UserSignInMapper, UserSignIn> implements IUserSignInService {

    @Override
    public int countSignIn(String clientId, Date startTime,Date endTime) {
        return count(new QueryWrapper<UserSignIn>()
            .eq("client_id", clientId)
            .between("sign_in_date", startTime, endTime));
    }
}
