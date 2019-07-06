package com.example.bilal.validator;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.bilal.util.Utils;

public class UserNameValidator implements ConstraintValidator<UserNameValidation, String> {

    @Override
    public void initialize(UserNameValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            Matcher matcher = Utils.USERNAME_VALIDATION_PATTERN.matcher(value);
            if (!matcher.matches()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(Utils.USERNAME_NOT_VALID_EXCEPTION).addConstraintViolation();
                return false;
            }
            return true;
        }
        return false;
    }
}
