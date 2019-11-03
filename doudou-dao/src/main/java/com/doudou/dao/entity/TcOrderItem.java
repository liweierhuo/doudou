package com.doudou.dao.entity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author liwei
 * @since 2019-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TcOrderItem extends BaseDO {

private static final long serialVersionUID=1L;


    /**
     * 订单id
     */
    private Long orderNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品sku条码
     */
    private String productSkuCode;

    /**
     * 单价
     */
    private BigDecimal productPrice;

    /**
     * 购买数量
     */
    private Integer productQuantity;

    /**
     * 实付价格
     */
    private BigDecimal productPaidPrice;


}
