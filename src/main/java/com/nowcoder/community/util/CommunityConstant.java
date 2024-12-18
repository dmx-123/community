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

    // 实体类型: 用户
    int ENTITY_TYPE_USER = 3;

    // 主题: 评论
    String TOPIC_COMMENT = "comment";

    // 主题: 点赞
    String TOPIC_LIKE = "like";

    // 主题: 关注
    String TOPIC_FOLLOW = "follow";

    // 系统用户ID
    int SYSTEM_USER_ID = 1;

    // 主题: 发帖
    String TOPIC_PUBLISH = "publish";

    // 主题: 删帖
    String TOPIC_DELETE = "delete";

    // 主题: 分享
    String TOPIC_SHARE = "share";

    // 权限: 普通用户
    String AUTHORITY_USER = "user";

    // 权限: 管理员
    String AUTHORITY_ADMIN = "admin";

    // 权限: 版主
    String AUTHORITY_MODERATOR = "moderator";

    /**
     * 个人主页 Tab: 个人信息
     */
    int TAB_PROFILE = 1;

    /**
     * 个人主页 Tab: 我的帖子
     */
    int TAB_MYPOST = 2;

    /**
     * 个人主页 Tab: 我的回复
     */
    int TAB_MYREPLY = 3;
}
