package com.doudou.wx.api.controller.ad;

import com.doudou.dao.entity.ad.DdAd;
import com.doudou.dao.service.ad.IDdAdService;
import com.doudou.wx.api.vo.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName AdController
 * @Description
 * @Author shenliuhai
 * @Date 2020/2/4 18:13
 **/
@RestController
@Slf4j
@RequestMapping("ad")
public class AdController {

    @Autowired
    private IDdAdService ddAdService;

    /**
     * 广告列表
     * @author shenliuhai
     * @date 2020/2/4 18:15
     */
    @RequestMapping("list")
    public AjaxResponse list() {
        List<DdAd> list = ddAdService.list();
        return AjaxResponse.success(list);
    }

    @PostMapping("saveOrUpdate")
    public AjaxResponse saveOrUpdate(DdAd ddAd, HttpServletRequest request) {

        ddAdService.save(ddAd);

        return AjaxResponse.success();
    }
}
