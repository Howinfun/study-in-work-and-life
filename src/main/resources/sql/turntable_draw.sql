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

 Date: 05/08/2019 16:42:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for turntable_draw
-- ----------------------------
DROP TABLE IF EXISTS `turntable_draw`;
CREATE TABLE `turntable_draw`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `draw_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `weight` double(5, 2) NOT NULL COMMENT '权重',
  `prize_num` int(11) NULL DEFAULT NULL COMMENT '奖品数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '转盘抽奖-奖项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of turntable_draw
-- ----------------------------
INSERT INTO `turntable_draw` VALUES (1, '一等奖', 100.00, 2);
INSERT INTO `turntable_draw` VALUES (2, '二等奖', 30.00, 3);
INSERT INTO `turntable_draw` VALUES (3, '三等奖', 10.00, 5);

SET FOREIGN_KEY_CHECKS = 1;
