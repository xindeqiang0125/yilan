package com.xindq.yilan.util;

public class UrlUtil {
    public static String getHttpUrl(String address, String path) {
        return "http://" + address + path;
    }

    public static String getWsUrl(String address, String path) {
        return "ws://" + address + path;
    }
}
