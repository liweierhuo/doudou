package com.doudou.wx.api.vo;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-03
 */
@Data
@NoArgsConstructor
public class ExchangeResourceVO implements Serializable {

    private String resourceId;
    private Integer integral;

}
