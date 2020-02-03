package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.Record;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
public interface IRecordService extends IService<Record> {

    /**
     * 增加记录
     * @param record
     * @return
     */
    boolean saveRecord(Record record);
	
}
