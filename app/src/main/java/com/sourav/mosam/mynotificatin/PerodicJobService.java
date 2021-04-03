package com.sourav.mosam.mynotificatin;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.sourav.mosam.WeatherApi;
import com.sourav.mosam.data.AppSettings;
import com.sourav.mosam.data.WeatherObject;

public class PerodicJobService extends JobService {

    private AsyncTask mAsynctask;

    @Override
    public boolean onStartJob(JobParameters params) {

        /**
         * Popping up a Notification
         * */
        mAsynctask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
                String place = sharedPreferences.getString(AppSettings.LOCATION_KEY, "delhi");
                WeatherApi.setLocation(place);

                WeatherObject obj = WeatherApi.getCurrentWeather();
                String text = WeatherObject.converttoCelsius( obj.getTemp())+" in "+ place ;
                NotificationUtils.executeNotification(PerodicJobService.this, text, obj.getDescription());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(params, false);
            }
        };

        mAsynctask.execute();

        return true;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        if (mAsynctask != null) {
            mAsynctask.cancel(true);
        }

        return false;
    }
}
