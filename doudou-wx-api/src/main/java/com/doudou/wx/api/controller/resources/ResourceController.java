package com.doudou.wx.api.controller.resources;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageResult;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.entity.resources.DdResources;
import com.doudou.dao.repository.member.IDdUserService;
import com.doudou.dao.repository.resources.IDdResourcesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ResourceController
 * @Description 资源控制器
 * @Author shenliuhai
 * @Date 2019/12/15 16:18
 **/
@RestController
@Slf4j
@RequestMapping("resource")
public class ResourceController {

    private final IDdResourcesService resourcesService;
    private final IDdUserService userService;

    public ResourceController(IDdResourcesService resourcesService,IDdUserService userService) {
        this.resourcesService = resourcesService;
        this.userService = userService;
    }

    /**
     * @author shenliuhai
     * @date 2019/12/15 17:10
     * @param page
     * @param searchString 搜索词
     * @return com.doudou.core.web.PageResult<com.doudou.dao.entity.resources.DdResources>
     */
    @RequestMapping("res/list")
    public ApiResponse<DdResources> list(Page<DdResources> page, String searchString) {

        PageResult<DdResources> pageResult = new PageResult<>();

        //设置没有显示10条
        page.setSize(10);
        try {

            BaseMapper<DdResources> baseMapper = resourcesService.getBaseMapper();
            QueryWrapper<DdResources> queryWrapper = new QueryWrapper<>();
            //过滤条件
            queryWrapper.like("res_title",searchString);
            queryWrapper.or().like("res_publish",searchString);
            queryWrapper.or().like("res_auth",searchString);
            IPage<DdResources> iPage = baseMapper.selectPage(page, queryWrapper);

            //数据封装
            pageResult.buildPageResult(iPage.getCurrent(),iPage.getSize(),
                    iPage.getTotal(),iPage.getRecords());
            return ApiResponse.success(pageResult);
        } catch (Exception e) {
            log.error("获取资源列表失败",e);
            return ApiResponse.error();
        }

    }

    @RequestMapping("user/list")
    public ApiResponse<DdUser> userList(Page<DdUser> page, String searchString) {

        PageResult<DdUser> pageResult = new PageResult<>();

        //设置没有显示10条
        page.setSize(10);
        try {

            BaseMapper<DdUser> baseMapper = userService.getBaseMapper();
            QueryWrapper<DdUser> queryWrapper = new QueryWrapper<>();
            //过滤条件
            queryWrapper.like("username",searchString);

            IPage<DdUser> iPage = baseMapper.selectPage(page, queryWrapper);

            //fan数据封装
            pageResult.buildPageResult(iPage.getCurrent(),iPage.getSize(),
                    iPage.getTotal(),iPage.getRecords());
            return ApiResponse.success(pageResult);
        } catch (Exception e) {
            log.error("获取资源列表失败",e);
            return ApiResponse.error();
        }

    }


}
