package com.xly.mall.common.base.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestPass {
    DigestPass() {
    }

    public static byte[] digest(String alg, byte[] plainByte) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(alg);
        md.update(plainByte);
        return md.digest();
    }

    public static byte[] digest(String alg, byte[] plainByte, byte[] key) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(alg);
        md.update(plainByte);
        return md.digest(key);
    }
}
