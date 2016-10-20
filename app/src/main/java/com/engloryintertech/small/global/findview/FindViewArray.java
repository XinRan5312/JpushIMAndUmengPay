package com.engloryintertech.small.global.findview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by qixinh on 16/9/19.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FindViewArray {
    public abstract int[] value();

    public abstract boolean canNull() default false;
}
