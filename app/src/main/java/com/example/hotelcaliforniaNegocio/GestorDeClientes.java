package com.example.hotelcaliforniaNegocio;

import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaDatos.ClienteDataAccess;
import com.example.hotelcaliforniaModelo.Cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GestorDeClientes {
    public final String KEY_ID_CLIENTE = "id";
    public final String KEY_USUARIO = "usuario";
    public final String KEY_EMAIL = "email";
    public final String KEY_PASSWORD = "password";

    ClienteDataAccess clienteDA;

    public GestorDeClientes(SQLiteDatabase db) {
        clienteDA = new ClienteDataAccess(db);
    }

    public Map<String, String> getDatosClienteLogueado(){
        Cliente clienteLogueado = UserSession.getInstance().getCliente();

        Map<String, String> datosCliente = new HashMap<>();
        datosCliente.put(KEY_ID_CLIENTE, String.valueOf(clienteLogueado.getId()));
        datosCliente.put(KEY_USUARIO, String.valueOf(clienteLogueado.getUsuario()));
        datosCliente.put(KEY_EMAIL, String.valueOf(clienteLogueado.getEmail()));
        datosCliente.put(KEY_PASSWORD, String.valueOf(clienteLogueado.getPassword()));

        return datosCliente;
    }

    private Cliente getClienteLogueado(){ return UserSession.getInstance().getCliente(); }

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
        Set<String> emailsExistentes = new HashSet<>();
        ArrayList<Cliente> clientes = clienteDA.getAll();

        for (Cliente cliente : clientes) {
            String mail = cliente.getEmail();
            emailsExistentes.add(mail);
        }
        return emailsExistentes.contains(email);
    }
    public boolean modificarDatosCliente(String usuario, String email, String password) {
        Cliente clienteLogueado = getClienteLogueado();
        // Modificamos los datos del cliente logueado
        clienteLogueado.setUsuario(usuario);
        clienteLogueado.setEmail(email);
        clienteLogueado.setPassword(password);

        Cliente clienteModificado = clienteDA.update(clienteLogueado);
        if (clienteModificado != null){
            UserSession.getInstance().setCliente(clienteModificado);
            return true;
        }
        else {
            return false;
        }
    }
}