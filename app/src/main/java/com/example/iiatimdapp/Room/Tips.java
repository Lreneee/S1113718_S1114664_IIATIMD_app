package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tips {

    @PrimaryKey
    private int id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String short_description;
    @ColumnInfo
    private String long_description;
    @ColumnInfo
    private String img;

    public Tips(int id, String title, String short_description, String long_description, String img){
        this.id =id;
        this.title=title;
        this.short_description=short_description;
        this.long_description=long_description;
        this.img=img;
    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getShort_description(){
        return short_description;
    }
    public String getLong_description(){
        return long_description;
    }
    public String getImg(){
        return getImg();
    }
    public String toString(){
        return id + " " + title + " " + short_description + " " + long_description;
    }
}
