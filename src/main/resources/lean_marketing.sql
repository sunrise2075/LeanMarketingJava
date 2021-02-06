/*
Navicat MySQL Data Transfer

Source Server         : company
Source Server Version : 50556
Source Host           : 39.104.92.85:3306
Source Database       : lean_marketing

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-12-24 10:06:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '后台管理员id',
  `username` varchar(32) DEFAULT NULL COMMENT '后台管理员名称',
  `password` varchar(32) DEFAULT NULL COMMENT '后台管理员',
  `head_portrait` varchar(255) DEFAULT NULL COMMENT '管理员头像',
  `state` tinyint(2) DEFAULT '1' COMMENT '是否禁用(1启用,2禁用)',
  `mail` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('12', 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', 'https://qiniu.yipage.cn/1544425880218.jpg', '1', '3387456701@qq.com', '13117967223', '1', '超级管理员', '2018-12-24 09:57:02', '2018-12-24 09:57:02');

-- ----------------------------
-- Table structure for administrator_log
-- ----------------------------
DROP TABLE IF EXISTS `administrator_log`;
CREATE TABLE `administrator_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '管理员日志id',
  `operate_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `operate_name` varchar(32) DEFAULT NULL COMMENT '操作人名称',
  `operate_type` tinyint(2) DEFAULT NULL COMMENT '操作类型(1增2删3修改4查询)',
  `operate_content` varchar(255) DEFAULT NULL COMMENT '操作内容',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of administrator_log
-- ----------------------------

-- ----------------------------
-- Table structure for agent
-- ----------------------------
DROP TABLE IF EXISTS `agent`;
CREATE TABLE `agent` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '代理商id',
  `name` varchar(32) DEFAULT NULL COMMENT '代理商名称',
  `phone` varchar(32) DEFAULT NULL COMMENT '代理商手机号',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `area` varchar(32) DEFAULT NULL COMMENT '区',
  `detailed_address` varchar(255) DEFAULT NULL COMMENT '详情地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agent
-- ----------------------------

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `name` varchar(32) DEFAULT NULL COMMENT '资源名称',
  `code` int(3) DEFAULT NULL COMMENT '资源代号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', '个人信息管理', '1');
INSERT INTO `authority` VALUES ('2', '代理商管理', '2');
INSERT INTO `authority` VALUES ('3', '权限管理', '3');
INSERT INTO `authority` VALUES ('4', '会员管理', '4');
INSERT INTO `authority` VALUES ('5', '视频管理', '5');
INSERT INTO `authority` VALUES ('6', '评估管理', '6');
INSERT INTO `authority` VALUES ('7', '文库管理', '7');
INSERT INTO `authority` VALUES ('8', '商品管理', '8');
INSERT INTO `authority` VALUES ('9', '订单管理', '9');
INSERT INTO `authority` VALUES ('10', '财务管理', '10');
INSERT INTO `authority` VALUES ('11', '轮播图管理', '11');

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '轮播图id',
  `title` varchar(32) DEFAULT NULL COMMENT '标题',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `img` varchar(255) DEFAULT NULL COMMENT '图片',
  `state` int(2) DEFAULT '1' COMMENT '1启用2禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for consume_record
-- ----------------------------
DROP TABLE IF EXISTS `consume_record`;
CREATE TABLE `consume_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '消费记录',
  `user_id` int(10) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `user_head_portrait` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `user_grade` int(255) DEFAULT NULL COMMENT '用户等级',
  `order_number` varchar(32) DEFAULT NULL COMMENT '订单号',
  `address` varchar(255) DEFAULT NULL COMMENT '用户所在地',
  `type` int(3) DEFAULT NULL COMMENT '消费类型(1视频消费2文库消费3考试消费4会员充值)',
  `pay_money` decimal(10,0) DEFAULT NULL COMMENT '支付金额',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consume_record
-- ----------------------------

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '员工账号id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id(企业身份)',
  `name` varchar(32) DEFAULT NULL COMMENT '员工名称',
  `identity` int(3) DEFAULT NULL COMMENT '身份(1总监2职员)',
  `head_portrait` varchar(255) DEFAULT NULL COMMENT '员工头像',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------

-- ----------------------------
-- Table structure for exam_category
-- ----------------------------
DROP TABLE IF EXISTS `exam_category`;
CREATE TABLE `exam_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '试题分类id',
  `name` varchar(32) DEFAULT NULL COMMENT '分类名称',
  `is_hide` int(2) DEFAULT '1' COMMENT '是否隐藏(1显示2隐藏)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_category
-- ----------------------------

-- ----------------------------
-- Table structure for exam_order
-- ----------------------------
DROP TABLE IF EXISTS `exam_order`;
CREATE TABLE `exam_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '试题订单id',
  `subject_id` int(11) DEFAULT NULL COMMENT '试题id',
  `subject_name` varchar(32) DEFAULT NULL COMMENT '试题名称',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `payment` varchar(32) DEFAULT NULL COMMENT '支付方式',
  `pay_number` varchar(32) DEFAULT NULL COMMENT '订单号',
  `pay_state` tinyint(2) DEFAULT NULL COMMENT '支付状态(1未支付2已支付)',
  `pay_money` decimal(10,0) DEFAULT NULL COMMENT '支付金额',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_order
-- ----------------------------

-- ----------------------------
-- Table structure for exam_questions
-- ----------------------------
DROP TABLE IF EXISTS `exam_questions`;
CREATE TABLE `exam_questions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '试题id',
  `subject_id` int(11) DEFAULT NULL COMMENT '科目id(分类id)',
  `subject_name` varchar(32) DEFAULT NULL COMMENT '试题名称',
  `questions` varchar(255) DEFAULT NULL COMMENT '试题题目',
  `options` varchar(255) DEFAULT NULL COMMENT '试题题目选项',
  `answer` varchar(255) DEFAULT NULL COMMENT '试题题目答案',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_questions
-- ----------------------------

-- ----------------------------
-- Table structure for exam_score
-- ----------------------------
DROP TABLE IF EXISTS `exam_score`;
CREATE TABLE `exam_score` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '考试成绩id',
  `subject_name` varchar(32) DEFAULT NULL COMMENT '试题名称',
  `subject_id` int(11) DEFAULT NULL COMMENT '科目id',
  `score` tinyint(3) DEFAULT NULL COMMENT '考试成绩',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `evaluate` varchar(255) DEFAULT NULL COMMENT '评语',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_score
-- ----------------------------

-- ----------------------------
-- Table structure for exam_subject
-- ----------------------------
DROP TABLE IF EXISTS `exam_subject`;
CREATE TABLE `exam_subject` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '科目id',
  `name` varchar(32) DEFAULT NULL COMMENT '试题总名称',
  `price` decimal(10,0) DEFAULT NULL COMMENT '试题价格',
  `category_id` int(11) DEFAULT NULL COMMENT '分类id',
  `category_name` varchar(32) DEFAULT NULL COMMENT '试题分类名称',
  `question_num` int(3) DEFAULT NULL COMMENT '试题题目',
  `is_free` int(2) DEFAULT NULL COMMENT '是否免费（2收费，1免费）',
  `is_hide` tinyint(2) DEFAULT '1' COMMENT '是否隐藏(1显示2隐藏)',
  `exam_duration` int(3) DEFAULT NULL COMMENT '考试时长',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_subject
-- ----------------------------

-- ----------------------------
-- Table structure for library
-- ----------------------------
DROP TABLE IF EXISTS `library`;
CREATE TABLE `library` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '文库内容id',
  `title` varchar(32) DEFAULT NULL COMMENT '文库内容标题',
  `file_type` varchar(32) DEFAULT NULL COMMENT '文件类型',
  `category_id` int(11) DEFAULT NULL COMMENT '分类id',
  `category_name` varchar(32) DEFAULT NULL COMMENT '分类名称',
  `url` varchar(255) DEFAULT NULL COMMENT '文件链接',
  `is_free` int(3) DEFAULT NULL COMMENT '是否免费(1免费2收费)',
  `is_hide` int(3) DEFAULT '1' COMMENT '是否隐藏(1显示2隐藏)',
  `introduce` text COMMENT '简介',
  `content` text COMMENT '文章内容（富文本）',
  `price` decimal(10,0) DEFAULT NULL COMMENT '价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of library
-- ----------------------------

-- ----------------------------
-- Table structure for library_category
-- ----------------------------
DROP TABLE IF EXISTS `library_category`;
CREATE TABLE `library_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '文库分类id',
  `category_name` varchar(32) DEFAULT NULL COMMENT '文库分类名',
  `is_hide` int(2) DEFAULT '1' COMMENT '是否隐藏(1显示2隐藏)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of library_category
-- ----------------------------

-- ----------------------------
-- Table structure for library_download_record
-- ----------------------------
DROP TABLE IF EXISTS `library_download_record`;
CREATE TABLE `library_download_record` (
  `id` int(11) NOT NULL COMMENT '文库下载记录',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `library_id` int(11) DEFAULT NULL COMMENT '文库id',
  `library_title` varchar(32) DEFAULT NULL COMMENT '文库标题',
  `file_size` int(11) DEFAULT NULL COMMENT '文件大小(k为单位)',
  `file_type` varchar(32) DEFAULT NULL COMMENT '文件类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of library_download_record
-- ----------------------------

-- ----------------------------
-- Table structure for library_order
-- ----------------------------
DROP TABLE IF EXISTS `library_order`;
CREATE TABLE `library_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '文库订单id',
  `library_id` int(11) DEFAULT NULL COMMENT '文库id',
  `library_title` varchar(32) DEFAULT NULL COMMENT '文库标题',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `payment` varchar(32) DEFAULT NULL COMMENT '支付方式',
  `pay_state` int(3) DEFAULT NULL COMMENT '支付状态(1未支付2已支付)',
  `pay_money` decimal(10,0) DEFAULT NULL COMMENT '支付金额',
  `pay_number` varchar(32) DEFAULT NULL COMMENT '支付订单号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of library_order
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(32) DEFAULT NULL COMMENT '商品名称',
  `title` varchar(32) DEFAULT NULL COMMENT '商品标题',
  `category_id` int(11) DEFAULT NULL COMMENT '分类id',
  `category_name` varchar(32) DEFAULT NULL COMMENT '商品分类名称',
  `cover_img` varchar(255) DEFAULT NULL COMMENT '商品封面图',
  `price` decimal(10,0) DEFAULT NULL COMMENT '价格',
  `price_normal` decimal(10,0) DEFAULT NULL COMMENT '普通会员和企业对应的商品价格',
  `price_junior` decimal(10,0) DEFAULT NULL COMMENT '初级会员对应的商品价格',
  `price_mid` decimal(10,0) DEFAULT NULL COMMENT '中级会员对应的商品价格',
  `price_high` decimal(10,0) DEFAULT NULL COMMENT '高级会员对应的商品价格',
  `description` text COMMENT '图文详情（富文本）',
  `author_name` varchar(32) DEFAULT NULL COMMENT '作者名',
  `author_img` varchar(255) DEFAULT NULL COMMENT '作者头像',
  `author_introduce` varchar(255) DEFAULT NULL COMMENT '作者简介',
  `publishing_house` varchar(32) DEFAULT NULL COMMENT '出版社',
  `page_number` int(3) DEFAULT NULL COMMENT '页数',
  `word_number` int(11) DEFAULT NULL COMMENT '字数',
  `suit` tinyint(2) DEFAULT NULL COMMENT '是否套装（1是2否）',
  `sales_num` int(11) DEFAULT '0' COMMENT '销售数量',
  `is_free` int(2) DEFAULT NULL COMMENT '是否免费',
  `is_hide` int(3) DEFAULT '1' COMMENT '是否隐藏(1显示2隐藏)',
  `state` tinyint(2) DEFAULT '1' COMMENT '商品状态(1上架 2下架)',
  `publish_time` datetime DEFAULT NULL COMMENT '出版时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '商品分类',
  `is_hide` int(3) DEFAULT NULL COMMENT '是否隐藏(1显示2隐藏)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_category
-- ----------------------------

-- ----------------------------
-- Table structure for product_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `product_evaluate`;
CREATE TABLE `product_evaluate` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品评价id',
  `product_id` int(11) unsigned DEFAULT NULL COMMENT '商品id',
  `star_num` int(3) DEFAULT NULL COMMENT '评价的星的数量',
  `description` varchar(255) DEFAULT NULL COMMENT '评价描述',
  `user_id` int(11) unsigned DEFAULT NULL COMMENT '评价人id',
  `evaluate_img` varchar(255) DEFAULT NULL COMMENT '评价图片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for product_order
-- ----------------------------
DROP TABLE IF EXISTS `product_order`;
CREATE TABLE `product_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(32) DEFAULT NULL COMMENT '订单商品名称',
  `product_img` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `product_num` int(3) DEFAULT NULL COMMENT '商品数量',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `payment` varchar(255) DEFAULT NULL COMMENT '支付方式',
  `pay_number` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `pay_state` tinyint(2) DEFAULT NULL COMMENT '支付状态(1待付款，2待收货，3待评价，4成功 5 已取消)',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_money` decimal(10,0) DEFAULT NULL COMMENT '订单价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_order
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `state` tinyint(2) DEFAULT '1' COMMENT '角色状态(1,启用,2禁用)',
  `codes` varchar(255) DEFAULT NULL COMMENT '权限集合',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '1', '1,2,3,4,5,6,7,8,9,10,11', '2018-12-24 10:00:19', '2018-12-24 10:00:22');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `wxid` varchar(255) DEFAULT NULL COMMENT '用户微信id',
  `is_bind` int(2) DEFAULT '2' COMMENT '是否绑定手机(1是2不是)',
  `identity` int(2) DEFAULT NULL COMMENT '用户身份(1是会员2是企业3是代理商)',
  `nickname` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `head_portrait` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `member_level` tinyint(2) DEFAULT '1' COMMENT '会员级别（1普通，2初级，3中级，4高级，5企业）',
  `registered_address` varchar(255) DEFAULT NULL COMMENT '注册地区',
  `user_benefit_id` int(11) DEFAULT NULL COMMENT '用户会员权益id',
  `expiration_time` datetime DEFAULT NULL COMMENT '到期时间',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名(是企业才会有)',
  `company_name` varchar(32) DEFAULT NULL COMMENT '企业名称',
  `agent_province` varchar(32) DEFAULT NULL COMMENT '代理省份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户收货地址id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `area` varchar(32) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `is_default_address` tinyint(2) DEFAULT '2' COMMENT '是否默认地址(1是,2不是)',
  `receiver` varchar(32) DEFAULT NULL COMMENT '收货人',
  `fixed_mobie` varchar(32) DEFAULT NULL COMMENT '固定电话',
  `phone` varchar(32) DEFAULT NULL COMMENT '收货人联系号码',
  `code` varchar(32) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_address
-- ----------------------------

-- ----------------------------
-- Table structure for user_benefit
-- ----------------------------
DROP TABLE IF EXISTS `user_benefit`;
CREATE TABLE `user_benefit` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '会员权益id',
  `name` varchar(32) DEFAULT NULL COMMENT '会员权益名称',
  `introduction` varchar(255) DEFAULT NULL COMMENT '会员权益说明',
  `type` int(2) DEFAULT NULL COMMENT '会员权益类型(1个人会员2企业会员)',
  `half_year_price` decimal(10,0) DEFAULT NULL COMMENT '半年的价格',
  `year_price` decimal(10,0) DEFAULT NULL COMMENT '一年的价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_benefit
-- ----------------------------

-- ----------------------------
-- Table structure for user_collection_product
-- ----------------------------
DROP TABLE IF EXISTS `user_collection_product`;
CREATE TABLE `user_collection_product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户收藏商品id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '收藏的商品id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_collection_product
-- ----------------------------

-- ----------------------------
-- Table structure for user_collection_video
-- ----------------------------
DROP TABLE IF EXISTS `user_collection_video`;
CREATE TABLE `user_collection_video` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户的收藏视频id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `video_id` int(11) DEFAULT NULL COMMENT '收藏的视频id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_collection_video
-- ----------------------------

-- ----------------------------
-- Table structure for user_recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `user_recharge_order`;
CREATE TABLE `user_recharge_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '会员充值订单id',
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `payment` varchar(32) DEFAULT NULL COMMENT '支付方式',
  `pay_state` int(2) DEFAULT NULL COMMENT '支付状态(1未支付2已支付)',
  `pay_money` decimal(10,0) DEFAULT NULL COMMENT '支付金额',
  `pay_number` varchar(32) DEFAULT NULL COMMENT '支付订单号',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_recharge_order
-- ----------------------------

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '视频id',
  `title` varchar(32) DEFAULT NULL COMMENT '视频标题',
  `author_name` varchar(32) DEFAULT NULL COMMENT '作者',
  `author_img` varchar(255) DEFAULT NULL COMMENT '作者头像',
  `author_introduce` varchar(255) DEFAULT NULL COMMENT '作者介绍',
  `is_free` tinyint(2) DEFAULT NULL COMMENT '（2收费，1免费）',
  `is_hide` int(2) DEFAULT '1' COMMENT '是否隐藏(1显示2隐藏)',
  `sales_num` int(11) DEFAULT '0' COMMENT '销售量(购买成功之后,才会改变这个数量)',
  `price` decimal(10,0) DEFAULT NULL COMMENT '价格',
  `description` varchar(255) DEFAULT NULL COMMENT '详情描述',
  `video_url` varchar(255) DEFAULT NULL COMMENT '视频连接',
  `cover_img` varchar(255) DEFAULT NULL COMMENT '封面图',
  `category_id` int(11) DEFAULT NULL COMMENT '视频类别id',
  `category_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------

-- ----------------------------
-- Table structure for video_category
-- ----------------------------
DROP TABLE IF EXISTS `video_category`;
CREATE TABLE `video_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '视频分类id',
  `is_hide` int(3) DEFAULT '1' COMMENT '是否隐藏(1显示2隐藏)',
  `name` varchar(32) DEFAULT NULL COMMENT '视频分类名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_category
-- ----------------------------

-- ----------------------------
-- Table structure for video_contents
-- ----------------------------
DROP TABLE IF EXISTS `video_contents`;
CREATE TABLE `video_contents` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '视频目录表',
  `name` varchar(32) DEFAULT NULL COMMENT '视频目录标题',
  `url` varchar(255) DEFAULT NULL COMMENT '视频目录的视频链接',
  `video_id` int(11) DEFAULT NULL COMMENT '视频id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_contents
-- ----------------------------

-- ----------------------------
-- Table structure for video_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `video_evaluate`;
CREATE TABLE `video_evaluate` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `video_id` int(11) DEFAULT NULL COMMENT '视频id',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `user_head_portrait` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `evaluate_img` varchar(255) DEFAULT NULL COMMENT '评价图片',
  `star_num` int(3) DEFAULT NULL COMMENT '星的数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for video_order
-- ----------------------------
DROP TABLE IF EXISTS `video_order`;
CREATE TABLE `video_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '视频订单id',
  `video_id` int(11) DEFAULT NULL COMMENT '视频id',
  `video_title` varchar(32) DEFAULT NULL COMMENT '视频标题',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `paymemt` varchar(32) DEFAULT NULL COMMENT '支付方式',
  `pay_state` tinyint(2) DEFAULT NULL COMMENT '支付状态(1未支付2已支付)',
  `pay_money` decimal(10,0) NOT NULL COMMENT '支付金额',
  `pay_number` varchar(32) DEFAULT NULL COMMENT '支付编码',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_order
-- ----------------------------

-- ----------------------------
-- Table structure for watch_record
-- ----------------------------
DROP TABLE IF EXISTS `watch_record`;
CREATE TABLE `watch_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '观看记录id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `video_id` int(11) DEFAULT NULL COMMENT '观看视频id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of watch_record
-- ----------------------------
