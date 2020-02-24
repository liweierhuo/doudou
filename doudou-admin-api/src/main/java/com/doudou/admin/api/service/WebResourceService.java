package com.doudou.admin.api.service;

import com.doudou.admin.api.vo.ResourceVO;
import com.doudou.core.constant.IntegralTypeEnum;
import com.doudou.core.constant.ResourceStatusEnum;
import com.doudou.core.service.WebIntegralService;
import com.doudou.core.util.RedisUtil;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.service.IResourceService;
import java.util.UUID;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-22
 */
@Service
@Slf4j
public class WebResourceService {

    @Resource
    private IResourceService resourceService;

    @Resource
    private WebIntegralService webIntegralService;

    @Resource
    private RedisUtil redisUtil;

    @Transactional(rollbackFor = Throwable.class)
    public void auditResource(ResourceVO resourceVO) {
        Assert.notNull(resourceVO,"resourceVO is required");
        String resourceId = resourceVO.getResourceId();
        Integer rewardIntegral = resourceVO.getRewardIntegral();
        Assert.hasText(resourceId,"resourceId is required");
        Assert.notNull(rewardIntegral,"rewardIntegral is required");
        log.info("[{}] 审核开始...",resourceVO.getResourceId());
        DataResource dataResource = resourceService.getResource(resourceId);
        Assert.notNull(dataResource,"资源不存在");
        Assert.isTrue(ResourceStatusEnum.PENDING.name().equalsIgnoreCase(dataResource.getStatus()),"资源状态不正确，不允许审核");
        //检查资源
        String requestId = UUID.randomUUID().toString();
        redisUtil.buessineslock(resourceId,requestId,100,() -> {
            //修改资源状态
            DataResource updateResource = new DataResource();
            updateResource.setStatus(ResourceStatusEnum.NORMAL.name());
            updateResource.setResourceId(resourceId);
            resourceService.updateResource(updateResource);
            //给发布者积分奖励
            if (rewardIntegral.equals(0)) {
                log.info("奖励积分为0,不用奖励");
                return true;
            }
            webIntegralService.saveIntegral(dataResource.getClientId(),rewardIntegral, IntegralTypeEnum.PUBLISH_RESOURCE);
            log.info("[{}] 审核结束...",requestId);
            return true;
        });
    }
}
