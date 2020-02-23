package com.doudou.wx.api.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cheney
 * @version $Id: EncryptConfig.class 2020/2/22 13:40 $
 * @description
 */
@Configuration
public class JasyptEncryptConfig {

    /**
     * 加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "PBEWithMD5AndDES";

    /**
     * 加密默认的key
     */
    private static final String DEFAULT_AES_KEY = "DouDou!093410912";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(DEFAULT_AES_KEY);
        config.setAlgorithm(DEFAULT_CIPHER_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}
