package com.doudou.core.password.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

/**
 * AES加解密 -- 仅仅提供底层调用，
 * 例如：加密email，再写外围服务A；加密交易数据，再写外围服务;
 * @author liwei
 */
@Slf4j
public class AESEncryptUtil {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密默认的key
     */
    private static final String DEFAULT_AES_KEY = "DouDou!093410912";

    public static String encrypt(String input){
        return encrypt(input,DEFAULT_AES_KEY);
    }

    public static String encrypt(String input, String key){
        byte[] crypted = null;
        try{
            Cipher cipher = buildCipher( key );
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(key));
            crypted = cipher.doFinal(input.getBytes());
        }catch(Exception e){
            log.error("encrypt异常", e);
        }
        return new String( Base64.encodeBase64(crypted) );
    }

    public static String decrypt(String input){
        return decrypt(input,DEFAULT_AES_KEY);
    }

    public static String decrypt(String input, String key){
        byte[] output = null;
        try {
            Cipher cipher = buildCipher(key);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(key));
            output = cipher.doFinal( Base64.decodeBase64(input) );
        } catch (Exception e) {
            log.error( "decrypt异常", e );
        }
        return new String(output);
    }


    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private static Cipher buildCipher( String key ) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        } catch (Exception e) {
            log.error("生成Cipher异常", e);
        }
        return cipher;
    }

    private static SecretKeySpec getSecretKeySpec(String key) {
        return new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
    }

    public static void main(String[] args) {
        System.out.println(encrypt("liwei@2020"));
    }
}