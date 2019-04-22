package com.xly.mall.common.util;

import com.xly.mall.common.base.user.MD5Util;

public class UserUtil {

    // 不使用正则表达式
    public static boolean isNotSimple(String s) {
        int len = s.length();
        if (len < 8 || len > 20)
            return false;
        int flag = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c >= 'a' & c <= 'z') { // 包含a-z
                flag |= 0b0001;
            } else if (c >= 'A' & c <= 'Z') { // 包含A-Z
                flag |= 0b0010;
            } else if (c >= '0' & c <= '9') { // 包含0-9
                flag |= 0b0100;
            } else if ((c >= '!' & c <= '/') || (c >= ':' & c <= '@') || (c >= '[' & c <= '`')
                    || (c >= '{' & c <= '~')) { // 包含特殊字符
                flag |= 0b1000;
            } else {
                return false;
            }
        }
        return Integer.bitCount(flag) >= 3;
    }

}
