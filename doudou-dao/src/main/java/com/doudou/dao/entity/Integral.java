package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户积分表
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@TableName("user_integral")
@Data
public class Integral extends BaseDO {

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
     * 用户积分
     */
    @TableField(value = "user_integral")
    private Integer userIntegral;

}
