package com.fiona.jwttoken.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * aes,base64
 *
 * @author Administrator
 */

public class AESUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);
    private static final String CHARTSET = "UTF-8";


    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    /**
     * 进行AES加密，key或者iv,toEncrypt为空，则会抛出空指针异常。
     *
     * @param key       秘钥
     * @param toEncrypt 要加密的数据
     * @return 加密后的数据，加密过程中出错则会返回null。
     */
    public static String cbcEncrypt(String key, String iv, String toEncrypt) {
        if (!checkParam(key, iv, toEncrypt)) {
            throw new NullPointerException("****AES encrypt STOP CAUSE BY param empty");
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key), getIv(iv));
            return Hex.encodeHexString(cipher.doFinal(toEncrypt.getBytes())).toUpperCase();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * 进行AES解密，key或者iv,toEncrypt为空，则会抛出空指针异常。
     * AES/CBC/PKCS7Padding 分别为算法/工作模式补码方式
     *
     * @param key       秘钥
     * @param toDecrypt 要解密的数据
     * @return 解密后的数据，解密过程中出错则会返回null。
     */
    public static String cbcDecrypt(String key, String iv, String toDecrypt) {
        if (!checkParam(key, iv, toDecrypt)) {
            throw new NullPointerException("****AES decrypt STOP CAUSED BY param empty");
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, getKey(key), getIv(iv));
            return new String(cipher.doFinal(Hex.decodeHex(toDecrypt.toCharArray())));
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }


    /**
     * 进行AES加密，key或者toEncrypt为空，则会抛出空指针异常。
     *
     * @param key       秘钥
     * @param toEncrypt 要加密的数据
     * @return 加密后的数据，加密过程中出错则会返回null。
     */
    public static String ecbEncrypt(String key, String toEncrypt) {
        if (!checkParam(key, toEncrypt)) {
            throw new NullPointerException("****AES encrypt STOP CAUSE BY param empty");
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
            return Hex.encodeHexString(cipher.doFinal(toEncrypt.getBytes(CHARTSET))).toUpperCase();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * 进行AES解密，key或者toEncrypt为空，则会抛出空指针异常。
     *
     * @param key       秘钥
     * @param toDecrypt 要解密的数据
     * @return 解密后的数据，解密过程中出错则会返回null。
     */
    public static String ecbDecrypt(String key, String toDecrypt) {
        if (!checkParam(key, toDecrypt)) {
            throw new NullPointerException("****AES decrypt STOP CAUSED BY param empty");
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, getKey(key));
            return new String(cipher.doFinal(Hex.decodeHex(toDecrypt.toCharArray())));
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 先AES再base64
     *
     * @param key
     * @param iv
     * @param toEncrypt
     * @return
     */
    public static String bulrCbcEncrypt(String key, String iv, String toEncrypt) {
        String rs = cbcEncrypt(key, iv, toEncrypt);
        if (Objects.isNull(rs)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(rs.getBytes());
    }

    /**
     * 先AES再base64
     *
     * @param key
     * @param iv
     * @param toEncrypt
     * @return
     */
    public static String bulrCbcDecrypt(String key, String iv, String toEncrypt) {
        return cbcDecrypt(key, iv, new String(Base64.getDecoder().decode(toEncrypt)));

    }

    /**
     * 先AES再base64
     *
     * @param key
     * @param toEncrypt
     * @return
     */
    public static String bulrEcbEncrypt(String key, String toEncrypt) {
        String rs = ecbEncrypt(key, toEncrypt);
        if (Objects.isNull(rs)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(rs.getBytes());
    }

    /**
     * 先AES再base64
     *
     * @param key
     * @param toEncrypt
     * @return
     */
    public static String bulrEcbDecrypt(String key, String toEncrypt) {
        return ecbDecrypt(key, new String(Base64.getDecoder().decode(toEncrypt)));
    }


    /**
     * @param args
     * @return true mean pass
     */
    private static boolean checkParam(String... args) {
        for (String s : args) {
            if (StringUtils.isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置秘钥
     *
     * @param myKey 秘钥
     */
    private static SecretKeySpec getKey(String myKey) {
        try {
            byte[] key = myKey.getBytes(CHARTSET);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    private static IvParameterSpec getIv(String myIv) {
        try {
            byte[] iv = myIv.getBytes(CHARTSET);
            return new IvParameterSpec(iv);
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}