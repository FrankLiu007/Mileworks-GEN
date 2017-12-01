package com.mileworks.config.datasource.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author long-laptop
 * 2017.11.6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
