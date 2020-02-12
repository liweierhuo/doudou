package com.doudou.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误信息码
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-09-02
 */
@AllArgsConstructor
@Getter
public enum ErrorMsgEnum {
    /**
     * 错误码枚举
     */
    INTERNAL_SYSTEM_ERROR(500,"系统繁忙，请稍后操作"),
    REPEAT_SIGN_IN(501001,"您已经签到了，请明天再来"),
    ALREADY_OWN_THE_RESOURCE(501002,"已经拥有该资源"),
    ;

    private int code;
    private String errorMsg;
}
