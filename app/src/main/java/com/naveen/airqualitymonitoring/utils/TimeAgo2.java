package com.naveen.airqualitymonitoring.utils;

import android.util.Log;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo2 {

    public static String covertTimeToText(Long milliSeconds) {
        String convTime = null;
        String suffix = "Ago";

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            Date pasTime = calendar.getTime();

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes "+ suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours "+ suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else  {
                convTime = day + " Days " + suffix;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }

}
