package com.hu.mm.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project：ssm
 * Date：2020/1/4
 * Time：0:36
 * Description：注解方式实现权限校验
 *
 * @author huxiongjun
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreAuthorize {
    String value();
}
