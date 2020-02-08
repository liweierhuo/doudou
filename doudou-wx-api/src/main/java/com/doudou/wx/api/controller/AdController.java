package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.doudou.core.web.ApiResponse;
import com.doudou.dao.entity.Ad;
import com.doudou.dao.service.IAdService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户签到表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/api/ad")
public class AdController extends BaseController {
    @Resource
    private IAdService adService;

    @GetMapping("list")
    public ApiResponse getAdList() {
        List<Ad> resultList = adService.list(new QueryWrapper<Ad>()
            .eq("status", AdStatusEnum.NORMAL.name()));
        return new ApiResponse<>(resultList);
    }

    public enum AdStatusEnum {
        /**
         * 广告状态枚举
         */
        NORMAL,FAIL
    }
	
}
