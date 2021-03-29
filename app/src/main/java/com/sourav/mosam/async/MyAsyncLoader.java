package com.sourav.mosam.async;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.sourav.mosam.WeatherApi;
import com.sourav.mosam.data.WeatherObject;

import java.util.ArrayList;

public class MyAsyncLoader extends AsyncTaskLoader<ArrayList<WeatherObject>> {
    public MyAsyncLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<WeatherObject> loadInBackground() {

        WeatherApi.setLocation("delhi");
        return WeatherApi.getOneCallApiData();
    }
}
