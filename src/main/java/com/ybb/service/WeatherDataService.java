package com.ybb.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybb.pojo.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.expression.Maps;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WeatherDataService {
    public String WEATHER_URI = "http://www.nmc.cn/rest/weather?stationid=54823&_=";
    public String WEATHER_URI2 = "http://www.nmc.cn/rest/weather?stationid=58024&_=";
    public String WeatherDataService(String city) {
        String uri = WEATHER_URI2 + new Date(System.currentTimeMillis());
        if (city.equals("枣庄")){
            uri = WEATHER_URI2 + new Date(System.currentTimeMillis());
        } else {
            uri = WEATHER_URI + new Date(System.currentTimeMillis());
        }
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        String strBody = null;
        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody().toString();
        }

        return strBody;
    }
    public Weather getWeatherService(String city){
        String s = this.WeatherDataService(city);
        System.out.println(s);
        Map maps = (Map) JSON.parse(s);
        Object data = maps.get("data");
        Map maps1 = (Map) JSON.parse( data.toString());
        List< Weather > tempchart = JSON.parseArray( maps1.get("tempchart").toString(),Weather.class);
        Map real = (Map)JSON.parse( maps1.get("real").toString());
        Map wind =(Map)JSON.parse(real.get("wind").toString());
        Map air = (Map)JSON.parse(maps1.get("air").toString());
        String text =  air.get("text").toString();
        String direct =  wind.get("direct").toString();
        String power =  wind.get("power").toString();
        String speed = wind.get("speed").toString();
        System.out.println(tempchart);
        Weather nowWeather = null;
        for (int i=0;i<tempchart.size();i++){
            Weather weather = tempchart.get(i);
            if (weather.getTime().equals(new SimpleDateFormat("yyyy/MM/dd").format(new Date()))){
                nowWeather = weather;
            }
        }
        nowWeather.setText(text);
        nowWeather.setDirect(direct);
        nowWeather.setPower(power);
        nowWeather.setSpeed(speed);
        return nowWeather;
    }
}
