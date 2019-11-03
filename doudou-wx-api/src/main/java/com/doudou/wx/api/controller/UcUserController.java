package com.doudou.wx.api.controller;


import com.doudou.core.web.ApiResponse;
import com.doudou.dao.entity.UcUser;
import com.doudou.dao.service.UcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/user")
public class UcUserController extends BaseController {

    @Autowired
    private UcUserService ucUserService;

    @RequestMapping("/get/{userId}")
    public ApiResponse<UcUser> getUser(@PathVariable Integer userId) {
        return new ApiResponse<>(ucUserService.queryById(userId));
    }

    @PostMapping("/add")
    public ApiResponse addUser(@RequestBody UcUser ucUser) {
        boolean result = ucUserService.addUser(ucUser);
        if (result) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error(500,"添加失败");
        }

    }

}

