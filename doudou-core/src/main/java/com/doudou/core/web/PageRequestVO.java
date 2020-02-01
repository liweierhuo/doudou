package com.doudou.core.web;

import lombok.Data;

/**
 * 分页查询的分页信息基础类
 * @author liwei
 */
@Data
public class PageRequestVO<T> {

    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 查询参数
     */
    private T queryParam;

    /**
     * 设置pageSize的值，最大不超过100
     *
     * @param pageSize ~
     */
    public void setPageSize(Integer pageSize) {
        int maxPageSize = 100;
        if (pageSize > maxPageSize || pageSize <= 0) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
    }

    /**
     * 设置当前页数
     *
     * @param pageNo ~
     */
    public void setPageNo(Integer pageNo) {
        if (pageNo <= 0) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }
}
