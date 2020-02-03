package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@TableName("dd_order")
@Data
public class Order extends BaseDO {
    /**
     * 备注
     */
    private String remark;

    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 用户ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 资源ID
     */
    @TableField(value = "resource_id")
    private String resourceId;

    /**
     * 状态，1：成功；2：失败
     */
    private String status;

    /**
     * 资源价格
     */
    private Integer price;

}
