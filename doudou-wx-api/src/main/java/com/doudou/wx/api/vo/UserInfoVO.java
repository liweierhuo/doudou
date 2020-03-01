package com.doudou.wx.api.vo;

import com.doudou.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO extends User {

    private Integer userIntegral;
    private Long registeredDays;
    private Integer resourceNum;

    private String phoneNo;
    private String backImageUrl;
    private Boolean signInStatus;

}
