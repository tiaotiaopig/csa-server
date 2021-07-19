/*
 Navicat Premium Data Transfer

 Source Server         : csa
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.50.17:33061
 Source Schema         : csa

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 19/07/2021 13:12:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '连接详情id',
  `source_node_id` int unsigned DEFAULT NULL COMMENT '源节点id',
  `target_node_id` int unsigned DEFAULT NULL COMMENT '目的节点id',
  `link_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '连接名称',
  `bandwidth` float DEFAULT NULL COMMENT '连接带宽',
  `delay` float DEFAULT NULL COMMENT '连接时延',
  `lose_package` decimal(10,0) DEFAULT NULL COMMENT '丢包率',
  `sn` float DEFAULT NULL COMMENT '信噪比',
  `link_type` int DEFAULT NULL COMMENT '连接类型（有线，无线）',
  `spectrum_utilization` decimal(10,0) DEFAULT NULL COMMENT '频段占用率（可选）',
  `channel_sum` int NOT NULL COMMENT '信道电路数（可选）',
  `protocol_level` int DEFAULT NULL COMMENT '连接逻辑类型(协议层次)',
  `protocol_type` int DEFAULT NULL COMMENT '连接服务类型（可选, 协议类型）',
  `init_weight` decimal(10,0) DEFAULT NULL COMMENT '连接初始权重',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '连接创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '连接修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of link
-- ----------------------------
BEGIN;
INSERT INTO `link` VALUES (1, 2, 1, '链路1', 10240, 75, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 05:59:48', '2021-06-21 05:59:48');
INSERT INTO `link` VALUES (2, 4, 1, '链路2', 10240, 12, 0, 30, 0, 1, 1, 1, 2, 0, '2021-06-21 06:01:07', '2021-06-21 06:01:07');
INSERT INTO `link` VALUES (3, 6, 1, '链路3', 10240, 28, 0, 30, 0, 1, 5, 1, 2, 0, '2021-06-21 06:01:14', '2021-06-21 06:01:14');
INSERT INTO `link` VALUES (4, 6, 8, '链路4', 10240, 37, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:01:33', '2021-06-21 06:01:33');
INSERT INTO `link` VALUES (5, 8, 9, '链路4', 10240, 54, 0, 30, 0, 1, 7, 1, 2, 0, '2021-06-21 06:01:41', '2021-06-21 06:01:41');
INSERT INTO `link` VALUES (6, 1, 3, '链路5', 10240, 32, 0, 30, 0, 1, 6, 1, 2, 0, '2021-06-21 06:02:13', '2021-06-21 06:02:13');
INSERT INTO `link` VALUES (7, 9, 3, '链路6', 10240, 87, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:02:26', '2021-06-21 06:02:26');
INSERT INTO `link` VALUES (8, 3, 7, '链路8', 10240, 55, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:02:50', '2021-06-21 06:02:50');
INSERT INTO `link` VALUES (9, 3, 5, '链路9', 10240, 66, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:02:54', '2021-06-21 06:02:54');
INSERT INTO `link` VALUES (10, 5, 10, '链路10', 10240, 45, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:03:06', '2021-06-21 06:03:06');
INSERT INTO `link` VALUES (11, 10, 11, '链路11', 10240, 38, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:03:18', '2021-06-21 06:03:18');
INSERT INTO `link` VALUES (12, 11, 13, '链路12', 10240, 25, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:03:32', '2021-06-21 06:03:32');
INSERT INTO `link` VALUES (13, 11, 14, '链路13', 10240, 31, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:03:38', '2021-06-21 06:03:38');
INSERT INTO `link` VALUES (14, 13, 15, '链路14', 10240, 26, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:04:03', '2021-06-21 06:04:03');
INSERT INTO `link` VALUES (15, 13, 16, '链路15', 10240, 486, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:04:06', '2021-06-21 06:04:06');
INSERT INTO `link` VALUES (16, 13, 17, '链路16', 10240, 245, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:04:08', '2021-06-21 06:04:08');
INSERT INTO `link` VALUES (17, 15, 18, '链路17', 10240, 754, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:04:49', '2021-06-21 06:04:49');
INSERT INTO `link` VALUES (18, 15, 20, '链路18', 10240, 7554, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:04:52', '2021-06-21 06:04:52');
INSERT INTO `link` VALUES (19, 16, 19, '链路19', 10240, 751, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:05:10', '2021-06-21 06:05:10');
INSERT INTO `link` VALUES (20, 17, 18, '链路20', 10240, 56, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:05:31', '2021-06-21 06:05:31');
INSERT INTO `link` VALUES (21, 12, 18, '链路21', 10240, 7147, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:05:46', '2021-06-21 06:05:46');
INSERT INTO `link` VALUES (22, 12, 17, '链路22', 10240, 2544, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-21 06:05:50', '2021-06-21 06:05:50');
INSERT INTO `link` VALUES (23, 14, 16, '链路23', 10240, 75, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-22 01:53:23', '2021-06-22 01:53:23');
INSERT INTO `link` VALUES (24, 19, 20, '链路24', 10240, 75, 0, 30, 0, 1, 3, 1, 2, 0, '2021-06-22 01:55:10', '2021-06-22 01:55:10');
COMMIT;

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '节点详细信息id索引',
  `node_ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '节点IP地址(可选)',
  `node_mac` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '节点MAC（可选）',
  `node_vlan` int DEFAULT NULL COMMENT '节点vlan(可选)',
  `node_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '节点名称',
  `physical_type` int DEFAULT NULL COMMENT '节点物理类型',
  `logical_type` int DEFAULT NULL COMMENT '节点逻辑类型',
  `spectrum_floor` double DEFAULT NULL COMMENT '频谱下限',
  `spectrum_top` double DEFAULT NULL COMMENT '频谱上限',
  `spectrum_availability` decimal(10,0) DEFAULT NULL COMMENT '频谱可利用率',
  `throughput` int DEFAULT NULL COMMENT '吞吐量',
  `compute_performance` int DEFAULT NULL COMMENT '节点计算性能',
  `hardware_type` int DEFAULT NULL COMMENT '硬件类型',
  `service_sum` int DEFAULT NULL COMMENT '节点服务总数',
  `vulnerability_sum` int DEFAULT NULL COMMENT '节点漏洞总数',
  `controllable_level` int DEFAULT NULL COMMENT '节点可控制级别',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '节点创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '节点修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of node
-- ----------------------------
BEGIN;
INSERT INTO `node` VALUES (1, '192.192.192.75', '00-01-6C-06-A6-29', 4002, '节点测试4', 2, 3, 2568, 2754, 1, 75, 75, 2, 4, 7, 1, '2021-07-14 02:21:45', '2021-07-14 02:21:45');
INSERT INTO `node` VALUES (2, '192.192.192.82', '00-21-6C-03-A9-27', 3542, '节点2', 1, 2, 1005, 2513, 1, 82, 47, 2, 3, 4, 2, '2021-07-14 02:25:09', '2021-07-14 02:25:09');
INSERT INTO `node` VALUES (3, '192.192.192.43', '00-20-5A-03-C9-31', 1997, '节点3', 2, 1, 1875, 1921, 1, 46, 87, 2, 3, 2, 1, '2021-07-14 02:25:13', '2021-07-14 02:25:13');
INSERT INTO `node` VALUES (4, '192.192.192.44', '00-21-3C-03-C9-31', 1054, '节点4', 3, 1, 2787, 1545, 1, 46, 77, 1, 4, 2, 1, '2021-07-14 02:25:19', '2021-07-14 02:25:19');
INSERT INTO `node` VALUES (5, '192.192.192.33', '00-21-2A-01-C7-21', 1054, '节点5', 1, 2, 1754, 2845, 0, 33, 87, 2, 4, 9, 2, '2021-07-14 02:25:22', '2021-07-14 02:25:22');
INSERT INTO `node` VALUES (6, '192.192.192.26', '00-15-2A-01-C6-31', 2005, '节点6', 1, 3, 1985, 2767, 1, 75, 87, 1, 4, 3, 1, '2021-07-14 02:25:42', '2021-07-14 02:25:42');
INSERT INTO `node` VALUES (7, '192.192.192.30', '00-21-2A-11-B3-19', 1865, '节点7', 1, 2, 1755, 2007, 0, 30, 55, 2, 4, 1, 2, '2021-07-14 02:25:49', '2021-07-14 02:25:49');
INSERT INTO `node` VALUES (8, '192.192.192.88', '00-21-2A-52-B5-19', 1568, '节点8', 2, 1, 2008, 1268, 1, 88, 46, 1, 4, 2, 2, '2021-07-14 02:25:48', '2021-07-14 02:25:48');
INSERT INTO `node` VALUES (9, '192.192.192.71', '00-21-1A-50-B5-11', 1330, '节点9', 2, 3, 2075, 1879, 1, 71, 44, 2, 4, 3, 2, '2021-07-14 02:25:52', '2021-07-14 02:25:52');
INSERT INTO `node` VALUES (10, '192.192.192.11', '00-21-1A-50-B5-22', 1008, '节点10', 1, 3, 2977, 2544, 0, 11, 28, 2, 4, 1, 3, '2021-07-14 02:25:55', '2021-07-14 02:25:55');
INSERT INTO `node` VALUES (11, '186.186.186.71', '00-21-1A-50-B5-22', 2187, '节点11', 2, 3, 1756, 1009, 1, 71, 55, 3, 4, 4, 3, '2021-07-14 02:25:56', '2021-07-14 02:25:56');
INSERT INTO `node` VALUES (12, '186.186.186.22', '00-5D-1A-10-B5-22', 2087, '节点12', 1, 3, 2058, 1078, 0, 22, 84, 3, 4, 6, 2, '2021-07-14 02:25:58', '2021-07-14 02:25:58');
INSERT INTO `node` VALUES (13, '186.186.186.85', '00-51-1A-10-B5-22', 2087, '节点13', 1, 3, 2078, 2745, 1, 85, 84, 2, 4, 2, 2, '2021-07-14 02:25:59', '2021-07-14 02:25:59');
INSERT INTO `node` VALUES (14, '186.186.186.08', '00-21-1A-17-B5-28', 1857, '节点14', 2, 3, 1078, 2565, 1, 8, 12, 2, 4, 6, 1, '2021-07-14 02:26:01', '2021-07-14 02:26:01');
INSERT INTO `node` VALUES (15, '186.186.186.13', '00-21-1A-35-B5-28', 1857, '节点15', 2, 3, 2078, 1965, 1, 19, 75, 1, 4, 3, 1, '2021-07-14 02:26:02', '2021-07-14 02:26:02');
INSERT INTO `node` VALUES (16, '186.186.186.19', '00-21-1A-15-B5-28', 1857, '节点16', 2, 1, 1061, 2085, 1, 19, 41, 2, 4, 7, 2, '2021-07-14 02:26:04', '2021-07-14 02:26:04');
INSERT INTO `node` VALUES (17, '186.186.186.141', '12-21-1A-15-B5-28', 1857, '节点17', 3, 3, 2069, 2177, 1, 141, 67, 2, 4, 4, 2, '2021-07-14 02:26:06', '2021-07-14 02:26:06');
INSERT INTO `node` VALUES (18, '186.186.186.77', '00-19-3A-15-B5-21', 2697, '节点18', 3, 1, 1069, 2937, 0, 77, 23, 1, 4, 7, 3, '2021-07-14 02:26:07', '2021-07-14 02:26:07');
INSERT INTO `node` VALUES (19, '186.186.186.36', 'AB-19-3A-18-B5-21', 2087, '节点19', 2, 1, 2000, 1398, 1, 36, 75, 1, 2, 2, 2, '2021-07-14 02:26:09', '2021-07-14 02:26:09');
INSERT INTO `node` VALUES (20, '186.186.186.5', '00-19-3A-19-B5-01', 2011, '节点20', 3, 2, 2000, 1087, 1, 36, 69, 1, 2, 1, 1, '2021-07-14 02:26:14', '2021-07-14 02:26:14');
INSERT INTO `node` VALUES (29, '1', '2', 3, '4', 5, 6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-07-18 10:46:48', '2021-07-18 10:46:48');
COMMIT;

-- ----------------------------
-- Table structure for service_net
-- ----------------------------
DROP TABLE IF EXISTS `service_net`;
CREATE TABLE `service_net` (
   `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '服务索引id',
   `service_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务名称',
   `service_port` int DEFAULT NULL COMMENT '服务协议端口',
   `service_protocol` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务的传输协议',
   `service_version` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务版本',
   `service_state` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务状态（open,closed）',
   `service_vulnerability_sum` int DEFAULT NULL COMMENT '服务漏洞数量',
   `service_safety_level` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务对应的安全等级（可选）',
   `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '服务创建时间',
   `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '服务修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of service_net
-- ----------------------------
BEGIN;
INSERT INTO `service_net` VALUES (1, 'ssh', 22, 'tcp', 'OpenSSH 7.2p2 Ubuntu 4ubuntu2.10 (Ubuntu Linux; protocol 2.0)', 'open', 5, '正常', '2021-06-20 07:49:36', '2021-06-20 07:49:36');
INSERT INTO `service_net` VALUES (2, 'rpcbind', 111, 'tcp', '2-4 (RPC #100000)', 'open', 3, '风险', '2021-06-20 15:50:07', '2021-06-20 15:50:12');
INSERT INTO `service_net` VALUES (3, 'mysql', 3306, 'tcp', 'MySQL 8.0.22', 'open', 2, '风险', '2021-06-20 07:52:43', '2021-06-20 07:52:43');
INSERT INTO `service_net` VALUES (4, 'svnserve', 3690, 'tcp', 'Subversion', 'open', 5, '正常', '2021-06-20 08:00:09', '2021-06-20 08:00:09');
INSERT INTO `service_net` VALUES (5, 'http', 8888, 'tcp', 'Tornado httpd 6.0.4', 'open', 7, '危险', '2021-06-20 08:02:22', '2021-06-20 08:02:22');
INSERT INTO `service_net` VALUES (6, 'postgresql', 32770, 'tcp', 'PostgreSQL DB 9.6.0 or later', 'open', 3, '正常', '2021-06-20 08:05:13', '2021-06-20 08:05:13');
COMMIT;

-- ----------------------------
-- Table structure for sub_network
-- ----------------------------
DROP TABLE IF EXISTS `sub_network`;
CREATE TABLE `sub_network` (
   `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '网络id',
   `sub_network_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网络名称',
   `physical_type` int DEFAULT NULL COMMENT '网络物理类型(有线，无线)',
   `logical_type` int DEFAULT NULL COMMENT '网络逻辑类型（星型、总线型等）',
   `node_sum` int DEFAULT NULL COMMENT '节点数量',
   `link_sum` int DEFAULT NULL COMMENT '边数量',
   `node_connectivity` int DEFAULT NULL COMMENT '节点连通度',
   `link_connectivity` int DEFAULT NULL COMMENT '边连通度',
   `reliability` decimal(10,0) DEFAULT NULL COMMENT '网络可靠度',
   `vulnerability_sum` int DEFAULT NULL COMMENT '网络脆弱点总数',
   `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
   `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sub_network
-- ----------------------------
BEGIN;
INSERT INTO `sub_network` VALUES (1, '子网1', 0, 1, 10, NULL, NULL, NULL, NULL, NULL, '2021-06-21 13:38:53', '2021-06-21 13:38:58');
INSERT INTO `sub_network` VALUES (2, '子网2', 0, 1, 10, NULL, NULL, NULL, NULL, NULL, '2021-06-21 13:39:30', '2021-06-21 13:39:34');
COMMIT;

-- ----------------------------
-- Table structure for node_service
-- ----------------------------
DROP TABLE IF EXISTS `node_service`;
CREATE TABLE `node_service` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '节点和服务关系id',
  `node_id` int unsigned DEFAULT NULL COMMENT '节点id',
  `service_id` int unsigned DEFAULT NULL COMMENT '服务id',
  `service_controllable` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务可运用能力（可破坏，可利用，可控制）',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关系创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_node-service_node-service_1` (`service_id`),
  KEY `fk_node-service_node-service_2` (`node_id`),
  CONSTRAINT `fk_node-service_node-service_1` FOREIGN KEY (`service_id`) REFERENCES `service_net` (`id`),
  CONSTRAINT `fk_node-service_node-service_2` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of node_service
-- ----------------------------
BEGIN;
INSERT INTO `node_service` VALUES (1, 1, 3, '可利用', '2021-06-22 02:04:20');
INSERT INTO `node_service` VALUES (2, 1, 4, '可利用', '2021-06-22 02:04:20');
INSERT INTO `node_service` VALUES (3, 1, 2, '可利用', '2021-06-22 02:04:20');
INSERT INTO `node_service` VALUES (4, 2, 1, '可利用', '2021-06-22 02:05:27');
INSERT INTO `node_service` VALUES (5, 2, 3, '可利用', '2021-06-22 02:05:27');
INSERT INTO `node_service` VALUES (6, 2, 5, '可利用', '2021-06-22 02:05:27');
INSERT INTO `node_service` VALUES (7, 1, 6, '可利用', '2021-06-22 02:05:27');
INSERT INTO `node_service` VALUES (8, 3, 1, '可利用', '2021-06-22 02:05:54');
INSERT INTO `node_service` VALUES (9, 3, 2, '可利用', '2021-06-22 02:05:54');
INSERT INTO `node_service` VALUES (10, 3, 4, '可利用', '2021-06-22 02:05:54');
INSERT INTO `node_service` VALUES (12, 4, 1, '可利用', '2021-06-22 02:06:26');
INSERT INTO `node_service` VALUES (13, 4, 2, '可利用', '2021-06-22 02:06:26');
INSERT INTO `node_service` VALUES (14, 4, 4, '可利用', '2021-06-22 02:06:26');
INSERT INTO `node_service` VALUES (15, 4, 3, '可利用', '2021-06-22 02:06:26');
INSERT INTO `node_service` VALUES (16, 5, 1, '可利用', '2021-06-22 02:07:21');
INSERT INTO `node_service` VALUES (17, 5, 2, '可利用', '2021-06-22 02:07:21');
INSERT INTO `node_service` VALUES (18, 5, 4, '可利用', '2021-06-22 02:07:21');
INSERT INTO `node_service` VALUES (19, 5, 5, '可利用', '2021-06-22 02:07:21');
INSERT INTO `node_service` VALUES (20, 6, 1, '可利用', '2021-06-22 02:07:58');
INSERT INTO `node_service` VALUES (21, 6, 2, '可利用', '2021-06-22 02:07:58');
INSERT INTO `node_service` VALUES (22, 6, 4, '可利用', '2021-06-22 02:07:58');
INSERT INTO `node_service` VALUES (23, 6, 6, '可利用', '2021-06-22 02:07:58');
INSERT INTO `node_service` VALUES (24, 7, 1, '可利用', '2021-06-22 02:08:11');
INSERT INTO `node_service` VALUES (25, 7, 2, '可利用', '2021-06-22 02:08:11');
INSERT INTO `node_service` VALUES (26, 7, 3, '可利用', '2021-06-22 02:08:11');
INSERT INTO `node_service` VALUES (27, 7, 6, '可利用', '2021-06-22 02:08:11');
INSERT INTO `node_service` VALUES (28, 8, 1, '可利用', '2021-06-22 02:08:35');
INSERT INTO `node_service` VALUES (29, 8, 4, '可利用', '2021-06-22 02:08:35');
INSERT INTO `node_service` VALUES (30, 8, 3, '可利用', '2021-06-22 02:08:35');
INSERT INTO `node_service` VALUES (31, 8, 6, '可利用', '2021-06-22 02:08:35');
INSERT INTO `node_service` VALUES (32, 9, 1, '可利用', '2021-06-22 02:08:52');
INSERT INTO `node_service` VALUES (33, 9, 4, '可利用', '2021-06-22 02:08:52');
INSERT INTO `node_service` VALUES (34, 9, 5, '可利用', '2021-06-22 02:08:52');
INSERT INTO `node_service` VALUES (35, 9, 6, '可利用', '2021-06-22 02:08:52');
INSERT INTO `node_service` VALUES (36, 10, 2, '可利用', '2021-06-22 02:09:14');
INSERT INTO `node_service` VALUES (37, 10, 4, '可利用', '2021-06-22 02:09:14');
INSERT INTO `node_service` VALUES (38, 10, 5, '可利用', '2021-06-22 02:09:14');
INSERT INTO `node_service` VALUES (39, 10, 6, '可利用', '2021-06-22 02:09:14');
INSERT INTO `node_service` VALUES (40, 11, 2, '可利用', '2021-06-22 02:09:32');
INSERT INTO `node_service` VALUES (41, 11, 4, '可利用', '2021-06-22 02:09:32');
INSERT INTO `node_service` VALUES (42, 11, 3, '可利用', '2021-06-22 02:09:32');
INSERT INTO `node_service` VALUES (43, 11, 6, '可利用', '2021-06-22 02:09:32');
INSERT INTO `node_service` VALUES (44, 12, 2, '可利用', '2021-06-22 02:09:44');
INSERT INTO `node_service` VALUES (45, 12, 4, '可利用', '2021-06-22 02:09:44');
INSERT INTO `node_service` VALUES (46, 12, 3, '可利用', '2021-06-22 02:09:44');
INSERT INTO `node_service` VALUES (47, 12, 5, '可利用', '2021-06-22 02:09:44');
INSERT INTO `node_service` VALUES (48, 13, 1, '可利用', '2021-06-22 02:09:59');
INSERT INTO `node_service` VALUES (49, 13, 4, '可利用', '2021-06-22 02:09:59');
INSERT INTO `node_service` VALUES (50, 13, 3, '可利用', '2021-06-22 02:09:59');
INSERT INTO `node_service` VALUES (51, 13, 5, '可利用', '2021-06-22 02:09:59');
INSERT INTO `node_service` VALUES (52, 14, 1, '可利用', '2021-06-22 02:10:10');
INSERT INTO `node_service` VALUES (53, 14, 4, '可利用', '2021-06-22 02:10:10');
INSERT INTO `node_service` VALUES (54, 14, 3, '可利用', '2021-06-22 02:10:10');
INSERT INTO `node_service` VALUES (55, 14, 6, '可利用', '2021-06-22 02:10:10');
INSERT INTO `node_service` VALUES (56, 15, 1, '可利用', '2021-06-22 02:10:35');
INSERT INTO `node_service` VALUES (57, 15, 4, '可利用', '2021-06-22 02:10:35');
INSERT INTO `node_service` VALUES (58, 15, 3, '可利用', '2021-06-22 02:10:35');
INSERT INTO `node_service` VALUES (59, 15, 2, '可利用', '2021-06-22 02:10:35');
INSERT INTO `node_service` VALUES (60, 16, 1, '可利用', '2021-06-22 02:10:44');
INSERT INTO `node_service` VALUES (61, 16, 4, '可利用', '2021-06-22 02:10:44');
INSERT INTO `node_service` VALUES (62, 16, 3, '可利用', '2021-06-22 02:10:44');
INSERT INTO `node_service` VALUES (63, 16, 2, '可利用', '2021-06-22 02:10:44');
INSERT INTO `node_service` VALUES (64, 17, 1, '可利用', '2021-06-22 02:10:52');
INSERT INTO `node_service` VALUES (65, 17, 4, '可利用', '2021-06-22 02:10:52');
INSERT INTO `node_service` VALUES (66, 17, 3, '可利用', '2021-06-22 02:10:52');
INSERT INTO `node_service` VALUES (67, 17, 2, '可利用', '2021-06-22 02:10:52');
INSERT INTO `node_service` VALUES (68, 18, 1, '可利用', '2021-06-22 02:11:04');
INSERT INTO `node_service` VALUES (69, 18, 4, '可利用', '2021-06-22 02:11:04');
INSERT INTO `node_service` VALUES (70, 18, 6, '可利用', '2021-06-22 02:11:04');
INSERT INTO `node_service` VALUES (71, 18, 2, '可利用', '2021-06-22 02:11:04');
INSERT INTO `node_service` VALUES (72, 19, 1, '可利用', '2021-06-22 02:11:15');
INSERT INTO `node_service` VALUES (73, 19, 4, '可利用', '2021-06-22 02:11:15');
INSERT INTO `node_service` VALUES (74, 20, 6, '可利用', '2021-06-22 02:11:15');
INSERT INTO `node_service` VALUES (75, 20, 2, '可利用', '2021-06-22 02:11:15');
COMMIT;

-- ----------------------------
-- Table structure for network
-- ----------------------------
DROP TABLE IF EXISTS `network`;
CREATE TABLE `network` (
    `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '全局网络id',
    `sub_network_id` int unsigned DEFAULT NULL COMMENT '子网id',
    PRIMARY KEY (`id`),
     KEY `fk_network_network_2` (`sub_network_id`),
    CONSTRAINT `fk_network_network_2` FOREIGN KEY (`sub_network_id`) REFERENCES `sub_network` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of network
-- ----------------------------
BEGIN;
INSERT INTO `network` VALUES (1, 1);
INSERT INTO `network` VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for sub_network_link
-- ----------------------------
DROP TABLE IF EXISTS `sub_network_link`;
CREATE TABLE `sub_network_link` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '网络-边关系索引id',
  `sub_network_id` int unsigned DEFAULT NULL COMMENT '网络id',
  `link_id` int unsigned DEFAULT NULL COMMENT '边id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关系创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_network-link_network-link_2` (`link_id`),
  KEY `sub_network_id` (`sub_network_id`),
  CONSTRAINT `fk_network-link_network-link_2` FOREIGN KEY (`link_id`) REFERENCES `link` (`id`),
  CONSTRAINT `sub_network_link_ibfk_1` FOREIGN KEY (`sub_network_id`) REFERENCES `sub_network` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sub_network_link
-- ----------------------------
BEGIN;
INSERT INTO `sub_network_link` VALUES (1, 1, 1, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (2, 1, 2, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (3, 1, 3, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (4, 1, 4, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (5, 1, 5, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (6, 1, 6, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (7, 1, 7, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (8, 1, 8, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (9, 1, 9, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (10, 1, 10, '2021-06-22 02:16:00');
INSERT INTO `sub_network_link` VALUES (11, 2, 12, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (12, 2, 21, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (13, 2, 13, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (14, 2, 14, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (15, 2, 15, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (16, 2, 16, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (17, 2, 17, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (18, 2, 18, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (19, 2, 19, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (20, 2, 20, '2021-06-22 02:18:35');
INSERT INTO `sub_network_link` VALUES (21, 2, 22, '2021-06-22 02:19:01');
INSERT INTO `sub_network_link` VALUES (22, 2, 23, '2021-06-22 02:19:01');
INSERT INTO `sub_network_link` VALUES (23, 2, 24, '2021-06-22 02:19:01');
COMMIT;

-- ----------------------------
-- Table structure for sub_network_node
-- ----------------------------
DROP TABLE IF EXISTS `sub_network_node`;
CREATE TABLE `sub_network_node` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '网络节点关系索引id',
  `sub_network_id` int unsigned DEFAULT NULL COMMENT '网络id',
  `node_id` int unsigned NOT NULL COMMENT '节点id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '关系创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_network-node_network-node_1` (`node_id`),
  KEY `fk_network-node_network-node_2` (`sub_network_id`),
  CONSTRAINT `fk_network-node_network-node_1` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`),
  CONSTRAINT `fk_network-node_network-node_2` FOREIGN KEY (`sub_network_id`) REFERENCES `sub_network` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sub_network_node
-- ----------------------------
BEGIN;
INSERT INTO `sub_network_node` VALUES (1, 1, 1, '2021-06-21 05:43:51');
INSERT INTO `sub_network_node` VALUES (2, 1, 2, '2021-06-21 05:44:05');
INSERT INTO `sub_network_node` VALUES (3, 1, 3, '2021-06-21 05:44:11');
INSERT INTO `sub_network_node` VALUES (4, 1, 4, '2021-06-21 05:44:14');
INSERT INTO `sub_network_node` VALUES (5, 1, 5, '2021-06-21 05:44:17');
INSERT INTO `sub_network_node` VALUES (6, 1, 6, '2021-06-21 05:44:20');
INSERT INTO `sub_network_node` VALUES (7, 1, 7, '2021-06-21 05:44:24');
INSERT INTO `sub_network_node` VALUES (8, 1, 8, '2021-06-21 05:44:26');
INSERT INTO `sub_network_node` VALUES (9, 1, 9, '2021-06-21 05:44:28');
INSERT INTO `sub_network_node` VALUES (10, 1, 10, '2021-06-21 05:44:31');
INSERT INTO `sub_network_node` VALUES (11, 2, 11, '2021-06-21 05:44:39');
INSERT INTO `sub_network_node` VALUES (12, 2, 12, '2021-06-21 05:44:41');
INSERT INTO `sub_network_node` VALUES (13, 2, 13, '2021-06-21 05:44:44');
INSERT INTO `sub_network_node` VALUES (14, 2, 14, '2021-06-21 05:44:46');
INSERT INTO `sub_network_node` VALUES (15, 2, 15, '2021-06-21 05:44:48');
INSERT INTO `sub_network_node` VALUES (16, 2, 16, '2021-06-21 05:44:50');
INSERT INTO `sub_network_node` VALUES (17, 2, 17, '2021-06-21 05:44:53');
INSERT INTO `sub_network_node` VALUES (18, 2, 18, '2021-06-21 05:44:55');
INSERT INTO `sub_network_node` VALUES (19, 2, 19, '2021-06-21 05:44:57');
INSERT INTO `sub_network_node` VALUES (20, 2, 20, '2021-06-21 05:45:00');
INSERT INTO `sub_network_node` VALUES (28, 1, 29, '2021-07-19 12:49:42');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
