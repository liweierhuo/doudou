package com.doudou.wx.api.controller.member;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.MemberConstant;
import com.doudou.core.constant.RedisConstant;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.core.password.util.AESEncryptUtil;
import com.doudou.core.properties.WeChatConfigProperties;
import com.doudou.core.util.RedisUtils;
import com.doudou.core.web.wx.RawDataBo;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.service.member.IDdUserService;
import com.doudou.wx.api.biz.LoginBizService;
import com.doudou.wx.api.controller.BaseController;
import com.doudou.wx.api.util.WeChatUtils;
import com.doudou.wx.api.vo.AjaxResponse;
import com.doudou.wx.api.vo.WxLoginVO;
import com.doudou.wx.api.vo.output.UserOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MemberController extends BaseController {

    @Autowired
    private WeChatConfigProperties weChatConfig;

    @Autowired
    private IDdUserService ddUserService;

    @Autowired
    private LoginBizService loginBizService;

    @PostMapping("login")
    public AjaxResponse index(@RequestBody WxLoginVO request) {
        log.info("request : [{}]", request);
        //初始化URL
        String requestUrl = String.format(weChatConfig.getLoginUrl(), weChatConfig.getAppId()
                , weChatConfig.getAppSecret(), request.getCode());
        //发起请求
        JSONObject jsonObject = WeChatUtils.requestToWx(requestUrl);
        //获取返回的数据
        String openId = jsonObject.getString(WxApiConstant.WX_OPEN_ID);
        String unionId = jsonObject.getString(WxApiConstant.WX_UNION_ID);
        String sessionKey = jsonObject.getString(WxApiConstant.WX_SESSION_KEY);
        RawDataBo rawDataBo = JSONObject.parseObject(request.getRawData(), RawDataBo.class);
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(sessionKey)) {
            return AjaxResponse.error();
        }

        log.info("开始登陆...数据..{},{}", openId, rawDataBo);
        UserOutput userOutput = loginBizService.getLoginUser(openId, unionId, rawDataBo);

        return AjaxResponse.success(userOutput);
    }

    private String creatUser(RawDataBo rawDataBo) {
        DdUser ddUser = new DdUser();
        ddUser.setNickName(rawDataBo.getNickName());
        ddUser.setLogo(rawDataBo.getNickName());
        ddUser.setNickName(rawDataBo.getNickName());
        //随机生成username
        StringBuilder username = new StringBuilder();
        int random2 = new Random().nextInt(10000000);
        username.append(String.format("duoduo_%d", random2));
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
        return DigestUtils.md5Hex(String.join(";", openId, sessionKey));
    }


    private String getAccessToken(String openId) {
        String requestUrl = String.format(weChatConfig.getAccessTokenUrl(), weChatConfig.getAppId()
                , weChatConfig.getAppSecret());
        JSONObject jsonObject = WeChatUtils.requestToWx(requestUrl);
        String accessToken = jsonObject.getString("access_token");
        Long expiresTimes = jsonObject.getLong("expires_in");
        RedisUtils.put(RedisConstant.getAccessTokenRedisKey(openId), accessToken, expiresTimes, TimeUnit.SECONDS);
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
