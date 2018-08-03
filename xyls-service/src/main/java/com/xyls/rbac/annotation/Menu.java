package com.xyls.rbac.annotation;

import com.xyls.rbac.enums.MenuType;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
//最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface Menu {

    String name() default "";

    String uri() default "";

    boolean isParent() default false;

    MenuType type() default MenuType.BUTTON;


    String description() default "";

    String group() default "";

}
