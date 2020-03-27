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

 Date: 27/03/2020 09:21:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book2
-- ----------------------------
DROP TABLE IF EXISTS `book2`;
CREATE TABLE `book2`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书名',
  `read_frequency` int(255) NULL DEFAULT 1 COMMENT '阅读次数',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `version` int(255) NULL DEFAULT 1 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_many`(`book_name`, `read_frequency`, `create_time`) USING BTREE COMMENT '复合索引'
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book2
-- ----------------------------
INSERT INTO `book2` VALUES (43, 'JDK1.8实战', 48, '2019-09-03 17:28:38', '2020-03-27 09:14:22', 1);
INSERT INTO `book2` VALUES (44, '高性能MySQL', 100, '2020-03-09 11:21:06', '2020-03-27 09:14:29', 1);
INSERT INTO `book2` VALUES (45, 'Redis入门', 1, '2020-03-20 16:10:13', '2020-03-27 09:14:35', 1);
INSERT INTO `book2` VALUES (50, 'Nginx', 1, '2020-03-20 16:10:13', '2020-03-27 09:14:39', 1);
INSERT INTO `book2` VALUES (51, '微服务架构', 1, '2020-03-20 16:10:13', '2020-03-27 09:14:44', 1);
INSERT INTO `book2` VALUES (55, 'Netty实战', 1, '2020-03-20 16:10:13', '2020-03-27 09:14:51', 1);
INSERT INTO `book2` VALUES (60, 'JVM调优', 1, '2020-03-20 17:16:58', '2020-03-27 09:14:56', 1);

SET FOREIGN_KEY_CHECKS = 1;
