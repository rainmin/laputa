package com.rainmin.common.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Base64;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncodeUtils {

    private final static String SHA1_PRNG = "SHA1PRNG";

    public static String encode_md5(String password) {
        MessageDigest md;
        try {
            // 生成一个MD5加密计算摘要
            md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    public static String encode_md532(String strSign) {
        MessageDigest md;
        String sign = "";
        try {
            // 生成一个MD5加密计算摘要
            md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(strSign.getBytes(StandardCharsets.UTF_8));
            // 计算md5函数
//            md.update(strSign.getBytes("UTF-8"));
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            sign = String.format("%032x", new BigInteger(1, bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static String encode_aes(String content, byte[] salt) {
        String encodeStr = "";
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
//            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组
//            keygen.init(128, new SecureRandom(SALT.getBytes()));
            SecretKey key;
            if (android.os.Build.VERSION.SDK_INT >= 28) {
//                byte[] passwordBytes = content.getBytes(StandardCharsets.ISO_8859_1);
                byte[] raw = new byte[]{-98, 107, 71, 68, -61, -10, -125, 89, -32, -125, 61, -113, 121, 118, 61, -45};
//        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(raw, AESEnconderNew.KEY_SIZE), "AES");
                key = new SecretKeySpec(raw, "AES");
            } else {

                // 5.根据字节数组生成AES密钥
                key = fixSmallVersion(content);
            }
            // 6.根据指定算法AES生成密码器
            @SuppressLint("GetInstance")
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes(StandardCharsets.UTF_8);
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            // 10.将加密后的数据转换为字符串

            // 11.将字符串返回
//            encodeStr = new BASE64Encoder().encode(byte_AES);
            encodeStr = Base64.encodeToString(byte_AES, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        // 如果有错就返加null JeC7XS5VBuhq3v4atFl8Tg==
        return encodeStr;
    }

    public static String decode_aes(String content, byte[] salt) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
//            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            SecretKey key;
            if (android.os.Build.VERSION.SDK_INT >= 28) {
//                byte[] passwordBytes = content.getBytes(StandardCharsets.ISO_8859_1);
                byte[] raw = new byte[]{-98, 107, 71, 68, -61, -10, -125, 89, -32, -125, 61, -113, 121, 118, 61, -45};
//        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(raw, AESEnconderNew.KEY_SIZE), "AES");
                key = new SecretKeySpec(raw, "AES");
            } else {

                // 5.根据字节数组生成AES密钥
                key = fixSmallVersion(content);
            }
            // 6.根据指定算法AES自成密码器
            @SuppressLint("GetInstance")
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = Base64.decode(content, Base64.NO_WRAP);

            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            return new String(byte_decode, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        // 如果有错就返加nulll
        return null;
    }

    @SuppressLint("DeletedProvider")
    private static SecretKeySpec fixSmallVersion(String password) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            secureRandom = SecureRandom.getInstance(SHA1_PRNG, new CryptoProvider());
        } else {
            secureRandom = SecureRandom.getInstance(SHA1_PRNG, "Crypto");
        }

        secureRandom.setSeed(password.getBytes(StandardCharsets.ISO_8859_1));
        generator.init(128, secureRandom);
//        byte[] enCodeFormat = generator.generateKey().getEncoded();
        byte[] raw = new byte[]{-98, 107, 71, 68, -61, -10, -125, 89, -32, -125, 61, -113, 121, 118, 61, -45};
        return new SecretKeySpec(raw, "AES");
    }

    private static final class CryptoProvider extends Provider {
        CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
