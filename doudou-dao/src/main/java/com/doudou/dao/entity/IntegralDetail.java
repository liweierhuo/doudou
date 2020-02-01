package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户积分明细表
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@TableName("user_integral_detail")
@Data
public class IntegralDetail extends BaseDO {

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
     * 用户积分表Id
     */
    @TableField(value = "user_integral_id")
    private Integer userIntegralId;

    /**
     * 类型，1：签到；2：资源上传；3：资源被下载；4：充值
     */
    private String type;

    /**
     * 用户积分
     */
    @TableField(value = "user_integral")
    private Integer userIntegral;

}
