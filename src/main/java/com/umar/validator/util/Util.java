package com.umar.validator.util;

import java.util.List;

public class Util {

    public static <T> boolean isNullOrEmpty(T t) {
        if(null == t) return true;
        if(t.getClass().equals(String.class)) {
            String casted = (String) t;
            return casted.isEmpty();
        }
        if(t.getClass().equals(CharSequence.class)) {
            CharSequence casted = (CharSequence) t;
            return casted.length() == 0;
        }
        if(t instanceof List) {
            List casted = (List) t;
            return casted.isEmpty();
        }
        return false;
    }
}
