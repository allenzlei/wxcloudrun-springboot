package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index控制器
 */
@Controller

public class IndexController {
  @Autowired
  private WeChatService weChatService;

  /**
   * 主页页面
   * @return API response html
   */
  @GetMapping
  public String index() {
    return "index";
  }


  @GetMapping("/getToken")
  public String getToken() {
    String token = weChatService.getAccessToken("wx129306a8a645f557", "ae4bbc435fd1adab609edc2143485e01");
    return token;
  }

}
