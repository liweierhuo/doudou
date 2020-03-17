package com.doudou.core.exception;

import lombok.Data;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-29
 */
@Data
public class WxApiException extends RuntimeException {

    private int code;
    public WxApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WxApiException(WxApiErrorCodeEnum errorMsgEnum) {
        super(errorMsgEnum.getErrorMsg());
        this.code = errorMsgEnum.getCode();
    }
    public WxApiException(String message) {
        super(message);
    }
}
