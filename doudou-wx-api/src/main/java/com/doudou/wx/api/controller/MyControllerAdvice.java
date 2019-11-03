package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller 增强器
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-09
 */
@RestControllerAdvice
@Slf4j
public class MyControllerAdvice {
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return 错误信息
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResponse errorHandler(Exception ex) {
        log.error("系统异常 ex:",ex);
        return ApiResponse.error(500,"系统繁忙，请稍后再试");
    }
}
