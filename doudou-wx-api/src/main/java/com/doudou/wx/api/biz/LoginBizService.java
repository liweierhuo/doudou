package com.doudou.wx.api.biz;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.MemberConstant;
import com.doudou.core.password.util.AESEncryptUtil;
import com.doudou.core.redis.RedisManager;
import com.doudou.dao.entity.member.DdThirdUser;
import com.doudou.dao.entity.member.DdUser;
import com.doudou.dao.repository.member.IDdThirdUserService;
import com.doudou.dao.repository.member.IDdUserService;
import com.doudou.wx.api.vo.input.ThirdUserVo;
import com.doudou.wx.api.vo.output.CurrentUser;
import com.doudou.wx.api.vo.output.UserOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @ClassName LoginBizService
 * @Description
 * @Author shenliuhai
 * @Date 2020/1/4 17:29
 **/
@Service
@Slf4j
public class LoginBizService {

    @Autowired
    private IDdThirdUserService thirdUserService;

    @Autowired
    private IDdUserService userService;

    @Autowired
    private RedisManager redis;


    public UserOutput thirdUserLogin(ThirdUserVo thirdUserVo, HttpServletRequest request) {

        log.info("ThirdUserVo的详细信息,{}",thirdUserVo);

        String openId = thirdUserVo.getOpenId();
        String unionId = thirdUserVo.getUnionId();

        //通过openId找到用户
        boolean openIdFound = false;
        DdThirdUser ddThirdUser = null;
        ddThirdUser = thirdUserService.queryByOpenId(openId);
        if (ddThirdUser == null) {
            if (StringUtils.isNotEmpty(thirdUserVo.getUnionId())) {
                ddThirdUser = thirdUserService.queryByUnionId(unionId);
            }
        } else {
            openIdFound = true;
        }

        DdUser ddUser;
        //第三方账号信息不为空
        if (null != ddThirdUser) {
            ddUser = userService.getUserById(ddThirdUser.getUserId());
            try {
                if (ddUser == null) {
                    log.error("微信授权登录异常,thirdUserVo--{}",thirdUserVo);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            ddUser.setNickName(thirdUserVo.getNickName());
            ddUser.setLogo(thirdUserVo.getLogo());

            userService.updateById(ddUser);

            //更新unionId
            if (StringUtils.isNotEmpty(thirdUserVo.getUnionId())) {
                boolean updateFlag = false;
                if (StringUtils.isEmpty(ddThirdUser.getUserId())) {
                    updateFlag = true;
                } else if (!ddThirdUser.getUnionId().equals(thirdUserVo.getUnionId())) {
                    updateFlag = true;
                }

                if (updateFlag) {
                    ddThirdUser.setUnionId(thirdUserVo.getUnionId());
                    thirdUserService.updateById(ddThirdUser);
                }
            }

            if (StringUtils.isNotEmpty(thirdUserVo.getUnionId())) {
                DdThirdUser thirdUser =
                        thirdUserService.getByUnionIdType(thirdUserVo.getUnionId(),thirdUserVo.getType());
                if (null == thirdUser) {
                    createTpUser(ddUser,thirdUserVo);
                } else {
                    if (openIdFound == false) {
                        createTpUser(ddUser,thirdUserVo);
                    }
                }
            }

        } else {
            ddUser = creatUser(thirdUserVo,request);
        }

        UserOutput userOutput = getLoginMember(ddUser,thirdUserVo.getOpenId());
        return userOutput;

    }

    private UserOutput getLoginMember(DdUser ddUser, String openId) {
        UserOutput userOutput = new UserOutput();
        userOutput.setLogo(ddUser.getLogo());
        userOutput.setUsername(ddUser.getNickName());
        userOutput.setUserTotalIntegral(ddUser.getUserTotalIntegral());


        //获取旧的没有过期的token
        String oldToken = redis.getToken(ddUser.getId());
        if (StringUtils.isNotEmpty(oldToken)) {
            String userId = redis.getKeyByToken(oldToken);
            if (StringUtils.isNotEmpty(userId)) {
                userOutput.setToken(oldToken);
                return userOutput;
            }
        }

        //获取登录交互凭证
        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(ddUser.getId());
        currentUser.setLoginName(ddUser.getNickName());
        currentUser.setOpenId(openId);

        String token = this.getToken(currentUser);
        redis.createRelationship(ddUser.getId(),token);

        userOutput.setToken(token);
        return userOutput;

    }

    private String getToken(CurrentUser currentUser) {
        String token = JSONObject.toJSONString(currentUser);
        token = AESEncryptUtil.encrypt(token, currentUser.getId());
        return token;
    }

    private DdUser creatUser(ThirdUserVo thirdUserVo, HttpServletRequest request) {
        DdUser ddUser = new DdUser();
        ddUser.setNickName(thirdUserVo.getNickName());
        ddUser.setLogo(thirdUserVo.getNickName());
        //随机生成username
        StringBuilder username = new StringBuilder();
        int random2 = new Random().nextInt(10000000);
        username.append(String.format("duoduo_%",random2));
        ddUser.setUsername(username.toString());
        //初始密码为123456
        ddUser.setPassword(DigestUtils.md5Hex(MemberConstant.PASSWORD));
        //初始积分100
        ddUser.setUserTotalIntegral(MemberConstant.INTEGRAL);
        //用户类型默认为普通用户(1)
        ddUser.setUserType(MemberConstant.USER_TYPE);

        //创建用户
        userService.save(ddUser);
        //保存授权关系
        createTpUser(ddUser,thirdUserVo);

        return ddUser;
    }

    private DdThirdUser createTpUser(DdUser ddUser, ThirdUserVo thirdUserVo) {
        DdThirdUser thirdUser = new DdThirdUser();
        thirdUser.setUnionId(thirdUserVo.getUnionId());
        thirdUser.setOpenId(thirdUserVo.getOpenId());
        thirdUser.setUserId(ddUser.getId());
        thirdUser.setNickname(thirdUserVo.getNickName());
        thirdUser.setType(thirdUserVo.getType());

        thirdUserService.saveOrUpdate(thirdUser);

        return thirdUser;
    }

}
