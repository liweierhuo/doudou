package com.doudou.wx.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.alibaba.fastjson.JSON;
import com.doudou.core.constant.ResourceTypeEnum;
import com.doudou.wx.api.vo.ExchangeResourceVO;
import com.doudou.wx.api.vo.ResourceVO;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2020-02-07
 */
public class ResourceControllerTest extends AbstractControllerTest{

    @Test
    public void saveResource() {
        try {
            ResourceVO resourceVO = new ResourceVO();
            resourceVO.setTitle("极客时间·数据结构与算法之美");
            resourceVO.setPrice(5);
            resourceVO.setResType(ResourceTypeEnum.ONLINE.name());
            resourceVO.setImageUrl("https://cdn17.pingpongx.com/project/web-front/assets/imgs/common/logo/logo.png");
            resourceVO.setResSummary("我确认，上述信息是正确的，与我提供并且出现在亚马逊卖家中心的信息匹配。我同意且承认，PingPong会在未来依赖这些信息及我的确认。");
            resourceVO.setSource("极客时间");
            resourceVO.setSubtitle("第4讲-关于人性");
            resourceVO.setTotalNum(20);
            resourceVO.setRemainingNum(18);
            resourceVO.setUrl("https://dev-us.pingpongx.com/store/welcomeletter/qoo10/sr2019121917422147c4?storeName=Qoo10-12.19");
            MvcResult result = super.perform(post("/api/resource/add")
                .content(JSON.toJSONString(resourceVO)));
            System.out.println("添加资源：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getResourceList() {
        try {
            MvcResult result = super.perform(get("/api/resource/list")
                .param("pageNo","1")
                .param("pageSize","20")
                .param("title","liwei"));
            System.out.println("资源列表：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void signIn() {
        try {
            MvcResult result = super.perform(post("/api/user/signIn"));
            System.out.println("签到：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void submitOrder() {
        try {
            ExchangeResourceVO exchangeResourceVO = new ExchangeResourceVO();
            exchangeResourceVO.setResourceId("R2002070000034250");
            exchangeResourceVO.setIntegral(5);
            MvcResult result = super.perform(post("/api/order/submit")
                .content(JSON.toJSONString(exchangeResourceVO)));
            System.out.println("兑换资源：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserResource() {
        try {
            MvcResult result = super.perform(get("/api/order/resource")
                .param("pageNo","1")
                .param("pageSize","20"));
            System.out.println("资源列表：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserPublishResource() {
        try {
            MvcResult result = super.perform(get("/api/resource/publish")
                .param("pageNo","1")
                .param("pageSize","20"));
            System.out.println("资源列表：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllPublishResource() {
        try {
            MvcResult result = super.perform(get("/api/user/resource")
                .param("pageNo","1")
                .param("pageSize","20"));
            System.out.println("资源列表：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserInfo() {
        try {
            MvcResult result = super.perform(get("/api/user/info"));
            System.out.println("用户信息：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
