package com.doudou.wx.api.service;

import com.doudou.wx.api.controller.AbstractControllerTest;
import com.doudou.wx.api.util.DouFileUtils;
import com.doudou.wx.api.vo.ImageVO;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-03-15
 */
public class FileServiceTest extends AbstractControllerTest {

    @Autowired
    private DouFileUtils douFileUtils;

    @Test
    @SneakyThrows
    public void testReduceImage() {
        ImageVO result = douFileUtils.saveInternetImageToLocal("https://static001.geekbang.org/resource/image/89/a9/8914f9b46c87424964efcc0755af3da9.png");
        System.out.println(result);
    }
}
