package com.doudou.core.web.wx;

import java.io.Serializable;
import lombok.Data;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-02
 */
@Data
public class RawDataBo implements Serializable {
    private String nickName;
    private Integer gender;
    private String language;
    private String country;
    private String province;
    private String city;
    private String avatarUrl;

}
