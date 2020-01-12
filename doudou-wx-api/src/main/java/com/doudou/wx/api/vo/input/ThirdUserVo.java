package com.doudou.wx.api.vo.input;

import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @ClassName ThirdUserVo
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 13:47
 **/
@Data
public class ThirdUserVo {

    @NotNull(message = "openId不能为空")
    private String openId;
    private String unionId;
    private String nickName;
    private String logo;

    /**
     * openId 类型(1: 小程序授权 2:app微信授权，3：qq，4：微博，5:微信公众号授权)
     */
    private Integer type;
}
