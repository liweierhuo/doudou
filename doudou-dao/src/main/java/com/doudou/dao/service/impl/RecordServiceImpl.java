package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.Record;
import com.doudou.dao.mapper.RecordMapper;
import com.doudou.dao.service.IRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分操作记录表  服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
	
}
