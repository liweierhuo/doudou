package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.ResourceStatusEnum;
import com.doudou.core.service.WebIntegralService;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Integral;
import com.doudou.dao.entity.Order;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IOrderService;
import com.doudou.dao.service.IResourceService;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.service.WebOrderService;
import com.doudou.wx.api.vo.ExchangeResourceVO;
import javax.annotation.Resource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private WebIntegralService integralService;
    @Resource
    private IOrderService orderService;
    @Resource
    private WebOrderService webOrderService;

    @PostMapping("submit")
    public ApiResponse exchangeResource(@SessionId String clientId, @RequestBody ExchangeResourceVO exchangeResourceVO) {
        //检查资源剩余数量是否还存在，是否兑换过
        Assert.notNull(exchangeResourceVO,"request is required");
        Assert.hasText(exchangeResourceVO.getResourceId(),"resourceId is required");
        Assert.notNull(exchangeResourceVO.getIntegral(),"Integral is required");
        User userInfo = userService.queryByClientId(clientId);
        DataResource dataResource = resourceService.getResource(exchangeResourceVO.getResourceId());
        Assert.notNull(dataResource,"资源不存在");
        Assert.isTrue(ResourceStatusEnum.NORMAL.name().equalsIgnoreCase(dataResource.getStatus()),"资源状态异常");
        Assert.isTrue(dataResource.getRemainingNum() > 0,"资源库存不足");
        Assert.isTrue(exchangeResourceVO.getIntegral().equals(dataResource.getPrice()),"传入的积分与所需积分不一致");
        Assert.isTrue(!dataResource.getClientId().equalsIgnoreCase(clientId),"自己发布的资源不能兑换");
        Integral integral = integralService.getIntegralByClientId(userInfo.getClientId());
        Assert.isTrue(integral.getUserIntegral() >= dataResource.getPrice(),"您的积分不足");
        Order orderInfo = orderService.getOrder(userInfo.getClientId(), dataResource.getResourceId());
        Assert.isTrue( orderInfo == null,"您已经兑换过该资源");
        //开始处理
        webOrderService.process(userInfo.getClientId(),exchangeResourceVO,dataResource);
        return ApiResponse.success();
    }

    @GetMapping("/resource")
    public ApiResponse getExchangeResource(@SessionId String clientId, PageRequestVO pageRequestVO) {
        Assert.notNull(userService.queryByClientId(clientId),"用户信息为空");
        Page<DataResource> page = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        page.setRecords(orderService.pageUserOrderResource(clientId,page));
        return new ApiResponse<>(page);
    }

    @GetMapping("/resource/{resourceId}")
    public ApiResponse checkUserResource(@SessionId String clientId, @PathVariable String resourceId) {
        Assert.notNull(userService.queryByClientId(clientId),"用户信息为空");
        boolean flag = false;
        if (orderService.getOrder(clientId,resourceId) != null) {
            flag = true;
        }
        DataResource dataResource = resourceService.getResource(resourceId);
        if (dataResource.getClientId().equalsIgnoreCase(clientId)) {
            flag = true;
        }
        return new ApiResponse<>(flag);
    }
}
