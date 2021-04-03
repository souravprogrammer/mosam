package com.sourav.mosam.mynotificatin;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;

import java.util.concurrent.TimeUnit;


/**
 * Non perodic job service
 * PERODIC JOB Initlised here
 **/
public class NonperodicJobscheduler extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {

        ComponentName componentName = new ComponentName(this, PerodicJobService.class);
        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo info = new JobInfo.Builder(1212, componentName)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(TimeUnit.HOURS.toMillis(5))
                .build();
        scheduler.schedule(info);

        jobFinished(params, false);
        return true;
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
