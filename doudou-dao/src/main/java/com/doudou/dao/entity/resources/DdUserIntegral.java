package com.doudou.dao.entity.resources;

import com.doudou.dao.entity.BaseModelDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户积分表
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DdUserIntegral extends BaseModelDo {

    private static final long serialVersionUID = 1L;

    /**
     * 积分类型(1:签到积分  2：投稿积分)
     */
    private Integer type;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户积分
     */
    private Integer userIntegral;


}
