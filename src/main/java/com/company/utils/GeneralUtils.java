package com.company.utils;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

public class GeneralUtils {

    public static Date prepareRegistrationExpirationDate() {
        Date date = new Date();//
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 1);
        return cal.getTime();
    }

    public static Date prepareForgetPasswordExpirationDate() {
        Date date = new Date();//
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 5);
        return cal.getTime();
    }
}
