package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoestuinDetails {

    @PrimaryKey
    private int moestuin_id;
    @ColumnInfo
    private String naam;
    @ColumnInfo
    private String img;
    @ColumnInfo
    private int lengte_in_vakjes;
    @ColumnInfo
    private int breedte_in_vakjes;

    public MoestuinDetails(int moestuin_id, String naam, String img,  int lengte_in_vakjes, int breedte_in_vakjes){
        this.moestuin_id = moestuin_id;
        this.naam = naam;
        this.img = img;
        this.lengte_in_vakjes = lengte_in_vakjes;
        this.breedte_in_vakjes = breedte_in_vakjes;
    }
    public int getMoestuin_id(){
        return moestuin_id;
    }
    public String getImg(){return img;}
    public int getLengte_in_vakjes(){
        return lengte_in_vakjes;
    }
    public int getBreedte_in_vakjes(){
        return breedte_in_vakjes;
    }
    public String getNaam(){
        return naam;
    }
    public String toString(){
        return moestuin_id + " " + naam + " " + lengte_in_vakjes + " " + breedte_in_vakjes;
    }
}
