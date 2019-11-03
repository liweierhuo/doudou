package com.doudou.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author liwei
 * @since 2019-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaProduct extends BaseDO {

private static final long serialVersionUID=1L;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 商品主图
     */
    private String mainImage;

    /**
     * 商品价格，单位分
     */
    private Integer price;

    /**
     * 商品上架状态，0初始化，1上架
     */
    private Integer status;

    /**
     * 商品详情描述
     */
    private String productDescription;

    /**
     * 商品类型，1普通品，2特供品，3加工品
     */
    private Integer productType;

    /**
     * 储藏方式，1常温，2冷冻，3冷藏
     */
    private Integer storeType;

    /**
     * 商品条形码
     */
    private String productSkuCode;

    /**
     * 商品总库存
     */
    private Integer totalStock;

    /**
     * 类目id
     */
    private Long categoryId;

}
