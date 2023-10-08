package com.ybb.service;

import com.ybb.pojo.Weather;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackupInformationService {
    @Autowired
    likeMeService lms;
    @Resource
    RedisTemplate redisTemplate;
    @Autowired
    messageSendService mss;
    @Scheduled(cron = "0 30 7 * * 1-5")
    public void autoPassLoveMe(){
        try {
            WxMpUserList likeList = lms.getLikeList();
            List<String> openids = likeList.getOpenids();
             openids.size();
            for (int i=0;i< openids.size();i++){
                redisTemplate.opsForSet().add("LikeMember", openids.get(i));
                WxMpUser userMessage = lms.getUserMessage(openids.get(i));
                redisTemplate.opsForHash().put("UserMessage",openids.get(i),userMessage.toString());
            }
            System.out.println("用户信息备份完成");
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }
    @Scheduled(cron = "0 0 8 * * 1-7")
    public void autoWeather(){
        String s = mss.messageSendMe();
        String s1 = mss.messageSendFather();
        if (s.equals("OK")&&s1.equals("OK")){
            System.out.println("天气推送完成");
        }
    }
}
