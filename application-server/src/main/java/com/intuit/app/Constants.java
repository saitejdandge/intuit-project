package com.intuit.app;

public class Constants {

    private Constants() {

    }

    public static final String TIMEZONE = "Asia/Kolkata";
    public static final String BUSINESS_PROFILE = "businessProfile";
    public static String id = "_id";

    public static String buildValidationUrl(String serviceName) {
        return "http://" + serviceName + "/validate/";
    }
}
