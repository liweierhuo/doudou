package com.doudou.wx.api.util;

import com.doudou.core.constant.ResourceStatusEnum;
import com.doudou.dao.entity.DataResource;
import com.doudou.wx.api.vo.ResourceVO;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-03-08
 */
public class ResourceConvert {

    public static List<ResourceVO> convertResourceVO(List<DataResource> dataResourceList) {
        if (CollectionUtils.isEmpty(dataResourceList)) {
            return null;
        }
        return dataResourceList.stream().map(ResourceConvert::convertResourceVO).collect(Collectors.toList());
    }

    private static ResourceVO convertResourceVO(DataResource dataResource) {
        ResourceVO result = new ResourceVO();
        BeanUtils.copyProperties(dataResource,result);
        result.setStatusDesc(ResourceStatusEnum.valueOf(dataResource.getStatus()).getDesc());
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        result.setPublishDate(dateTimeFormatter.format(dataResource.getCreated()));
        return result;
    }

}
