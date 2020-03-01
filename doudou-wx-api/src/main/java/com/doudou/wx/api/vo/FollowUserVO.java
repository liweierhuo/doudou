package com.doudou.wx.api.vo;

import com.doudou.dao.entity.FollowUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-03-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FollowUserVO extends FollowUser {

    private Integer myFollowCount;
    private Integer followMeCount;

    private String followUserNickname;
    private String followUserIcon;

    private String createDate;

    private String label;

}
