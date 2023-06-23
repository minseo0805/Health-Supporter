package com.example.healthsupporter;

public class GlobalVariables {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        GlobalVariables.token = token;
    }

    public static void clear() {
        token = null;
    }
}
