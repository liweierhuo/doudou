drop database if exists doudou;
create database doudou;
use doudou;
drop table if exists uc_user;
create table uc_user
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `username`   varchar(64)         DEFAULT NULL,
    `icon`       varchar(500)        DEFAULT NULL COMMENT '头像',
    `email`      varchar(100)        DEFAULT NULL COMMENT '邮箱',
    `nick_name`  varchar(200)        DEFAULT NULL COMMENT '昵称',
    `remark`     varchar(500)        DEFAULT NULL COMMENT '备注信息',
    `login_time` datetime            DEFAULT NULL COMMENT '最后登录时间',
    `created`    timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`       tinyint(4) NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `tc_order`;
CREATE TABLE `tc_order`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `user_id`            bigint(20)   NOT NULL,
    `order_no`           varchar(64)           DEFAULT NULL COMMENT '订单编号',
    `total_amount`       decimal(10, 2)        DEFAULT NULL COMMENT '订单总金额',
    `pay_amount`         decimal(10, 2)        DEFAULT NULL COMMENT '应付金额（实际支付金额）',
    `pay_type`           int(1)                DEFAULT NULL COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
    `order_source`       int(1)                DEFAULT NULL COMMENT '订单来源：0->PC订单；1->app订单',
    `status`             int(1)                DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    `order_type`         int(1)                DEFAULT NULL COMMENT '订单类型：0->正常订单；1->秒杀订单',
    `receiver_name`      varchar(100) NOT NULL COMMENT '收货人姓名',
    `receiver_phone`     varchar(32)  NOT NULL COMMENT '收货人电话',
    `receiver_post_code` varchar(32)           DEFAULT NULL COMMENT '收货人邮编',
    `receiver_address`   varchar(200)          DEFAULT NULL COMMENT '省份|城市|区|详细地址',
    `remark`             varchar(500)          DEFAULT NULL COMMENT '订单备注',
    `payment_time`       datetime              DEFAULT NULL COMMENT '支付时间',
    `created`            timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`               tinyint(4)   NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 27
  DEFAULT CHARSET = utf8 COMMENT ='订单表';

DROP TABLE IF EXISTS `tc_order_item`;
CREATE TABLE `tc_order_item`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `order_no`           bigint(20)          DEFAULT NULL COMMENT '订单id',
    `product_name`       varchar(200)        DEFAULT NULL COMMENT '商品名称',
    `product_sku_code`   varchar(50)         DEFAULT NULL COMMENT '商品sku条码',
    `product_price`      decimal(10, 2)      DEFAULT NULL COMMENT '单价',
    `product_quantity`   int(11)             DEFAULT NULL COMMENT '购买数量',
    `product_paid_price` decimal(10, 2)      DEFAULT NULL COMMENT '实付价格',
    `created`            timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified`           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag`               tinyint(4) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  DEFAULT CHARSET = utf8 COMMENT ='订单明细表';


-- ----------------------------
-- Table structure for ta_product
-- ----------------------------
DROP TABLE IF EXISTS `ta_product`;
CREATE TABLE `ta_product`
(
    `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `product_name`        varchar(128)        NOT NULL DEFAULT '' COMMENT '商品名称',
    `product_title`       varchar(256)        NOT NULL DEFAULT '' COMMENT '商品标题',
    `main_image`          varchar(256)        NOT NULL DEFAULT '' COMMENT '商品主图',
    `price`               int(11)             NOT NULL DEFAULT '0' COMMENT '商品价格，单位分',
    `status`              tinyint(4)          NOT NULL DEFAULT '0' COMMENT '商品上架状态，0初始化，1上架',
    `product_description` text COMMENT '商品详情描述',
    `product_type`        tinyint(4)          NOT NULL DEFAULT '0' COMMENT '商品类型，1普通品，2特供品，3加工品',
    `store_type`          tinyint(4)          NOT NULL DEFAULT '0' COMMENT '储藏方式，1常温，2冷冻，3冷藏',
    `product_sku_code`    varchar(128)        NOT NULL DEFAULT '0' COMMENT '商品条形码',
    `total_stock`         int(11)             NOT NULL DEFAULT '0' COMMENT '商品总库存',
    `category_id`         bigint(20)          NOT NULL DEFAULT '0' COMMENT '类目id',
    `created`             timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified`            timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `flag`                tinyint(4)          NOT NULL DEFAULT '0' COMMENT '删除标记，1表示删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品表';

DROP TABLE IF EXISTS `dd_user`;
CREATE TABLE `dd_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT '0' COMMENT '删除标记(0：否 1：是)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `logo` varchar(64) DEFAULT NULL COMMENT '用户图像',
  `user_type` tinyint(2) DEFAULT NULL COMMENT '用户类型',
  `user_total_integral` int(11) DEFAULT '0' COMMENT '用户总积分'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dd_third_user`;
CREATE TABLE `dd_third_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_data` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT NULL COMMENT '删除标记(0：否 1：是)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `open_id` varchar(64) DEFAULT NULL COMMENT '第三方标识',
  `union_id` varchar(64) DEFAULT NULL COMMENT '第三方唯一标识',
  `type` tinyint(2) DEFAULT NULL COMMENT '第三方类型(1:微信小程序)',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方用户表';

DROP TABLE IF EXISTS `dd_resources`;
CREATE TABLE `dd_resources` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_data` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT NULL COMMENT '删除标记(0：否 1：是)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `res_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `res_auth` varchar(255) DEFAULT NULL COMMENT '作者',
  `res_publish` varchar(32) DEFAULT NULL COMMENT '发布者',
  `res_upload_address` varchar(255) DEFAULT NULL COMMENT '下载地址',
  `res_upload_num` int(11) DEFAULT NULL COMMENT '下载次数',
  `res_convert_integralinteger` int(11) DEFAULT NULL COMMENT '兑换积分',
  `res_summary` varchar(255) DEFAULT NULL COMMENT '简介',
  `res_type` decimal(2,0) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据资源表';

DROP TABLE IF EXISTS `dd_user_integral`;
CREATE TABLE `dd_user_integral` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_data` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT NULL COMMENT '删除标记(0：否 1：是)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `type` tinyint(2) DEFAULT NULL COMMENT '积分类型(1:签到积分  2：投稿积分)',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `user_integral` int(11) DEFAULT NULL COMMENT '用户积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分表';

DROP TABLE IF EXISTS `dd_user_resource`;
CREATE TABLE `dd_user_resource` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_data` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` decimal(2,0) DEFAULT NULL COMMENT '删除标记',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `res_id` varchar(32) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户兑换资源表';

DROP TABLE IF EXISTS `dd_user_sign`;
CREATE TABLE `dd_user_sign` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_data` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(2) DEFAULT NULL COMMENT '删除标记(0：否 1：是)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `sign_type` tinyint(2) DEFAULT NULL COMMENT '签到状态',
  `sign_date` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户签到表';