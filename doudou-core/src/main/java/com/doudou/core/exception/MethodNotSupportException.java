package com.doudou.core.exception;

/**
 * @ClassName MethodNotSupportException
 * @Description 方法不支持当前的异常
 * @Author shenliuhai
 * @Date 2020/1/4 16:51
 **/
public class MethodNotSupportException extends RuntimeException {

    public MethodNotSupportException(String message) {
        super(message);
    }
}
