package org.hzero.service.annotation;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/25 10:01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenDataSourceAnnotation {
    // 跨库的数据库名
    String value();
}
