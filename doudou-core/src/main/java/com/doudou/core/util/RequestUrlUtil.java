package com.doudou.core.util;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.core.exception.WxApiException;
import com.github.kevinsawicki.http.HttpRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-03-15
 */
@Slf4j
public class RequestUrlUtil {

    public static JSONObject requestToWx(String requestUrl) {
        log.info("requestUrl : [{}]",requestUrl);
        String response = HttpRequest.get(requestUrl).body();
        log.info("response : [{}]",response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (jsonObject.getIntValue(WxApiConstant.WX_ERROR_CODE) != 0) {
            throw new WxApiException(jsonObject.getString(WxApiConstant.WX_ERROR_MSG));
        }
        return jsonObject;
    }

    public static JSONObject postToWx(String requestUrl, String requestBody) {
        log.info("requestUrl : [{}]",requestUrl);
        log.info("param : [{}]",requestBody);
        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST");
        httpRequest.contentType("application/json", "UTF-8");
        // 将请求体信息放入send中
        httpRequest.send(requestBody);
        String response = httpRequest.body();
        log.info("response : [{}]",response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (jsonObject.getIntValue(WxApiConstant.WX_ERROR_CODE) != 0) {
            throw new WxApiException(jsonObject.getString(WxApiConstant.WX_ERROR_MSG));
        }
        return jsonObject;
    }

}
