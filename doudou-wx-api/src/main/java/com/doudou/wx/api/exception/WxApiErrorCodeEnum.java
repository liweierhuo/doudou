package com.doudou.wx.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-29
 */
@AllArgsConstructor
@Getter
public enum WxApiErrorCodeEnum {

    /**
     * 错误码枚举
     */
    INTERNAL_SYSTEM_ERROR(500,"系统繁忙，请稍后操作"),
    ;
    private int code;
    private String errorMsg;
}
