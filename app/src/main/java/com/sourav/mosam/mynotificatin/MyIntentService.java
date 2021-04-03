package com.sourav.mosam.mynotificatin;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyIntentService extends IntentService {



    public MyIntentService() {
        super("mosam service");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
            ScheduleNonPerodicJob.executeJob(this);
    }
}
