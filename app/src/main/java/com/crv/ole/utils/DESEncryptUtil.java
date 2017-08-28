package com.crv.ole.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created with IntelliJ IDEA.
 * User: admin3
 * To change this template use File | Settings | File Templates.
 * DES加密码工具
 * algorithm = "DESede";
 * transformation = "DESede/CBC/PKCS5Padding"
 */
public class DESEncryptUtil {

    /**
     * 签名解密
     *
     * @param key
     * @param iv   随机数
     * @param data 需解密的数据
     * @return
     */
    public static String decSign(String key, String iv, String data) {
        String deCode = "";
        try {
            byte[] decodeBytes = decryptDes3(key.getBytes("utf-8"),
                    Hex.decodeHex(data.toCharArray()),
                    iv.getBytes("utf-8"));

            deCode = new String(decodeBytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deCode;
    }

    /**
     * 签名加密
     *
     * @param key
     * @param iv
     * @param data
     * @return
     */
    public static String encSign(String key, String iv, String data) {
        String enCode = "";
        try {
            byte[] ivBytes = iv.getBytes("utf-8");
            byte[] codeBytes = encryptDes3(key.getBytes("utf-8"),
                    data.getBytes("utf-8"), ivBytes);
            enCode = Hex.encodeHexString(codeBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enCode;
    }

    private static byte[] decryptDes3(byte[] keyvalue, byte[] encrypted, byte[] ivvalue) throws Exception {
        String algorithm = "DESede";
        String transformation = "DESede/CBC/PKCS5Padding";
        byte[] keyBytes = new byte[24];
        if (keyvalue.length >= keyBytes.length) {
            System.arraycopy(keyvalue, 0, keyBytes, 0, keyBytes.length);
        } else {
            System.arraycopy(keyvalue, 0, keyBytes, 0, keyvalue.length);
        }

        DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
        IvParameterSpec iv;
        if (ivvalue == null) {
            iv = new IvParameterSpec(new byte[8]);
        } else {
            iv = new IvParameterSpec(ivvalue);
        }

        SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
        Cipher decrypter = Cipher.getInstance(transformation);
        decrypter.init(2, key, iv);
        byte[] decrypted = decrypter.doFinal(encrypted);
        return decrypted;
    }

    private static byte[] encryptDes3(byte[] keyvalue, byte[] plainContent, byte[] ivvalue) throws Exception {
        byte[] keyBytes = new byte[24];
        if (keyvalue.length >= keyBytes.length) {
            System.arraycopy(keyvalue, 0, keyBytes, 0, keyBytes.length);
        } else {
            System.arraycopy(keyvalue, 0, keyBytes, 0, keyvalue.length);
        }

        String algorithm = "DESede";
        String transformation = "DESede/CBC/PKCS5Padding";
        DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
        IvParameterSpec iv;
        if (ivvalue == null) {
            iv = new IvParameterSpec(new byte[8]);
        } else {
            iv = new IvParameterSpec(ivvalue);
        }

        SecretKey key = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
        Cipher encrypter = Cipher.getInstance(transformation);
        encrypter.init(1, key, iv);
        byte[] encrypted = encrypter.doFinal(plainContent);
        return encrypted;
    }

    static class Hex {
        private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        public static byte[] decodeHex(char[] data)
                throws Exception {
            int len = data.length;

            if ((len & 0x1) != 0) {
                throw new Exception("Odd number of characters.");
            }

            byte[] out = new byte[len >> 1];

            int i = 0;
            for (int j = 0; j < len; i++) {
                int f = toDigit(data[j], j) << 4;
                j++;
                f |= toDigit(data[j], j);
                j++;
                out[i] = ((byte) (f & 0xFF));
            }

            return out;
        }

        protected static int toDigit(char ch, int index)
                throws Exception {
            int digit = Character.digit(ch, 16);
            if (digit == -1) {
                throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
            }
            return digit;
        }

        public static String encodeHexString(byte[] data) {
            return new String(encodeHex(data));
        }

        public static char[] encodeHex(byte[] data) {
            return encodeHex(data, true);
        }

        public static char[] encodeHex(byte[] data, boolean toLowerCase) {
            return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
        }

        protected static char[] encodeHex(byte[] data, char[] toDigits) {
            int l = data.length;
            char[] out = new char[l << 1];

            int i = 0;
            for (int j = 0; i < l; i++) {
                out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
                out[(j++)] = toDigits[(0xF & data[i])];
            }
            return out;
        }
    }
}
