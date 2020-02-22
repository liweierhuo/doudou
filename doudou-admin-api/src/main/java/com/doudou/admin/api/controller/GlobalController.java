package com.doudou.admin.api.controller;

import com.doudou.core.web.ApiResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalController extends BaseController {

    @GetMapping("/error")
    public ApiResponse error() {
        return ApiResponse.error();
    }

    @GetMapping("/heart.jsp")
    public int heart() {
        log.info("system is ...");
        return 1;
    }
	
}
