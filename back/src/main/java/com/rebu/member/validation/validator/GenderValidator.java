package com.rebu.member.validation.validator;

import com.rebu.member.exception.GenderMismatchException;
import com.rebu.member.validation.annotation.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, com.rebu.common.enums.Gender> {

    @Override
    public boolean isValid(com.rebu.common.enums.Gender value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            throw new GenderMismatchException();
        }

        for (com.rebu.common.enums.Gender gender : com.rebu.common.enums.Gender.values()) {
            if (gender.equals(value)) {
                return true;
            }
        }

        throw new GenderMismatchException();
    }
}
