delimiter $$

CREATE TABLE `adreess` (
  `id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `add_one` varchar(45) DEFAULT NULL COMMENT '地址1',
  `add_two` varchar(45) DEFAULT NULL COMMENT '地址2',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表'$$

delimiter $$

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(45) DEFAULT NULL COMMENT '用户名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `address_one` varchar(45) DEFAULT NULL COMMENT '地址',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `index_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户表'$$

