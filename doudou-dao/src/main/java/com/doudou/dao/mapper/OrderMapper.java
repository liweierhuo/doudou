package com.doudou.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Order;
import java.util.List;
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
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询用户全部资源列表
     * @param page ~
     * @param clientId ~
     * @return ~
     */
    @Select("SELECT r.*\n"
        + "FROM dd_resource r left join dd_order o on o.resource_id = r.resource_id\n"
        + "WHERE o.flag = 1 and r.flag = 1 and (o.client_id = #{clientId} and r.status = 'NORMAL') or (r.client_id = #{clientId} and r.status = 'NORMAL') ")
    List<DataResource> pageUserResource(Page page,@Param("clientId") String clientId);


    /**
     * 查询用户兑换的资源列表
     * @param page ~
     * @param clientId ~
     * @param status ~
     * @return ~
     */
    @Select("SELECT r.*\n"
        + "FROM dd_resource r,dd_order o where o.resource_id = r.resource_id and o.flag = 1 and r.flag = 1\n"
        + "and o.client_id = #{clientId} and r.status = #{status}")
    List<DataResource> pageUserOrderResource(Page page,@Param("clientId") String clientId, @Param("status") String status);


    /**
     * 查询用户资源总数
     * @param clientId ~
     * @return ~
     */
    @Select("SELECT count(*)\n"
        + "FROM dd_resource r left join dd_order o on o.resource_id = r.resource_id\n"
        + "WHERE o.flag = 1 and r.flag = 1 and (o.client_id = #{clientId} and r.status = 'NORMAL' )or r.client_id = #{clientId} ")
    int countUserResource(@Param("clientId") String clientId);

}