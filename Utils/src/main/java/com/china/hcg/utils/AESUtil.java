package com.china.hcg.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


@Slf4j
public class AESUtil {

    private static final String KEY_AES = "AES";

    public static String encrypt(String key, String src) {
        return encrypt(KEY_AES, key, src);
    }

    public static String encrypt(String transformation, String privateKey, String str) {
        return encrypt(getCipher(transformation), privateKey, str);
    }

    public static String encrypt(String transformation, String provider, String privateKey, String str) {
        return encrypt(getCipher(transformation, provider), privateKey, str);
    }

    public static String encrypt(Cipher cipher, String key, String src) {
        try {
            log.info("AES加密,privateKey：{}，原值：{}", key, src);
            if (key == null || key.length() != 16) {
                throw new Exception("key不满足条件");
            }
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), KEY_AES));
            byte[] encrypted = cipher.doFinal(src.getBytes());
            String result = byte2hex(encrypted);
            log.info("加密后：" + result);
            return result;
        } catch (Exception e) {
            log.error("AES加密失败！" + e.getMessage());
            return null;
        }
    }


    public static String decrypt(String key, String src) {
        return decrypt(KEY_AES, key, src);
    }

    public static String decrypt(String transformation, String privateKey, String str) {
        return decrypt(getCipher(transformation), privateKey, str);
    }

    public static String decrypt(String transformation, String provider, String privateKey, String str) {
        return decrypt(getCipher(transformation, provider), privateKey, str);
    }

    public static String decrypt(Cipher cipher, String privateKey, String str) {
        log.info("AES解密,privateKey：{}，原值：{}", privateKey, str);
        try {
            if (privateKey == null || privateKey.length() != 16) {
                throw new Exception("key不满足条件");
            }
            byte[] raw = privateKey.getBytes();
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(raw, KEY_AES));
            byte[] encrypted1 = hex2byte(str);
            byte[] original = cipher.doFinal(encrypted1);
            String result = new String(original);
            log.info("解密后：" + result);
            return result;
        } catch (Exception e) {
            log.error("AES解密失败！" + e.getMessage());
            return null;
        }
    }


    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }


    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    public static Cipher getCipher(String transformation) {
        try {
            return Cipher.getInstance(transformation);
        } catch (Exception e) {
            return null;
        }
    }

    public static Cipher getCipher(String transformation, String provider) {
        try {
            return Cipher.getInstance(transformation, provider);
        } catch (Exception e) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        String str = "{\n" +
//                "    \"billNo\": \"266390223339292f1e693b253065023\",\n" +
//                "    \"platform\": \"default\",\n" +
//                "    \"version\": 2,\n" +
//                "    \"positive\": 1,\n" +
//                "    \"amount\": 0.01,\n" +
//                "    \"actualAmount\": 0.01,\n" +
//                "    \"actualAmount2\": 0.01,\n" +
//                "    \"createTime\": 1673761685720,\n" +
//                "    \"updateTime\": 1673761685720,\n" +
//                "    \"paymentList\": [\n" +
//                "        {\n" +
//                "            \"amount\": 0.01,\n" +
//                "            \"payType\": 1,\n" +
//                "            \"subPayType\": \"BARPAY\",\n" +
//                "            \"barCode\": \"0102102100021\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"saler\": {\n" +
//                "        \"merchantId\": \"MD1367303806756458496\",\n" +
//                "        \"merchantNo\": \"ZIVJPrhc\",\n" +
//                "        \"regionNo\": \"QY1199616627596075008\",\n" +
//                "        \"terminalNo\": \"92f9e692b112\",\n" +
//                "        \"storeNo\": \"92f9e692b112\"\n" +
//                "    }\n" +
//                "}";
//        System.out.println(encrypt("AES/ECB/PKCS5Padding", "1234567890123456",str));
//    }
    public static void main(String[] args) {
        String d = "71cf565a371132b74e311bf92087460a88985608abed8700a50601fd80d73c1d";
        System.err.println(d.toUpperCase());
        String jm = "{\"billNo\":\"202403113397818293792\",\"merchantNo\":\"NRIORTFM\"}";
        String string = encrypt("6ed78a4c9d060109",jm);
//        String  string =decrypt("6ed78a4c9d06b68a","4AE94B1E944469A278A0C4C96959C9A63910D9F55CA8C85A59BD8D5DB5EB37F6DB62FCCA97232A67FB6375D2F12341A2D019FD374D9BED1F642F3C0A585CED1A4D1C440C30F3218CC20947EACF547581A975745D592F1B5F0483C5634B249564DBA689EDBAFDA79EBF526A91C94EB70175FC91E411E7FB4140EF0490DDBC3641");
        System.err.println(string);
    }

}
