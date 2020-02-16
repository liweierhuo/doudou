package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import com.doudou.wx.api.config.WebConfig;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户签到表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping
@Slf4j
public class GlobalController extends BaseController {

    @Resource
    private WebConfig webConfig;

    @GetMapping("/error")
    public ApiResponse error() {
        return ApiResponse.error();
    }

    @PostMapping("/api/common/upload")
    public ApiResponse upload(MultipartFile file,@RequestParam("type") String type) {
        log.info("type:"+type);
        String filePath = saveToLocalServer(file,type);
        log.info(filePath);
        return new ApiResponse<>(filePath);
    }

    private String saveToLocalServer(final MultipartFile file,final String type) {
        String fileName = file.getOriginalFilename();
        String filePath = webConfig.getLocalFileServerDir() + File.separator + type + File.separator+ fileName;
        // 3. 写入流到本地文件中
        try {
            FileUtils.writeByteArrayToFile(new File(filePath), file.getBytes());
        } catch (IOException ex) {
            log.error("保存文件：{}失败，异常:{}", fileName, ex);
            throw new IllegalArgumentException("文件保存失败");
        }
        return toServerPath(filePath);
    }

    /**
     * 文件实际路径转为服务器url路径
     * @param absolutePath
     * @return
     */
    private String toServerPath(String absolutePath) {
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        String host = getRequest().getScheme()+"://"+getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath();
        return host + "/" + absolutePath.replace(webConfig.getLocalFileServerDir(), webConfig.getLocalFileServerPath());
    }
	
}
