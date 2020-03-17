package com.doudou.core.service;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.RedisConstant;
import com.doudou.core.util.RedisUtil;
import com.doudou.core.util.RequestUrlUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-03-15
 */
@Service
@Slf4j
public class SendMessageService {

    @Setter
    private String sendUrl;
    @Setter
    private String appId;
    @Setter
    private String appSecret;
    @Setter
    private String accessTokenUrl;

    public void initService(String appId,String accessTokenUrl,String appSecret,String sendUrl) {
        this.setSendUrl(sendUrl);
        this.setAppId(appId);
        this.setAppSecret(appSecret);
        this.setAccessTokenUrl(accessTokenUrl);
    }

    public void sendMiniProgramMessage(String openId) {
        String requestUrl = String.format(sendUrl,getToken(appId,accessTokenUrl,appSecret));
        JSONObject paramMap = new JSONObject();
        paramMap.put("touser",openId);
        paramMap.put("template_id","mXnaarVH7sJ31XoNd8MIhzr1TuTK_MjsEJXmogGbsMQ");
        paramMap.put("page","pages/index/index");
        JSONObject dataJson = new JSONObject();
        JSONObject jsonData1 = new JSONObject();
        jsonData1.put("value", "测试的资源阿斯顿发舒服");
        dataJson.put("thing3",jsonData1);
        JSONObject jsonData2 = new JSONObject();
        jsonData2.put("value","2020-03-15 20:09");
        dataJson.put("date4",jsonData2);
        JSONObject jsonData3 = new JSONObject();
        jsonData3.put("value","兑换成功");
        dataJson.put("phrase2",jsonData3);
        JSONObject jsonData4 = new JSONObject();
        jsonData4.put("value","奖励4积分");
        dataJson.put("thing5",jsonData4);
        paramMap.put("data",dataJson);
        paramMap.put("emphasis_keyword","thing3");
        paramMap.put("miniprogram_state","trial");
        JSONObject result = RequestUrlUtil.postToWx(requestUrl, paramMap.toJSONString());
        log.info("发送结果：[{}]",result);
    }

    private String getAccessToken(String appId,String accessTokenUrl,String appSecret) {
        String requestUrl = String.format(accessTokenUrl,appId,appSecret);
        JSONObject jsonObject = RequestUrlUtil.requestToWx(requestUrl);
        String accessToken = jsonObject.getString("access_token");
        Long expiresTimes = jsonObject.getLong("expires_in");
        RedisUtil.setex(RedisConstant.getMiniAccessTokenRedisKey(),accessToken,expiresTimes);
        return accessToken;
    }

    private String getToken(String appId,String accessTokenUrl,String appSecret) {
        String accessToken = RedisUtil.get(RedisConstant.getMiniAccessTokenRedisKey());
        if (StringUtils.isNotBlank(accessToken)) {
            return accessToken;
        }
        String newToken = getAccessToken(appId,accessTokenUrl,appSecret);
        if (StringUtils.isNotBlank(newToken)) {
            throw new RuntimeException("获取 Access Token 异常");
        }
        return newToken;
    }
}
