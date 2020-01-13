drop database if exists doudou;
create database doudou;
use doudou;
#用户表
DROP TABLE IF EXISTS `dd_user`;
CREATE TABLE `dd_user`
(
    `id`                  varchar(32) NOT NULL COMMENT '主键',
    `create_date`         date         DEFAULT NULL COMMENT '创建时间',
    `update_date`         date         DEFAULT NULL COMMENT '修改时间',
    `del_flag`            tinyint(1)   DEFAULT '0' COMMENT '删除标记(0：否 1：是)',
    `remark`              varchar(255) DEFAULT NULL COMMENT '备注',
    `username`            varchar(64)  DEFAULT NULL COMMENT '用户名',
    `password`            varchar(64)  DEFAULT NULL COMMENT '密码',
    `nick_name`           varchar(64)  DEFAULT NULL COMMENT '用户昵称',
    `logo`                varchar(64)  DEFAULT NULL COMMENT '用户图像',
    `user_type`           tinyint(2)   DEFAULT NULL COMMENT '用户类型',
    `user_total_integral` int(11)      DEFAULT '0' COMMENT '用户总积分'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


#第三方登录表
DROP TABLE IF EXISTS `dd_third_user`;
CREATE TABLE `dd_third_user`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `create_date` date         DEFAULT NULL COMMENT '创建时间',
    `update_date` date         DEFAULT NULL COMMENT '修改时间',
    `del_flag`    tinyint(1)   DEFAULT '0' COMMENT '删除标记(0：否 1：是)',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    `open_id`     varchar(64)  DEFAULT NULL COMMENT '第三方标识',
    `union_id`    varchar(64)  DEFAULT NULL COMMENT '第三方唯一标识',
    `type`        tinyint(2)   DEFAULT NULL COMMENT '第三方类型(1:微信小程序)',
    `nickname`    varchar(64)  DEFAULT NULL COMMENT '昵称',
    `user_id`     varchar(32)  DEFAULT NULL COMMENT '用户主键',
    PRIMARY KEY (`id`),
    KEY `index_open_id` (`open_id`),
    KEY `index_union_id` (`union_id`),
    KEY `index_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='第三方用户表';


#资源表
DROP TABLE IF EXISTS `dd_resources`;
CREATE TABLE `dd_resources`
(
    `id`                   varchar(32) NOT NULL COMMENT '主键',
    `user_id`              int         not null COMMENT '用户id',
    `create_date`          date          DEFAULT NULL COMMENT '创建时间',
    `update_date`          date          DEFAULT NULL COMMENT '修改时间',
    `del_flag`             tinyint(1)    DEFAULT '0' COMMENT '删除标记(0：否 1：是)',
    `remark`               varchar(255)  DEFAULT NULL COMMENT '备注',
    `res_title`            varchar(255)  DEFAULT NULL COMMENT '标题',
    `res_auth`             varchar(255)  DEFAULT NULL COMMENT '作者',
    `res_publish`          varchar(255)  DEFAULT NULL COMMENT '发布者',
    `res_upload_address`   varchar(255)  DEFAULT NULL COMMENT '下载地址',
    `res_upload_num`       int(11)       DEFAULT NULL COMMENT '下载次数',
    `res_convert_integral` int(11)       DEFAULT NULL COMMENT '兑换积分',
    `res_summary`          varchar(255)  DEFAULT NULL COMMENT '简介',
    `res_type`             decimal(2, 0) DEFAULT NULL COMMENT '类型',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='数据资源表';


#用户积分表
DROP TABLE IF EXISTS `dd_user_integral`;
CREATE TABLE `dd_user_integral`
(
    `id`            varchar(32) NOT NULL COMMENT '主键',
    `create_date`   date         DEFAULT NULL COMMENT '创建时间',
    `update_date`   date         DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1)   DEFAULT '0' COMMENT '删除标记(0：否 1：是)',
    `remark`        varchar(255) DEFAULT NULL COMMENT '备注',
    `type`          tinyint(2)   DEFAULT NULL COMMENT '积分类型(1:签到积分  2：投稿积分)',
    `user_id`       varchar(32)  DEFAULT NULL COMMENT '用户ID',
    `user_integral` int(11)      DEFAULT '0' COMMENT '用户积分',
    PRIMARY KEY (`id`),
    KEY `index_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户积分表';


#用户兑换积分表
DROP TABLE IF EXISTS `dd_user_resource`;
CREATE TABLE `dd_user_resource`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `create_date` date         DEFAULT NULL COMMENT '创建时间',
    `update_date` date         DEFAULT NULL COMMENT '修改时间',
    `del_flag`    tinyint(1)   DEFAULT '0' COMMENT '删除标记',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    `user_id`     varchar(32)  DEFAULT NULL COMMENT '用户ID',
    `res_id`      varchar(32)  DEFAULT NULL COMMENT '资源ID',
    PRIMARY KEY (`id`),
    KEY `index_user_id` (`user_id`),
    KEY `index_res_id` (`res_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户兑换资源表';

#用户签到表
DROP TABLE IF EXISTS `dd_user_sign`;
CREATE TABLE `dd_user_sign`
(
    `id`          varchar(32) NOT NULL COMMENT '主键',
    `create_date` date         DEFAULT NULL COMMENT '创建时间',
    `update_date` date         DEFAULT NULL COMMENT '修改时间',
    `del_flag`    tinyint(1)   DEFAULT '0' COMMENT '删除标记(0：否 1：是)',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    `user_id`     varchar(32)  DEFAULT NULL COMMENT '用户id',
    `sign_type`   tinyint(2)   DEFAULT NULL COMMENT '签到状态',
    `sign_date`   datetime     DEFAULT NULL COMMENT '签到时间',
    PRIMARY KEY (`id`),
    KEY `index_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户签到表';
