package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 关注表
 * </p>
 *
 * @author liwei
 * @since 2020-03-01
 */
@EqualsAndHashCode(callSuper = true)
@TableName("uc_follow_user")
@Data
public class FollowUser extends BaseDO {

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
     * 关注用户
     */
    @TableField(value = "follow_client_id")
    private String followClientId;

    /**
     * 最新留言
     */
    @TableField(value = "last_message")
    private String lastMessage;


}
