package com.doudou.dao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.doudou.dao.entity.UcUser;
import com.doudou.dao.repository.UcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-13
 */
@Service
public class UcUserService {

    @Autowired
    private UcUserRepository ucUserRepository;

    public UcUser queryById(Integer id) {
        return ucUserRepository.getById(id);
    }

    public boolean addUser(UcUser ucUser) {
        return ucUserRepository.save(ucUser);
    }

    public boolean update(UcUser ucUser) {
        return ucUserRepository.updateById(ucUser);
    }

    public UcUser queryByOpenId(String openId) {
        return ucUserRepository.getOne(new QueryWrapper<UcUser>().eq("open_id",openId));
    }

}
