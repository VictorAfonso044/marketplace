package com.frwk.marketplace.core.shared.utils;

public class AppUtils {
    
    public static String removeAllDotsAndHyphen(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("\\.","").replaceAll("\\-","");
    }
}
