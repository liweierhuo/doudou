package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.UserMessage;
import com.doudou.dao.mapper.UserMessageMapper;
import com.doudou.dao.service.IUserMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户签到表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-02-16
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements IUserMessageService {
	
}
