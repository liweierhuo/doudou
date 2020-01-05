package com.doudou.dao.service.resources.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdUserIntegral;
import com.doudou.dao.mapper.resources.DdUserIntegralMapper;
import com.doudou.dao.service.resources.IDdUserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户积分表 服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdUserIntegralServiceImpl extends
        ServiceImpl<DdUserIntegralMapper, DdUserIntegral> implements IDdUserIntegralService {

    @Autowired
    private DdUserIntegralMapper userIntegralMapper;

    @Override
    public List<DdUserIntegral> getList(Page page, String userId) {
        return userIntegralMapper.getList(page,userId);
    }
}
