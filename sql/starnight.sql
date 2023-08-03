SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_exam_paper
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_paper`;
CREATE TABLE `t_exam_paper`  (
                               `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                               `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                               `create_by` bigint(11) NULL DEFAULT NULL COMMENT '创建者id',
                               `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '删除标志位',
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷名称',
                               `subject_id` bigint(11) NULL DEFAULT NULL COMMENT '学科id',
                               `paper_type` int(11) NULL DEFAULT NULL COMMENT '试卷类型 1.固定试卷 4.时段试卷 6.任务试卷',
                               `grade_level` int(11) NULL DEFAULT NULL COMMENT '年级',
                               `score` int(11) NULL DEFAULT NULL COMMENT '试卷总分',
                               `question_count` int(11) NULL DEFAULT NULL COMMENT '题目数量',
                               `suggest_time` int(11) NULL DEFAULT NULL COMMENT '建议时长 分钟',
                               `limit_start_time` datetime NULL DEFAULT NULL COMMENT '时段试卷 开始时间',
                               `limit_end_time` datetime NULL DEFAULT NULL COMMENT '时段试卷 结束时间',
                               `frame_text_content_id` bigint(11) NULL DEFAULT NULL COMMENT '试卷内容id',
                               `task_exam_id` bigint(11) NULL DEFAULT NULL COMMENT '考试任务id',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '试卷表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_exam_paper_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_paper_answer`;
