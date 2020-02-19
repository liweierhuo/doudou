package com.doudou.wx.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-14
 */
@Data
@Builder
@NoArgsConstructor
public class IntegralRecordVO implements Serializable {
    private String type;
    private String typeDesc;
    private String integral;
    private String transDate;
    private Long id;

}
