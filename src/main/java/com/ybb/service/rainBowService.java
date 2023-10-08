package com.ybb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ybb.pojo.rainBow;
import com.ybb.pojo.HotMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class rainBowService {
    private final String rainBowUrl = "https://apis.tianapi.com/caihongpi/index?key=a566f8c1ad86f87da608fc4987f2cb7f";
    private final String hotNewUrl = "https://apis.tianapi.com/toutiaohot/index?key=a566f8c1ad86f87da608fc4987f2cb7f";

    public String getRainBow() {
        System.out.println(rainBowUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<rainBow> forEntity = restTemplate.getForEntity(rainBowUrl, rainBow.class);
        System.out.println(forEntity);
        rainBow rainBowBody = new rainBow();
        if (forEntity.getStatusCodeValue() == 200) {
            rainBowBody = forEntity.getBody();
        }
        Map<String, String> result = rainBowBody.getResult();
        String content = result.get("content");
        return content;
    }

    public List<HotMessage> getHotNew() {
        System.out.println(hotNewUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(hotNewUrl, String.class);
        String hotMessBody = "";
        if (forEntity.getStatusCodeValue() == 200) {
            hotMessBody = forEntity.getBody();
        }
        System.out.println(hotMessBody);
        rainBow rainBow = JSON.parseObject(hotMessBody, rainBow.class);
        String hot = rainBow.getResult().get("list");
        List<HotMessage> hotMessages = JSON.parseArray(hot, HotMessage.class);
        return hotMessages;
    }
}
