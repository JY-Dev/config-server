package com.jydev.configserver.util;

public class StringUtil {
    public static String removeAllWhiteSpace(String s){
        return s.replaceAll("[\s\t]", "");
    }
}
