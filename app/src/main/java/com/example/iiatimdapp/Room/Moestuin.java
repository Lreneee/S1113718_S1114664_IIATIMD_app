package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Moestuin {

    @PrimaryKey
    private int moestuin_id;
    @ColumnInfo
    private String img;
    @ColumnInfo
    private String naam;
    @ColumnInfo
    private int moestuin_lengte;
    @ColumnInfo
    private int moestuin_breedte;

    public Moestuin(int moestuin_id, String img, String naam, int moestuin_lengte, int moestuin_breedte) {
        this.moestuin_id = moestuin_id;
        this.img = img;
        this.naam = naam;
        this.moestuin_lengte = moestuin_lengte;
        this.moestuin_breedte = moestuin_breedte;

    }

    public int getMoestuin_id() {
        return moestuin_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNaam() {
        return naam;
    }

    public void setTitle(String naam) {
        this.naam = naam;
    }

    public int getMoestuin_lengte() {
        return moestuin_lengte;
    }

    public int getMoestuin_breedte() {
        return moestuin_breedte;
    }
    public String toString(){
        return moestuin_id + " " + img + " " + naam + " " + moestuin_lengte + " " + moestuin_breedte;
    }
}

