package com.doudou.core.constant;

/**
 * @ClassName Constant
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 19:28
 **/
public class Constant {

    //存放鉴权信息的Header名称，默认是Authorization
    public static final String HTTP_HEADER_NAME = "Authorization";

    //审核通过
    public static final Integer CHECK_STATUS_PASS = 1;
    //审核拒绝
    public static final Integer CHECK_STATUS_REJECT = 0;
    //未审核
    public static final Integer CHECK_STATUS_AWAIT = 2;
    //兑换资源类型
    public static final Integer RES_TYPE_CONVERT = 1;
    //付费资源类型
    public static final Integer RES_TYPE_PAY = 2;

}
