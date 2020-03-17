package com.doudou.dao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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


    /**
     * 查询分页
     * @param clientId
     * @param pageQuery
     * @return
     */
    IPage<Record> pageResource(String clientId, IPage<Record> pageQuery);

    /**
     * 收入总和
     * @param clientId
     * @return
     */
    Integer sumIncome(String clientId);

    /**
     * 支出总和
     * @param clientId
     * @return
     */
    Integer sumExpend(String clientId);
}
