package com.doudou.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源类型
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-09-02
 */
@AllArgsConstructor
@Getter
public enum ResourceStatusEnum {
    /**
     * 资源类型
     */
    PENDING("待审核"),
    NORMAL("正常"),
    REJECT("拒绝"),
    TAKE_OFF("下架"),
    ;
    private String desc;

}
