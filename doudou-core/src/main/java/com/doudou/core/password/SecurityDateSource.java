package com.doudou.core.password;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-03
 */
public class SecurityDateSource extends DruidDataSource {

    @Override
    public void setUsername(String username) {
        try {
            username = ConfigTools.decrypt(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        try {
            password = ConfigTools.decrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setPassword(password);
    }
}
