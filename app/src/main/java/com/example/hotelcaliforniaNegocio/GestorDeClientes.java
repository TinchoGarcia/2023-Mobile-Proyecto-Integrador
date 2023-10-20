package com.example.hotelcaliforniaNegocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaDatos.ClienteDataAccess;
import com.example.hotelcaliforniaModelo.Cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class GestorDeClientes {
    ClienteDataAccess clienteDA;

    public GestorDeClientes(SQLiteDatabase db) {
        clienteDA = new ClienteDataAccess(db);
    }

    public Cliente getClienteLogueado(){
        return UserSession.getInstance().getCliente();
    }

    public void logout(){ UserSession.getInstance().logout(); }

    public boolean login(String email, String password){
        if (email.isEmpty() || password.isEmpty())
            return false;

        ArrayList<Cliente> clientes = clienteDA.getAll();
        for (Cliente cliente : clientes) {
            String mail = cliente.getEmail();
            String pass = cliente.getPassword();
            boolean activo = cliente.getActivo();

            if (mail.equals(email) && pass.equals(password) && activo){
                UserSession.getInstance().setCliente(cliente);
                return true;
            }
        }
        return false;
    }

    public boolean registrar(String usu, Date fecha, String mail, String pass) {
        if (usu.isEmpty() || mail.isEmpty() || pass.isEmpty())
            return false;

        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setUsuario(usu);
        clienteNuevo.setEmail(mail);
        clienteNuevo.setPassword(pass);
        clienteNuevo.setFechaNac(fecha);
        clienteNuevo.setActivo(true);
        clienteDA.create(clienteNuevo);
        return true;
    }

    public void eliminarCliente() {
        Cliente cliente = UserSession.getInstance().getCliente();
        clienteDA.delete(cliente.getId());
    }

    public boolean esEmailExistente(String email) {
        HashSet<String> emailsExistentes = new HashSet<>();
        ArrayList<Cliente> clientes = clienteDA.getAll();

        for (Cliente cliente : clientes) {
            String mail = cliente.getEmail();
            emailsExistentes.add(mail);
        }

        return emailsExistentes.contains(email);
    }
}
