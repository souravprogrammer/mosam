package com.sourav.mosam.mynotificatin;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.TimeUnit;

public class ScheduleNonPerodicJob {
    private static final int Job_id = 112;
    private static boolean mInitlize = false;

    synchronized public static void executeJob(Context context) {

        if (mInitlize) {
            return;
        }
        ComponentName componentName = new ComponentName(context, NonperodicJobscheduler.class);

        JobInfo jobInfo = new JobInfo.Builder(Job_id, componentName)
                .setMinimumLatency(TimeUnit.MINUTES.toMillis(50))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);

        mInitlize = true;
    }

    public static void cancelJ0b(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(Job_id);
    }
}
