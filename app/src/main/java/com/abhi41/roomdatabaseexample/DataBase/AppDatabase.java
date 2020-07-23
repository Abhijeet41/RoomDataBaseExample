package com.abhi41.roomdatabaseexample.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.abhi41.roomdatabaseexample.Interface.TaskDao;
import com.abhi41.roomdatabaseexample.Model.Task;

@Database(entities = {Task.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
