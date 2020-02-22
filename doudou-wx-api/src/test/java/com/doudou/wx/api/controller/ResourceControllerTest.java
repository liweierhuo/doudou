package com.doudou.wx.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.alibaba.fastjson.JSON;
import com.doudou.core.constant.ResourceTypeEnum;
import com.doudou.wx.api.vo.ExchangeResourceVO;
import com.doudou.wx.api.vo.ResourceVO;
import com.github.kevinsawicki.http.HttpRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Lists;
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

    @Test
    @SneakyThrows
    public void batchAddResource() {
        try {
            ResourceVO resource1 = new ResourceVO();
            resource1.setTitle("alsdfasf");
            ResourceVO resource2 = new ResourceVO();
            resource2.setTitle("qerewr");
            List<ResourceVO> resourceList = Lists.newArrayList(resource1,resource2);
            String content = "[\n"
                + "\t{\n"
                + "\t\t\"title\":\"MySQL实战45讲\",\n"
                + "\t\t\"subtitle\":\"01 | 基础架构：一条SQL查询语句是如何执行的？\",\n"
                + "\t\t\"source\":\"极客时间\",\n"
                + "\t\t\"price\":5,\n"
                + "\t\t\"url\":\"https://time.geekbang.org/column/article/1111\",\n"
                + "\t\t\"imageUrl\":\"https://static001.geekbang.org/resource/image/38/8b/38dd4b12f16d2f9667fb169be0f0698b.jpg\",\n"
                + "\t\t\"resSummary\":\"这是专栏的第一篇文章，我想来跟你聊聊 MySQL 的基础架构。我们经常说，看一个事儿千万不要直接陷入细节里，你应该先鸟瞰其全貌，这样能够帮助你从高维度理解问题。同样，对于 MySQL 的学习也是这样。平时我们使用数据库，看到的通常都是一个整体。比如，你有个最简单的表，表里只有一个 ID 字段，在执行下面这个查询语句时：...\",\n"
                + "\t\t\"totalNum\":20\n"
                + "\t},\n"
                + "\t{\n"
                + "\t\t\"title\":\"MySQL实战45讲\",\n"
                + "\t\t\"subtitle\":\"02 | 日志系统：一条SQL更新语句是如何执行的？\",\n"
                + "\t\t\"source\":\"极客时间\",\n"
                + "\t\t\"price\":5,\n"
                + "\t\t\"url\":\"https://time.geekbang.org/column/article/1111\",\n"
                + "\t\t\"imageUrl\":\"https://static001.geekbang.org/resource/image/f6/f5/f613f6d2d8a72032c16b211f933c1cf5.jpg\",\n"
                + "\t\t\"resSummary\":\"前面我们系统了解了一个查询语句的执行流程，并介绍了执行过程中涉及的处理模块。相信你还记得，一条查询语句的执行过程一般是经过连接器、分析器、优化器、执行器等功能模块，最后到达存储引擎。那么，一条更新语句的执行流程又是怎样的呢？...\",\n"
                + "\t\t\"totalNum\":20\n"
                + "\t}\n"
                + "]";
            System.out.println(content);
            MvcResult result = super.perform(post("/api/resource/batch/add/W2002015110715563")
                .content(content));
            System.out.println("批量发布资源：" + result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @SneakyThrows
    public void testUrlToLocalImage() {
        URL url = new URL("https://static001.geekbang.org/resource/image/38/8b/38dd4b12f16d2f9667fb169be0f0698b.jpg");
        HttpRequest response = HttpRequest.get(url);
        InputStream fileInputStream = response.getConnection().getInputStream();
        IOUtils.copy(fileInputStream,new FileOutputStream("/Users/liwei/studySpace/doudou/doudou-wx-api/src/main/resources/file/publishResource/test1.jpg"));
        System.out.println();
    }

}
