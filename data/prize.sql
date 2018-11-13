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

 Date: 13/11/2018 10:43:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for prize
-- ----------------------------
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prize_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prize_rate` double(4, 2) NULL DEFAULT 0.00,
  `prize_total_num` bigint(20) NULL DEFAULT NULL,
  `prize_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remaining_prize` bigint(20) NULL DEFAULT NULL,
  `activity_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK4qutn0x3bp35h9s2t8sqyd3ps`(`activity_id`) USING BTREE,
  CONSTRAINT `FK4qutn0x3bp35h9s2t8sqyd3ps` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of prize
-- ----------------------------
INSERT INTO `prize` VALUES (1, '1', 'Iphone7', 10.00, 10, '0', 10, 1);
INSERT INTO `prize` VALUES (2, '1', 'Iphone6', 15.00, 20, '0', 20, 1);
INSERT INTO `prize` VALUES (3, '1', '200元购物卡', 25.00, 200, '0', 200, 1);
INSERT INTO `prize` VALUES (4, '1', '如意U盘', 30.00, 400, '0', 400, 1);

SET FOREIGN_KEY_CHECKS = 1;
