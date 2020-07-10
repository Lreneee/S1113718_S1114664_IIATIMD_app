package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ZaadjesToegevoegd {

    @ColumnInfo
    private int zaadjes_id;
    @ColumnInfo
    private int x;

    public ZaadjesToegevoegd(int zaadjes_id, int x){
        this.zaadjes_id = zaadjes_id;
        this.x = x;
    }
    public int getZaadjes_id(){
        return zaadjes_id;
    }
    public int getX(){
        return x;
    }
    public String toString(){
        return "zaadjes id: " + zaadjes_id + "x: " + x;
    }
}
