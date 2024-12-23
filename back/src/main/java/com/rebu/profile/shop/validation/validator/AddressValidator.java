package com.rebu.profile.shop.validation.validator;

import com.rebu.profile.shop.exception.AddressMismatchException;
import com.rebu.profile.shop.validation.annotation.Address;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressValidator implements ConstraintValidator<Address, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            throw new AddressMismatchException();
        }

        String regex = "^[가-힣a-zA-Z0-9\\s\\-.,()\\u00C0-\\u017F]+(?:\\s*\\d{1,5}(?:[-\\s]\\d{1,4})?)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new AddressMismatchException();
        }
        return true;
    }
}
