package com.doudou.wx.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.IntegralOperateTypeEnum;
import com.doudou.core.web.ApiResponse;
import com.doudou.core.web.PageRequestVO;
import com.doudou.core.web.annotation.SessionId;
import com.doudou.dao.entity.Record;
import com.doudou.dao.service.IRecordService;
import com.doudou.wx.api.vo.IntegralRecordVO;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-14
 */
@RestController
@RequestMapping("/api/integral")
public class IntegralController extends BaseController{
    @Resource
    private IRecordService recordService;

    @GetMapping
    public ApiResponse index(@SessionId String clientId, PageRequestVO pageRequestVO) {
        IPage<Record> pageQuery = new Page<>(pageRequestVO.getPageNo(),pageRequestVO.getPageSize());
        pageQuery = recordService.pageResource(clientId,pageQuery);
        IPage<IntegralRecordVO> pageResult = new Page<>(pageQuery.getCurrent(),pageQuery.getSize());
        pageResult.setTotal(pageQuery.getTotal());
        pageResult.setPages(pageQuery.getPages());
        pageResult.setRecords(buildRecordVO(pageQuery.getRecords()));
        return new ApiResponse<>(pageResult);
    }

    private List<IntegralRecordVO> buildRecordVO(List<Record> records) {
        if (CollectionUtils.isEmpty(records)) {
            return new ArrayList<>();
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return records.stream().map(record -> IntegralRecordVO.builder()
            .integral(IntegralOperateTypeEnum.valueOf(record.getType()).getInOrOut()+record.getIntegral())
            .transDate(record.getCreated().format(dateTimeFormatter))
            .type(record.getType())
            .typeDesc(IntegralOperateTypeEnum.valueOf(record.getType()).getDesc())
            .id(record.getId())
            .build()).collect(Collectors.toList());
    }

}
