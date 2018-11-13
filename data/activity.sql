/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50632
 Source Host           : 120.77.200.240:3306
 Source Schema         : lottery

 Target Server Type    : MySQL
 Target Server Version : 50632
 File Encoding         : 65001

 Date: 13/11/2018 10:43:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `start_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, 'CN', '注册抽奖', '2017-11-30 23:59:59', '2020-11-01 00:00:00', '1');

SET FOREIGN_KEY_CHECKS = 1;
