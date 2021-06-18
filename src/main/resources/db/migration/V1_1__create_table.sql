CREATE TABLE `link`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '连接详情id',
  `source_node_id` int NULL COMMENT '源节点id',
  `target_node_id` int NULL COMMENT '目的节点id',
  `link_name` varchar(255) NULL COMMENT '连接名称',
  `bandwidth` double NULL COMMENT '连接带宽',
  `delay` float NULL COMMENT '连接时延',
  `lose_package` float NULL COMMENT '丢包率',
  `sn` float NULL COMMENT '信噪比',
  `link_type` varchar(255) NULL COMMENT '连接类型（有线，无线）',
  `spectrum_utilization` float NULL COMMENT '频段占用率（可选）',
  `channel_sum` int NOT NULL COMMENT '信道电路数（可选）',
  `protocol_level` varchar(255) NULL COMMENT '连接逻辑类型(协议层次)',
  `protocol_type` varchar(255) NULL COMMENT '连接服务类型（可选, 协议类型）',
  `init_weight` float NULL COMMENT '连接初始权重',
  `create_time` datetime NULL COMMENT '连接创建时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `network`  (
  `id` int NOT NULL COMMENT '全局网络id',
  `sub_network_id` int NULL COMMENT '子网id',
  PRIMARY KEY (`id`)
);

CREATE TABLE `node`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '节点详细信息id索引',
  `node_ip` varchar(255) NULL COMMENT '节点IP地址(可选)',
  `node_mac` varchar(255) NULL COMMENT '节点MAC（可选）',
  `node_vlan` varchar(255) NULL COMMENT '节点vlan(可选)',
  `node_name` varchar(255) NULL COMMENT '节点名称',
  `physical_type` int NULL COMMENT '节点物理类型',
  `logical_type` int NULL COMMENT '节点逻辑类型',
  `spectrum_floor` double NULL DEFAULT NULL COMMENT '频谱下限',
  `spectrum_top` double NULL DEFAULT NULL COMMENT '频谱上限',
  `spectrum_availability` float NULL DEFAULT NULL COMMENT '频谱可利用率',
  `throughput` int NULL DEFAULT NULL COMMENT '吞吐量',
  `compute_performance` int NULL COMMENT '节点计算性能',
  `hardware_type` int NULL DEFAULT NULL COMMENT '硬件类型',
  `service_sum` int NULL COMMENT '节点服务总数',
  `vulnerability_sum` int NULL COMMENT '节点漏洞总数',
  `controllable_level` int NULL COMMENT '节点可控制级别',
  PRIMARY KEY (`id`)
);

CREATE TABLE `node-service`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '节点和服务关系id',
  `node_id` int NULL COMMENT '节点id',
  `service_id` int NULL COMMENT '服务id',
  `create_time` timestamp NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '关系创建时间',
  `service_controllable` varchar(255) NULL COMMENT '服务可运用能力（可破坏，可利用，可控制）',
  PRIMARY KEY (`id`)
);

CREATE TABLE `service`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '服务索引id',
  `service_name` varchar(255) NULL COMMENT '服务名称',
  `service_port` int NULL COMMENT '服务端口',
  `service_version` varchar(255) NULL COMMENT '服务版本',
  `service_vulnerability_sum` int NULL COMMENT '服务漏洞数量',
  `service_safety_level` varchar(255) NULL COMMENT '服务对应的安全等级（可选）',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sub_network`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '网络id',
  `sub_network_name` varchar(255) NULL COMMENT '网络名称',
  `physical_type` varchar(255) NULL COMMENT '网络物理类型(有线，无线)',
  `logical_type` varchar(255) NULL COMMENT '网络逻辑类型（星型、总线型等）',
  `node_sum` int NULL COMMENT '节点数量',
  `link_sum` int NULL COMMENT '边数量',
  `node_connectivity` int NULL COMMENT '节点连通度',
  `link_connectivity` int NULL COMMENT '边连通度',
  `reliability` varchar(255) NULL COMMENT '网络可靠度',
  `vulnerability_sum` int NULL COMMENT '网络脆弱点总数',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sub_network-link`  (
  `id` int NOT NULL COMMENT '网络-边关系索引id\r\n',
  `sub_network_id` int NULL COMMENT '网络id',
  `link_id` int NULL COMMENT '边id',
  `create_time` timestamp NULL COMMENT '关系创建时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sub_network-node`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '网络节点关系索引id',
  `sub_network_id` int NULL COMMENT '网络id',
  `node_id` int NOT NULL COMMENT '节点id',
  `create_time` timestamp NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '关系创建时间',
  PRIMARY KEY (`id`)
);

ALTER TABLE `network` ADD CONSTRAINT `fk_network_network_2` FOREIGN KEY (`sub_network_id`) REFERENCES `sub_network` (`id`);
ALTER TABLE `node-service` ADD CONSTRAINT `fk_node-service_node-service_1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`);
ALTER TABLE `node-service` ADD CONSTRAINT `fk_node-service_node-service_2` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`);
ALTER TABLE `sub_network-link` ADD CONSTRAINT `fk_network-link_network-link_2` FOREIGN KEY (`link_id`) REFERENCES `link` (`id`);
ALTER TABLE `sub_network-link` ADD FOREIGN KEY (`sub_network_id`) REFERENCES `sub_network` (`id`);
ALTER TABLE `sub_network-node` ADD CONSTRAINT `fk_network-node_network-node_1` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`);
ALTER TABLE `sub_network-node` ADD CONSTRAINT `fk_network-node_network-node_2` FOREIGN KEY (`sub_network_id`) REFERENCES `sub_network` (`id`);
