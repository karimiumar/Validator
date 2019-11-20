package com.umar.validator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
/**
 * Annotation for field that will contain the ErrorMessage 
 * @author braindead
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ErrorMessage {
    String errorMessage() default "";
}
