package com.doudou.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.mapper.ResourceMapper;
import com.doudou.dao.service.IResourceService;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, DataResource> implements IResourceService {

    /**
     * @param resourceId
     * @return
     */
    @Override
    public DataResource getResource(String resourceId) {
        Assert.hasText(resourceId,"resourceId is required");
        return getOne(new QueryWrapper<DataResource>().eq("resource_id",resourceId));
    }

    @Override
    public void updateResourceRemainingNum(String resourceId,Integer remainingNum) {
        Assert.notNull(remainingNum,"剩余数量不能为空");
        DataResource dataResource = getResource(resourceId);
        DataResource updateBean = new DataResource();
        updateBean.setRemainingNum(remainingNum);
        updateBean.setDownloadNum((dataResource.getDownloadNum() == null ? 0 : dataResource.getDownloadNum()) + 1);
        boolean updateResult = update(updateBean, new QueryWrapper<DataResource>()
            .eq("resource_id", dataResource.getResourceId())
            .eq("remaining_num", dataResource.getRemainingNum()));
        Assert.isTrue(updateResult,"更新失败");
    }

    @Override
    public void updateResource(DataResource dataResource) {
        Assert.notNull(dataResource,"更新对象不能为空");
        Assert.hasText(dataResource.getResourceId(),"resourceId is required");
        DataResource updateBean = new DataResource();
        if (!StringUtils.isEmpty(dataResource.getTitle())) {
            updateBean.setTitle(dataResource.getTitle());
        }
        if (!StringUtils.isEmpty(dataResource.getTitle())) {
            updateBean.setTitle(dataResource.getTitle());
        }
        if (!StringUtils.isEmpty(dataResource.getSubtitle())) {
            updateBean.setSubtitle(dataResource.getSubtitle());
        }
        if (!StringUtils.isEmpty(dataResource.getSource())) {
            updateBean.setSource(dataResource.getSource());
        }
        if (dataResource.getDownloadNum() != null) {
            updateBean.setDownloadNum(dataResource.getDownloadNum());
        }
        if (!StringUtils.isEmpty(dataResource.getImageUrl())) {
            updateBean.setImageUrl(dataResource.getImageUrl());
        }
        if (dataResource.getPrice() != null) {
            updateBean.setPrice(dataResource.getPrice());
        }
        if (dataResource.getViewNum() != null) {
            updateBean.setViewNum(dataResource.getViewNum());
        }
        if (!StringUtils.isEmpty(dataResource.getResSummary())) {
            updateBean.setResSummary(dataResource.getResSummary());
        }
        if (!StringUtils.isEmpty(dataResource.getStatus())) {
            updateBean.setStatus(dataResource.getStatus());
        }
        updateBean.setModified(new Date());
        boolean updateResult = update(updateBean, new QueryWrapper<DataResource>().eq("resource_id", dataResource.getResourceId()));
        Assert.isTrue(updateResult,"更新失败");
    }

    @Override
    public IPage<DataResource> pageResource(DataResource dataResource, IPage<DataResource> pageQuery) {
        return page(pageQuery,new QueryWrapper<DataResource>()
            .eq("client_id",dataResource.getClientId())
            .eq("status",dataResource.getStatus())
        );
    }

    @Override
    public int countResourceByUrl(String url) {
        return count(new QueryWrapper<DataResource>().eq("url",url));
    }

    @Override
    public int countPublishResourceNum(String clientId, String status) {
        return count(new QueryWrapper<DataResource>()
            .eq("client_id",clientId)
            .eq("status",status));
    }
}
