package com.doudou.wx.api.controller.resources;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.Constant;
import com.doudou.core.constant.MemberConstant;
import com.doudou.core.web.annotation.Authorization;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.entity.resources.DdResources;
import com.doudou.dao.entity.resources.DdUserIntegral;
import com.doudou.dao.entity.resources.DdUserResource;
import com.doudou.dao.service.member.IDdUserService;
import com.doudou.dao.service.resources.IDdResourcesService;
import com.doudou.dao.service.resources.IDdUserIntegralService;
import com.doudou.dao.service.resources.IDdUserResourceService;
import com.doudou.wx.api.biz.CurrentUserBizService;
import com.doudou.wx.api.vo.AjaxResponse;
import com.doudou.wx.api.vo.output.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName ResourceController
 * @Description 资源控制器
 * @Author shenliuhai
 * @Date 2019/12/15 16:18
 **/
@RestController
@Slf4j
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private IDdResourcesService resourcesService;
    @Autowired
    private IDdUserService userService;
    @Autowired
    private CurrentUserBizService currentUserBizService;
    @Autowired
    private IDdUserResourceService userResourceService;
    @Autowired
    private IDdUserIntegralService userIntegralService;


    /**
     * @author shenliuhai
     * @date 2019/12/15 17:10
     * @param page
     * @param searchString 搜索词
     * @return AjaxResponse
     */
    @RequestMapping("/list")
    public AjaxResponse list(Page page, String searchString) {

        try {
            List<DdResources> resourcesList = resourcesService.listPage(page,searchString);

            return AjaxResponse.success(resourcesList,page);
        } catch (Exception e) {
            log.error("获取资源列表失败,{}",e);
            e.printStackTrace();
            return AjaxResponse.error();
        }

    }

    /**
     * 资源详情
     * @author shenliuhai
     * @date 2020/1/5 18:18
     * @param redId
     * @param request
     * @return com.doudou.wx.api.vo.AjaxResponse
     */
    @Authorization
    @RequestMapping("/details")
    public AjaxResponse resDetails(String redId, HttpServletRequest request) {
        if (StringUtils.isEmpty(redId)) {
            return AjaxResponse.error(1000,"参数为空");
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        String userId = currentUser.getId();

        DdUserResource userResource = userResourceService.getOne(new QueryWrapper<DdUserResource>().
                eq("user_id", userId).eq("res_id", redId));

        DdResources ddResources = resourcesService.getById(redId);
        if (userResource != null) {
            ddResources.setResConvertIntegral(Integer.valueOf(0));
        }

        if (StringUtils.isNotEmpty(ddResources.getResContent())) {
            //富文本转化
            String content = StringEscapeUtils.unescapeHtml4(ddResources.getResContent());

            ddResources.setResContent(content);
        }

        return AjaxResponse.success(ddResources);
    }


    /**
     * 兑换资源
     * @author shenliuhai
     * @date 2020/1/5 17:16
     * @param redId
     * @return com.doudou.wx.api.vo.AjaxResponse
     */
    @Authorization
    @RequestMapping("/convert")
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public AjaxResponse convert(String redId, HttpServletRequest request) {

        if (StringUtils.isEmpty(redId)) {
            return AjaxResponse.error(1000,"参数为空");
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        try {
            String userId = currentUser.getId();

            DdUser ddUser = userService.getUserById(userId);

            Integer totalIntegral = ddUser.getUserTotalIntegral();

            DdUserResource userResource = userResourceService.getOne(new QueryWrapper<DdUserResource>().
                    eq("user_id", userId).eq("res_id", redId));

            DdResources ddResources = resourcesService.getById(redId);

            Integer convertIntegral = ddResources.getResConvertIntegral() == null ?
                                            0 : ddResources.getResConvertIntegral() ;

            Integer resUploadNum = ddResources.getResUploadNum() == null ?
                                            0 : ddResources.getResUploadNum();

            if (StringUtils.isNotEmpty(ddResources.getResContent())) {
                String content = StringEscapeUtils.unescapeHtml4(ddResources.getResContent());
                ddResources.setResContent(content);
            }


            if (Integer.valueOf(0).equals(convertIntegral)) {
                //不需要积分
                ddResources.setResUploadNum(++resUploadNum);
                resourcesService.saveOrUpdate(ddResources);

                return AjaxResponse.success(ddResources);
            } else if (userResource != null) {
                //已经兑换，不在需要积分
                ddResources.setResUploadNum(++resUploadNum);
                resourcesService.saveOrUpdate(ddResources);

                return AjaxResponse.success(ddResources);
            } else if (totalIntegral >= convertIntegral) {
                //用户减积分
                int total = totalIntegral - convertIntegral;
                ddUser.setUserTotalIntegral(total);
                userService.saveOrUpdate(ddUser);

                //插入一条消费积分记录
                DdUserIntegral ddUserIntegral = new DdUserIntegral();
                ddUserIntegral.setUserId(userId);
                String consumeIntegral = "-"+convertIntegral;
                ddUserIntegral.setUserIntegral(Integer.valueOf(consumeIntegral));
                ddUserIntegral.setType(MemberConstant.INTEGRAL_TYPE_CONSUME);
                userIntegralService.save(ddUserIntegral);

                //插入一条兑换资源记录
                DdUserResource convertResource = new DdUserResource();
                convertResource.setUserId(userId);
                convertResource.setUserResType(MemberConstant.RES_TYPE_CONVERT);
                convertResource.setResId(redId);
                userResourceService.save(convertResource);

                //资源发布者得积分
                DdUser publisher = userService.getUserById(ddResources.getUserId());
                Integer publisherTotal = publisher.getUserTotalIntegral() + convertIntegral;
                publisher.setUserTotalIntegral(publisherTotal);
                userService.saveOrUpdate(publisher);

                //插入一条获得积分记录
                DdUserIntegral publisherIntegral = new DdUserIntegral();
                publisherIntegral.setUserId(ddResources.getUserId());
                String produceIntegral = "+" + convertIntegral;
                publisherIntegral.setUserIntegral(Integer.valueOf(produceIntegral));
                publisherIntegral.setType(MemberConstant.INTEGRAL_TYPE_PUBLISH);
                userIntegralService.save(publisherIntegral);

                ddResources.setResUploadNum(++resUploadNum);
                resourcesService.saveOrUpdate(ddResources);

                return AjaxResponse.success(ddResources);
            } else {
                return AjaxResponse.error(1000,"抱歉!,您的积分不够!");
            }
        }catch (Exception e) {
            log.error("兑换资源失败,{}",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return AjaxResponse.error();
    }


    @Authorization
    @PostMapping("/saveOrUpdate")
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public AjaxResponse saveOrUpdate(DdResources ddResources, HttpServletRequest request) {

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        String userId = currentUser.getId();

        try {
            if (StringUtils.isEmpty(ddResources.getId())) {
                ddResources.setUserId(userId);
                if (StringUtils.isNotEmpty(ddResources.getResContent())) {
                    String content = StringEscapeUtils.escapeHtml4(ddResources.getResContent());
                    ddResources.setResContent(content);
                }
                //默认为未审核
                ddResources.setCheckStatus(Constant.CHECK_STATUS_AWAIT);
                //兑换资源类型
                ddResources.setResType(Constant.RES_TYPE_CONVERT);
                resourcesService.save(ddResources);

                DdUserResource userResource = new DdUserResource();
                userResource.setUserId(userId);
                userResource.setResId(ddResources.getId());
                userResource.setUserResType(MemberConstant.RES_TYPE_PUBLISH);
                userResourceService.save(userResource);
            } else {
                resourcesService.updateById(ddResources);
            }
            return AjaxResponse.success();
        } catch (Exception e) {
            log.error("发布失败,{}",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResponse.error();
        }

    }

}
