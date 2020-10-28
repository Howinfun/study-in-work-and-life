/*
 Navicat Premium Data Transfer

 Source Send         : 本地MySQL
 Source Send Type    : MySQL
 Source Send Version : 50717
 Source Host           : localhost:3306
 Source Schema         : test-easyexcel

 Target Send Type    : MySQL
 Target Send Version : 50717
 File Encoding         : 65001

 Date: 05/08/2019 16:42:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for turntable_record
-- ----------------------------
DROP TABLE IF EXISTS `turntable_record`;
CREATE TABLE `turntable_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `prize_id` bigint(20) UNSIGNED NOT NULL COMMENT '奖项ID',
  `prize_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖品名称',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '中奖人手机号码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '转盘抽奖-中奖纪录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
