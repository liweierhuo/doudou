package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户签到表
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@TableName("dd_ad")
@Data
public class Ad extends BaseDO {

    /**
     * 广告唯一标示
     */
    @TableField(value = "ad_id")
    private String adId;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告描述
     */
    private String description;

    /**
     * 广告url
     */
    private String url;

    /**
     * 广告图片url
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 状态，有效，失效
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

}
