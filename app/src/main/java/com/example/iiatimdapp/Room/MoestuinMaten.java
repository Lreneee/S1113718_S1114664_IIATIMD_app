package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoestuinMaten {

    @PrimaryKey
    private int id;
    @ColumnInfo
    private int lengte_in_cm;
    @ColumnInfo
    private int lengte_in_vakjes;
    @ColumnInfo
    private int breedte_in_cm;
    @ColumnInfo
    private int breedte_in_vakjes;

    public MoestuinMaten(int id, int lengte_in_cm, int lengte_in_vakjes, int breedte_in_cm, int breedte_in_vakjes) {
        this.id = id;
        this.lengte_in_cm = lengte_in_cm;
        this.lengte_in_vakjes = lengte_in_vakjes;
        this.breedte_in_cm = breedte_in_cm;
        this.breedte_in_vakjes = breedte_in_vakjes;
    }

    public int getId() {
        return id;
    }

    public int getLengte_in_cm() {
        return lengte_in_cm;
    }

    public int getLengte_in_vakjes() {
        return lengte_in_vakjes;
    }

    public int getBreedte_in_cm() {
        return breedte_in_cm;
    }

    public int getBreedte_in_vakjes() {
        return breedte_in_vakjes;
    }

    public String toString() {
        return "id: " + id + ", lengte in cm: " + lengte_in_cm + ", lengte in vakjes: " + lengte_in_vakjes  + ", breedte_in_cm: " + breedte_in_cm + ", breedte_in_vakjes: " +  breedte_in_vakjes;
    }
}
