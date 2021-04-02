package com.sourav.mosam.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Date convert class converts seconds into a desire formats */
public class MyTimeConveter {

    public static String getDay(long seconds) {
        Date date = new Date(seconds * 1000);
        //String simpleDateFormat = SimpleDateFormat.getDateInstance().format(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        return simpleDateFormat.format(date);
    }

    public String getDate(long seconds) {

        return SimpleDateFormat.getDateInstance().format(new Date(seconds));

    }

    public static  String getFullDate(long seconds){
        Date date= new Date( seconds * 1000);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM, dd");
        return simpleDateFormat.format(date);
    }
    public static String getTime(long seconds){
        Date date = new Date(seconds*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");
        return simpleDateFormat.format(date) ;
    }
}
