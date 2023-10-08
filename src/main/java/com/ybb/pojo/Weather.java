package com.ybb.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@ToString
public class Weather {
   private String day_img;
   private String day_text;
   private String max_temp;
   private String min_temp;
   private String night_img;
   private String night_text;
   private String time;
   private String text;
   private String direct;
   private String power;
   private String speed;
}
