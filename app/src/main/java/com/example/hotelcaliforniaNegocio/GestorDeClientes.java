package com.example.hotelcaliforniaNegocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaDatos.ClienteDataAccess;
import com.example.hotelcaliforniaModelo.Cliente;

import java.util.ArrayList;

public class GestorDeClientes {
    ClienteDataAccess clienteDA;

    public GestorDeClientes(SQLiteDatabase db) {
        clienteDA = new ClienteDataAccess(db);
    }

    public Cliente getClienteLogueado(){
        return UserSession.getInstance().getCliente();
    }

    public boolean login(String email, String password){
        if (email.isEmpty() || password.isEmpty())
            return false;

        ArrayList<Cliente> clientes = clienteDA.getAll();
        for (Cliente cliente : clientes) {
            String mail = cliente.getEmail();
            String pass = cliente.getPassword();

            if (mail.equals(email) && pass.equals(password)){
                UserSession.getInstance().setCliente(cliente);
                return true;
            }
        }
        return false;
    }
}