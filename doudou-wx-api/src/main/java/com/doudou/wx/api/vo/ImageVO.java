package com.doudou.wx.api.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-03-15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageVO implements Serializable {

    private String imageUrl;
    private String thumbnailsImageUrl;
}
