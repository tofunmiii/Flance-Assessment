package com.flance.assessment.util;



import io.micrometer.common.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static void validateProperty(String value, String errorMessage) throws Exception {
        if (StringUtils.isEmpty(value)) {

            throw new Exception("999");
        }
    }
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
