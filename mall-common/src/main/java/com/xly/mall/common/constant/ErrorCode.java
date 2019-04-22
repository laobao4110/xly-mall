package com.xly.mall.common.constant;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
    private static Map<String, String> MAP = new HashMap<String, String>();
    /**
     * 返回码
     */
    public static final String SUCCESS = "X000000";
    public static final String BUSINESS_EXCEPTION = "X000001";
    public static final String CLEAR = "X000002";
    public static final String USER_NAME_NOT_FOUND = "X000003";
    public static final String USER_DISABLE = "X000004";
    public static final String USER_NOT_ACTIVATED = "X000005";
    public static final String USER_PASSWORD_ERROR = "X000006";
    public static final String USER_PASSWORD_TOO_SIMPLE = "X000007";
    public static final String USER_EXIST = "X000008";

    static {
        MAP.put(SUCCESS, "成功");
        MAP.put(BUSINESS_EXCEPTION, "业务异常");
        MAP.put(CLEAR, "清除错误信息");
        MAP.put(USER_NAME_NOT_FOUND, "用户名不存在");
        MAP.put(USER_DISABLE, "用户已禁用，请联系管理员");
        MAP.put(USER_NOT_ACTIVATED, "用户未激活，请联系管理员");
        MAP.put(USER_PASSWORD_ERROR, "用户密码错误");
        MAP.put(USER_PASSWORD_TOO_SIMPLE, "密码包含大写字母、小写字母、数字、特殊符号中的至少三类，且长度在8到20之间");
        MAP.put(USER_EXIST, "用户存在");
    }

    public static String getMessage(String code) {
        return MAP.get(code);
    }

    public static String getMessage(String code, String paramName) {
        return MAP.get(code) + ":" + paramName;
    }

    public static String clear(String code) {
        return MAP.put(code, "");
    }
}
