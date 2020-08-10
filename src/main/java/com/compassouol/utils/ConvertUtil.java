package com.compassouol.utils;

public class ConvertUtil {

    public static Long getLongValue(Object id) {
        if (id instanceof Integer) {
            return ((Integer) id).longValue();
        }
        if (id instanceof String) {
            return Long.parseLong((String) id);
        }
        return null;
    }
}
