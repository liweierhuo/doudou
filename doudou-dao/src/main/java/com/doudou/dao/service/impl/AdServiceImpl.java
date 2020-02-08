package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.Ad;
import com.doudou.dao.mapper.AdsMapper;
import com.doudou.dao.service.IAdService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户签到表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdsMapper, Ad> implements IAdService {
	
}
