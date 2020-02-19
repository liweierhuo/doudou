package com.doudou.wx.api.vo;

import com.doudou.dao.entity.DataResource;
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
@NoArgsConstructor
public class ResourceVO extends DataResource {

    private String keywords;

    private String publishDate;

    private String publisher;

    private String publisherIcon;

}
