package com.doudou.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doudou.dao.entity.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 收入总和
     * @param clientId ~
     * @return ~
     */
    @Select("select sum(integral) from integral_record where client_id = #{clientId} and flag = 1 and type in ('SIGN_IN','PUBLISH_RESOURCE','EARN','RECHARGE')")
    Integer sumIncome(@Param("clientId") String clientId);

    /**
     * 支出总和
     * @param clientId ~
     * @return ~
     */
    @Select("select sum(integral) from integral_record where client_id = #{clientId} and flag = 1 and type in ('EXCHANGE_RESOURCES')")
    Integer sumExpend(@Param("clientId") String clientId);

}