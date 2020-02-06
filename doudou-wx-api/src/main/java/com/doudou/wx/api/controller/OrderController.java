package com.doudou.wx.api.controller;

import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Integral;
import com.doudou.dao.entity.Order;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IOrderService;
import com.doudou.dao.service.IResourceService;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.service.IntegralService;
import com.doudou.wx.api.vo.ExchangeResourceVO;
import javax.annotation.Resource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Resource
    private IUserService userService;
    @Resource
    private IntegralService integralService;
    @Resource
    private IOrderService orderService;

    @PostMapping("submit")
    public ApiResponse exchangeResource(@SessionId String sessionId, @RequestBody ExchangeResourceVO exchangeResourceVO) {
        //检查资源剩余数量是否还存在，是否兑换过
        Assert.notNull(exchangeResourceVO,"request is required");
        Assert.hasText(exchangeResourceVO.getResourceId(),"resourceId is required");
        Assert.notNull(exchangeResourceVO.getIntegral(),"Integral is required");
        User userInfo = userService.queryByOpenId(sessionId);
        DataResource dataResource = resourceService.getResource(exchangeResourceVO.getResourceId());
        Assert.notNull(dataResource,"资源不存在");
        Assert.isTrue(dataResource.getRemainingNum() > 0,"资源库存不足");
        Assert.isTrue(exchangeResourceVO.getIntegral().equals(dataResource.getPrice()),"传入的积分与所需积分不一致");
        Integral integral = integralService.getIntegralByClientId(userInfo.getClientId());
        Assert.isTrue(integral.getUserIntegral() >= dataResource.getPrice(),"您的积分不足");
        Order orderInfo = orderService.getOrder(userInfo.getClientId(), dataResource.getResourceId());
        Assert.isTrue( orderInfo == null,"您已经兑换过该资源");
        //process()
        //扣减积分
        integralService.expendIntegral(userInfo.getClientId(),dataResource.getPrice());
        //扣件资源剩余数量
        //插入订单
        return ApiResponse.success();
    }

}
