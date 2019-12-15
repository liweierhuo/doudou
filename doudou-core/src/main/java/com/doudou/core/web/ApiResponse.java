package com.doudou.core.web;

import lombok.Data;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-10-13
 */
@Data
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private PageResult<T> page;

    private ApiResponse() {
        this.code = 0;
        this.message = "OK";
    }

    private ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(PageResult<T> page) {
        this.page = page;
    }

    public PageResult<T> getPage() {
        return page;
    }

    public void setPage(PageResult<T> page) {
        this.page = page;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>();
    }

    public static <T> ApiResponse<T> success(PageResult<T> page) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setPage(page);
        return apiResponse;
    }


    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(500, "系统繁忙");
    }

}
