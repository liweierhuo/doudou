package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户签到表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping
public class GlobalController {

    @GetMapping("/error")
    public ApiResponse error() {
        return ApiResponse.error();
    }
	
}
