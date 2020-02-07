package com.doudou.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.DataResource;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liwei
 * @since 2020-01-31
 */
public interface IResourceService extends IService<DataResource> {

    /**
     * 根据resourceId获取资源
     * @param resourceId
     * @return
     */
    DataResource getResource(String resourceId);

    /**
     * 更新资源信息
     * @param resourceId
     * @param remainingNum
     */
    void updateResourceRemainingNum(String resourceId,Integer remainingNum);
	
}
