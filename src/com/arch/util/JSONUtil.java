package com.arch.util;

import com.google.gson.Gson;

public class JSONUtil {
    public static String toJson(Object data) {
        Gson gson = new Gson();
        String result = gson.toJson(data);
        return result;
    }
}