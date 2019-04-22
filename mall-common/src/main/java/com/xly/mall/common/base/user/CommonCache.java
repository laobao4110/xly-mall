package com.xly.mall.common.base.user;

import com.xly.mall.common.domain.user.pojo.User;

import java.util.HashMap;
import java.util.Map;

public class CommonCache {
    public static Map<Integer, User> userMap = new HashMap<>();
}
