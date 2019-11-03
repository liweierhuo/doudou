package com.doudou.wx.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.wx.RawDataBo;
import com.doudou.dao.entity.UcUser;
import com.doudou.dao.service.UcUserService;
import com.doudou.wx.api.vo.WxLoginVO;
import com.github.kevinsawicki.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-09
 */
@RestController
@RequestMapping("/api/wechat")
@Slf4j
public class WechatAuthController extends BaseController{

    @Value("${miniprogram.loginUrl}")
    private String wxLoginUrl;

    @Value("${miniprogram.appId}")
    private String appId;

    @Value("${miniprogram.appSecret}")
    private String appSecret;

    @Autowired
    private UcUserService ucUserService;


    @RequestMapping("index")
    public ApiResponse index() {
        Map map = new HashMap();
        map.put("code", "0");
        map.put("message", "hello world-1");
        return new ApiResponse<>(map);
    }

    @PostMapping("login")
    public ApiResponse index(@RequestBody WxLoginVO request) {
        log.info("request : [{}]", request);
        String requestUrl = wxLoginUrl.replace("APP_ID", appId).replace("APP_SECRET", appSecret).replace("JSCODE", request.getCode());
        log.info("requestUrl : [{}]",requestUrl);
        String response = HttpRequest.get(requestUrl).body();
        log.info("response : [{}]",response);
        //将用户信息保存数据库中
        JSONObject jsonObject = JSONObject.parseObject(response);
        String openId = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        RawDataBo rawDataBo = JSONObject.parseObject(request.getRawData(), RawDataBo.class);
        if (StringUtils.isEmpty(openId)) {
           return ApiResponse.error();
        }
        UcUser userInfo = ucUserService.queryByOpenId(openId);
        if (userInfo != null) {
            userInfo.setLoginTime(LocalDateTime.now());
            return ucUserService.update(userInfo) ? new ApiResponse<>(sessionKey): ApiResponse.error();
        }
        UcUser ucUser = new UcUser();
        ucUser.setIcon(rawDataBo.getAvatarUrl());
        ucUser.setNickName(rawDataBo.getNickName());
        ucUser.setUsername(rawDataBo.getNickName());
        getSession().setAttribute("sessionKey",sessionKey);
        ucUser.setOpenId(openId);
        boolean result = ucUserService.addUser(ucUser);
        return result ? new ApiResponse<>(sessionKey) : ApiResponse.error();
    }

}
