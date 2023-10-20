package com.example.hotelcaliforniaDatos;

import com.example.hotelcaliforniaModelo.ClaseBase;

public interface CUDDataAccess<T extends ClaseBase> {
    void create(T entidad);
    T update(T entidad);
    T update(int id);
    void delete(T entidad);
    void delete(int id);
}