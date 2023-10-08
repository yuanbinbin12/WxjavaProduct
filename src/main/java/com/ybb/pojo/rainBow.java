package com.ybb.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Map;
@Data
@ToString
public class rainBow {
    private String code;
    private String msg;
    private Map<String,String> result;
}
