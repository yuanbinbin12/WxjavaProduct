package com.ybb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//生命周期
@Target(ElementType.METHOD)//标注用在什么地方
public @interface HasPermissions {
    /*
     * 权限标识：用来关联角色
     * 如系统管理员角色 拥有 删除商品接口的权限
     * 表设计大致如下
     *
     * 角色ID     权限标识
     *  1        delete:commodity
     *  2        update:commodity
     *
     *  在权限切面逻辑里 通过 token 获取当前登录人信息
     *  查询当前登录人所有角色及拥有的权限
     *  然后和方法上此注解的值进行比较即可
     */
    String value();
}
