package com.doudou.dao.repository.resources.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdUserIntegral;
import com.doudou.dao.mapper.resources.DdUserIntegralMapper;
import com.doudou.dao.repository.resources.IDdUserIntegralService;
import org.springframework.stereotype.Service;

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

}
