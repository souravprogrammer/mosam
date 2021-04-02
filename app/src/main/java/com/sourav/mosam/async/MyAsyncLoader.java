package com.sourav.mosam.async;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.sourav.mosam.MyViewModel;
import com.sourav.mosam.WeatherApi;
import com.sourav.mosam.data.AppSettings;
import com.sourav.mosam.data.WeatherObject;
import com.sourav.mosam.database.ApplicationDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyAsyncLoader extends AsyncTaskLoader<ArrayList<WeatherObject>> {
    private MyViewModel model;
    ApplicationDatabase database = null;

    public MyAsyncLoader(Context context, MyViewModel model) {
        super(context);
        database = ApplicationDatabase.getInstance(context);
        this.model = model;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<WeatherObject> loadInBackground() {



            if (model.isDatanull()) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String place = sharedPreferences.getString(AppSettings.LOCATION_KEY, "delhi");
                ArrayList<WeatherObject> fetched = null;
                try {
                    WeatherApi.setLocation(place);
                 fetched  = WeatherApi.getOneCallApiData();
                }catch (Exception e )
                {
                    Log.e("weather:",e.getMessage());
                }
                /**if data we fetch from api return null or internet is not availabe */
                if (fetched == null) {
                    List<WeatherObject> Alldata = database.TaskDao().readData();
                    if (Alldata != null && Alldata.size() > 0) {
                        model.setCurrent(Alldata.get(0));
                        ArrayList<WeatherObject> buff = new ArrayList<WeatherObject>();
                        for (int i = 1; i < 8; i++) {
                            WeatherObject f = Alldata.get(i);
                            buff.add(f);
                        }
                        model.setData(buff);
                        ArrayList<WeatherObject> hour = new ArrayList<WeatherObject>();
                        int len = Alldata.size();
                        for (int i = 8; i < len; i++) {
                            hour.add(Alldata.get(i));
                        }
                        model.setHourData(hour);
                    }
                } else {
                    database.TaskDao().delete();
                    database.TaskDao().reset_id();
                    model.setData(fetched);
                    model.setCurrent(WeatherApi.getCurrentWeather());
                    model.setHourData(WeatherApi.getHourData());
                    database.TaskDao().insert(model.getCurrent());
                    int len = fetched.size();
                    for (int i = 0; i < len; i++) {
                        WeatherObject f = fetched.get(i);
                        database.TaskDao().insert(f);
                    }
                    len = model.getHourData().size();
                    for (int i = 0; i < len; i++) {
                        database.TaskDao().insert(model.getHourData().get(i));
                    }
                }
                return fetched;
            }

        return null;

    }


}
