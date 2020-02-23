package com.doudou.wx.api.service;

import com.doudou.core.constant.ResourceStatusEnum;
import com.doudou.core.constant.ResourceTypeEnum;
import com.doudou.core.util.RedisUtil;
import com.doudou.core.web.ApiResponse;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IResourceService;
import com.doudou.dao.service.IUserService;
import com.doudou.wx.api.util.DouFileUtils;
import com.doudou.wx.api.util.ValidatorUtil;
import com.doudou.wx.api.vo.ResourceVO;
import com.github.kevinsawicki.http.HttpRequest;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-23
 */
@Service
@Slf4j
public class WebResourceService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private IResourceService resourceService;

    @Resource
    private IUserService userService;

    @Resource
    private DouFileUtils douFileUtils;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("${admin.audit.url}")
    private String adminAuditUrl;

    private static final int HTTP_RESPONSE_CODE = 200;

    public ApiResponse<String> addResource(ResourceVO resourceVO) {
        checkParam(resourceVO);
        String requestId = UUID.randomUUID().toString();
        return redisUtil.buessineslock(resourceVO.getUrl(),requestId,10,() -> {
            resourceVO.setStatus(ResourceStatusEnum.PENDING.name());
            resourceVO.setResType(ResourceTypeEnum.ONLINE.name());
            resourceVO.setRemainingNum(resourceVO.getTotalNum());
            String resourceId = redisUtil.genericUniqueId("R");
            resourceVO.setResourceId(resourceId);
            boolean result = resourceService.save(resourceVO);
            if (!result){
                return ApiResponse.error();
            }
            return new ApiResponse<>(resourceId);
        });
    }

    public ResourceVO getResourceById(String resourceId) {
        Assert.hasText(resourceId,"resourceId 不能为空");
        DataResource dataResource = resourceService.getResource(resourceId);
        Assert.notNull(dataResource,"资源不能为空");
        ResourceVO resourceVO = new ResourceVO();
        BeanUtils.copyProperties(dataResource,resourceVO);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        resourceVO.setPublishDate(dataResource.getCreated().format(dateTimeFormatter));
        User userInfo = userService.queryByClientId(dataResource.getClientId());
        Assert.notNull(userInfo,"用户不存在");
        resourceVO.setPublisher(userInfo.getNickName());
        resourceVO.setPublisherIcon(userInfo.getIcon());
        //更新查看次数
        threadPoolTaskExecutor.execute(() -> {
            log.info("异步更新资源[{}]浏览次数",dataResource.getResourceId());
            DataResource updateBean = new DataResource();
            updateBean.setResourceId(dataResource.getResourceId());
            updateBean.setViewNum((dataResource.getViewNum() == null ? 0 : dataResource.getViewNum()) + 1);
            resourceService.updateResource(updateBean);
        });
        return resourceVO;
    }

    public ApiResponse<String> batchAddResource(List<ResourceVO> resourceList,String clientId, HttpServletRequest request) {
        int successCount = 0;
        int failedCount = 0;
        for (ResourceVO resourceVO : resourceList) {
            String localPath = douFileUtils.saveInternetImageToLocal((resourceVO.getImageUrl()));
            resourceVO.setImageUrl(douFileUtils.toServerPath(localPath,request));
            resourceVO.setRemark("批量发布");
            try {
                resourceVO.setClientId(clientId);
                ApiResponse<String> apiResponse = addResource(resourceVO);
                if (apiResponse.getCode() == 0) {
                    successCount++;
                    //异步触发自动审核
                    handleAutoAuditResource(apiResponse.getData(),resourceVO.getPrice());
                } else {
                    failedCount++;
                }
            } catch (Exception e) {
                log.error("[{}] 资源发布失败，",resourceVO.getUrl(),e);
                failedCount++;
            }
        }
        String message = String.format("添加成功%d条，失败%d条",successCount,failedCount);
        return new ApiResponse<>(message);
    }


    @SneakyThrows
    private void checkParam(ResourceVO resourceVO) {
        Assert.notNull(resourceVO,"request is required");
        Assert.hasText(resourceVO.getTitle(),"title is required");
        Assert.notNull(resourceVO.getTotalNum(),"total num is required");
        String url = resourceVO.getUrl();
        Assert.isTrue(ValidatorUtil.isUrl(url),"url格式不正确");
        Assert.isTrue(resourceService.countResourceByUrl(url) <= 0,"资源链接已经存在");
        HttpRequest response = HttpRequest.get(url);
        Assert.isTrue(response.getConnection().getResponseCode() == HTTP_RESPONSE_CODE,"URL无法访问");
    }



    private void handleAutoAuditResource(String resourceId,Integer rewardIntegral) {
        threadPoolTaskExecutor.execute(() -> {
            Map<String,Object> paramMap = new HashMap<>(2);
            paramMap.put("resourceId",resourceId);
            paramMap.put("rewardIntegral",rewardIntegral);
            HttpRequest response = HttpRequest.post(adminAuditUrl, paramMap, true);
            log.info("审核结果：[{}]",response.body());
        });
    }

}
