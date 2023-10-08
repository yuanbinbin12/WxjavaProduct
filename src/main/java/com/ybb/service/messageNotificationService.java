package com.ybb.service;

import com.ybb.pojo.noteMessage;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@Service
public class messageNotificationService {
    @Resource
    RedisTemplate redisTemplate;
    public Object getNoteMessage(noteMessage nM){
        redisTemplate.opsForList().leftPush(nM.getFromUserName(),nM.getContent());
        //新建一个响应对象
        noteMessage responseMessage = new noteMessage();
        //消息来自谁
        responseMessage.setFromUserName(nM.getToUserName());
        //消息发送给谁
        responseMessage.setToUserName(nM.getFromUserName());
        //消息类型，返回的是文本
        responseMessage.setMsgType("text");
        //消息创建时间，当前时间就可以
        responseMessage.setCreateTime(System.currentTimeMillis());
        //这个是响应消息内容，直接复制收到的内容做演示，甚至整个响应对象都可以直接使用原请求参数对象，只需要换下from和to就可以了哈哈哈
        responseMessage.setContent(nM.getContent());
        return responseMessage;
    }
    public Object getFriendMessage(noteMessage nM){
        redisTemplate.opsForList().leftPush(nM.getFromUserName(),nM.getContent());
        //新建一个响应对象
        noteMessage responseMessage = new noteMessage();
        //消息来自谁
        responseMessage.setFromUserName(nM.getToUserName());
        //消息发送给谁
        responseMessage.setToUserName(nM.getFromUserName());
        //消息类型，返回的是文本
        responseMessage.setMsgType("text");
        //消息创建时间，当前时间就可以
        responseMessage.setCreateTime(System.currentTimeMillis());
        //这个是响应消息内容，直接复制收到的内容做演示，甚至整个响应对象都可以直接使用原请求参数对象，只需要换下from和to就可以了哈哈哈
       /* Circle within = new Circle(new Point(116,39),new Distance(300, Metrics.KILOMETERS));
        GeoResults radius = redisTemplate.opsForGeo().radius("china:city", within, RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates());
        List<GeoResult> content1 = radius.getContent();
        StringBuffer content =new StringBuffer();
        for (GeoResult ss:content1) {
            GeoLocation content2 = (GeoLocation)ss.getContent();

            content.append("附近人的名字为："+content2.getName()+":\n");
            content.append("距离为"+ss.getDistance()+"\n");
        }
        responseMessage.setContent(content.toString());*/
        try {
            GeoResults radius = redisTemplate.opsForGeo().radius("china:city", nM.getFromUserName(), new Distance(500, Metrics.KILOMETERS),RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates());
            List<GeoResult> content1 = radius.getContent();
            if (content1.size()==0){
                responseMessage.setContent("请打开位置，在使用找朋友功能");
            } else{
                StringBuffer content =new StringBuffer("");
                for (GeoResult ss:content1) {
                    GeoLocation content2 = (GeoLocation)ss.getContent();
                    if (!(content2.getName().equals(nM.getFromUserName()))){
                        String userMessage = (String) redisTemplate.opsForHash().get("UserMessageName", content2.getName());
                        content.append("附近人的名字为："+userMessage+":\n");
                        content.append("距离为"+ss.getDistance()+"\n");
                    }
                }
                if (content.toString().equals("")){
                    responseMessage.setContent("附近没有朋友");
                } else {
                    responseMessage.setContent(content.toString());
                }
            }
        } catch (Exception e){
            System.out.println("没有位置权限");
            responseMessage.setContent("请打开位置，在使用找朋友功能");
            e.printStackTrace();
        }
        return responseMessage;
    }
    public Object getLocalMessage(noteMessage nM){
        Long add = redisTemplate.opsForGeo().add("china:city", new Point(Double.parseDouble(nM.getLongitude()), Double.parseDouble(nM.getLatitude())), nM.getFromUserName());
        System.out.println("add为"+add);
        System.out.println("精度为:"+nM.getLongitude()+"\n维度为"+nM.getLatitude());
        return "OK";
    }
}
