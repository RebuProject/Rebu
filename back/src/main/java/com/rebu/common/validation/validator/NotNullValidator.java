package com.rebu.common.validation.validator;

import com.rebu.common.validation.annotation.NotNull;
import com.rebu.feed.exception.FeedImageMismatchException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if(obj == null)
            throw new FeedImageMismatchException();
        return true;
    }
}