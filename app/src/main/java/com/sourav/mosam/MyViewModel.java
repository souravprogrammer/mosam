package com.sourav.mosam;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.sourav.mosam.data.WeatherObject;
import com.sourav.mosam.database.ApplicationDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {


    private ArrayList<WeatherObject> Data = null;
    private ArrayList<WeatherObject> HourData = null;
    private WeatherObject current  = null ;

    public void setCurrent(WeatherObject current) {
        this.current = current;
    }
    public WeatherObject getCurrent() {
        return current;
    }

    public void setData(ArrayList<WeatherObject> data) {
        Data = data;
    }

    public boolean isDatanull() {

        if (Data == null) {
            return true;
        }

           return false;

    }

    public void setHourData(ArrayList<WeatherObject> hourData) {
        HourData = hourData;
    }

    public ArrayList<WeatherObject> getHourData() {
        return HourData;
    }

    public ArrayList<WeatherObject> getData() {
        return Data;
    }

}
