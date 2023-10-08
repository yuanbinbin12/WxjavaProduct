package com.ybb.service;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.ybb.pojo.HotMessage;
import com.ybb.pojo.WeChatTemplateMsg;
import com.ybb.pojo.Weather;
import com.ybb.pojo.WxMssVO;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.enums.WxMpApiUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.engine.TemplateData;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class messageSendService {
    @Resource
    RedisTemplate redisTemplate;
    @Autowired
    private  WxMpService wxMpService;
    @Autowired
    private WeatherDataService weatherDataService;
    @Resource
    private rainBowService rainBowService;
    public String messageSendUser(){
        RestTemplate restTemplate = new RestTemplate();
        WxMpApiUrl.TemplateMsg messageTemplateSend = WxMpApiUrl.TemplateMsg.MESSAGE_TEMPLATE_SEND;
        String s = messageTemplateSend.getPrefix() + messageTemplateSend.getPath()+"?access_token="+this.getAccessToken();
        Set likeMember = redisTemplate.opsForSet().members("LikeMember");
        Object[] objects = likeMember.toArray();
        for (int i=0;i< objects.length;i++){
            System.out.println("用户的id:"+objects[i]);
            //拼接推送的模版
            WxMssVO wxMssVo = new WxMssVO();
            //用户的openId
            wxMssVo.setTouser((String) objects[i]);
            //订阅消息模板id
            wxMssVo.setTemplate_id("UMD-wEpqVss8IOCj2Q5Oyp_Enw7ieIwYx0jXlkNAPsc");
            Map<String, WeChatTemplateMsg> m = new HashMap<>(4);
            m.put("character_string36", new WeChatTemplateMsg("1111","#777777"));
            m.put("thing2", new WeChatTemplateMsg("用户下单通知啊"));
            m.put("phrase28", new WeChatTemplateMsg("待付款","#111111"));
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            m.put("time10", new WeChatTemplateMsg(simpleDateFormat.format(new Date()),"#555555"));
            wxMssVo.setData(m);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(s, wxMssVo, String.class);
            System.out.println(stringResponseEntity.getBody());
        }
        return "OK";
    }
    public String messageSendMe(){
        RestTemplate restTemplate = new RestTemplate();
        WxMpApiUrl.TemplateMsg messageTemplateSend = WxMpApiUrl.TemplateMsg.MESSAGE_TEMPLATE_SEND;
        String s = messageTemplateSend.getPrefix() + messageTemplateSend.getPath()+"?access_token="+this.getAccessToken();
        List mylist = new ArrayList();
        mylist.add("og3Oh6lJ8mRc184MSVSCl7HqNyNk");
        mylist.add("og3Oh6iKSA8Cm0-wNq1AqcdmE3sM");
        for (int i=0;i< mylist.size();i++){
            System.out.println("用户的id:"+mylist.get(i));
            //拼接推送的模版
            WxMssVO wxMssVo = new WxMssVO();
            //用户的openId
            wxMssVo.setTouser((String) mylist.get(i));
            //订阅消息模板id
            wxMssVo.setTemplate_id("BoNc0waRY0JNypAR-ak627BaYljiVVvJjXsBEeXnSuE");
            Map<String, WeChatTemplateMsg> m = new HashMap<>(4);
            Weather weatherService = weatherDataService.getWeatherService("济南");
            m.put("date", new WeChatTemplateMsg(weatherService.getTime(),"#555555"));
            m.put("city",new WeChatTemplateMsg("济南"));
            m.put("night_text",new WeChatTemplateMsg(weatherService.getNight_text()));
            m.put("min_temp",new WeChatTemplateMsg(weatherService.getMin_temp()));
            m.put("max_temp",new WeChatTemplateMsg(weatherService.getMax_temp()));
            m.put("dress",new WeChatTemplateMsg(weatherService.getText()));
            m.put("direct",new WeChatTemplateMsg(weatherService.getDirect()));
            m.put("speed",new WeChatTemplateMsg(weatherService.getSpeed()));
            m.put("power",new WeChatTemplateMsg(weatherService.getPower()));
            String day = "2021-07-07";
            String maoDay = "2023-04-09";
            String s1 = DateUtil.formatBetween(DateUtil.parseDate(day), DateUtil.date(), BetweenFormatter.Level.DAY);
            String s2 = DateUtil.formatBetween(DateUtil.parseDate(maoDay),DateUtil.date(),BetweenFormatter.Level.DAY);
            String rainBow = rainBowService.getRainBow();
            System.out.println(rainBow);
            m.put("loveDay",new WeChatTemplateMsg(s1));
            m.put("maoDay",new WeChatTemplateMsg(s2));
            m.put("rain",new WeChatTemplateMsg(rainBow));
            m.put("girlBirthday",new WeChatTemplateMsg(" 7月17日"));
            m.put("boyBirthday",new WeChatTemplateMsg("4月30日"));
            m.put("maoBirthday",new WeChatTemplateMsg("2月21日"));
            wxMssVo.setData(m);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(s, wxMssVo, String.class);
            System.out.println(stringResponseEntity.getBody());
        }
        return "OK";
    }
    public String messageSendFather(){
        RestTemplate restTemplate = new RestTemplate();
        WxMpApiUrl.TemplateMsg messageTemplateSend = WxMpApiUrl.TemplateMsg.MESSAGE_TEMPLATE_SEND;
        String s = messageTemplateSend.getPrefix() + messageTemplateSend.getPath()+"?access_token="+this.getAccessToken();
        List mylist = new ArrayList();
        mylist.add("og3Oh6t_FYEA_hAhToL4UC2X1rL8");
        mylist.add("og3Oh6iKSA8Cm0-wNq1AqcdmE3sM");
        for (int i=0;i< mylist.size();i++){
            System.out.println("用户的id:"+mylist.get(i));
            //拼接推送的模版
            WxMssVO wxMssVo = new WxMssVO();
            //用户的openId
            wxMssVo.setTouser((String) mylist.get(i));
            //订阅消息模板id
            wxMssVo.setTemplate_id("9MEmG4izwbl3e9210hu3SO_YwF3azDBnpCK_QYT_mJk");
            Map<String, WeChatTemplateMsg> m = new HashMap<>(4);
            Weather weatherService = weatherDataService.getWeatherService("枣庄");
            m.put("date", new WeChatTemplateMsg(weatherService.getTime(),"#555555"));
            m.put("city",new WeChatTemplateMsg("枣庄"));
            m.put("night_text",new WeChatTemplateMsg(weatherService.getNight_text()));
            m.put("min_temp",new WeChatTemplateMsg(weatherService.getMin_temp()));
            m.put("max_temp",new WeChatTemplateMsg(weatherService.getMax_temp()));
            m.put("dress",new WeChatTemplateMsg(weatherService.getText()));
            m.put("direct",new WeChatTemplateMsg(weatherService.getDirect()));
            m.put("speed",new WeChatTemplateMsg(weatherService.getSpeed()));
            m.put("power",new WeChatTemplateMsg(weatherService.getPower()));
            int oldYear = 1998;
            int year = new Date(System.currentTimeMillis()).getYear()+1900;
            int loveYear = year - oldYear;
            List<HotMessage> hotNew = rainBowService.getHotNew();
            StringBuffer stringBuffer = new StringBuffer();
            int num = 1;

            List<HotMessage> list = hotNew.subList(0, 5);
            for (HotMessage hotMessage:list) {
                if (i==5){

                }
                stringBuffer.append(num+"、"+hotMessage.getWord()+";");
                        num+=1;
            }
            m.put("loveDay",new WeChatTemplateMsg(loveYear+"年"));
            m.put("newPaper",new WeChatTemplateMsg(stringBuffer.toString()));
            m.put("girlBirthday",new WeChatTemplateMsg("1月20日\n"));
            m.put("boyBirthday",new WeChatTemplateMsg("1月01日"));
            wxMssVo.setData(m);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(s, wxMssVo, String.class);
            System.out.println(stringResponseEntity.getBody());
        }
        return "OK";
    }

    public  String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "appid=" + wxMpService.getWxMpConfigStorage().getAppId()+ "&secret=" + wxMpService.getWxMpConfigStorage().getSecret() + "&grant_type=client_credential";
        PrintWriter out = null;
        BufferedReader in = null;
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性 设置请求格式
            //设置返回类型
            conn.setRequestProperty("contentType", "text/plain");
            //设置请求类型
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.setDoOutput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
            return jsonObject.getString("access_token");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
