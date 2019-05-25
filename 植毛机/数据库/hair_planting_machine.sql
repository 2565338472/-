/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80012
Source Host           : 127.0.0.1:3306
Source Database       : hair_planting_machine

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-15 08:49:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_connect_info
-- ----------------------------
DROP TABLE IF EXISTS `ss_connect_info`;
CREATE TABLE `ss_connect_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `port` int(10) DEFAULT NULL COMMENT '端口号',
  `thread_state` tinyint(1) DEFAULT '1' COMMENT '线程状态',
  `modification_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `mender` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for ss_exceeding_production
-- ----------------------------
DROP TABLE IF EXISTS `ss_exceeding_production`;
CREATE TABLE `ss_exceeding_production` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `connect_info_id` int(11) DEFAULT NULL COMMENT '连接信息表的id',
  `exceeding_production` int(11) DEFAULT '0' COMMENT '超出的产量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for ss_production_data
-- ----------------------------
DROP TABLE IF EXISTS `ss_production_data`;
CREATE TABLE `ss_production_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '型号',
  `customer_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户单号',
  `work_order_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工单单号',
  `scheduled_production` int(255) DEFAULT NULL COMMENT '计划产量',
  `production_state` int(1) DEFAULT '0' COMMENT '生产状态 0 未完成 1 已完成 2 未开始',
  `connect_Info_id` int(11) NOT NULL COMMENT 'connect_Info_id表的id',
  `ok` int(11) DEFAULT NULL COMMENT '合格数',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '机器状态 0 暂停 1 运行 2 未连接',
  `ng` int(11) DEFAULT NULL COMMENT '不合格数',
  `owe_hole` int(10) DEFAULT NULL COMMENT '欠孔数量',
  `long_hair` int(10) DEFAULT NULL COMMENT '长毛数量',
  `loose_wool` int(10) DEFAULT NULL COMMENT '散毛数量',
  `Long_short_hair` int(10) DEFAULT NULL COMMENT '长短毛数量',
  `dirty` int(10) DEFAULT NULL COMMENT '脏污数量',
  `implantation_error` varchar(255) DEFAULT NULL COMMENT '错误植毛数量',
  `difference_hair` varchar(255) DEFAULT NULL COMMENT '差毛数量',
  `total` int(11) DEFAULT NULL COMMENT '总数',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开机时间',
  `end_time` datetime DEFAULT NULL COMMENT '停机时间',
  `run_time` varchar(100) DEFAULT NULL COMMENT '运行时间',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`connect_Info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=505 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for ss_user
-- ----------------------------
DROP TABLE IF EXISTS `ss_user`;
CREATE TABLE `ss_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
  `modification_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `mender` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
