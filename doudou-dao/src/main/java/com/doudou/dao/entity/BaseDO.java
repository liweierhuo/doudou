package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-02
 */
@Data
class BaseDO implements Serializable {

    private static final long serialVersionUID = 5820295301868902533L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Date created;

    private Date modified;

    @TableLogic
    private Integer flag;
}
