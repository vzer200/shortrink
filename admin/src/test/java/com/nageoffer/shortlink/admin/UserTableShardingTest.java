package com.nageoffer.shortlink.admin;

public class UserTableShardingTest {

    public static final String sql = "CREATE TABLE `t_link_goto_%d`  (\n" +
            "  `id` bigint(0) NOT NULL COMMENT 'id',\n" +
            "  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '完整短链接',\n" +
            "  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分组标识',\n" +
            "  PRIMARY KEY (`id`) USING BTREE\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;" ;
    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.printf((sql) + "%n",i);
        }

    }

}
