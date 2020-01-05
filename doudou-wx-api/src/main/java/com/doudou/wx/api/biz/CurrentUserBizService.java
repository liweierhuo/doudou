package com.doudou.wx.api.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.Constant;
import com.doudou.core.password.util.AESEncryptUtil;
import com.doudou.core.redis.RedisManager;
import com.doudou.dao.service.member.IDdUserService;
import com.doudou.wx.api.vo.output.CurrentUser;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CurrentUserBizService
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 19:23
 **/
@Service
public class CurrentUserBizService {

    @Autowired
    private IDdUserService userService;

    @Autowired
    private RedisManager redis;


    public CurrentUser getCurrentUser(HttpServletRequest request) {
        //获取传入token
        String token = request.getHeader(Constant.HTTP_HEADER_NAME);
        if (Strings.isNullOrEmpty(token)){
            return null;
        }

        //获取对应key
        String key = redis.getKey(token);
        if (Strings.isNullOrEmpty(key)){
            return null;
        }
        //token解密
        token = AESEncryptUtil.decrypt(token,key);
        //转为对象类型
        JSONObject jsonObject = JSON.parseObject(token);
        CurrentUser currentUser = JSONObject.toJavaObject(jsonObject, CurrentUser.class);
        return currentUser;
    }

}
