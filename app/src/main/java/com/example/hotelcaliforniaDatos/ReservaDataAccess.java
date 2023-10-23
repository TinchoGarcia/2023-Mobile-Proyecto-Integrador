package com.example.hotelcaliforniaDatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaModelo.Cliente;
import com.example.hotelcaliforniaModelo.Reserva;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReservaDataAccess implements IWritableDataAccess<Reserva> {

    SQLiteDatabase db;
    public ReservaDataAccess(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Reserva getById(int id) {
        return null;
    }

    @Override
    public ArrayList<Reserva> getAll() {
        return null;
    }


    public ArrayList<Reserva> getAll(int id) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        if(db != null)
        {
                String[] args = new String[]{
                        String.valueOf(id)
                };
                String[] campos = new String[] {
                    "reservaId", "habitacionId", "clienteId", "chechIn", "checkOut", "notificadoAlCliente", "anulada","pagada" };
            Cursor c = db.query("Reserva", campos, "clienteId = ?", args, null, null, null);

            if (c.moveToFirst()) { // Verifica que exista al menos un registro.
                //Recorremos el cursor por los todos los registros que trae la query
                do {
                    // Casteamos todos los datos del registro y lo guardamos en un objeto Cliente
                    // para almacenarlo en la lista clientes.
                    Reserva reserva = new Reserva();


                    String fechaCheckIn = c.getString(3);
                    Date checkin;
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        checkin = formatoFecha.parse(fechaCheckIn);
                    } catch (ParseException e) {
                        checkin = new Date(1900,1,1);
                    }
                    reserva.setCheckIn(checkin);

                    reservas.add(reserva);
                } while(c.moveToNext());
            }
            c.close();
        }
        return reservas;
    }

    @Override
    public void create(Reserva entidad) {

    }

    @Override
    public Reserva update(Reserva entidad) {
        return null;
    }

    @Override
    public void delete(Reserva entidad) {

    }

    @Override
    public void delete(int id) {

    }
}
