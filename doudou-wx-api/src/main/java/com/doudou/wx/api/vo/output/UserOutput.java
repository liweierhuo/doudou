package com.doudou.wx.api.vo.output;

import lombok.Data;

/**
 * @ClassName UserOutput
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 17:31
 **/
@Data
public class UserOutput {

    private String token;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户图像
     */
    private String logo;

    /**
     * 用户总积分
     */
    private Integer userTotalIntegral;

}
