package com.sourav.mosam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourav.mosam.async.MyAsyncLoader;
import com.sourav.mosam.data.AppSettings;
import com.sourav.mosam.data.MyTimeConveter;
import com.sourav.mosam.data.WeatherObject;
import com.sourav.mosam.mynotificatin.MyIntentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WeatherObject>>
        , DateRecycleViewAdapter.OnclicklistnerRecycle {

    private final int LOADER_1 = 12;
    private DateRecycleViewAdapter dayAdapter;
    private HourRecycleViewAdapter hourAdapter;
    private MyViewModel model = null;
    private String headercolour, actvitycolour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.
                        getInstance(getApplication())).get(MyViewModel.class);


        /**Starting Service for notification*/
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);

        setTheam();
        setRecycleView();
        getLoaderManager().initLoader(LOADER_1, null, this);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.settings_bar) {
            Intent intent = new Intent(this, Settings.class);
            intent.putExtra(AppSettings.header_colour, headercolour);
            intent.putExtra(AppSettings.activity_colour, actvitycolour);
            startActivity(intent);

            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    /**
     * set theam by time
     **/
    private void setTheam() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat.format(Calendar.getInstance().getTime()).split(":")[0];

        int hours = Integer.parseInt(time);
        /** NIGHT MODE**/
        if (hours >= 19 && hours <= 23 || hours >= 0 && hours <= 4) {
            nightTheam();
        } else {
            dayTheam();
        }
    }

    /**
     * Night Theam
     **/
    private void nightTheam() {
        String grey = "#263137";
        ImageView status = findViewById(R.id.weather_status_image);
        status.setImageResource(R.drawable.ic_moon);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        String light_grey = "#37474f";
        headercolour = light_grey;
        actvitycolour = grey;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(light_grey)));
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor(light_grey));
        window.setNavigationBarColor(Color.parseColor(grey));
        FrameLayout frameLayout = findViewById(R.id.parent);
        frameLayout.setBackgroundColor(Color.parseColor(grey));
        try {

            findViewById(R.id.main_view).setBackgroundColor(Color.parseColor(light_grey));
        } catch (Exception e) {
            Log.e("view", e.getMessage());
            // findViewById(R.id.include).setBackgroundColor(Color.parseColor(light_grey));
        }


    }

    /**
     * Day Theam
     **/
    void dayTheam() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        /**TODO change this hard coded colour*/
        window.setStatusBarColor(Color.parseColor("#2196f3"));
        headercolour = "#2196f3";
        actvitycolour = "#1769aa";
        window.setNavigationBarColor(Color.parseColor("#1769aa"));
        Button button = findViewById(R.id.humidity_buttion);
        button.setBackground(ContextCompat.getDrawable(this, R.drawable.button_blue));
        findViewById(R.id.rain_button_mm).setBackground(ContextCompat.getDrawable(this, R.drawable.button_blue));
        findViewById(R.id.wind_button).setBackground(ContextCompat.getDrawable(this, R.drawable.button_blue));
    }

    private void setRecycleView() {
        RecyclerView recyclerView_days = findViewById(R.id.recycleView_days);
        RecyclerView recyclerView_hours = findViewById(R.id.recycleView_hours);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView_days.setLayoutManager(manager);
        recyclerView_hours.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        dayAdapter = new DateRecycleViewAdapter(this);
        hourAdapter = new HourRecycleViewAdapter();
        recyclerView_days.setAdapter(dayAdapter);
        recyclerView_hours.setAdapter(hourAdapter);
    }

    @Override
    public Loader<ArrayList<WeatherObject>> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_1) {
            return new MyAsyncLoader(this, model);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherObject>> loader, ArrayList<WeatherObject> data) {

        if (!model.isDatanull()) {
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            findViewById(R.id.scrollView_view).setVisibility(View.VISIBLE);
            try {
                findViewById(R.id.landscape_constrain).setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e("not landscape", e.getMessage());
            }
            ArrayList<WeatherObject> obj = model.getData();
            setcurrent(model.getCurrent());
            dayAdapter.updateData(obj, this);
            hourAdapter.updateData(model.getHourData(), this);
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherObject>> loader) {

    }

    /**
     * set the current tempreature to the view
     **/
    private void setcurrent(WeatherObject weatherObject) {
        String temp_unint = PreferenceManager.getDefaultSharedPreferences(this).getString(AppSettings.TEMP_UNIT_CHANGE
                , AppSettings.CELSIUS);
        TextView curr_temp = findViewById(R.id.current_temp);
        TextView max_temp = findViewById(R.id.max_temp);
        TextView min_temp = findViewById(R.id.min_temp);
        TextView Realfeel = findViewById(R.id.real_feel);
        TextView description = findViewById(R.id.description);


        TextView date = findViewById(R.id.today_date_hour_layout);
        date.setText(MyTimeConveter.getFullDate(Long.parseLong(weatherObject.getDt())));

        String x = weatherObject.getWind_speed() + " Mi/h";
        Button button = findViewById(R.id.wind_button);
        button.setText(x);
        button = findViewById(R.id.humidity_buttion);
        x = weatherObject.getHumidity() + "%";
        button.setText(x);
        description.setText(weatherObject.getdescription());
        if (temp_unint.equals(AppSettings.KELVIN)) {
            curr_temp.setText(WeatherObject.convertToKelvin(weatherObject.getTemp()));
            max_temp.setText(WeatherObject.convertToKelvin(weatherObject.get_max_temp()));
            min_temp.setText(WeatherObject.convertToKelvin(weatherObject.getMin_temp()));
            String tmp = getString(R.string.Realfeel) + " " + weatherObject.getReal_feel_like();
            Realfeel.setText(tmp);
        } else if (temp_unint.equals(AppSettings.FAHEENHEIT)) {
            curr_temp.setText(WeatherObject.contverttoFahrenheit(weatherObject.getTemp()));
            max_temp.setText(WeatherObject.contverttoFahrenheit(weatherObject.get_max_temp()));
            min_temp.setText(WeatherObject.contverttoFahrenheit(weatherObject.getMin_temp()));
            String tmp = getString(R.string.Realfeel) + " " + WeatherObject.contverttoFahrenheit(weatherObject.getReal_feel_like());
            Realfeel.setText(tmp);

        } else if (temp_unint.equals(AppSettings.CELSIUS)) {
            curr_temp.setText(WeatherObject.converttoCelsius(weatherObject.getTemp()));
            max_temp.setText(WeatherObject.converttoCelsius(weatherObject.get_max_temp()));
            min_temp.setText(WeatherObject.converttoCelsius(weatherObject.getMin_temp()));
            String tmp = getString(R.string.Realfeel) + " " + WeatherObject.converttoCelsius(weatherObject.getReal_feel_like());
            Realfeel.setText(tmp);
        }


    }

    /**
     * On Date Item Clicked
     */
    @Override
    public void onClicked(WeatherObject weather) {
        // TODO onclick date item
    }
}