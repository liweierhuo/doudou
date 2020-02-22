package com.doudou.admin.api.vo;

import com.doudou.dao.entity.DataResource;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVO implements Serializable {

    private String resourceId;

    private Integer rewardIntegral;

}
