package com.doudou.dao.entity.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.doudou.dao.entity.BaseModelDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author shenLiuHai
 * @since 2019-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DdUser extends BaseModelDo {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户图像
     */
    private String logo;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户总积分
     */
    private Integer userTotalIntegral;

    /**
     * 性别 0：未知、1：男、2：女
     */
    private Integer gender;

    /**
     * 用户头像图片
     */
    @TableField("avatarUrl")
    private String avatarUrl;

    /**
     * 用户所在省份
     */
    private String province;

    /**
     * 用户所在国家
     */
    private String country;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 显示 country，province，city 所用的语言（en 英文 zh_CN 简体中文 zh_TW 繁体中文）
     */
    private String language;

    /**
     * 用户唯一标识
     */
    private String openId;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
     */
    private String unionId;

    /**
     * 用户手机号
     */
    private Integer mobile;

    /**
     * 最新资源推送设置
     */
    private Boolean resMesSet;

    /**
     * 资源审核通知消息设置
     */
    private Boolean resCheckSet;


}
