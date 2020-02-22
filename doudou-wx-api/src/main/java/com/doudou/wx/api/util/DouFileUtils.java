package com.doudou.wx.api.util;

import com.doudou.core.util.RedisUtil;
import com.doudou.wx.api.config.WebConfig;
import com.github.kevinsawicki.http.HttpRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-22
 */
@Component
@Slf4j
public class DouFileUtils {

    @Resource
    private WebConfig webConfig;

    @Resource
    private RedisUtil redisUtil;

    public String saveToLocalServer(final MultipartFile file, final String type, HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        String fileFullName = getLocalFilePath(fileName, webConfig.getLocalFileServerDir() + File.separator + type);
        // 3. 写入流到本地文件中
        try {
            FileUtils.writeByteArrayToFile(new File(fileFullName), file.getBytes());
        } catch (IOException ex) {
            log.error("保存文件：{}失败，异常:{}", fileName, ex);
            throw new IllegalArgumentException("文件保存失败");
        }
        return toServerPath(fileFullName, request);
    }

    /**
     * 文件实际路径转为服务器url路径
     */
    public String toServerPath(String absolutePath, HttpServletRequest request) {
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        String host = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return host + "/" + absolutePath.replace(webConfig.getLocalFileServerDir(), webConfig.getLocalFileServerPath());
    }

    @SneakyThrows
    public String saveInternetImageToLocal(String imageUrl) {
        Assert.hasText(imageUrl, "图片URL不能为空");
        String[] tempArray = imageUrl.split("\\.");
        String imageSuffix = "." + tempArray[tempArray.length - 1];
        String imageName = redisUtil.genericUniqueId("IMAGE");
        URL url = new URL(imageUrl);
        HttpRequest response = HttpRequest.get(url);
        InputStream fileInputStream = response.getConnection().getInputStream();
        String imageFullName = getLocalFilePath(imageName + imageSuffix, webConfig.getLocalFileServerDir() + File.separator + "batchPublish");
        IOUtils.copy(fileInputStream, new FileOutputStream(imageFullName));
        return imageFullName;
    }

    private String getLocalFilePath(String fileName, String localFileSavePath) {
        // 1. 创建存放文件夹
        File fileFolder = new File(localFileSavePath);
        if (fileFolder.exists()) {
            return localFileSavePath + File.separator + fileName;
        }
        if (!fileFolder.mkdirs()) {
            throw new IllegalArgumentException("文件夹创建失败");
        }
        // 2. 创建文件空文件+文件名称
        return localFileSavePath + File.separator + fileName;
    }
}
