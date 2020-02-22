package com.doudou.core.password;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Cheney
 * @version $Id: DruidEncryptFilter.class 2020/2/22 12:10 $
 * @description
 */
@Slf4j
@Data
public class DruidEncryptFilter extends FilterAdapter {

    private boolean enabled;

    private String publicKey;

    private String privateKey;

    private static final String ENCRYPT_PREFIX = "EC(";
    private static final String ENCRYPT_SUFFIX = ")";

    @Override
    public void init(DataSourceProxy dataSource) {
        if (!(dataSource instanceof DruidDataSource)) {
            log.error("DruidEncryptFilter only support DruidDataSource");
            return;
        }
        if (isDecrypt()) {
            decrypt((DruidDataSource) dataSource);
        }
    }

    /**
     * 是否需要解密
     * @return
     */
    private boolean isDecrypt() {
        if (! enabled) {
            return false;
        }
        if (StringUtils.isBlank(publicKey)) {
            publicKey = ConfigTools.DEFAULT_PUBLIC_KEY_STRING;
        }
        return true;
    }

    /**
     * 解密数据库用户名 & 密码
     * @param dataSource
     */
    private void decrypt(DruidDataSource dataSource) {
        String username = decrypt(dataSource.getUsername());
        dataSource.setUsername(username);
        String password = decrypt(dataSource.getPassword());
        dataSource.setPassword(password);
    }

    /**
     * 解密
     * @param encryptMsg
     * @return
     */
    private String decrypt(String encryptMsg) {
        if (null == encryptMsg || encryptMsg.length() < 5) {
            return encryptMsg;
        }
        try {
            if (encryptMsg.startsWith(ENCRYPT_PREFIX) && encryptMsg.endsWith(ENCRYPT_SUFFIX)) {
                String encryptStr = encryptMsg.substring(ENCRYPT_PREFIX.length(), encryptMsg.length() - ENCRYPT_SUFFIX.length());
                String decryptMsg = ConfigTools.decrypt(publicKey, encryptStr);
                return decryptMsg;
            }
        } catch (Exception e) {
            log.error("decrypt encrypt msg error, encrypt msg: {}.", encryptMsg, e);
        }
        return encryptMsg;
    }

}
