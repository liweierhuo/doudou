package com.doudou.wx.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doudou.core.constant.ResourceStatusEnum;
import com.doudou.core.util.RedisUtil;
import com.doudou.core.web.PageRequestVO;
import com.doudou.dao.entity.FollowUser;
import com.doudou.dao.entity.User;
import com.doudou.dao.service.IFollowUserService;
import com.doudou.dao.service.IResourceService;
import com.doudou.wx.api.util.MyDateUtils;
import com.doudou.wx.api.vo.FollowUserVO;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-03-01
 */
@Service
@Slf4j
public class WebFollowUserService {

    @Resource
    private IFollowUserService followUserService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private WebUserService webUserService;

    @Resource
    private IResourceService resourceService;

    public void addFollowUser(FollowUserVO followUserVO) {
        Assert.hasText(followUserVO.getClientId(), "clientId is required");
        Assert.hasText(followUserVO.getFollowClientId(), "Follow clientId is required");
        Assert.isTrue(!followUserVO.getClientId().equalsIgnoreCase(followUserVO.getFollowClientId()), "您不能关注您自己");
        Assert.isTrue(!followUserCheck(followUserVO), "您已经关注过该用户了");
        followUserVO.setTxId(redisUtil.genericUniqueId("FOLLOW"));
        Assert.isTrue(followUserService.save(followUserVO), "添加失败");
    }

    public boolean followUserCheck(FollowUserVO followUserVO) {
        Assert.hasText(followUserVO.getClientId(), "clientId is required");
        Assert.hasText(followUserVO.getFollowClientId(), "Follow clientId is required");
        if (followUserVO.getClientId().equalsIgnoreCase(followUserVO.getFollowClientId())) {
            return true;
        }
        int count = followUserService.count(new QueryWrapper<FollowUser>()
            .eq("client_id", followUserVO.getClientId())
            .eq("follow_client_id", followUserVO.getFollowClientId()));
        return count > 0;
    }

    public void cancelFollow(FollowUserVO followUserVO) {
        Assert.hasText(followUserVO.getClientId(), "clientId is required");
        Assert.hasText(followUserVO.getFollowClientId(), "Follow clientId is required");
        FollowUser followUserInfo = followUserService.getOne(new QueryWrapper<FollowUser>()
            .eq("client_id", followUserVO.getClientId())
            .eq("follow_client_id", followUserVO.getFollowClientId()));
        Assert.notNull(followUserInfo, "取关失败，您尚未关注该用户");
        Assert.isTrue(followUserService.removeById(followUserInfo.getId()), "取关失败");
    }

    public FollowUserVO getUserFollowInfo(String clientId) {
        Assert.hasText(clientId, "clientId is required");
        int myFollowCount = followUserService.count(new QueryWrapper<FollowUser>().eq("client_id", clientId));
        int followMeCount = followUserService.count(new QueryWrapper<FollowUser>().eq("follow_client_id", clientId));
        FollowUserVO followUserVO = new FollowUserVO();
        followUserVO.setFollowMeCount(followMeCount);
        followUserVO.setMyFollowCount(myFollowCount);
        return followUserVO;
    }

    public IPage<FollowUserVO> pageMyFollow(String clientId, PageRequestVO pageRequestVO) {
        Assert.hasText(clientId, "clientId is required");
        IPage<FollowUser> pageQuery = new Page<>(pageRequestVO.getPageNo(), pageRequestVO.getPageSize());
        pageQuery = followUserService.page(pageQuery, new QueryWrapper<FollowUser>().eq("client_id", clientId));
        return buildFollowUserList(pageQuery, PageTypeEnum.MY_FOLLOW);
    }

    public IPage<FollowUserVO> pageFollowMe(String clientId, PageRequestVO pageRequestVO) {
        Assert.hasText(clientId, "clientId is required");
        IPage<FollowUser> pageQuery = new Page<>(pageRequestVO.getPageNo(), pageRequestVO.getPageSize());
        pageQuery = followUserService.page(pageQuery, new QueryWrapper<FollowUser>().eq("follow_client_id", clientId));
        return buildFollowUserList(pageQuery, PageTypeEnum.FOLLOW_ME);
    }

    private IPage<FollowUserVO> buildFollowUserList(IPage<FollowUser> page, PageTypeEnum type) {
        IPage<FollowUserVO> pageResult = new Page<>(page.getCurrent(), page.getSize());
        pageResult.setRecords(page.getRecords().stream().map(followUser -> convert(followUser, type)).collect(Collectors.toList()));
        return pageResult;
    }

    private FollowUserVO convert(FollowUser followUser, PageTypeEnum type) {
        FollowUserVO followUserVO = new FollowUserVO();
        BeanUtils.copyProperties(followUser, followUserVO);
        User userInfo;
        if (type == PageTypeEnum.MY_FOLLOW) {
            userInfo = webUserService.getSimpleUserInfo(followUser.getFollowClientId());
        } else {
            userInfo = webUserService.getSimpleUserInfo(followUser.getClientId());
        }
        if (userInfo != null) {
            followUserVO.setFollowUserIcon(userInfo.getIcon());
            followUserVO.setFollowUserNickname(userInfo.getNickName());
            long days = ChronoUnit.DAYS.between(MyDateUtils.date2LocalDate(userInfo.getCreated()), LocalDateTime.now());
            followUserVO
                .setLabel("来了" + days + "天，发布了" + resourceService.countPublishResourceNum(userInfo.getClientId(), ResourceStatusEnum.NORMAL.name()) + "个资源");
        }
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        followUserVO.setCreateDate(dateTimeFormatter.format(followUser.getCreated()));
        return followUserVO;
    }

    @AllArgsConstructor
    enum PageTypeEnum {
        /**
         * 列表渲染类型
         */
        MY_FOLLOW, FOLLOW_ME;
    }

}
