package com.vlocity.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractValidator {

    public boolean isFormatNotValid(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return !matcher.matches();
    }
}
