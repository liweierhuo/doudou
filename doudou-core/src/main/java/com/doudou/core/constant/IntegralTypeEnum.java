package com.doudou.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 积分类型枚举
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-09-02
 */
@AllArgsConstructor
@Getter
public enum IntegralTypeEnum {
    /**
     * 错误码枚举
     */
    SIGN_IN("signIn","签到"),
    PUBLISH_RESOURCE("publishResource","发布资源"),
    EARN("earn","赚取"),
    RECHARGE("recharge","赚取"),
    ;

    private String value;
    private String desc;
}
