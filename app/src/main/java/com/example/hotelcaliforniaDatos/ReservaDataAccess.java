package com.example.hotelcaliforniaDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaModelo.Habitacion;
import com.example.hotelcaliforniaModelo.Reserva;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReservaDataAccess implements IWritableDataAccess<Reserva> {

    SQLiteDatabase db;
    HabitacionDataAccess habitacionDA;
    ClienteDataAccess clienteDA;
    public ReservaDataAccess(Context context) {
        this.db = HotelSQLiteHelper.getInstance(context).getDatabase();
        habitacionDA = new HabitacionDataAccess(context);
        clienteDA = new ClienteDataAccess(context);
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
            String[] campos = new String[] {
                    "reservaId", "habitacionId", "clienteId", "chechIn", "checkOut", "notificadoAlCliente", "anulada","pagada" };
            String[] args = new String[]{ String.valueOf(id) };
            Cursor c = db.query("Reserva", campos, "clienteId = ?", args, null, null, null);

            if (c.moveToFirst()) { // Verifica que exista al menos un registro.
                //Recorremos el cursor por los todos los registros que trae la query
                do {
                    // Casteamos todos los datos del registro y lo guardamos en un objeto Cliente
                    // para almacenarlo en la lista clientes.
                    Reserva reserva = new Reserva();

                    int reservaId = c.getInt(0);
                    reserva.setId(reservaId);

                    int habitacionId = c.getInt(1);
                    // TODO: esto deberia verse asi
                    // reserva.setHabitacion(habitacionDA.getById(habitacionId));
                    // Lo harcodeamos por ahora:
                    Habitacion hab = new Habitacion();
                    hab.setHabTipo("Simple, hab id = " + String.valueOf(habitacionId));
                    hab.setHabPrecio(habitacionId*1000);
                    reserva.setHabitacion(hab);

                    int clienteId = c.getInt(2);
                    reserva.setCliente(clienteDA.getById(clienteId));

                    String fechaCheckIn = c.getString(3);
                    String fechaCheckOut = c.getString(4);
                    Date checkin, chechout;
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        checkin = formatoFecha.parse(fechaCheckIn);
                        chechout = formatoFecha.parse(fechaCheckOut);
                    } catch (ParseException e) {
                        checkin = new Date(1900,1,1);
                        chechout = new Date(1900,2,2);
                    }
                    reserva.setCheckIn(checkin);
                    reserva.setCheckOut(chechout);

                    int notificadoAlCliente = c.getInt(5);
                    boolean notificado = notificadoAlCliente == 1;
                    reserva.setNotificadoAlCliente(notificado);

                    int anulada = c.getInt(6);
                    boolean anu = anulada == 1;
                    reserva.setAnulada(anu);

                    int pagada = c.getInt(7);
                    boolean pago = pagada == 1;
                    reserva.setPagada(pago);

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