package com.abhi41.roomdatabaseexample.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.abhi41.roomdatabaseexample.DataBase.AppDatabase;
import com.abhi41.roomdatabaseexample.Model.HeroTask;
import com.abhi41.roomdatabaseexample.Model.Task;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM "+ AppDatabase.MyToDos)
    List<Task> getAll();

    @Insert
    void insert(Task task);


    @Query("DELETE FROM " + AppDatabase.MyHeros)
    void truncateMyheros();

    @Insert
    void insertHeroListData(List<HeroTask> heroTaskList);

    @Query("SELECT * FROM " + AppDatabase.MyHeros)
    List<HeroTask> getAllHeroList();

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

    @Query("SELECT * FROM "+ AppDatabase.MyToDos + " WHERE id = :id ")
    int getUser(int id);





}
