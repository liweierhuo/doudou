package com.doudou.wx.api.vo;

import com.doudou.dao.entity.Ad;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdVO extends Ad {

    private String creatFormatDate;

}
