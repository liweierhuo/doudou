package com.doudou.dao.repository.resources.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.resources.DdUserSign;
import com.doudou.dao.mapper.resources.DdUserSignMapper;
import com.doudou.dao.repository.resources.IDdUserSignService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户签到表 服务实现类
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Service
public class DdUserSignServiceImpl extends
            ServiceImpl<DdUserSignMapper, DdUserSign> implements IDdUserSignService {

}
