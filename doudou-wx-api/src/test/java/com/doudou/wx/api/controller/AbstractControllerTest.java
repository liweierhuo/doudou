package com.doudou.wx.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.doudou.core.constant.RedisConstant;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.core.util.RedisUtil;
import com.doudou.wx.api.DoudouWxApiApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = DoudouWxApiApplication.class)
public abstract class AbstractControllerTest {

    @Autowired
    WebApplicationContext wac;
    protected String mockToken = "asdfqrqwer9201234";
    protected String mockClientId = "W2002015110715563";
    protected MockMvc mockMvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RedisUtil.setex(RedisConstant.getSessionIdKey(mockToken),mockClientId,7200L);
    }


    protected MvcResult perform(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(
            requestBuilder
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .header(WxApiConstant.CLIENT_SESSION_ID_KEY,mockToken)

        ).andExpect(status().isOk())
            .andReturn();
    }
}
