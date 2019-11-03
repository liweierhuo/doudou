package com.doudou.core.web;

import java.util.List;
import lombok.Data;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-13
 */
@Data
public class PageResult<T> {
    private long current = 1;
    private long pages;
    private long size;
    private long total;
    private List<T> records;

    public long getPages() {
        if (this.size == 0) {
            return 0;
        }
        this.pages = this.total / this.size;
        if (this.total % this.size != 0) {
            this.pages++;
        }
        return this.pages;
    }

    public void buildDefaultPageResult(int currentPage,int pageSize) {
        this.setSize(pageSize);
        this.setCurrent(currentPage);
    }

    public void buildPageResult(long currentPage,long pageSize,long total,List<T> records) {
        this.setSize(pageSize);
        this.setCurrent(currentPage);
        this.setTotal(total);
        this.setRecords(records);
    }
}
