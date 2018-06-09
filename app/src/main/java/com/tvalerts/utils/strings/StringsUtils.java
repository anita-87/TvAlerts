package com.tvalerts.utils.strings;

public class StringsUtils {

    public static String returnValueIfEmpty(String value) {
        return value.isEmpty() ? "--" : value;
    }
}
