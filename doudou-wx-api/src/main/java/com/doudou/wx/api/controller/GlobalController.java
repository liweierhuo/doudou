package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import com.doudou.wx.api.util.DouFileUtils;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
    private DouFileUtils fileUtils;

    @GetMapping("/error")
    public ApiResponse error() {
        return ApiResponse.error();
    }

    @GetMapping("/heart.jsp")
    public int heart() {
        return 1;
    }

    @PostMapping("/api/common/upload")
    public ApiResponse upload(MultipartFile file,@RequestParam("type") String type) {
        log.info("type:"+type);
        String filePath = fileUtils.saveToLocalServer(file,type,getRequest());
        log.info(filePath);
        return new ApiResponse<>(filePath);
    }
	
}
