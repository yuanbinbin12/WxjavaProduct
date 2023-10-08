package com.ybb.testController;

import com.ybb.pojo.noteMessage;
import com.ybb.service.loginAndAccessTokenService;
import com.ybb.service.messageNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@Api("使用公共类返回")
@Controller
@RequestMapping("/wxjava/mp")
@Slf4j
public class WxjavaTestController {
    @Resource
    private WxMpService wxMpService;
    @Resource
    messageNotificationService mns;
    @Resource
    private WxMpConfigStorage wxMpConfigStorage;
    @Resource
    private loginAndAccessTokenService laats;
    @ApiOperation("测试数据")
    @GetMapping("/test")
    public void testAutowire(){
        System.out.println(wxMpService);
        System.out.println(wxMpConfigStorage);
    }
    @ApiOperation("用于token回调函数")
    @GetMapping("/message")
    @ResponseBody
    public String configAccess(@ApiParam("回调后对比参数") String signature, @ApiParam("回调参数1") String timestamp, @ApiParam("回调参数2") String nonce, @ApiParam("返回参数") String echostr) {
        // 校验签名
        System.out.println("token为："+wxMpService.getWxMpConfigStorage().getToken());
        if (wxMpService.checkSignature(timestamp, nonce, signature)){
            // 校验成功原样返回echostr

            return echostr;
        }
        // 校验失败
        return null;
    }
    @PostMapping(value = "/message",produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    @ResponseBody
    public Object getNoteMessage(@RequestBody noteMessage message){
        if (message.getLatitude()==""||message.getLatitude()==null){
            if (message.getContent().equals("找朋友")) {
                noteMessage friendMessage = (noteMessage)mns.getFriendMessage(message);
                System.out.println("找朋友结果："+friendMessage.getContent());
                return friendMessage;
            }else {
                System.out.println("post"+message);
                return mns.getNoteMessage(message);
            }
        } else {
            System.out.println("post"+message);
            return mns.getLocalMessage(message);
        }

    }
    @ApiOperation("用户登录调取方法")
    @GetMapping("/login")
    public String getOpenId() throws IOException {
         String redirectUrl= laats.getOpenId();
         return "redirect:" + redirectUrl;
    }
    @ApiOperation("获取用户信息")
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public WxOAuth2UserInfo getUserInfo(String code,String state){
        WxOAuth2UserInfo accessToken = laats.getAccessToken(code, state);
        return accessToken;
    }
}