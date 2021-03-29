package com.sourav.mosam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.widget.Toast;

import com.sourav.mosam.async.MyAsyncLoader;
import com.sourav.mosam.data.WeatherObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<WeatherObject>> {

    private final int LOADER_1 = 12;
    private DateRecycleViewAdapter dayAdapter ;
    private RecyclerView recyclerView_days ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView_days = findViewById(R.id.recycleView_days) ;
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false) ;
        recyclerView_days.setLayoutManager(manager);
        dayAdapter = new DateRecycleViewAdapter();
        recyclerView_days.setAdapter(dayAdapter);
        getLoaderManager().initLoader(LOADER_1, null, this);

    }

    @Override
    public Loader<ArrayList<WeatherObject>> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_1) {
            return new MyAsyncLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherObject>> loader, ArrayList<WeatherObject> data) {

//        int size = data.size() ;
//        WeatherObject obj = data.get(size-3);

        dayAdapter.updateData(data);
        Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherObject>> loader) {

    }
}