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

    // 微信服务器验证
//    @GetMapping
//    public String validateServer(
//            @RequestParam("signature") String signature,
//            @RequestParam("timestamp") String timestamp,
//            @RequestParam("nonce") String nonce,
//            @RequestParam("echostr") String echostr) {
//
//        if (checkSignature(signature, timestamp, nonce)) {
//            return echostr;
//        }
//        return "Verification failed";
//    }
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(sb.toString().getBytes());
            String calculatedSignature = byteToHex(digest);
            return calculatedSignature.equals(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 字节数组转十六进制字符串
    private String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
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
