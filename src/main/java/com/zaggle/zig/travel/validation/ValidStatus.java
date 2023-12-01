package com.zaggle.zig.travel.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatus {
//    validate status
    String message() default "Status must be either 0 or 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
