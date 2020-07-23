package com.abhi41.roomdatabaseexample.DatabaseClient;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abhi41.roomdatabaseexample.DataBase.AppDatabase;

public class DatabaseClient {
    Context context;
    private static DatabaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    public DatabaseClient(Context context) {
        this.context = context;
        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(context,AppDatabase.class,"MyToDos").build();
    }


    public static synchronized DatabaseClient getInstance(Context context){
        if (mInstance == null){
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase(){ // passing interface instanced to add activity class
        return appDatabase;
    }

}
