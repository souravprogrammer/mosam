package com.sourav.mosam.data;

public class WeatherObject {


    private String temp;
    private String real_feel_like;
    private String humidity;
    private String wind_speed, dt;
    private String min_temp, _max_temp;


    public WeatherObject( String dt,String temp, String real_feel_like,
                         String humidity, String wind_speed,
                         String min_temp, String _max_temp) {

        this.temp = temp;
        this.real_feel_like = real_feel_like;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.dt = dt;
        this.min_temp = min_temp;
        this._max_temp = _max_temp;
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
