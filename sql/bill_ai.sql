/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50650 (5.6.50-log)
 Source Host           : localhost
 Source Schema         : bill_ai

 Target Server Type    : MySQL
 Target Server Version : 50650 (5.6.50-log)
 File Encoding         : 65001

 Date: 16/01/2024 16:06:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bill_record
-- ----------------------------
DROP TABLE IF EXISTS `bill_record`;
CREATE TABLE `bill_record` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `icon` varchar(20) DEFAULT NULL COMMENT 'emoji',
  `remark` varchar(255) DEFAULT NULL COMMENT '记账信息',
  `price` decimal(24,4) DEFAULT '0.0000' COMMENT '金额',
  `record_type` tinyint(1) DEFAULT NULL COMMENT '记录类型( 0: 入账 1: 出账 )',
  `ai_say` varchar(255) DEFAULT NULL COMMENT 'ai的吐槽',
  `record_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `open_id` varchar(64) DEFAULT NULL COMMENT '微信用户open_id',
  `type_id` int(5) DEFAULT NULL COMMENT '分类id',
  `type_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `images` text COMMENT '图片',
  PRIMARY KEY (`id`),
  KEY `wx_user_id` (`open_id`),
  KEY `record_time` (`record_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记账记录表';

-- ----------------------------
-- Table structure for bill_type
-- ----------------------------
DROP TABLE IF EXISTS `bill_type`;
CREATE TABLE `bill_type` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `record_type` tinyint(1) NOT NULL COMMENT '类型( 0: 支出 1: 收入 )',
  `name` varchar(255) NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='记账分类表';

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(640) DEFAULT NULL COMMENT '内容',
  `re` varchar(640) DEFAULT NULL COMMENT '回复',
  `open_id` varchar(64) DEFAULT NULL COMMENT '用户open_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='反馈';

-- ----------------------------
-- Table structure for ma_config
-- ----------------------------
DROP TABLE IF EXISTS `ma_config`;
CREATE TABLE `ma_config` (
  `config_key` varchar(64) NOT NULL COMMENT 'key',
  `config_value` text NOT NULL COMMENT '配置内容(JSON字符串)',
  PRIMARY KEY (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序配置表';

-- ----------------------------
-- Table structure for request_history
-- ----------------------------
DROP TABLE IF EXISTS `request_history`;
CREATE TABLE `request_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `open_id` varchar(64) DEFAULT NULL COMMENT 'openId',
  `message_content` longtext COMMENT '消息体',
  `result_content` longtext COMMENT '响应结果',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1779 DEFAULT CHARSET=utf8mb4 COMMENT='请求历史表';

-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user` (
  `open_id` varchar(64) NOT NULL COMMENT '用户open_id',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT '/avatar/64193f48e4b0fcfc58469980.jpg' COMMENT '头像',
  `is_ban` tinyint(1) DEFAULT '0' COMMENT '是否永久封禁',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `ban_end_time` datetime DEFAULT NULL COMMENT '短时间封禁截止时间',
  `desc_of_ban` varchar(255) DEFAULT NULL COMMENT '封禁备注',
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信用户表';

SET FOREIGN_KEY_CHECKS = 1;
