package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController {

    @GetMapping
    public ApiResponse getPageOrder() {
        return ApiResponse.success();
    }
	
}
