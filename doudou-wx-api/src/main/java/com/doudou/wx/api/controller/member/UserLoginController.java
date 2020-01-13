package com.doudou.wx.api.controller.member;

import com.doudou.wx.api.biz.LoginBizService;
import com.doudou.wx.api.vo.AjaxResponse;
import com.doudou.wx.api.vo.input.ThirdUserVo;
import com.doudou.wx.api.vo.output.UserOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserLogin
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 12:10
 **/
@RestController
@RequestMapping("user/login")
@Slf4j
public class UserLoginController {

    @Autowired
    private LoginBizService loginBizService;

    @RequestMapping("wxLogin")
    public AjaxResponse wxLogin(ThirdUserVo thirdUserVo, HttpServletRequest request) {
        if (StringUtils.isEmpty(thirdUserVo.getOpenId())) {
            return AjaxResponse.error(10000,"openId为空");
        }
        AjaxResponse ajaxResponse = AjaxResponse.success();
        try {
            UserOutput userOutput = loginBizService.thirdUserLogin(thirdUserVo, request);
            ajaxResponse.setData(userOutput);
        } catch (Exception e) {
            log.error("微信授权登录异常,{}",e);
            e.printStackTrace();
            ajaxResponse.setSuccess(false);
            ajaxResponse.setDescription("error");
            ajaxResponse.setErrorCode(500);
        }
        return ajaxResponse;
    }


}
