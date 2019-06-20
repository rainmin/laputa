package com.rainmin.common.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.IntDef;


/**
 * <dl>  Class Description
 * <dd> 项目名称：mea
 * <dd> 类名称：
 * <dd> 类描述：
 * <dd> 创建时间：2017/12/1
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author Jing Lu
 * @version 1.0
 */

public class AESEnconderNew {

    private final static String SHA1_PRNG = "SHA1PRNG";

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return 密文
     */
    public static String encode(String content) {
        return encode(content, "safecloud", Cipher.ENCRYPT_MODE);
    }

    /**
     * Aes加密/解密
     *
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return 加密/解密结果字符串
     */
    @SuppressLint({"DeletedProvider", "GetInstance"})
    public static String encode(String content, String password, @AESType int type) {

        String encodeStr = "";

        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(password)) {
            return null;
        }
        try {
            SecretKeySpec secretKeySpec;
            if (Build.VERSION.SDK_INT >= 28) {
                secretKeySpec = deriveKeyInsecurely(password);
            } else {
                secretKeySpec = fixSmallVersion(password);
            }
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(type, secretKeySpec);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
                encodeStr = parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = parseHexStr2Byte(content);
                encodeStr = new String(cipher.doFinal(byteContent), StandardCharsets.UTF_8);
            }
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
                InvalidKeyException | NoSuchPaddingException |
                NoSuchProviderException e) {
            e.printStackTrace();
        }
        return encodeStr;
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

    private static SecretKeySpec deriveKeyInsecurely(String password) {
//        byte[] passwordBytes = password.getBytes(StandardCharsets.ISO_8859_1);
        byte[] raw = new byte[]{-98, 107, 71, 68, -61, -10, -125, 89, -32, -125, 61, -113, 121, 118, 61, -45};
//        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(raw, AESEnconderNew.KEY_SIZE), "AES");
        return new SecretKeySpec(raw, "AES");
    }

    private static String parseByte2HexStr(byte[] buf) {
        /*StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();*/
        return Base64.encodeToString(buf, Base64.NO_WRAP);
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        /*byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;*/
        return Base64.decode(hexStr, Base64.NO_WRAP);
    }

    @IntDef({Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE})
    @interface AESType {
    }

    private static final class CryptoProvider extends Provider {
        CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
