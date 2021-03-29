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
                JSONObject temp = item.getJSONObject("temp");
                list.add(new WeatherObject(String.valueOf(item.getLong("dt")), "" + temp.getDouble("day")
                        , "" + temp.getString("day"), "" + item.getInt("humidity"),
                        "" + item.getDouble("wind_speed")
                        , "" + temp.getDouble("min"),
                        "" + temp.getDouble("max")
                ));
            }
            return list;
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
