package com.recob.utils;

public class Utils {

    public static String getUUID (String uri) {

        return uri.substring(uri.lastIndexOf('/') + 1);
    }
}
