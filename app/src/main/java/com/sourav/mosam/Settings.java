package com.sourav.mosam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.sourav.mosam.data.AppSettings;

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settheam();

    }

    private void settheam() {


        Intent intent = getIntent();

        String header = intent.getStringExtra(AppSettings.header_colour);
        String activity = intent.getStringExtra(AppSettings.activity_colour);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(header)));
//        findViewById(R.id.settings_layout).setBackgroundColor(Color.parseColor(activity));
        Window window = getWindow() ;
        window.setNavigationBarColor(Color.parseColor(activity));
        window.setStatusBarColor(Color.parseColor(header));
    }
}