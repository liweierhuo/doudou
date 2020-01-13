package com.doudou.wx.api.controller.member;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.MemberConstant;
import com.doudou.core.constant.RedisConstant;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.core.password.util.AESEncryptUtil;
import com.doudou.core.util.RedisUtils;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.wx.RawDataBo;
import com.doudou.dao.entity.member.DdThirdUser;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.service.member.IDdThirdUserService;
import com.doudou.dao.service.member.IDdUserService;
import com.doudou.wx.api.controller.BaseController;
import com.doudou.wx.api.util.WeChatUtils;
import com.doudou.wx.api.vo.WxLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-09
 */
@RestController
@RequestMapping("/api/wechat")
@Slf4j
public class MemberController extends BaseController{

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

    private final IDdUserService ddUserService;

    private final IDdThirdUserService thirdUserService;

    public MemberController(IDdUserService ddUserService,
                            IDdThirdUserService thirdUserService) {
        this.ddUserService = ddUserService;
        this.thirdUserService = thirdUserService;
    }

    @PostMapping("login")
    public ApiResponse index(@RequestBody WxLoginVO request) {
        log.info("request : [{}]", request);
        //初始化URL
        String requestUrl = String.format(wxLoginUrl,AESEncryptUtil.decrypt(appId),AESEncryptUtil.decrypt(appSecret),request.getCode());
        //发起请求
        JSONObject jsonObject = WeChatUtils.requestToWx(requestUrl);
        //获取返回的数据
        String openId = jsonObject.getString(WxApiConstant.WX_OPEN_ID);
        String unionId = jsonObject.getString(WxApiConstant.WX_UNION_ID);
        String sessionKey = jsonObject.getString(WxApiConstant.WX_SESSION_KEY);
        RawDataBo rawDataBo = JSONObject.parseObject(request.getRawData(), RawDataBo.class);

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(sessionKey)) {
           return ApiResponse.error();
        }
        //加密获取token
        String userToken = getUserToken(openId,sessionKey);
        //保存在redis中
        RedisUtils.put(RedisConstant.getSessionIdKey(userToken),openId,7200L,TimeUnit.SECONDS);

        DdThirdUser ddThirdUser = thirdUserService.queryByOpenId(openId);
        //有授权关系
        if (ddThirdUser != null) {
            String userId = ddThirdUser.getUserId();
            //数据库没有用户
            if (StringUtils.isEmpty(userId)) {
                String id = creatUser(rawDataBo);
                return id != null ? new ApiResponse<>(userToken): ApiResponse.error();
            }
            DdUser user = ddUserService.getUserById(userId);
            user.setLogo(rawDataBo.getAvatarUrl());
            user.setNickName(rawDataBo.getNickName());

            return ddUserService.updateById(user) ? new ApiResponse<>(userToken): ApiResponse.error();
        }

        String userId = creatUser(rawDataBo);
        //没有授权
        DdThirdUser thirdUser = new DdThirdUser();
        thirdUser.setOpenId(openId);
        thirdUser.setUnionId(unionId);
        thirdUser.setUserId(userId);
        thirdUser.setType(MemberConstant.XCX_TYPE);
        boolean result = thirdUserService.save(thirdUser);
        return result ? new ApiResponse<>(userToken) : ApiResponse.error();
    }

    private String creatUser(RawDataBo rawDataBo) {
        DdUser ddUser = new DdUser();
        ddUser.setNickName(rawDataBo.getNickName());
        ddUser.setLogo(rawDataBo.getNickName());
        ddUser.setNickName(rawDataBo.getNickName());
        //随机生成username
        StringBuilder username = new StringBuilder();
        int random2 = new Random().nextInt(10000000);
        username.append(String.format("duoduo_%d",random2));
        ddUser.setUsername(username.toString());
        //初始密码为123456
        ddUser.setPassword(DigestUtils.md5Hex(MemberConstant.PASSWORD));
        //初始积分100
        ddUser.setUserTotalIntegral(MemberConstant.INTEGRAL);
        //用户类型默认为普通用户(1)
        ddUser.setUserType(MemberConstant.USER_TYPE);
        ddUserService.save(ddUser);
        return ddUser.getId();
    }

    private String getUserToken(@NotNull String openId, @NotNull String sessionKey) {
        return DigestUtils.md5Hex(String.join(";",openId,sessionKey));
    }


    private String getAccessToken(String openId) {
        String requestUrl = String.format(accessTokenUrl,AESEncryptUtil.decrypt(appId),AESEncryptUtil.decrypt(appSecret));
        JSONObject jsonObject = WeChatUtils.requestToWx(requestUrl);
        String accessToken = jsonObject.getString("access_token");
        Long expiresTimes = jsonObject.getLong("expires_in");
        RedisUtils.put(RedisConstant.getAccessTokenRedisKey(openId),accessToken,expiresTimes,TimeUnit.SECONDS);
        return accessToken;
    }

    private String getToken(String openId) {
        String accessToken = RedisUtils.get(RedisConstant.getAccessTokenRedisKey(openId));
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
