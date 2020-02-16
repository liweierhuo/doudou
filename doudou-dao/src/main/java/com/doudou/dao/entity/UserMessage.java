package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户签到表
 * </p>
 *
 * @author liwei
 * @since 2020-02-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("uc_user_message")
public class UserMessage extends BaseDO {

    /**
     * 流水Id
     */
    @TableField(value = "tx_id")
    private String txId;

    /**
     * 用户标示
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 留言类型，想要资源，建议，留言，其他
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

}
