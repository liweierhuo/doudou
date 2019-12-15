package com.doudou.core.web.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-02
 */
@Data
public class RawDataBo implements Serializable {

    private static final long serialVersionUID = 6000949189012629876L;

    private String nickName;
    private Integer gender;
    private String language;
    private String country;
    private String province;
    private String city;
    private String avatarUrl;

}
