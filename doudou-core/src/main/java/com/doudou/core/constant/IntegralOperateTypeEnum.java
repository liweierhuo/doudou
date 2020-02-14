package com.doudou.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-06
 */
@AllArgsConstructor
@Getter
public enum IntegralOperateTypeEnum {
    /**
     * 积分操作类型
     */
    SIGN_IN("签到","+"),
    PUBLISH_RESOURCE("发布资源","+"),
    EARN("赚取","+"),
    RECHARGE("充值","+"),
    EXCHANGE_RESOURCES("兑换资源","-")
    ;
    private String desc;
    private String inOrOut;
}
