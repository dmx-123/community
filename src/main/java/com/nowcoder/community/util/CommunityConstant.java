package com.nowcoder.community.util;

public interface CommunityConstant {

    // activated successfully
    int ACTIVATION_SUCCESS = 0;

    // repetitive activation
    int ACTIVATION_REPEAT = 1;

    // failed to activate account
    int ACTIVATION_FAILURE = 2;

    // default timeout for login ticket
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // "remember me" timeout
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    // 实体类型: 帖子
    int ENTITY_TYPE_POST = 1;

    // 实体类型: 评论
    int ENTITY_TYPE_COMMENT = 2;

}
