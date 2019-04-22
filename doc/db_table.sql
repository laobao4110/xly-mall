CREATE TABLE `mall_user_login_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `user_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `login_ip` varchar(40) CHARACTER SET ascii DEFAULT NULL COMMENT '登录IP',
  `login_mac_address` varchar(40) CHARACTER SET ascii DEFAULT NULL COMMENT '登录mac地址',
  `remark` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户登录日志表';

CREATE TABLE `mall_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型，1为总部人员',
  `user_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `real_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名,1为普通用户',
  `password` varchar(128) CHARACTER SET ascii DEFAULT NULL COMMENT '密码',
  `email` varchar(128) CHARACTER SET ascii DEFAULT NULL COMMENT 'email',
  `email_verify_code` varchar(8) CHARACTER SET ascii DEFAULT NULL COMMENT 'email验证码',
  `email_verify_time` datetime DEFAULT NULL COMMENT 'email验证时间',
  `phone` varchar(24) CHARACTER SET ascii DEFAULT NULL COMMENT '手机号',
  `phone_verify_code` varchar(8) CHARACTER SET ascii DEFAULT NULL COMMENT '手机号验证码',
  `phone_verify_time` datetime DEFAULT NULL COMMENT '手机验证时间',
  `is_activated` tinyint(4) NOT NULL COMMENT '是否激活，0不可用；1可用',
  `is_disabled` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否禁用，0启用；1禁用',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(40) CHARACTER SET ascii DEFAULT NULL COMMENT '最后登录IP',
  `remark` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `create_user` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '添加人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=500001 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='管理系统用户信息表';

