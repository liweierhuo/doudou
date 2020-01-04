package com.doudou.wx.api.vo.output;

import lombok.Data;

/**
 * @ClassName CurrentUser
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 18:54
 **/
@Data
public class CurrentUser {

    //第三方登录id
    private String openId;
    private String id;
    private String loginName;

}
