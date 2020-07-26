package com.abhi41.roomdatabaseexample.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.abhi41.roomdatabaseexample.DataBase.AppDatabase;

import java.io.Serializable;

@Entity(tableName = AppDatabase.MyHeros)
public class HeroTask implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "realname")
    String realname;
    @ColumnInfo(name = "imageurl")
    String imageurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
