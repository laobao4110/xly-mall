package com.xly.mall.common.base.user;

import com.xly.mall.common.base.SystemException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final String CHARSET = "UTF-8";
    private static final String ALG = "MD5";

    public MD5Util() {
    }

    public static String encrypt(byte[] bytes) {
        return encrypt(bytes, false);
    }

    public static String encrypt(String str) {
        return encrypt(str, false);
    }

    public static String encryptWithKey(String str, String key) {
        return encryptWithKey(str, key, false);
    }

    public static String encrypt(byte[] bytes, boolean base64) {
        Object var2 = null;

        byte[] digest;
        try {
            digest = DigestPass.digest("MD5", bytes);
        } catch (NoSuchAlgorithmException var4) {
            throw new SystemException(var4);
        }

        return base64 ? EncodeUtil.base64Encode(digest) : EncodeUtil.encode(digest);
    }

    public static String encrypt(String str, boolean base64) {
        Object var2 = null;

        byte[] digest;
        try {
            digest = DigestPass.digest("MD5", str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException var4) {
            throw new SystemException(var4);
        } catch (UnsupportedEncodingException var5) {
            throw new SystemException(var5);
        }

        return base64 ? EncodeUtil.base64Encode(digest) : EncodeUtil.encode(digest);
    }

    public static String encryptWithKey(String str, String key, boolean base64) {
        Object var3 = null;

        byte[] digest;
        try {
            digest = DigestPass.digest("MD5", str.getBytes("UTF-8"), key.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException var5) {
            throw new SystemException(var5);
        } catch (UnsupportedEncodingException var6) {
            throw new SystemException(var6);
        }

        return base64 ? EncodeUtil.base64Encode(digest) : EncodeUtil.encode(digest);
    }

    public static void main(String[] args) {
        String s = "123abc";
        String s1 = encrypt(s);
        String s2 = encrypt(s, true);
        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);
    }
}
