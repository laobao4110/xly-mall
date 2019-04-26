package com.xly.mall.common.base.db.dbInterface;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Documented
public @interface ExcelField {
    int order() default 0;

    String header() default "";

    String[] explicitListValues() default {};
}
