package com.example.hotelcaliforniaNegocio;

import com.example.hotelcaliforniaModelo.Cliente;

public class UserSession {
    private static UserSession instance;

    // Almacena la información del usuario logueado
    private Cliente cliente;

    private UserSession() {
        // Constructor privado para asegurar que solo haya una instancia
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isClienteLoggedIn() {
        return cliente != null;
    }

    public void logout() {
        cliente = null; // Cierra la sesión
    }
}
