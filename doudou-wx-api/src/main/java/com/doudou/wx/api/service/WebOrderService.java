package com.doudou.wx.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.util.RedisUtil;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Order;
import com.doudou.dao.service.IOrderService;
import com.doudou.dao.service.IResourceService;
import com.doudou.wx.api.vo.ExchangeResourceVO;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-03
 */
@Service
public class WebOrderService {

    @Resource
    private WebIntegralService integralService;
    @Resource
    private IOrderService orderService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private IResourceService resourceService;

    @Transactional(rollbackFor = Throwable.class)
    public void process(String clientId, ExchangeResourceVO exchangeResourceVO, DataResource dataResource) {
        //扣减积分
        integralService.expendIntegral(clientId,exchangeResourceVO.getIntegral());
        //扣件资源剩余数量
        resourceService.updateResourceRemainingNum(exchangeResourceVO.getResourceId(),dataResource.getRemainingNum() - 1);
        //插入订单
        orderService.save(buildOrder(clientId,exchangeResourceVO));
    }

    public int countUserResourceNum(String clientId) {
        return orderService.countUserResource(clientId);
    }

    public List<DataResource> pageUserResource(String clientId, Page pageQuery) {
        return orderService.pageUserResource(clientId,pageQuery);
    }

    private Order buildOrder(String clientId,ExchangeResourceVO exchangeResourceVO) {
        Order order = new Order();
        order.setClientId(clientId);
        order.setOrderId(redisUtil.genericUniqueId("O"));
        order.setPrice(exchangeResourceVO.getIntegral());
        order.setResourceId(exchangeResourceVO.getResourceId());
        order.setStatus(OrderStatusEnum.DONE.name());
        return order;
    }

    public enum OrderStatusEnum {
        /**
         * 订单状态枚举
         */
        INIT,PENDING,PROCESSING,DONE
    }

}
