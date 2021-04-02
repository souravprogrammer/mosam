package com.sourav.mosam.data;

import com.sourav.mosam.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppSettings {
    public static final int Day = 1;
    public static final int Night = 2;
    public static final String KELVIN = "Kelvin";
    public static final String CELSIUS = "Celsius";
    public static final String FAHEENHEIT = "Fahrenheit";
    public static final int Database_day = 7;
    public static final String LOCATION_KEY = "mylocation";
    public static final String TEMP_UNIT_CHANGE = "tempUnit";
    public static final String APP_THEME = "theme";
    public static final String header_colour = "header";
    public static final String activity_colour = "colour";


    public static int drawablecondition(String description , long dt) {

        if (description.contains("rain")) {

            return R.drawable.ic_rain;
        } else if (description.contains("Haze")) {
            return R.drawable.ic_wind;

        } else if (description.contains("clouds")) {

            return R.drawable.ic_cloud;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            String time = simpleDateFormat.format(new Date(dt*1000)).split(":")[0];
           // String time = simpleDateFormat.format(Calendar.getInstance().getTime()).split(":")[0];

            int hours = Integer.parseInt(time);
            /** NIGHT MODE**/
            if (hours >= 19 && hours <= 23 || hours >= 0 && hours <= 4) {
                return R.drawable.ic_moon;
            } else {
                return R.drawable.ic_sun;
            }
        }
    }
}
