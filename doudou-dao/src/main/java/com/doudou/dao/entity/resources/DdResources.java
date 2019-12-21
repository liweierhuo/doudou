package com.doudou.dao.entity.resources;

import com.doudou.dao.entity.BaseModelDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 数据资源表
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DdResources extends BaseModelDo {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String resTitle;

    /**
     * 作者
     */
    private String resAuth;

    /**
     * 发布者
     */
    private String resPublish;

    /**
     * 下载地址
     */
    private String resUploadAddress;

    /**
     * 下载次数
     */
    private Integer resUploadNum;

    /**
     * 兑换积分
     */
    private Integer resConvertIntegralinteger;

    /**
     * 简介
     */
    private String resSummary;

    /**
     * 类型
     */
    private BigDecimal resType;
    /**
     * 审核状态 0 拒绝  1 通过
     */
    private Integer checkStatus;


}
