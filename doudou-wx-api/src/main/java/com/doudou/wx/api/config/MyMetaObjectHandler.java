package com.doudou.wx.api.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //添加数据时，自动填充，创建时间和更新时间
        this.setInsertFieldValByName("createDate", new Date(), metaObject);
        this.setUpdateFieldValByName("updateDate", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //修改数据时，自动填充更新时间
        this.setUpdateFieldValByName("updateDate", new Date(), metaObject);

    }
}