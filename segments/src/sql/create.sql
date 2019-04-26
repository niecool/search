CREATE TABLE `keyword_dict` (
                              `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '流水号',
                              `keyword` varchar(100)  DEFAULT '' COMMENT '词组',
                              `type` int(2) DEFAULT '99' COMMENT '词性',
                              `synonym` varchar(500) DEFAULT NULL COMMENT '同义词',
                              `extented` varchar(2000) DEFAULT '' COMMENT '扩展词',
                              `last_update_id` decimal(20,0) DEFAULT NULL COMMENT '最后修改人ID',
                              `last_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                              `status` integer(1) DEFAULT '0' COMMENT '状态，1:已作废,0:正常',
                              `data_from` char(1) DEFAULT NULL COMMENT '数据来源，1:淘宝导入，2:一号店导入，3:用户录入，4.用户导入,5.初始导入',
                              `last_update_name` varchar(100)  DEFAULT '' COMMENT '最后修改人',
                              `is_check` integer(1)  DEFAULT '0' COMMENT '0:未检查 1：已检查',
                              `relatived` varchar(500) DEFAULT '' COMMENT '父子词',
                              `incompatible` varchar(500) DEFAULT '' COMMENT '互斥词',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4
