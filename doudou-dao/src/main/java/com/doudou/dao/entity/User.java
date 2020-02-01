package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@TableName("uc_user")
@Data
public class User extends BaseDO {


    /**
     *
     */
    private String username;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "phone_no")
    private String phoneNo;

    /**
     * 用户唯一编号
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 微信openId
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 微信unionId
     */
    @TableField(value = "union_id")
    private String unionId;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 最后登录时间
     */
    @TableField(value = "login_time")
    private LocalDateTime loginTime;

}
