package com.ybb.service;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class loginAndAccessTokenService {
    @Resource
    private WxMpService wxMpService;
    @Resource
    RedisTemplate redisTemplate;
    public String getOpenId(){
        String s = wxMpService.getOAuth2Service().buildAuthorizationUrl("http://www.yuanbinbin.cloud/wxjava/mp/getUserInfo", WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        System.out.println("返回的结果"+s);
        return s;
    }
    public WxOAuth2UserInfo getAccessToken(String code,String state){
            try {
                    WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);  //获取用户的accessToken
                    WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);   //获取用户的所有信息
                    System.out.println("userInfo：\n"+userInfo);
                    //wxMpService.getUserService().userUpdateRemark(userInfo.getOpenid(),userInfo.getNickname()); //修改名称
                    /*redisTemplate.opsForHash().put(userInfo.getOpenid(),"AccessToken",accessToken);*/
                    return userInfo;
            } catch (WxErrorException e) {
                e.printStackTrace();
                return null;
            }
        }

}
