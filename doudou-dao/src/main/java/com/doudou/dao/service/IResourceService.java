package com.doudou.dao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.doudou.dao.entity.DataResource;
import com.doudou.dao.entity.Order;
import java.util.List;

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

    /**
     * 更新资源信息
     * @param dataResource
     */
    void updateResource(DataResource dataResource);

    /**
     * 查询分页
     * @param dataResource
     * @param pageQuery
     * @return
     */
    IPage<DataResource> pageResource(DataResource dataResource, IPage<DataResource> pageQuery);

    /**
     * 检验资源url是否存在
     * @param url
     * @return
     */
    int countResourceByUrl(String url);
	
}
