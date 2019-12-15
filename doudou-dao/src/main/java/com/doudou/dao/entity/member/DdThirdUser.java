package com.doudou.dao.entity.member;

import com.doudou.dao.entity.BaseModelDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 第三方用户表
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DdThirdUser extends BaseModelDo {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方标识
     */
    private String openId;

    /**
     * 第三方唯一标识
     */
    private String unionId;

    /**
     * 第三方类型(1:微信小程序)
     */
    private Integer type;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户主键
     */
    private String userId;


}
