/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001
*/

use blog;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'username',
  `password` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'password',
  `photo` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'avatar key',
  `email` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `unq_username`(`username`) USING BTREE,
  INDEX `unq_email`(`email`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'user table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `user_id` int(11) NOT NULL COMMENT 'user id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'content',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'title',
  `topic` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'type',
  `comment` int(11) NOT NULL DEFAULT 0 COMMENT 'comment',
  `introduction` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'type',
  `likes` int(11) NOT NULL DEFAULT 0 COMMENT 'like',
  `cover` varchar(200) COMMENT 'cover key',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `unq_user_id`(`user_id`) USING BTREE,
  INDEX `unq_topic`(`topic`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'article' ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `sender_id` int(11) NOT NULL COMMENT 'user id',
  `blog_id` int(11) NOT NULL COMMENT 'blog id',
  `replier_id` int(11) NOT NULL DEFAULT 0 COMMENT 'reply user id',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'content',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `unq_blog_id`(`blog_id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'comment' ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `tb_like`;
CREATE TABLE `tb_like`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `user_id` int(11) NOT NULL COMMENT 'user id',
  `blog_id` int(11) NOT NULL COMMENT 'blog id',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'status 1liked 0 canceled',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `unq_blog_id`(`blog_id`) USING BTREE,
  INDEX `unq_user_id`(`user_id`) USING BTREE,
  UNIQUE INDEX`unq_user_blog_id`(`user_id`, `blog_id`)USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'like' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
