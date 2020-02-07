package com.doudou.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源类型
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-09-02
 */
@AllArgsConstructor
@Getter
public enum ResourceTypeEnum {
    /**
     * 资源类型
     */
    PDF("pdf"),
    ONLINE("在线课程"),
    ;
    private String desc;

}
