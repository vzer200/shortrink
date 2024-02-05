package com.nageoffer.shortlink.admin;

public class UserTableShardingTest {

    public static final String sql = "CREATE TABLE `t_link_%d`  (\n" +
            "  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '域名',\n" +
            "  `short_uri` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短链接',\n" +
            "  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '完整短链接',\n" +
            "  `origin_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原始链接',\n" +
            "  `click_num` int(0) NULL DEFAULT 0 COMMENT '点击量',\n" +
            "  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分组标识',\n" +
            "  `favicon` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '网站图标',\n" +
            "  `enable_status` tinyint(1) NULL DEFAULT NULL COMMENT '启用标识 0：未启用 1：已启用',\n" +
            "  `created_type` tinyint(1) NULL DEFAULT NULL COMMENT '创建类型 0：控制台 1：接口',\n" +
            "  `valid_date_type` tinyint(1) NULL DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：用户自定义',\n" +
            "  `valid_date` datetime(0) NULL DEFAULT NULL COMMENT '有效期',\n" +
            "  `describe` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',\n" +
            "  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',\n" +
            "  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',\n" +
            "  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',\n" +
            "  `total_pv` int(0) NULL DEFAULT NULL COMMENT '历史pv',\n" +
            "  `total_uv` int(0) NULL DEFAULT NULL COMMENT '历史uv',\n" +
            "  `total_uip` int(0) NULL DEFAULT NULL COMMENT '历史uip',\n" +
            "  `del_time` bigint(0) NULL DEFAULT NULL COMMENT '删除时间',\n" +
            "  PRIMARY KEY (`id`) USING BTREE,\n" +
            "  UNIQUE INDEX `idx_unique_full_short_url`(`full_short_url`, `del_time`) USING BTREE\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 1736725734631702530 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;\n" ;
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((sql) + "%n",i);
        }

    }

}
