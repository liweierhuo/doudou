package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户签到表
 * </p>
 *
 * @author liwei
 * @since 2020-02-02
 */
@EqualsAndHashCode(callSuper = true)
@TableName("uc_user_sign_in")
@Data
public class UserSignIn extends BaseDO {

    /**
     * 签到表流水Id
     */
    @TableField(value = "tx_id")
    private String txId;

    /**
     * 用户标示
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 签到日期
     */
    @TableField(value = "sign_in_date")
    private Date signInDate;

    /**
     * 备注
     */
    private String remark;

}
