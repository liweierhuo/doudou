package com.doudou.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.UcUser;
import com.doudou.dao.mapper.UcUserMapper;
import com.doudou.dao.repository.UcUserRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2019-10-13
 */
@Repository
public class UcUserRepositoryImpl extends ServiceImpl<UcUserMapper, UcUser> implements UcUserRepository {

}
