package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.doudou.core.web.ApiResponse;
import com.doudou.dao.entity.Ad;
import com.doudou.dao.service.IAdService;
import com.doudou.wx.api.vo.AdVO;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("{adId}")
    public ApiResponse getAdDetail(@PathVariable("adId") String adId) {
        Assert.hasText(adId,"adId is required");
        Ad ad = adService.getOne(new QueryWrapper<Ad>()
            .eq("ad_id", adId));
        AdVO adVO = new AdVO();
        BeanUtils.copyProperties(ad,adVO);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        adVO.setCreatFormatDate(ad.getCreated().format(dateTimeFormatter));
        return new ApiResponse<>(adVO);
    }


    public enum AdStatusEnum {
        /**
         * 广告状态枚举
         */
        NORMAL,FAIL
    }
	
}
