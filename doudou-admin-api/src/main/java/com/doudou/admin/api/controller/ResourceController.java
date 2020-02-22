package com.doudou.admin.api.controller;

import com.doudou.admin.api.service.WebResourceService;
import com.doudou.admin.api.vo.ResourceVO;
import com.doudou.core.web.ApiResponse;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-22
 */
@RestController
@RequestMapping("/api/admin/resource")
@Slf4j
public class ResourceController extends BaseController {

    @Resource
    private WebResourceService webResourceService;

    @PostMapping("audit")
    public ApiResponse auditResource(@RequestBody ResourceVO resourceVO) {
        webResourceService.auditResource(resourceVO);
        return ApiResponse.success();
    }

}
