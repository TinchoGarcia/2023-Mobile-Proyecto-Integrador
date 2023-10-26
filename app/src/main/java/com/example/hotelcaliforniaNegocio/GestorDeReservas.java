package com.example.hotelcaliforniaNegocio;

import android.content.Context;

import com.example.hotelcaliforniaDatos.IWritableDataAccess;
import com.example.hotelcaliforniaDatos.ReservaDataAccess;
import com.example.hotelcaliforniaModelo.Reserva;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class GestorDeReservas {
    IWritableDataAccess<Reserva> reservaDA;
    GestorDeClientes gestorDeClientes;

    public GestorDeReservas(Context context){
        reservaDA = new ReservaDataAccess(context);
        gestorDeClientes = new GestorDeClientes(context);
    }

    public boolean usuarioTieneReservas() {
        return !obtenerReservasNoAnuladasClienteLogueado().isEmpty();
    }

    public Reserva obtenerReservaParaMostrar(int reservaIndex) {
        return (0 <= reservaIndex && reservaIndex <= obtenerReservasNoAnuladasClienteLogueado().size())
                ? obtenerReservasNoAnuladasClienteLogueado().get(reservaIndex)
                : null;
    }

    public ArrayList<Reserva> obtenerReservasNoAnuladasClienteLogueado() {
        String idClienteString = gestorDeClientes.getDatosClienteLogueado().get(gestorDeClientes.KEY_ID_CLIENTE);
        int idCliente = Integer.parseInt(Objects.requireNonNull(idClienteString));
        ArrayList<Reserva> reservas = ((ReservaDataAccess) reservaDA).getAll(idCliente);
        reservas.removeIf(Reserva::isAnulada);
        return reservas;
    }

   public float calculoPrecio(Date fechaIngreso, Date fechaEgreso, float precioHab) {
    long milisegundosIngreso = fechaIngreso.getTime();
    long milisegundosEgreso = fechaEgreso.getTime();
    long diferenciaMilisegundos = milisegundosEgreso - milisegundosIngreso;
    long diferenciaDias = diferenciaMilisegundos / (24 * 60 * 60 * 1000);
    float precioTotal = precioHab * diferenciaDias;
    return precioTotal;
}

}