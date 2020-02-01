package com.doudou.wx.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.RedisConstant;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.core.password.util.AESEncryptUtil;
import com.doudou.core.util.RedisUtil;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.wx.RawDataBo;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.exception.WxApiException;
import com.doudou.wx.api.vo.WxLoginVO;
import com.github.kevinsawicki.http.HttpRequest;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${miniprogram.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${miniprogram.paidUnionIdUrl}")
    private String paidUnionIdUrl;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IUserService ucUserService;

    @PostMapping("login")
    public ApiResponse index(@RequestBody WxLoginVO request) {
        log.info("request : [{}]", request);
        String requestUrl = String.format(wxLoginUrl,AESEncryptUtil.decrypt(appId),AESEncryptUtil.decrypt(appSecret),request.getCode());
        JSONObject jsonObject = requestToWx(requestUrl);
        String openId = jsonObject.getString(WxApiConstant.WX_OPEN_ID);
        String unionId = jsonObject.getString(WxApiConstant.WX_UNION_ID);
        String sessionKey = jsonObject.getString(WxApiConstant.WX_SESSION_KEY);
        RawDataBo rawDataBo = JSONObject.parseObject(request.getRawData(), RawDataBo.class);
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(sessionKey)) {
           return ApiResponse.error();
        }
        String userToken = getUserToken(openId,sessionKey);
        RedisUtil.setex(RedisConstant.getSessionIdKey(userToken),openId,7200L);
        User userInfo = ucUserService.queryByOpenId(openId);
        if (userInfo != null) {
            if (StringUtils.isEmpty(userInfo.getUnionId())) {
                userInfo.setUnionId(unionId);
            }
            if (StringUtils.isEmpty(userInfo.getClientId())) {
                userInfo.setClientId(redisUtil.genericUniqueId("W"));
            }
            userInfo.setLoginTime(LocalDateTime.now());
            ucUserService.updateUserByOpenId(userInfo,openId);
            return new ApiResponse<>(userToken);
        }
        User ucUser = new User();
        ucUser.setIcon(rawDataBo.getAvatarUrl());
        ucUser.setNickName(rawDataBo.getNickName());
        ucUser.setClientId(redisUtil.genericUniqueId("W"));
        ucUser.setUsername(rawDataBo.getNickName());
        ucUser.setOpenId(openId);
        ucUser.setUnionId(unionId);
        ucUser.setLoginTime(LocalDateTime.now());
        boolean result = ucUserService.save(ucUser);
        return result ? new ApiResponse<>(userToken) : ApiResponse.error();
    }

    private String getUserToken(@NotNull String openId, @NotNull String sessionKey) {
        return DigestUtils.md5Hex(String.join(";",openId,sessionKey));
    }

    private JSONObject requestToWx(String requestUrl) {
        log.info("requestUrl : [{}]",requestUrl);
        String response = HttpRequest.get(requestUrl).body();
        log.info("response : [{}]",response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (StringUtils.isNotBlank(jsonObject.getString(WxApiConstant.WX_ERROR_CODE))) {
            throw new WxApiException(jsonObject.getString(WxApiConstant.WX_ERROR_MSG));
        }
        return jsonObject;
    }

    private String getAccessToken(String openId) {
        String requestUrl = String.format(accessTokenUrl,AESEncryptUtil.decrypt(appId),AESEncryptUtil.decrypt(appSecret));
        JSONObject jsonObject = requestToWx(requestUrl);
        String accessToken = jsonObject.getString("access_token");
        Long expiresTimes = jsonObject.getLong("expires_in");
        RedisUtil.setex(RedisConstant.getAccessTokenRedisKey(openId),accessToken,expiresTimes);
        return accessToken;
    }

    private String getToken(String openId) {
        String accessToken = RedisUtil.get(RedisConstant.getAccessTokenRedisKey(openId));
        if (StringUtils.isNotBlank(accessToken)) {
            return accessToken;
        }
        String newToken = getAccessToken(openId);
        if (StringUtils.isNotBlank(newToken)) {
            throw new RuntimeException("获取 Access Token 异常");
        }
        return newToken;
    }
}
