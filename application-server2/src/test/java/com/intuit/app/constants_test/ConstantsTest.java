package com.intuit.app.constants_test;

import com.intuit.app.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsTest {

    @Test
    public void constantTest() {
        assertEquals("Asia/Kolkata", Constants.TIMEZONE);
        assertEquals("businessProfile", Constants.BUSINESS_PROFILE);
        assertEquals("_id", Constants.id);
        assertEquals("http://appy/validate/", Constants.buildValidationUrl("appy"));

    }
}
