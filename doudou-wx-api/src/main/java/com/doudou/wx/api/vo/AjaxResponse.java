package com.doudou.wx.api.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;

/**
 * @ClassName AjaxResponse
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 17:41
 **/
public class AjaxResponse implements Serializable {

    private static final long serialVersionUID = -9039228515094842639L;

    private static final String SUCCESS_DESCRIPTION = "success";

    public static final Integer SUCCESS_CODE = 200;

    public static final Integer ERROR_CODE = 99999;

    public static final String ERROR_DESCRIPTION = "error";

    /** ajax 交互结果 */
    private boolean success;

    /** 交互描述信息 */
    private String description;

    /** 错误编码 */
    private Integer errorCode;

    /** 交互数据 */
    private Object data;

    /** 分页模型 */
    private Page page;

    private String sessionToken;

    private String sessionKey;

    public AjaxResponse() {
        this(false);
    }

    public AjaxResponse(boolean success) {
        this(success, SUCCESS_DESCRIPTION, SUCCESS_CODE);
    }

    public AjaxResponse(boolean success, String description) {
        this(success, description, SUCCESS_CODE, null, null);
    }

    public AjaxResponse(boolean success, String description, Integer errorCode) {
        this.success = success;
        this.description = description;
        this.errorCode = errorCode;
    }

    public AjaxResponse(boolean success, String description, Integer errorCode, Object data, Page page) {
        this.success = success;
        this.description = description;
        this.errorCode = errorCode;
        this.data = data;
        this.page = page;
    }

    public static AjaxResponse error(){
        return new AjaxResponse(false, ERROR_DESCRIPTION, ERROR_CODE);
    }
    public static AjaxResponse error(Integer errorCode, String description) {
        return new AjaxResponse(false, description, errorCode);
    }
    public static AjaxResponse error(Object data, Integer errorCode, String description) {
        return new AjaxResponse(false, description, errorCode, data, null);
    }
    public static AjaxResponse success() {
        return new AjaxResponse(true, SUCCESS_DESCRIPTION, SUCCESS_CODE);
    }

    public static AjaxResponse success(String description) {
        return new AjaxResponse(true, description);
    }

    public static AjaxResponse success(Object data) {
        return new AjaxResponse(true, SUCCESS_DESCRIPTION, SUCCESS_CODE, data, null);
    }

    public static AjaxResponse success(Object data, Page page) {
        return new AjaxResponse(true, SUCCESS_DESCRIPTION, SUCCESS_CODE, data, page);
    }

    public static AjaxResponse success(String description, Object data) {
        return new AjaxResponse(true, description, null, data, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}
