package com.doudou.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName BaseModelDo
 * @Description 公共基础DO
 * @Author shenliuhai
 * @Date 2019/12/7 10:28
 **/
@Data
public class BaseModelDo implements Serializable{

    private static final long serialVersionUID = 163248528745490622L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;//主键

    @TableField(fill = FieldFill.INSERT)   //插入时，自动填充
    private Date createDate;//创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)   //插入或更新时，自动填充
    private Date updateDate;//修改时间

    @TableLogic(value = "0",delval = "1")
    private Integer delFlag;//删除标记（0：否 1：是)

    private String remark;//备注

}
