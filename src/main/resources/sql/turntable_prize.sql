/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : test-easyexcel

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 05/08/2019 16:42:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for turntable_prize
-- ----------------------------
DROP TABLE IF EXISTS `turntable_prize`;
CREATE TABLE `turntable_prize`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `draw_id` bigint(20) UNSIGNED NOT NULL COMMENT '奖项ID',
  `prize_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品名称',
  `weight` double(5, 2) NOT NULL COMMENT '权重',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '转盘抽奖-奖品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of turntable_prize
-- ----------------------------
INSERT INTO `turntable_prize` VALUES (1, 1, '一等奖奖品一', 30.00);
INSERT INTO `turntable_prize` VALUES (2, 1, '一等奖奖品二', 100.00);
INSERT INTO `turntable_prize` VALUES (3, 2, '二等奖奖品一', 30.00);
INSERT INTO `turntable_prize` VALUES (4, 2, '二等奖奖品二', 60.00);
INSERT INTO `turntable_prize` VALUES (5, 2, '二等奖奖品三', 100.00);
INSERT INTO `turntable_prize` VALUES (6, 3, '三等奖奖品一', 10.00);
INSERT INTO `turntable_prize` VALUES (7, 3, '三等奖奖品二', 20.00);
INSERT INTO `turntable_prize` VALUES (8, 3, '三等奖奖品三', 50.00);
INSERT INTO `turntable_prize` VALUES (9, 3, '三等奖奖品四', 70.00);
INSERT INTO `turntable_prize` VALUES (10, 3, '三等奖奖品五', 100.00);

SET FOREIGN_KEY_CHECKS = 1;
