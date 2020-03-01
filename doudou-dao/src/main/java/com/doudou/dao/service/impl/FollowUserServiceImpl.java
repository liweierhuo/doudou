package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.FollowUser;
import com.doudou.dao.mapper.FollowUserMapper;
import com.doudou.dao.service.IFollowUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关注表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-03-01
 */
@Service
public class FollowUserServiceImpl extends ServiceImpl<FollowUserMapper, FollowUser> implements IFollowUserService {
	
}
