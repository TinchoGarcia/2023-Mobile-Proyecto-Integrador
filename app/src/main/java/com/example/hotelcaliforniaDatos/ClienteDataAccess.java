package com.example.hotelcaliforniaDatos;

import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaModelo.Cliente;

import java.util.ArrayList;

public class ClienteDataAccess implements CUDDataAccess<Cliente>, RDataAccess<Cliente> {
    SQLiteDatabase db;

    private static final String FORMATO_FECHA_DB = "yyyy-MM-dd";

    public ClienteDataAccess(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void create(Cliente entidad) {
    }

    @Override
    public Cliente update(Cliente entidad) {
        return null;
    }

    @Override
    public Cliente update(int id) {
        return null;
    }

    @Override
    public void delete(Cliente entidad) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Cliente getById(int id) {
        return null;
    }

    @Override
    public ArrayList<Cliente> getAll() {
        return null;
    }
}
