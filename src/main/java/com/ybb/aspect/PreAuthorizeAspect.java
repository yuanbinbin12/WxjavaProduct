package com.ybb.aspect;

import com.ybb.annotations.HasPermissions;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class PreAuthorizeAspect {
    @Pointcut("@annotation(com.ybb.annotations.HasPermissions)")
    public void PointCut(){
    }
    @Around("PointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        // JointPoint 对象很有用，可以用它来获取一个签名，
        // 利用签名可以获取请求的包名、方法名，包括参数（通过 joinPoint.getArgs() 获取）等。

        // 获取方法上的HasPermissions注解信息
        HasPermissions annotation = method.getAnnotation(HasPermissions.class);

        /*
         * 注解值 如 delete:commodity
         * 这里可以从token里获取当前登录人的信息 然后查询角色及拥有的权限标识
         * 然后判断 delete:commodity 是否在所拥有的权限标识内
         * 存在则验证通过
         * 否则验证失败
         */
        String authority = annotation.value();

        // 验证失败
        // return RestResult.fail(ResultCode.PERMISSION_NO_ACCESS);

        // 验证通过执行方法
        return point.proceed();
    }
}
