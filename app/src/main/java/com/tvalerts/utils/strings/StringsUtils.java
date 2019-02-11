package com.tvalerts.utils.strings;

public class StringsUtils {

    public static String returnValueIfEmptyOrNull(Object value) {
        if (value == null) {
            return "--";
        } else {
            String valueAsString = value.toString();
            return valueAsString.isEmpty() ? "--" : valueAsString;
        }
    }
}
