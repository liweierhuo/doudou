package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IUserService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户积分表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/api/integral")
public class IntegralController extends BaseController {

    @Resource
    private IUserService userService;
	
}
