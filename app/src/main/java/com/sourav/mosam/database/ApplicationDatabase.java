package com.sourav.mosam.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.sourav.mosam.data.WeatherObject;

@Database(entities ={WeatherObject.class} , version = 1 , exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static String DATABASENAME = "weatherCache" ;
    private static ApplicationDatabase mInstance =null ;
    private static final Object LOCK = new Object() ;
    public static ApplicationDatabase getInstance(Context context){

        if(mInstance==null){
            synchronized (LOCK){
                mInstance = Room.databaseBuilder(context.getApplicationContext(),ApplicationDatabase.class,
                        ApplicationDatabase.DATABASENAME).build();
                return mInstance ;
            }
        }
        return mInstance ;
    }

    public abstract DataBaseDao TaskDao() ;
}