CREATE TABLE `t_exam_paper_answer`  (
                                      `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                      `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间（提交时间）',
                                      `create_by` bigint(11) NULL DEFAULT NULL COMMENT '创建者id',
                                      `exam_paper_id` bigint(11) NULL DEFAULT NULL COMMENT '试卷id',
                                      `paper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '试卷名称',
                                      `paper_type` int(11) NULL DEFAULT NULL COMMENT '试卷类型 1.固定试卷 4.时段试卷 6.任务试卷',
                                      `subject_id` bigint(11) NULL DEFAULT NULL COMMENT '学科id',
                                      `system_score` int(11) NULL DEFAULT NULL COMMENT '系统判定得分',
                                      `user_score` int(11) NULL DEFAULT NULL COMMENT '最终得分',
                                      `paper_score` int(11) NULL DEFAULT NULL COMMENT '试卷总分',
                                      `question_correct` int(11) NULL DEFAULT NULL COMMENT '做对题目数量',
                                      `question_count` int(11) NULL DEFAULT NULL COMMENT '题目总数量',
                                      `do_time` int(11) NULL DEFAULT NULL COMMENT '做题时间 秒',
                                      `status` int(11) NULL DEFAULT NULL COMMENT '试卷状态 1待判分 2完成',
                                      `task_exam_id` bigint(11) NULL DEFAULT NULL COMMENT '考试任务id',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '答卷表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_exam_paper_question_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_exam_paper_question_answer`;
CREATE TABLE `t_exam_paper_question_answer`  (
                                               `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                               `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                               `create_by` bigint(11) NULL DEFAULT NULL COMMENT '创建者id（做题人）',
                                               `question_id` bigint(11) NULL DEFAULT NULL COMMENT '题目id',
                                               `exam_paper_id` bigint(11) NULL DEFAULT NULL COMMENT '试卷id',
                                               `exam_paper_answer_id` bigint(11) NULL DEFAULT NULL COMMENT '试卷答案id',
                                               `question_type` int(11) NOT NULL COMMENT '题型',
                                               `subject_id` bigint(11) NULL DEFAULT NULL COMMENT '学科id',
                                               `customer_score` int(11) NULL DEFAULT NULL COMMENT '做题人得分',
                                               `question_score` int(11) NULL DEFAULT NULL COMMENT '题目原始分数',
                                               `question_text_content_id` bigint(11) NULL DEFAULT NULL COMMENT '问题内容',
                                               `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '做题人答案',
                                               `answer_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '做题人答案图片',
                                               `text_content_id` bigint(11) NULL DEFAULT NULL COMMENT '题目内容id',
                                               `do_right` bit(1) NULL DEFAULT NULL COMMENT '是否正确',
                                               `item_order` int(11) NULL DEFAULT NULL COMMENT '排序字段',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '答卷题目表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
                            `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                            `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                            `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
                            `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
                            `send_user_id` bigint(11) NULL DEFAULT NULL COMMENT '发送者id',
                            `send_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送者用户名',
                            `send_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送者真实姓名',
                            `receive_user_count` int(11) NULL DEFAULT NULL COMMENT '接收人数',
                            `read_count` int(11) NULL DEFAULT NULL COMMENT '已读人数',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_message_user
-- ----------------------------
DROP TABLE IF EXISTS `t_message_user`;
CREATE TABLE `t_message_user`  (
                                 `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                 `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `message_id` bigint(11) NULL DEFAULT NULL COMMENT '消息内容id',
                                 `receive_user_id` bigint(11) NULL DEFAULT NULL COMMENT '接收人id',
                                 `receive_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收人用户名',
                                 `receive_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收人真实姓名',
                                 `readed` bit(1) NULL DEFAULT NULL COMMENT '是否已读',
                                 `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户消息表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question`  (
                             `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                             `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_by` bigint(11) NULL DEFAULT NULL COMMENT '创建者id',
                             `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '删除标志位',
                             `question_type` int(11) NULL DEFAULT NULL COMMENT '题目类型 1.单选题 2.多选题 3.判断题 4.填空题 5.简答题',
                             `subject_id` bigint(11) NULL DEFAULT NULL COMMENT '学科',
                             `score` int(11) NULL DEFAULT NULL COMMENT '题目总分',
                             `grade_level` int(11) NULL DEFAULT NULL COMMENT '级别',
                             `difficult` int(11) NULL DEFAULT NULL COMMENT '题目难度',
                             `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '正确答案',
                             `info_text_content_id` bigint(11) NULL DEFAULT NULL COMMENT '题目内容信息id',
                             `status` int(11) NULL DEFAULT NULL COMMENT '状态',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_subject
-- ----------------------------
DROP TABLE IF EXISTS `t_subject`;
CREATE TABLE `t_subject`  (
                            `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                            `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '删除标志位',
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学科名',
                            `level` int(11) NULL DEFAULT NULL COMMENT '年级',
                            `level_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年级名',
                            `item_order` int(11) NULL DEFAULT NULL COMMENT '排序字段',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学科表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_task_exam
-- ----------------------------
DROP TABLE IF EXISTS `t_task_exam`;
CREATE TABLE `t_task_exam`  (
                              `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                              `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                              `create_by` bigint(11) NULL DEFAULT NULL COMMENT '创建者id',
                              `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '删除标志位',
                              `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务标题',
                              `grade_level` int(11) NULL DEFAULT NULL COMMENT '级别',
                              `frame_text_content_id` bigint(11) NULL DEFAULT NULL COMMENT '任务内容id',
                              `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者用户名',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_task_exam_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_task_exam_answer`;
CREATE TABLE `t_task_exam_answer`  (
                                     `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                     `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `create_by` bigint(11) NULL DEFAULT NULL COMMENT '创建者id',
                                     `task_exam_id` bigint(11) NULL DEFAULT NULL COMMENT '任务id',
                                     `text_content_id` bigint(11) NULL DEFAULT NULL COMMENT '任务完成情况id',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户任务表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_text_content
-- ----------------------------
DROP TABLE IF EXISTS `t_text_content`;
CREATE TABLE `t_text_content`  (
                                 `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                 `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文本内容',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文本表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                         `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                         `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '删除标志位',
                         `user_uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '唯一uuid',
                         `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
                         `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
                         `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
                         `sex` int(11) NULL DEFAULT NULL COMMENT '性别 1.男 2女',
                         `birth_day` datetime NULL DEFAULT NULL COMMENT '生日',
                         `user_level` int(11) NULL DEFAULT NULL COMMENT '学生年级(1-12)',
                         `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
                         `role` int(11) NULL DEFAULT NULL COMMENT '角色 1.学生 2.教师 3.管理员',
                         `status` int(11) NULL DEFAULT NULL COMMENT '状态 1.启用 2禁用',
                         `avatar_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
                         `last_active_time` datetime NULL DEFAULT NULL COMMENT '最后一次上线时间',
                         `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openId',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = COMPACT;

INSERT INTO `t_user` (`id`, `create_time`, `update_time`, `del_flag`, `user_uuid`, `user_name`, `password`, `real_name`, `age`, `sex`, `birth_day`, `user_level`, `phone`, `role`, `status`, `avatar_path`, `last_active_time`, `wx_open_id`) VALUES (1, '2023-07-21 13:03:10', '2023-06-01 00:00:00', b'0', '52045f5f-a13f-4ccc-93dd-f7ee8270ad4c', 'admin', '$2a$10$cATvZHyNR2BvFHvi9JEXLeXvg7EXSCMUn4BfSdGo94PQV9.e.uv2C', '管理员', NULL, NULL, NULL, NULL, NULL, 3, 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_user_event_log
-- ----------------------------
DROP TABLE IF EXISTS `t_user_event_log`;
CREATE TABLE `t_user_event_log`  (
                                   `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                   `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `user_id` bigint(11) NULL DEFAULT NULL COMMENT '用户id',
                                   `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
                                   `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
                                   `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作记录',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作事件表' ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
