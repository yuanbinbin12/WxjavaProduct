package com.ybb;

import com.alibaba.fastjson.JSON;
import com.ybb.pojo.Weather;
import com.ybb.service.WeatherDataService;
import com.ybb.service.messageSendService;
import com.ybb.service.rainBowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WxjavaProductApplicationTests {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    com.ybb.service.rainBowService rainBowService;
    @Test
    void contextLoads() {
      /*  Map<String,String> map = new HashMap<>();
        map.put("og3Oh6lH8T0J0yM75B-srqtRzLVE","。");
        map.put("og3Oh6t_FYEA_hAhToL4UC2X1rL8","@森屿海港！");
        map.put("og3Oh6iKSA8Cm0-wNq1AqcdmE3sM","仙女养的笨猪");
        map.put("og3Oh6ml0UdX0yMx5f8vefaKvk0Q","冬天");
        map.put("og3Oh6lJ8mRc184MSVSCl7HqNyNk","养猪的小仙女");
        map.put("og3Oh6k4lAKUMi_bDh42CgKgoN4Y","星星是我点亮的");
        map.put("og3Oh6s3mFicnYQWJS2y-40qh5i0","A0-社保优化-代缴五险一金");
        redisTemplate.opsForHash().putAll("UserMessageName",map);*/
        /*List remove = redisTemplate.opsForGeo().position("china:city","og3Oh6lJ8mRc184MSVSCl7HqNyNk","og3Oh6iKSA8Cm0-wNq1AqcdmE3sM");
        Long add = redisTemplate.opsForGeo().add("china:city", new Point(117.162138, 36.672570), "og3Oh6iKSA8Cm0-wNq1AqcdmE3sM");
        System.out.println(add);
        System.out.println("删除："+remove);*/
    }
    @Test
    void RainBow(){
    }

}
