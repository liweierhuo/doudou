package com.doudou.wx.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.RedisConstant;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.core.util.RedisUtil;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.wx.RawDataBo;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.config.WeChatConfigProperties;
import com.doudou.wx.api.exception.WxApiException;
import com.doudou.wx.api.vo.WxLoginVO;
import com.github.kevinsawicki.http.HttpRequest;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Resource
    private WeChatConfigProperties weChatConfigProperties;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IUserService ucUserService;

    @PostMapping("login")
    public ApiResponse index(@RequestBody WxLoginVO request) {
        log.info("request : [{}]", request);
        String wxLoginUrl = weChatConfigProperties.getLoginUrl();
        String appId = weChatConfigProperties.getAppId();
        String appSecret = weChatConfigProperties.getAppSecret();
        String requestUrl = String.format(wxLoginUrl,appId,appSecret,request.getCode());
        JSONObject jsonObject = requestToWx(requestUrl);
        String openId = jsonObject.getString(WxApiConstant.WX_OPEN_ID);
        String unionId = jsonObject.getString(WxApiConstant.WX_UNION_ID);
        String sessionKey = jsonObject.getString(WxApiConstant.WX_SESSION_KEY);
        RawDataBo rawDataBo = JSONObject.parseObject(request.getRawData(), RawDataBo.class);
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(sessionKey)) {
           return ApiResponse.error();
        }
        String userToken = getUserToken(openId,sessionKey);
        User userInfo = ucUserService.queryByOpenId(openId);
        if (userInfo != null) {
            if (StringUtils.isEmpty(userInfo.getUnionId())) {
                userInfo.setUnionId(unionId);
            }
            if (StringUtils.isEmpty(userInfo.getClientId())) {
                userInfo.setClientId(redisUtil.genericUniqueId("W"));
            }
            if (rawDataBo.getGender() != null) {
                userInfo.setGender(rawDataBo.getGender());
            }
            if (StringUtils.isNotBlank(rawDataBo.getCountry())) {
                userInfo.setCountry(rawDataBo.getCountry());
            }
            if (StringUtils.isNotBlank(rawDataBo.getProvince())) {
                userInfo.setProvince(rawDataBo.getProvince());
            }
            if (StringUtils.isNotBlank(rawDataBo.getCity())) {
                userInfo.setCity(rawDataBo.getCity());
            }
            userInfo.setLoginTime(LocalDateTime.now());
            ucUserService.updateUserByOpenId(userInfo,openId);
            RedisUtil.setex(RedisConstant.getSessionIdKey(userToken),userInfo.getClientId(),7200L);
            return new ApiResponse<>(userToken);
        }
        User ucUser = buildUserBean(rawDataBo);
        ucUser.setClientId(redisUtil.genericUniqueId("W"));
        ucUser.setOpenId(openId);
        ucUser.setUnionId(unionId);
        boolean result = ucUserService.save(ucUser);
        RedisUtil.setex(RedisConstant.getSessionIdKey(userToken),ucUser.getClientId(),7200L);
        return result ? new ApiResponse<>(userToken) : ApiResponse.error();
    }

    private User buildUserBean(RawDataBo rawDataBo) {
        User ucUser = new User();
        ucUser.setIcon(rawDataBo.getAvatarUrl());
        ucUser.setNickName(rawDataBo.getNickName());
        ucUser.setUsername(rawDataBo.getNickName());
        ucUser.setLoginTime(LocalDateTime.now());
        ucUser.setGender(rawDataBo.getGender());
        ucUser.setCountry(rawDataBo.getCountry());
        ucUser.setProvince(rawDataBo.getProvince());
        ucUser.setCity(rawDataBo.getCity());
        return ucUser;
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
        String accessTokenUrl = weChatConfigProperties.getAccessTokenUrl();
        String appId = weChatConfigProperties.getAppId();
        String appSecret = weChatConfigProperties.getAppSecret();
        String requestUrl = String.format(accessTokenUrl,appId,appSecret);
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
