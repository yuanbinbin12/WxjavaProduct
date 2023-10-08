package com.ybb.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class controllerAspect {
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void PointCut(){
    }
    @Around("PointCut()")
    public Object logAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        // 开始时间
        long startTime = System.currentTimeMillis();

        // 执行方法
        Object result = joinPoint.proceed();

        // 方法执行耗时
        long time =  System.currentTimeMillis() - startTime;

        // 记录日志
        recordLog(joinPoint,time,result);
        return result;
    }

    /**
     * 记录日志
     * @param joinPoint ProceedingJoinPoint
     * @param time 方法运行总时长
     * @param result 方法返回结果
     */
    private void recordLog(ProceedingJoinPoint joinPoint, long time, Object result) throws JsonProcessingException {
        // 获取签名
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 可以获得方法名 方法上的注解等信息
        Method method = methodSignature.getMethod();
        // 请求的方法名
        String methodName = method.getName();

        // 接口请求参数
        Object[] args = joinPoint.getArgs();

        // 返回结果
        String resultJsonStr = JSONObject.toJSONString(result);;

        // 获取请求的 URL 和 IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求 URL
        String url = request.getRequestURL().toString();
        // 获取请求 IP
        String ip = request.getRemoteAddr();

        log.info("请求 URL：{},请求方法：{},参数：{},返回结果：{},耗时：{} ms",url,methodName,args,resultJsonStr,time);

    }
}
