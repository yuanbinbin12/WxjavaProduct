package com.ybb.testController;

import com.ybb.service.messageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.interfaces.PBEKey;

@Controller
public class messageSendController {
    @Autowired
    messageSendService mss;
    @PostMapping("/messageSendUser")
    @ResponseBody
    public String messageSendUser(){
         return mss.messageSendUser();
    }
    @PostMapping("/Weather")
    @ResponseBody
    public String messageSendMe(){
        return mss.messageSendMe();
    }
    @PostMapping("/WeatherFather")
    @ResponseBody
    public String messageSendFather(){
        return mss.messageSendFather();
    }
}
