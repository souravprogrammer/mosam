package com.sourav.mosam.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sourav.mosam.data.WeatherObject;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DataBaseDao {

    @Query("SELECT * FROM daylist")
    List<WeatherObject> readData();

    @Insert
     void insert(WeatherObject weatherObject);

    @Update
    void update(WeatherObject weatherObject);
    @Delete
    void Deleteitem(WeatherObject weatherObject);

    @Query("DELETE FROM daylist")
    void delete();
    @Query("DELETE FROM sqlite_sequence;")
    void reset_id();
}
