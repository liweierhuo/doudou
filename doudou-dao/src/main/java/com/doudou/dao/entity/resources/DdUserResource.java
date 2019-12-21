package com.doudou.dao.entity.resources;

import com.doudou.dao.entity.BaseModelDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户兑换资源表
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DdUserResource extends BaseModelDo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 资源ID
     */
    private String resId;

    /**
     * 用户资源类型 0 兑换资源  1 投稿资源
     */
    private Integer userResType;


}
