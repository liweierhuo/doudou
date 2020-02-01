package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.service.IResourceService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController extends BaseController{

    @Resource
    private IResourceService resourceService;

    @GetMapping("list")
    public ApiResponse getPageResource(PageRequestVO<DataResource> pageRequestVO) {
        IPage<DataResource> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        IPage<DataResource> result = resourceService.page(pageQuery);
        return new ApiResponse<>(result);
    }

    @PostMapping("add")
    public ApiResponse addResource(@RequestBody DataResource dataResource) {
        boolean result = resourceService.save(dataResource);
        if (!result){
            return ApiResponse.error();
        }
        return ApiResponse.success();
    }
}
