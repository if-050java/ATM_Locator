package com.ss.atmlocator.utils;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Olavin on 02.12.2014.
 */
public class TimeUtil {

    public static Timestamp currentTimestamp() {
        return new Timestamp(Calendar.getInstance().getTime().getTime());
    }
}
