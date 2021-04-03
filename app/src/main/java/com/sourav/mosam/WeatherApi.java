package com.sourav.mosam;

import android.text.style.AlignmentSpan;

import com.sourav.mosam.data.ApiKey;
import com.sourav.mosam.data.WeatherObject;
import com.sourav.mosam.networkreq.JsonConveter;
import com.sourav.mosam.networkreq.ReadApi;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class WeatherApi {
    private static String city_name = "delhi";

    /**TODO paste your api key here in replace of {Apikey.API_KEY}   to make your get request */
    private static final String CURRENT_API = "https://api.openweathermap.org/data/2.5/weather?q=NAME&appid=" + ApiKey.API_KEY;
    private static final String ONE_CALL_API = "https://api.openweathermap.org/data/2.5/onecall?lat=LATI&lon=LONGI&exclude=minutely&appid=" + ApiKey.API_KEY;

    private static WeatherObject currentWeather;
    private static String Latitude = null;
    private static String LONGITUDE = null;
    private static String Raw = null;

    static public void setLocation(String CITY_NAME) {
        city_name = CITY_NAME;

        String Required_SEARCH_API = CURRENT_API.replace("NAME", city_name);
        ReadApi api = new ReadApi(Required_SEARCH_API);
        String result_raw = api.getData();
        if (result_raw.equals("No data")) {
            return;
        }
        JsonConveter.Setcoardinates(result_raw); // this will returns the cordinates of the location
        currentWeather = JsonConveter.getcurrent(result_raw);
        Latitude = JsonConveter.getCordinates().getLatitude();
        LONGITUDE = JsonConveter.getCordinates().getLongitude();
    }

    static public WeatherObject getCurrentWeather() {
        return currentWeather;
    }

    public static ArrayList<WeatherObject> getOneCallApiData() {
        ArrayList<WeatherObject> list = new ArrayList<WeatherObject>();
        if (Latitude == null && LONGITUDE == null) {
            return null;
        }

        String Request = ONE_CALL_API.replace("LATI", Latitude).replace("LONGI", LONGITUDE);

        ReadApi api = new ReadApi(Request);
        Raw = api.getData();

        list.addAll(JsonConveter.getall(Raw));
        if (list.size() == 0) {
            return null;
        }
      //  list.remove(list.size() - 1);
        list.remove(0);
        return list;
    }

    public static ArrayList<WeatherObject> getHourData() {
        if (Latitude == null && LONGITUDE == null) {
            return null;
        }
        if (Raw != null) {
            return JsonConveter.getHour(Raw);
        } else {
            return null;
        }


    }

}
