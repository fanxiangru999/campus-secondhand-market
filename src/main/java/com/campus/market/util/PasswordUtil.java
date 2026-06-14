package com.campus.market.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码工具类
 */
public class PasswordUtil {

    /**
     * MD5 加密
     */
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    /**
     * 验证密码
     */
    public static boolean verify(String rawPassword, String encryptedPassword) {
        return encrypt(rawPassword).equals(encryptedPassword);
    }
}
