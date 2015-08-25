package com.postal.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;

/**
 * Created by brakhar on 22.03.15.
 */
public class YearConstraintValidator implements ConstraintValidator<Year, Integer> {

    private Year year;

    @Override
    public void initialize(Year year) {
        this.year = year;
    }

    @Override
    public boolean isValid(Integer targetYear, ConstraintValidatorContext context) {
        if(targetYear == null) {
            return true;
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, targetYear);
        int fieldYear = c.get(Calendar.YEAR);

        return fieldYear == targetYear;
    }

}
