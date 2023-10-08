package com.ybb.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class likeMeService {
    @Resource
    private WxMpService wxMpService;
    public WxMpUserList getLikeList() throws WxErrorException {
        String nextOpenid = null;// 可选，第一个拉取的OPENID，null为从头开始拉取
        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(nextOpenid);
        System.out.println(wxMpService.getWxMpConfigStorage().getAppId());
        return wxMpUserList;
    }
    public WxMpUser getUserMessage(String openId){
        try {
            WxMpUser user = wxMpService.getUserService().userInfo(openId, "zh_CN");
            return user;
        } catch (WxErrorException e) {
            e.printStackTrace();
            return null;
        }
    }
}
