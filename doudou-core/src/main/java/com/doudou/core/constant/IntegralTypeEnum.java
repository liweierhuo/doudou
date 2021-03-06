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
     * 积分类型
     */
    SIGN_IN(1,"签到"),
    PUBLISH_RESOURCE(2,"发布资源"),
    EARN(2,"赚取"),
    RECHARGE(4,"充值"),
    ;

    private int sortNum;
    private String desc;

}
