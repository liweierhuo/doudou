package com.doudou.wx.api.controller.member;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.MemberConstant;
import com.doudou.core.web.annotation.Authorization;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.entity.resources.DdUserIntegral;
import com.doudou.dao.entity.resources.DdUserResource;
import com.doudou.dao.entity.resources.DdUserSign;
import com.doudou.dao.repository.member.IDdUserService;
import com.doudou.dao.repository.resources.IDdUserIntegralService;
import com.doudou.dao.repository.resources.IDdUserResourceService;
import com.doudou.dao.repository.resources.IDdUserSignService;
import com.doudou.wx.api.biz.CurrentUserBizService;
import com.doudou.wx.api.biz.UserIntegralBizService;
import com.doudou.wx.api.util.DateUtils;
import com.doudou.wx.api.vo.AjaxResponse;
import com.doudou.wx.api.vo.output.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserSignController
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 21:52
 **/
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private IDdUserIntegralService integralService;

    @Autowired
    private IDdUserSignService signService;

    @Autowired
    private IDdUserResourceService userResourceService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private IDdUserService userService;

    @Autowired
    private UserIntegralBizService userIntegralBizService;

    /**
     * 获取用户积分详情
     * @author shenliuhai
     * @date 2020/1/4 22:16
     * @param request
     * @return com.doudou.wx.api.vo.AjaxResponse
     */
    @Authorization
    @RequestMapping("/integral")
    public AjaxResponse getUserIntegral(HttpServletRequest request) {

        //获取当前登录用户
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        try {
            String userId = currentUser.getId();

            Map<String,Object> data = new HashMap<>();
            DdUser userById = userService.getUserById(userId);
            //总积分
            data.put("totalIntegral",userById.getUserTotalIntegral());
            //积分详情
            IPage<DdUserIntegral> userIntegralIPage = integralService.page(new Page<>(),
                    new QueryWrapper<DdUserIntegral>().eq("user_id", userId));

            data.put("userIntegralList",userIntegralIPage);
            return AjaxResponse.success(data);
        } catch (Exception e) {
            log.error("获取用户积分失败,{}",e);
            e.printStackTrace();

        }
        return AjaxResponse.error();
    }

    /**
     * 获取用户兑换资源列表
     * @author shenliuhai
     * @date 2020/1/4 22:31
     * @param request
     * @return com.doudou.wx.api.vo.AjaxResponse
     */
    @Authorization
    @RequestMapping("/convert/resource")
    public AjaxResponse getUserConvertRes(Page page,HttpServletRequest request) {

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        try {
            String userId = currentUser.getId();

            Map<String,Object> data = new HashMap<>();

            int count = userResourceService.count(new QueryWrapper<DdUserResource>().
                            eq("user_id", userId).
                            eq("user_res_type", 0));

            Map<String,Object> resList =  userResourceService.getList(page,userId, MemberConstant.RES_TYPE_CONVERT);

            data.put("total",count);
            data.put("resList",resList);
            data.put("page",page);

            return AjaxResponse.success(data);
        } catch (Exception e) {
            log.error("获取兑换资源列表失败,{}",e);
            e.printStackTrace();

        }
        return AjaxResponse.error();
    }


    /**
     * 获取用户发布资源列表
     * @author shenliuhai
     * @date 2020/1/4 23:28
     * @param page
     * @param request
     * @return com.doudou.wx.api.vo.AjaxResponse
     */
    @Authorization
    @RequestMapping("/publish/resource")
    public AjaxResponse getUserPublishRes(Page page,HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        try {
            String userId = currentUser.getId();

            Map<String,Object> data = new HashMap<>();

            Map<String, Object> publishList
                    = userResourceService.getList(page, userId, MemberConstant.RES_TYPE_PUBLISH);
            data.put("page",page);
            data.put("publishList",publishList);
            return AjaxResponse.success(data);
        } catch (Exception e) {
            log.error("获取用户发布资源列表失败,{}",e);
            e.printStackTrace();
        }
        return AjaxResponse.error();
    }


    /**
     * 获取注册时间天数
     * @author shenliuhai
     * @date 2020/1/4 22:58
     * @param request
     * @return com.doudou.wx.api.vo.AjaxResponse
     */
    @Authorization
    @RequestMapping("/register/days")
    public AjaxResponse getRegisterDays(HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        String userId = currentUser.getId();
        DdUser userById = userService.getUserById(userId);
        long days = DateUtils.pastDays(userById.getCreateDate());
        return AjaxResponse.success(days);
    }

    @Authorization
    @RequestMapping("/sign")
    public AjaxResponse sign(HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        String userId = currentUser.getId();
        DdUserSign userSign = signService.getById(userId);
        //获取当前时间
        String date = DateUtils.getDate(DateUtils.DATETIME_FORMAT);
        Date now = DateUtils.parseDate(date);
        if (userSign == null) {
            userSign.setSignNums(1);
            userSign.setSignDate(now);
            signService.saveOrUpdate(userSign);

            //添加积分
            userIntegralBizService.addIntegral(userId);

            return AjaxResponse.success("签到成功");
        }
        Date signDate =  userSign.getSignDate();
        Integer signNums = userSign.getSignNums();
        boolean sameDay = org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(), signDate);

        if (sameDay) {
            return AjaxResponse.error(1000,"今天已经签到了");
        } else {

            userSign.setSignNums(++signNums);
            userSign.setSignDate(now);
            signService.saveOrUpdate(userSign);

            userIntegralBizService.addIntegral(userId);
            return AjaxResponse.success("签到成功");
        }
    }


}
