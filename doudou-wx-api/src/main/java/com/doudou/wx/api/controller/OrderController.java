package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.service.IResourceService;
import com.doudou.wx.api.vo.ExchangeResourceVO;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表  前端控制器
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController {

    @Resource
    private IResourceService resourceService;

    @GetMapping
    public ApiResponse exchangeResource(@SessionId String sessionId, @RequestBody ExchangeResourceVO exchangeResourceVO) {
        //检查资源剩余数量是否还存在，是否兑换过
        //扣减积分
        //扣件资源剩余数量
        //插入订单
        return ApiResponse.success();
    }

}
