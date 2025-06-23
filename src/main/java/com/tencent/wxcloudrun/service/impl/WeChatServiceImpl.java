package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
    @Autowired
    private RestTemplate restTemplate;

    public String getAccessToken(String appId, String secret) {
        String url = "https://api.weixin.qq.com/cgi-bin/token"
                + "?grant_type=client_credential"
                + "&appid=" + appId
                + "&secret=" + secret;

        Map<String, Object> result = restTemplate.getForObject(url, Map.class);
        if (result != null && result.containsKey("access_token")) {
            return result.get("access_token").toString();
        } else {
            throw new RuntimeException("获取 access_token 失败: " + result);
        }
    }
}
