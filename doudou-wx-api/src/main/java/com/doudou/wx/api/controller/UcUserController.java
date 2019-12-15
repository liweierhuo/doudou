package com.doudou.wx.api.controller;


import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.UcUser;
import com.doudou.dao.service.UcUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author K神带你飞
 * @since 2019-10-13
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UcUserController extends BaseController {

    private final UcUserService ucUserService;

    public UcUserController(UcUserService ucUserService) {
        this.ucUserService = ucUserService;
    }

    @GetMapping
    public ApiResponse<UcUser> getUser(@SessionId String sessionId) {
        log.info("sessionId : [{}]",sessionId);
        return new ApiResponse<>(ucUserService.queryByOpenId(sessionId));
    }

}

