package com.sourav.mosam.networkreq;

import com.sourav.mosam.data.WeatherObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonConveter {

    private static Location cordinates;

    public static void Setcoardinates(String Raw_current_data) {
        if (Raw_current_data == null) {
            return;
        }
        try {
            JSONObject object = new JSONObject(Raw_current_data);
            JSONObject coord = object.getJSONObject("coord");
            cordinates = new Location(coord.getString("lon"), coord.getString("lat"));
        } catch (JSONException e) {
            e.printStackTrace();
            cordinates = null;
        }
    }

    public static ArrayList<WeatherObject> getall(String raw) {
        ArrayList<WeatherObject> list = new ArrayList<WeatherObject>();
        try {
            JSONObject object = new JSONObject(raw);
            JSONArray daily_array = object.getJSONArray("daily");
            int Length = daily_array.length();
            for (int i = 0; i < Length; i++) {
                JSONObject item = daily_array.getJSONObject(i);
                JSONArray weather = item.getJSONArray("weather");
                JSONObject description = weather.getJSONObject(0);

                JSONObject temp = item.getJSONObject("temp");
                list.add(new WeatherObject(String.valueOf(item.getLong("dt")), "" + temp.getDouble("day")
                        , "" + temp.getString("day"), "" + item.getInt("humidity"),
                        "" + item.getDouble("wind_speed")
                        , "" + temp.getDouble("min"),
                        "" + temp.getDouble("max"), description.getString("description")
                ));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static WeatherObject getcurrent(String Raw) {
        try {
            JSONObject object = new JSONObject(Raw);
            JSONObject main = object.getJSONObject("main");
            JSONObject wind = object.getJSONObject("wind");
            JSONArray weather = object.getJSONArray("weather");
            JSONObject Weather_item = weather.getJSONObject(0);
            return new WeatherObject("" + object.getLong("dt"), "" + main.getDouble("temp")
                    , "" + main.getDouble("feels_like"), "" + main.getInt("humidity"),
                    "" + wind.getDouble("speed"), "" + main.getDouble("temp_min"),
                    "" + main.getDouble("temp_max"), Weather_item.getString("description"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Location getCordinates() {
        if (cordinates == null) {
            return null;
        }
        return cordinates;
    }

    public static ArrayList<WeatherObject> getHour(String raw) {
        ArrayList<WeatherObject> list = new ArrayList<WeatherObject>();
        try {
            JSONObject object = new JSONObject(raw);
            JSONArray hourly = object.getJSONArray("hourly");
            int length = hourly.length();
            for (int i = 1; i < length / 2; i++) {
                JSONObject hourly_obj = hourly.getJSONObject(i);
                JSONObject weather = hourly_obj.getJSONArray("weather").getJSONObject(0);
                String temp = "" + hourly_obj.getDouble("temp") ;
                String dtime ="" + hourly_obj.getLong("dt") ;
                String real  = "" + hourly_obj.getDouble("feels_like");

                String dis = ""+weather.getString("description") ;
                String win = "" + hourly_obj.getDouble("wind_speed") ;
                String hu  = "" + hourly_obj.getInt("humidity") ;

               list.add( new WeatherObject(dtime,temp,real,hu,win,"1","1",dis) );

//                list.add(new WeatherObject("" + hourly_obj.getLong("dt"), "" + hourly_obj.getDouble("temp")
//                        , "" + hourly_obj.getDouble("feels_like"), "" + hourly_obj.getInt("humidity")
//                        , "" + hourly_obj.getDouble("wind_speed"), "", "",
//                        ""+weather.getString("description")));
            }
            return list ;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class Location {
        private String longitude;
        private String latitude;

        public Location(String longitude, String latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }
    }

}
