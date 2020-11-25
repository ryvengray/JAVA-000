CREATE TABLE `user`
(
    `id`          bigint(20) unsigned NOT NULL COMMENT '主键id',
    `name`        varchar(32)         NOT NULL COMMENT '姓名',
    `phone`       varchar(32)         NOT NULL COMMENT '手机号',
    `avatar_url`  varchar(128)                 DEFAULT '' COMMENT '头像照片url',
    `email`       varchar(64)                  DEFAULT NULL COMMENT '邮箱',
    `password`    varchar(128)                 DEFAULT NULL COMMENT '登录密码',
    `salt`        varchar(255)                 DEFAULT NULL COMMENT '盐',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    `update_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_phone` (`phone`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = COMPACT COMMENT ='用户信息表';

CREATE TABLE `product`
(
    `id`          bigint(20) unsigned NOT NULL COMMENT '主键id',
    `category_id` bigint(20)          NOT NULL COMMENT '分类id,对应category表的主键',
    `name`        varchar(100)        NOT NULL COMMENT '商品名称',
    `subtitle`    varchar(200)                 DEFAULT NULL COMMENT '商品副标题',
    `main_image`  varchar(500)                 DEFAULT NULL COMMENT '产品主图url',
    `detail`      text COMMENT '商品详情',
    `price`       decimal(20, 2)      NOT NULL COMMENT '价格,单位-元保留两位小数',
    `stock`       int(11)             NOT NULL COMMENT '库存数量',
    `status`      int(11)                      DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    `update_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = COMPACT COMMENT ='商品信息表';

CREATE TABLE `category`
(
    `id`          bigint(20) unsigned NOT NULL COMMENT '主键id',
    `parent_id`   bigint(20) unsigned          DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
    `name`        varchar(50)                  DEFAULT NULL COMMENT '分类名称',
    `status`      tinyint(4)                   DEFAULT '1' COMMENT '分类状态 1-正常,2-已废弃',
    `sort_order`  int(11)                      DEFAULT NULL COMMENT '排序编号',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    `update_time` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = COMPACT COMMENT ='商品分类表';


CREATE TABLE `order`
(
    `id`           bigint(20) unsigned NOT NULL COMMENT '主键id',
    `order_no`     bigint(20)          NOT NULL COMMENT '订单号',
    `user_id`      bigint(20)          NOT NULL COMMENT '用户id',
    `payment`      decimal(20, 2)               DEFAULT NULL COMMENT '付款金额',
    `payment_type` int(4)                       DEFAULT NULL COMMENT '支付类型,0-AliPay,1-WX',
    `postage`      int(11)                      DEFAULT NULL COMMENT '运费',
    `status`       int(11)                      DEFAULT NULL COMMENT '订单状态:0-已取消，10-未付款，20-已付款，30-已发货，40-交易成功，50-交易关闭',
    `payment_time` datetime                     DEFAULT NULL COMMENT '支付时间',
    `send_time`    datetime                     DEFAULT NULL COMMENT '发货时间',
    `end_time`     datetime                     DEFAULT NULL COMMENT '交易完成时间',
    `close_time`   datetime                     DEFAULT NULL COMMENT '交易关闭时间',
    `create_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    `update_time`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = COMPACT COMMENT ='订单表';

CREATE TABLE `order_item`
(
    `id`                 bigint(20) unsigned NOT NULL COMMENT '主键id',
    `order_no`           bigint(20)          NOT NULL COMMENT '订单号',
    `user_id`            bigint(20)          NOT NULL COMMENT '用户id',
    `product_id`         bigint(20)                   DEFAULT NULL COMMENT '商品id',
    `product_name`       varchar(100)                 DEFAULT NULL COMMENT '商品名称',
    `product_image`      varchar(500)                 DEFAULT NULL COMMENT '商品图片地址',
    `current_unit_price` decimal(20, 2)               DEFAULT NULL COMMENT '生成订单时的商品单价',
    `quantity`           int(11)                      DEFAULT NULL COMMENT '商品数量',
    `total_price`        decimal(20, 2)               DEFAULT NULL COMMENT '商品总价',
    `create_time`        datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    `update_time`        timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (`id`),
    KEY `order_no_index` (`order_no`) USING BTREE,
    KEY `order_no_user_id_index` (`user_id`, `order_no`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = COMPACT COMMENT ='订单详情表';