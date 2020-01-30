package com.doudou.dao.service.member.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.mapper.member.DdUserMapper;
import com.doudou.dao.service.member.IDdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdUserServiceImpl extends ServiceImpl<DdUserMapper, DdUser> implements IDdUserService {

    @Autowired
    private DdUserMapper userMapper;

    @Override
    public DdUser getUserById(String userId) {
        DdUser ddUser = userMapper.selectById(userId);
        return ddUser;
    }

    @Override
    public List<DdUser> listPage(Page page, String searchString) {
        return userMapper.listPage(page,searchString);
    }


}
