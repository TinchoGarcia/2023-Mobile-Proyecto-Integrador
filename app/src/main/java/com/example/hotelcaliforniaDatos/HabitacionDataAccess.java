package com.example.hotelcaliforniaDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaModelo.Habitacion;

import java.util.ArrayList;

public class HabitacionDataAccess implements IReadableDataAccess<Habitacion> {
    SQLiteDatabase db;
    public HabitacionDataAccess(Context context){
        db = HotelSQLiteHelper.getInstance(context).getDatabase();
    }

    @Override
    public Habitacion getById(int id) {
        return null;
    }

    @Override
    public ArrayList<Habitacion> getAll() {
        return null;
    }
}
