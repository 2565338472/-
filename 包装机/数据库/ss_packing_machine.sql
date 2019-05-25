/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80012
Source Host           : 127.0.0.1:3306
Source Database       : ss_packing_machine

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-05-15 08:47:23
*/
create database ss_packing_machine;

use ss_packing_machine;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_connect_info
-- ----------------------------
DROP TABLE IF EXISTS `ss_connect_info`;
CREATE TABLE `ss_connect_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `port` int(10) DEFAULT NULL COMMENT '端口号',
  `thread_state` tinyint(1) DEFAULT '1' COMMENT '线程状态 1 线程正在运行 0 线程已退出',
  `modification_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `mender` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for ss_exceeding_production
-- ----------------------------
DROP TABLE IF EXISTS `ss_exceeding_production`;
CREATE TABLE `ss_exceeding_production` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `connect_info_id` int(11) DEFAULT NULL COMMENT '连接信息表的id',
  `exceeding_production` int(11) DEFAULT '0' COMMENT '超出的产量',
  `total_completed` int(11) DEFAULT '0',
  `previousOk` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for ss_production_data
-- ----------------------------
DROP TABLE IF EXISTS `ss_production_data`;
CREATE TABLE `ss_production_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `machine_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '机器名称',
  `machine_state` varchar(255) DEFAULT NULL COMMENT '机器状态',
  `work_order_number` varchar(100) DEFAULT NULL COMMENT '工单单号',
  `customer_number` varchar(100) DEFAULT NULL COMMENT '客户单号',
  `production_state` int(11) DEFAULT '2' COMMENT '生产状态，因为有不同的型号的在同一个机器上生产，这个字段是为了判断该型号是否在该机器上生产  0 未完成 1 已完成 2 未开始',
  `connect_Info_id` int(11) DEFAULT NULL COMMENT 'connect_Info_id表的id',
  `scheduled_production` int(11) DEFAULT '0' COMMENT '计划产量',
  `version` varchar(100) DEFAULT NULL COMMENT '型号',
  `unfinished_amount` int(11) NOT NULL DEFAULT '0' COMMENT '未完成量',
  `ng_number_camera` int(11) DEFAULT '0' COMMENT '相机ng次数',
  `ok` int(11) DEFAULT '0' COMMENT '当前的socekt连接的ng数量',
  `ng` int(11) DEFAULT '0' COMMENT '当前的socekt 连接的ng数量',
  `total` int(255) DEFAULT '0' COMMENT '当前的socekt 连接的total数量',
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
