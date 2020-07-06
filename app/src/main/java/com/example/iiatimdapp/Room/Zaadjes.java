package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Zaadjes {

    @PrimaryKey
    private int zaadjes_id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String img;
    @ColumnInfo
    private String soort;
    @ColumnInfo
    private String description;

    public Zaadjes(int zaadjes_id, String name, String img, String soort, String description){
        this.zaadjes_id = zaadjes_id;
        this.name = name;
        this.img = img;
        this.soort = soort;
        this.description = description;
    }

    public int getId() {
        return zaadjes_id;
    }
    public String getName() {
        return name;
    }
    public String getImg() {
        return img;
    }
    public String getSoort() {
        return soort;
    }
    public String getDescription() {
        return description;
    }
    public String toString(){
        return "id: " + zaadjes_id + ", name: " + name + ", soort: " + soort + ", img: " + img;
    }
}
