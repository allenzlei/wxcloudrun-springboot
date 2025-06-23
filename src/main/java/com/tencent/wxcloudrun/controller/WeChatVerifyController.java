package com.tencent.wxcloudrun.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.Arrays;

@RestController
public class WeChatVerifyController {

    @Value("${wechat.token}")
    private String token;

    @GetMapping("/wechat")
    public String verifyWeChat(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr) {

        String[] arr = new String[]{token, timestamp, nonce};
        // 字典序排序
        Arrays.sort(arr);

        // 拼接成一个字符串
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }

        // SHA1加密
        String tempSignature = sha1(content.toString());

        // 校验签名
        if (tempSignature.equals(signature)) {
            return echostr; // 验证通过，返回echostr
        } else {
            return "Invalid signature";
        }
    }

    private String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() < 2) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
