package com.zaggle.zig.travel.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<ValidStatus, Integer> {
    @Override
    public void initialize(ValidStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext context) {
        // Check if the status is either 0 or 1
        return status == null || (status >= 0 && status <= 1);
    }
}
