package com.sourav.mosam;

import android.text.style.AlignmentSpan;

import com.sourav.mosam.data.WeatherObject;
import com.sourav.mosam.networkreq.JsonConveter;
import com.sourav.mosam.networkreq.ReadApi;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class WeatherApi {
    private static final String API_KEY = "5b3e458de8f5c5d30b9b84f3405365a5";
    private static String city_name = "delhi";
    private static final String CURRENT_API = "https://api.openweathermap.org/data/2.5/weather?q=NAME&appid=" + API_KEY;
    private static final String ONE_CALL_API = "https://api.openweathermap.org/data/2.5/onecall?lat=LATI&lon=LONGI&exclude=minutely&appid=" + API_KEY;

    private WeatherObject currentWeather;
    private static String Latitude;
    private static String LONGITUDE;

    static public void setLocation(String CITY_NAME) {
        city_name = CITY_NAME;

        String Required_SEARCH_API = CURRENT_API.replace("NAME", city_name);
        ReadApi api = new ReadApi(Required_SEARCH_API);
        String result_raw = api.getData();
        JsonConveter.Setcoardinates(result_raw); // this will returns the cordinates of the location

        Latitude = JsonConveter.getCordinates().getLatitude();
        LONGITUDE = JsonConveter.getCordinates().getLongitude();
    }

//    static public WeatherObject getCurrentWeather() {
//
//    }

    public static ArrayList<WeatherObject> getOneCallApiData() {
        if (Latitude == null && LONGITUDE == null) {
            return null;
        }

        String Request = ONE_CALL_API.replace("LATI", Latitude).replace("LONGI", LONGITUDE);

        ReadApi api = new ReadApi(Request);
        String Raw_data = api.getData();
        ArrayList<WeatherObject> list = JsonConveter.getall(Raw_data);
        return list;
    }

}
