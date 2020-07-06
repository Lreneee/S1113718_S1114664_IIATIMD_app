package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Moestuin {

    @PrimaryKey
    private int moestuin_id;
    @ColumnInfo
    private String image;
    @ColumnInfo
    private String naam;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private int moestuin_lengte;
    @ColumnInfo
    private int moestuin_breedte;

    public Moestuin(int moestuin_id, String image, String naam, String description, int moestuin_lengte, int moestuin_breedte) {
        this.moestuin_id = moestuin_id;
        this.image = image;
        this.naam = naam;
        this.description = description;
        this.moestuin_lengte = moestuin_lengte;
        this.moestuin_breedte =moestuin_breedte;

    }
    public int getMoestuin_id(){
        return moestuin_id;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNaam() {
        return naam;
    }
    public void setTitle(String naam) {
        this.naam = naam;
    }

    public String getDescription() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }
    public int getMoestuin_lengte(){
        return moestuin_lengte;
    }
    public int getMoestuin_breedte(){
        return moestuin_breedte;
    }
}
