package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 积分操作记录表
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@TableName("integral_record")
@Data
public class Record extends BaseDO {

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 操作类型，1：签到；2：资源上传；3：资源被下载；4：充值；5：下载资源；
     */
    private String type;

    /**
     * 积分
     */
    private Integer integral;

}
