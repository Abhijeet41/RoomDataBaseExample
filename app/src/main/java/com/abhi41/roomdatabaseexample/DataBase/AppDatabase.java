package com.abhi41.roomdatabaseexample.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.abhi41.roomdatabaseexample.Interface.TaskDao;
import com.abhi41.roomdatabaseexample.Model.HeroTask;
import com.abhi41.roomdatabaseexample.Model.Task;

@Database(entities = {Task.class, HeroTask.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static final String DB_NAME = "app_db";

    public static final String MyToDos = "MyToDos";
    public static final String MyHeros = "MyHeros";

    public static AppDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,DB_NAME).build();
        }
        return instance;
    }

    public abstract TaskDao taskDao();
}
