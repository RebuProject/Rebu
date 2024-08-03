package com.rebu.feed.review.validation.validator;

import com.rebu.feed.review.exception.ReviewRatingMismatchException;
import com.rebu.feed.review.validation.annotation.ReviewRating;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReviewRatingValidator implements ConstraintValidator<ReviewRating, Long> {

    @Override
    public boolean isValid(Long rating, ConstraintValidatorContext constraintValidatorContext) {
        if(rating == null || rating < 1 || rating > 5)
            throw new ReviewRatingMismatchException();
        return true;
    }
}