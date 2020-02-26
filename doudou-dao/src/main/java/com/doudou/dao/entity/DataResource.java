package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("dd_resource")
@Data
public class DataResource extends BaseDO {

    /**
     * 资源唯一标示
     */
    @TableField(value = "resource_id")
    private String resourceId;

    /**
     * 上传者唯一标示
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 资源标题
     */
    private String title;

    /**
     * 资源副标题
     */
    private String subtitle;

    /**
     * 资源来源
     */
    private String source;

    /**
     * 资源价格
     */
    private Integer price;

    /**
     * 资源链接地址
     */
    private String url;

    /**
     * 资源宣传图片链接地址
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 状态，待审核，正常，审核不通过，下架
     */
    private String status;

    /**
     * 简介
     */
    @TableField(value = "res_summary")
    private String resSummary;

    /**
     * 类型
     */
    @TableField(value = "res_type")
    private String resType;

    /**
     * 下载次数
     */
    @TableField(value = "download_num")
    private Integer downloadNum;

    /**
     * 浏览次数次数
     */
    @TableField(value = "view_num")
    private Integer viewNum;

    /**
     * 库存总量
     */
    @TableField(value = "total_num")
    private Integer totalNum;
    /**
     * 剩余数量
     */
    @TableField(value = "remaining_num")
    private Integer remainingNum;

    /**
     * 排序号，越大越靠前
     */
    @TableField(value = "sort_num")
    private Integer sortNum;

    /**
     * 置顶字段，置顶为1，否则为0
     */
    @TableField(value = "sticky")
    private Integer sticky;

}
