package br.com.codecode.paymobile.android.helper;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by felipe on 07/01/18.
 */

public class ApplicationDateFormater {
    private static final String DATE_PATTERN =  "yyyy-mm-dd HH:mm:ss Z";
    private static final String DEFAULT_TIME_ZONE = "UTC";

    private ApplicationDateFormater(){}

    public static SimpleDateFormat getDefaultFormater(){
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        formatter.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        return formatter;
    }
}