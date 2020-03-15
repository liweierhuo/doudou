package com.doudou.wx.api.util;

import com.doudou.core.util.RedisUtil;
import com.doudou.wx.api.config.WebConfig;
import com.doudou.wx.api.vo.ImageVO;
import com.github.kevinsawicki.http.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
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

    /**
     * 只适合上传图片
     */
    public ImageVO saveToLocalServer(final MultipartFile file, final String type, HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        String fileFullName = getLocalFilePath(fileName, webConfig.getLocalFileServerDir() + File.separator + type);
        String thumbnailsFileFullName = getLocalFilePath(fileName, webConfig.getLocalFileServerDir() + File.separator + type + File.separator + "thumbnails");
        // 3. 写入流到本地文件中
        try {
            reduceImage(file.getInputStream(), fileFullName, 1f);
            reduceImage(fileFullName, thumbnailsFileFullName, 180, 180);
        } catch (IOException ex) {
            log.error("保存文件：{}失败，异常:{}", fileName, ex);
            throw new IllegalArgumentException("文件保存失败");
        }
        ImageVO imageVO = new ImageVO();
        imageVO.setImageUrl(toServerPath(fileFullName, request));
        imageVO.setThumbnailsImageUrl(toServerPath(thumbnailsFileFullName, request));
        return imageVO;
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
    public ImageVO saveInternetImageToLocal(String imageUrl) {
        Assert.hasText(imageUrl, "图片URL不能为空");
        String[] tempArray = imageUrl.split("\\.");
        String imageSuffix = "." + tempArray[tempArray.length - 1];
        String imageName = redisUtil.genericUniqueId("IMAGE");
        URL url = new URL(imageUrl);
        HttpRequest response = HttpRequest.get(url);
        InputStream fileInputStream = response.getConnection().getInputStream();
        String imageFullName = getLocalFilePath(imageName + imageSuffix, webConfig.getLocalFileServerDir() + File.separator + "batchPublish");
        String thumbnailsImageName = getLocalFilePath(imageName + imageSuffix,
            webConfig.getLocalFileServerDir() + File.separator + "batchPublish" + File.separator + "thumbnails");
        reduceImage(fileInputStream, imageFullName, 1f);
        reduceImage(imageFullName, thumbnailsImageName, 180, 180);
        ImageVO imageVO = new ImageVO();
        imageVO.setImageUrl(imageFullName);
        imageVO.setThumbnailsImageUrl(thumbnailsImageName);
        return imageVO;
    }

    @SneakyThrows
    private void reduceImage(InputStream fileInputStream, String outputFileName, float scale) {
        //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
        Thumbnails.of(fileInputStream).scale(scale).outputQuality(1f).toFile(outputFileName);
        File file = new File(outputFileName);
        log.info("压缩后文件为 [{}]", file.length());
    }

    @SneakyThrows
    private void reduceImage(String imagePath, String outputFileName, int with, int height) {
        //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
        Thumbnails.of(imagePath).size(with, height).keepAspectRatio(true).outputQuality(0.8f).toFile(outputFileName);
        File file = new File(outputFileName);
        log.info("压缩后文件为 [{}]", file.length());
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
