package com.doudou.dao.entity.ad;


import com.doudou.dao.entity.BaseModelDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @author shenLiuHai
 * @since 2020-02-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DdAd extends BaseModelDo {

    private static final long serialVersionUID = 1L;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告链接
     */
    private String link;

    /**
     * 图片
     */
    private String pic;

    /**
     * 广告类型（0 外部广告 1 内部广告）
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 广告商
     */
    private String advertisers;


}
