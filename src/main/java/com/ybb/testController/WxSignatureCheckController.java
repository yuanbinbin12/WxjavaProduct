package com.ybb.testController;

import com.ybb.service.WxSignatureCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api("回调参数自定义类")
@RestController
public class WxSignatureCheckController {
    @Autowired
    private WxSignatureCheckService wxSignatureCheckService;
    @ApiOperation("自定义参数返回方法")
    @RequestMapping("/wxCheck")
    public String wxSignatureCheck(
            @ApiParam("返回是否比对参数") @RequestParam(value = "signature") String signature,
            @ApiParam("对比参数1")@RequestParam(value = "timestamp") String timestamp,
            @ApiParam("对比参数2")@RequestParam(value = "nonce") String nonce,
            @ApiParam("返回的参数")@RequestParam(value = "echostr") String echostr
    ){
        return wxSignatureCheckService.wxSignatureCheck(signature, timestamp, nonce, echostr);
    }
}
