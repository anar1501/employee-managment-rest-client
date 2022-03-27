package com.company.utils;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ApplicationUtils implements Serializable {

    //registration confirm linki ucun expiration time hazirlayir
    public static Date prepareRegistrationExpirationDate() {
        Date date = new Date();//
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 1);
        return cal.getTime();
    }

    //yenilenecek password ucun expiration time hazirlayir
    public static Date prepareForgetPasswordExpirationDate() {
        Date date = new Date();//
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 5);
        return cal.getTime();
    }

    //6 reqemli nomre generate edirki emaile gonderilecek
    public static String getRandomNumberString() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
