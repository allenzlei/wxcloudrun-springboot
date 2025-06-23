package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * index控制器
 */
@Controller
public class IndexController {
    @Autowired
    private WeChatService weChatService;

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;

    /**
     * 主页页面
     *
     * @return API response html
     */
    @GetMapping
    public String index() {
        return "index";
    }


    @GetMapping("/getToken")
    @ResponseBody
    public String getToken() {
        String token = weChatService.getAccessToken(appid, secret);
        return token;
    }

}
