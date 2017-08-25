package com.camile.common.annotation;

import java.lang.annotation.*;

/**
 * 初始化继承Service的service
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InitService {
}
