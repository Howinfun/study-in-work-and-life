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

 Date: 24/03/2020 17:53:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_phone`(`phone`) USING BTREE COMMENT 'phone索引'
) ENGINE = InnoDB AUTO_INCREMENT = 200007 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (100007, '测试', 1, '15627230001');
INSERT INTO `user` VALUES (100008, '测试', 2, '15627230002');
INSERT INTO `user` VALUES (100009, '测试', 3, '15627230003');
INSERT INTO `user` VALUES (100010, '测试', 4, '15627230004');
INSERT INTO `user` VALUES (100011, '测试', 5, '15627230005');
INSERT INTO `user` VALUES (100012, '测试', 6, '15627230006');
INSERT INTO `user` VALUES (100013, '测试', 7, '15627230007');
INSERT INTO `user` VALUES (100014, '测试', 8, '15627230008');
INSERT INTO `user` VALUES (100015, '测试', 9, '15627230009');
INSERT INTO `user` VALUES (100016, '测试', 10, '15627230010');
INSERT INTO `user` VALUES (100017, '测试', 11, '15627230011');
INSERT INTO `user` VALUES (100018, '测试', 12, '15627230012');
INSERT INTO `user` VALUES (100019, '测试', 13, '15627230013');
INSERT INTO `user` VALUES (100020, '测试', 14, '15627230014');
INSERT INTO `user` VALUES (100021, '测试', 15, '15627230015');
INSERT INTO `user` VALUES (100022, '测试', 16, '15627230016');
INSERT INTO `user` VALUES (100023, '测试', 17, '15627230017');
INSERT INTO `user` VALUES (100024, '测试', 18, '15627230018');
INSERT INTO `user` VALUES (100025, '测试', 19, '15627230019');
INSERT INTO `user` VALUES (100026, '测试', 20, '15627230020');
INSERT INTO `user` VALUES (100027, '测试', 21, '15627230021');
INSERT INTO `user` VALUES (100028, '测试', 22, '15627230022');
INSERT INTO `user` VALUES (100029, '测试', 23, '15627230023');
INSERT INTO `user` VALUES (100030, '测试', 24, '15627230024');
INSERT INTO `user` VALUES (100031, '测试', 25, '15627230025');
INSERT INTO `user` VALUES (100032, '测试', 26, '15627230026');
INSERT INTO `user` VALUES (100033, '测试', 27, '15627230027');
INSERT INTO `user` VALUES (100034, '测试', 28, '15627230028');
INSERT INTO `user` VALUES (100035, '测试', 29, '15627230029');
INSERT INTO `user` VALUES (100036, '测试', 30, '15627230030');
INSERT INTO `user` VALUES (100037, '测试', 31, '15627230031');
INSERT INTO `user` VALUES (100038, '测试', 32, '15627230032');
INSERT INTO `user` VALUES (100039, '测试', 33, '15627230033');
