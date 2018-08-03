package com.xyls.utils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateUtil {

    public static String todayDate(){
        return LocalDate.now().toString();
    }

    public static String todayDateTime(){
        return todayDate()+" "+ LocalTime.now().withNano(0);
    }

    public static long  milliseconds(String timer){
        try {
            return DateUtils.parseDate(timer,"yyyy-MM-DD HH:mm:ss").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }
}
