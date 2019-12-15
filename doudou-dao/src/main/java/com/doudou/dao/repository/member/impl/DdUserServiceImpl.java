package com.doudou.dao.repository.member.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.mapper.member.DdUserMapper;
import com.doudou.dao.repository.member.IDdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdUserServiceImpl extends
        ServiceImpl<DdUserMapper, DdUser> implements IDdUserService {

    @Autowired
    private DdUserMapper userMapper;


    @Override
    public DdUser getUserById(String userId) {
        DdUser ddUser = userMapper.selectById(userId);
        return ddUser;
    }


}
