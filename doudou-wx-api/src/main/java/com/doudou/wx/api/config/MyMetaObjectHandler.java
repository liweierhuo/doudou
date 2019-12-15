package com.doudou.wx.api.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.doudou.wx.api.util.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    public String nowDate = DateUtils.formatDate(new Date(),DateUtils.DATETIME_FORMAT);

    @Override
    public void insertFill(MetaObject metaObject) {
        //添加数据时，自动填充，创建时间和更新时间
        this.setInsertFieldValByName("createDate", DateUtils.parseDate(nowDate), metaObject);
        this.setUpdateFieldValByName("updateDate", DateUtils.parseDate(nowDate), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //修改数据时，自动填充更新时间
        this.setUpdateFieldValByName("updateDate", DateUtils.parseDate(nowDate), metaObject);

    }
}