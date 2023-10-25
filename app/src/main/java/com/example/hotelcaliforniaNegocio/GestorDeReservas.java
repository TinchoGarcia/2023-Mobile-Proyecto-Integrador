package com.example.hotelcaliforniaNegocio;

import android.content.Context;

import com.example.hotelcaliforniaDatos.IWritableDataAccess;
import com.example.hotelcaliforniaDatos.ReservaDataAccess;
import com.example.hotelcaliforniaModelo.Reserva;

import java.util.ArrayList;
import java.util.Objects;

public class GestorDeReservas {
    IWritableDataAccess<Reserva> reservaDA;
    GestorDeClientes gestorDeClientes;

    public GestorDeReservas(Context context){
        reservaDA = new ReservaDataAccess(context);
        gestorDeClientes = new GestorDeClientes(context);
    }

    public boolean usuarioTieneReservas() {
        return !obtenerReservasClienteLogueado().isEmpty();
    }

    public Reserva obtenerReserva(int reservaIndex) {
        return (0 <= reservaIndex && reservaIndex <= obtenerReservasClienteLogueado().size())
                ? obtenerReservasClienteLogueado().get(reservaIndex)
                : null;
    }

    public ArrayList<Reserva> obtenerReservasClienteLogueado() {
        String idClienteString = gestorDeClientes.getDatosClienteLogueado().get(gestorDeClientes.KEY_ID_CLIENTE);
        int idCliente = Integer.parseInt(Objects.requireNonNull(idClienteString));
        ArrayList<Reserva> reservas = ((ReservaDataAccess) reservaDA).getAll(idCliente);
        return reservas;
    }
}
