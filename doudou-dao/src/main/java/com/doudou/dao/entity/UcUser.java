package com.doudou.dao.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwei
 * @since 2019-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UcUser extends BaseDO {

private static final long serialVersionUID=1L;



    private String ucAccount;

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
     * 昵称
     */
    private String nickName;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 微信的openid
     */
    private String openId;

    private String unionId;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginTime;



}
