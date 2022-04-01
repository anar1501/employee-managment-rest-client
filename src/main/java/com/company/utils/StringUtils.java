package com.company.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String value;

    public StringUtils() {
    }

    public static String parseString(Object value) {
        if (value == null) return "";
        return defaultString(value);
    }


    public static boolean isBlank(Object str) {
        String str_ = defaultString(str);
        return StringUtils.isBlank(str_) || str_.trim().equalsIgnoreCase("null");
    }

    public static boolean isNotBlank(Object str) {
        return !isBlank(str);
    }

    public static String beautify(Object obj) {
        if (obj == null) return "";
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            return obj.toString();
        }
    }

    public static boolean equals(Object original, Object compare) {
        if (original == null && compare == null) return true;
        if (original == null || compare == null) return false;
        return original.toString().equals(compare.toString());
    }

    public static String valueOf(Object obj) {
        if (obj == null) return null;
        return obj + "";
    }

    public static String defaultString(Object str) {
        return defaultString(str, "");
    }

    public static String defaultString(Object str, String defaultStr) {
        if (str == null || str.toString().trim().length() == 0 || str.toString().trim().equalsIgnoreCase("null")) {
            return defaultStr;
        }
        return str.toString();
    }

    public static String substring(String str, int begin, int end) {
        if (isBlank(str)) return str;
        return str.substring(begin, end);
    }

    public String getValue() {
        return this.value;
    }

}
