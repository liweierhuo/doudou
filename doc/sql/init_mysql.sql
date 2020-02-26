create database doudou_new;
use doudou_new;

drop table if exists uc_user;
create table uc_user
(
    `id`                 bigint(32)              NOT NULL AUTO_INCREMENT,
    username             varchar(64)             null,
    icon                 varchar(512)            null comment '头像',
    email                varchar(64)             null comment '邮箱',
    phone_no             varchar(16)             null comment '手机号',
    client_id            varchar(64)             null comment '用户唯一编号',
    open_id              varchar(64)             null comment '微信openId',
    union_id             varchar(64)             null comment '微信unionId',
    nick_name            varchar(128)            null comment '昵称',
    remark               varchar(512)            null comment '备注信息',
    gender               int          default 0  null comment '性别',
    country              varchar(128) default '' null comment '国家',
    province             varchar(128) default '' null comment '省份',
    city                 varchar(128) default '' null comment '城市',
    signature            varchar(512) default '' null comment '用户签名',
    background_image_url varchar(512) default '' null comment '用户主页背景图',
    login_time           datetime                null comment '最后登录时间',
    `created`            timestamp               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`           timestamp               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`               tinyint(4)              NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
);

drop table if exists dd_resource;
create table dd_resource
(
    `id`          bigint(32)                NOT NULL AUTO_INCREMENT,
    resource_id   varchar(64)  default ''   not null comment '资源唯一标示',
    client_id     varchar(64)  default ''   not null comment '上传者唯一标示',
    title         varchar(256) default ''   not null comment '资源标题',
    subtitle      varchar(256) default ''   not null comment '资源副标题',
    source        varchar(256) default ''   not null comment '资源来源',
    price         int(16)      default 0    not null comment '资源价格',
    url           varchar(512) default ''   not null comment '资源链接地址',
    image_url     varchar(512) default ''   not null comment '资源宣传图片链接地址',
    remark        varchar(512)              null comment '备注信息',
    status        varchar(64)               null comment '状态，待审核，审核中，正常，审核不通过，下架',
    res_summary   text                      null comment '简介',
    res_type      varchar(64)               null comment '类型',
    download_num  int          default 0    null comment '下载次数',
    view_num      int          default 0    null comment '浏览次数',
    total_num     int          default 0    null comment '资源总数',
    remaining_num int          default 0    null comment '剩余数量',
    sort_num      int          default 1000 null comment '排序号，越大越靠前',
    sticky        tinyint(4)   default 0    null comment '是否置顶，1：置顶；0：不置顶',
    `created`     timestamp                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`    timestamp                 NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`        tinyint(4)                NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
);

#用户积分表
DROP TABLE IF EXISTS `user_integral`;
CREATE TABLE `user_integral`
(
    `id`            int(11)    NOT NULL auto_increment COMMENT '主键',
    `remark`        varchar(255)        DEFAULT NULL COMMENT '备注',
    `client_id`     varchar(32)         DEFAULT NULL COMMENT '用户ID',
    `user_integral` int(11)             DEFAULT '0' COMMENT '用户积分',
    `created`       timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`      timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`          tinyint(4) NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    KEY `index_user_id` (`client_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户积分表';

#用户积分明细表
DROP TABLE IF EXISTS `user_integral_detail`;
CREATE TABLE `user_integral_detail`
(
    `id`               int(11)    NOT NULL auto_increment COMMENT '主键',
    `remark`           varchar(255)        DEFAULT NULL COMMENT '备注',
    `client_id`        varchar(32)         DEFAULT NULL COMMENT '用户ID',
    `user_integral_id` int(11)             DEFAULT NULL COMMENT '用户积分表Id',
    `type`             varchar(64)         DEFAULT NULL COMMENT '类型，1：签到；2：资源上传；3：资源被下载；4：充值',
    `user_integral`    int(11)             DEFAULT '0' COMMENT '用户积分',
    `created`          timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`         timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`             tinyint(4) NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    KEY `index_user_id` (`client_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户积分明细表';


#订单表
DROP TABLE IF EXISTS `dd_order`;
CREATE TABLE `dd_order`
(
    `id`          int(11)     NOT NULL auto_increment COMMENT '主键',
    `order_id`    varchar(128)         DEFAULT NULL COMMENT '订单编号',
    `remark`      varchar(255)         DEFAULT NULL COMMENT '备注',
    `client_id`   varchar(32)          DEFAULT NULL COMMENT '用户ID',
    `resource_id` varchar(32)          DEFAULT NULL COMMENT '资源ID',
    `status`      varchar(64) not null default '' comment '状态，1：成功；2：失败',
    `price`       int(16)     not null default 0 comment '资源价格',
    `created`     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`        tinyint(4)  NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    unique key (`order_id`),
    KEY `index_user_id` (`client_id`),
    KEY `index_res_id` (`resource_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='订单表';

#积分操作记录表
DROP TABLE IF EXISTS `integral_record`;
CREATE TABLE `integral_record`
(
    `id`        int(11)    NOT NULL auto_increment COMMENT '主键',
    `remark`    varchar(255)        DEFAULT NULL COMMENT '备注',
    `client_id` varchar(32)         DEFAULT NULL COMMENT '用户ID',
    `type`      varchar(32)         DEFAULT NULL COMMENT '操作类型，1：签到；2：资源上传；3：资源被下载；4：充值；5：下载资源；',
    `integral`  int(11)             DEFAULT '0' COMMENT '积分',
    `created`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`  timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`      tinyint(4) NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    KEY `index_user_id` (`client_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='积分操作记录表';


#广告表
DROP TABLE IF EXISTS `dd_ad`;
CREATE TABLE `dd_ad`
(
    `id`          int(11)      NOT NULL auto_increment COMMENT '主键',
    `ad_id`       varchar(128) not null default '' comment '广告唯一标示',
    `title`       varchar(128) not null default '' comment '广告标题',
    `description` varchar(512) not null default '' comment '广告描述',
    `url`         varchar(512) not null default '' comment '广告url',
    `image_url`   varchar(512) not null default '' comment '广告图片url',
    `status`      varchar(64)  not null default '' comment '状态，有效，失效',
    `remark`      varchar(255)          DEFAULT NULL COMMENT '备注',
    `created`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`        tinyint(4)   NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='广告表';

#用户签到表
DROP TABLE IF EXISTS `uc_user_sign_in`;
CREATE TABLE `uc_user_sign_in`
(
    `id`           int(11)      NOT NULL auto_increment COMMENT '主键',
    `tx_id`        varchar(128) not null default '' comment '签到表流水Id',
    `client_id`    varchar(64)  not null default '' comment '用户标示',
    `sign_in_date` timestamp    not null comment '签到日期',
    `remark`       varchar(255)          DEFAULT NULL COMMENT '备注',
    `created`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`         tinyint(4)   NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户签到表';

#用户留言表
DROP TABLE IF EXISTS `uc_user_message`;
CREATE TABLE `uc_user_message`
(
    `id`        int(11)      NOT NULL auto_increment COMMENT '主键',
    `tx_id`     varchar(128) not null default '' comment '流水Id',
    `client_id` varchar(64)  not null default '' comment '用户标示',
    `type`      varchar(128) not null default '' comment '留言类型，想要资源，建议，留言，其他',
    `title`     varchar(128) not null default '' comment '标题',
    `content`   varchar(512) not null default '' comment '留言内容',
    `remark`    varchar(255)          DEFAULT NULL COMMENT '备注',
    `created`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`      tinyint(4)   NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户签到表';

/**
  清库脚本
 */
truncate table dd_resource;
truncate table dd_order;