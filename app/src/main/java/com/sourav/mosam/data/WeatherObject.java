package com.sourav.mosam.data;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.PublicKey;

@Entity(tableName = "daylist")
public class WeatherObject {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    private String temp;
    private String real_feel_like;
    private String humidity;
    private String wind_speed, dt;
    private String min_temp, _max_temp;
    private String description;


    public WeatherObject(String dt, String temp, String real_feel_like,
                         String humidity, String wind_speed,
                         String min_temp, String _max_temp, String description) {

        this.description = description;
        this.temp = temp;
        this.real_feel_like = real_feel_like;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.dt = dt;
        this.min_temp = min_temp;
        this._max_temp = _max_temp;
    }

    public static String converttoCelsius(String temp) {
        double temp_celcius = Double.parseDouble(temp) - 273.15;
        /** removing the unwanted values after decimal and keeping only one value after decimal*/

        return "" + String.format("%.0f", temp_celcius) + "\u2103";
    }

    public static String contverttoFahrenheit(String temp) {
        double temp_faranite = (Double.parseDouble(temp) - 273.15) * 9 / 5 + 32;

        return String.format("%.0f", temp_faranite) + "\u2109";
    }
    public static String convertToKelvin(String temp_kelvin){

        double temp = Double.parseDouble(temp_kelvin);
        return String.format("%.0f",temp)+"K";
    }
    public String getdescription() {
        return this.description;
    }

    public String gettime() {
        return this.dt;
    }

    public String getDt() {
        return dt;
    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }

    public String getReal_feel_like() {
        return real_feel_like;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public String get_max_temp() {
        return _max_temp;
    }
}
