package com.doudou.wx.api.controller;

import com.doudou.core.util.RedisUtil;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.UserMessage;
import com.doudou.dao.service.IUserMessageService;
import javax.annotation.Resource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户签到表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/api/message")
public class UserMessageController extends BaseController{

    @Resource
    private IUserMessageService userMessageService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("add")
    public ApiResponse addMessage(@SessionId String clientId,@RequestBody UserMessage userMessage) {
        userMessage.setClientId(clientId);
        userMessage.setTxId(redisUtil.genericUniqueId("M"));
        boolean result = userMessageService.save(userMessage);
        Assert.isTrue(result,"保存失败");
        return ApiResponse.success();
    }
	
}
