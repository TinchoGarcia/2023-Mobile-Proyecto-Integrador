package com.example.hotelcaliforniaDatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hotelcaliforniaModelo.Cliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClienteDataAccess implements CUDDataAccess<Cliente>, RDataAccess<Cliente> {
    SQLiteDatabase db;
    private static final String FORMATO_FECHA_DB = "yyyy-MM-dd";

    public ClienteDataAccess(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void create(Cliente entidad) {
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("usuario", entidad.getUsuario());
        nuevoRegistro.put("email", entidad.getEmail());
        nuevoRegistro.put("password", entidad.getPassword());
        String fechaNacimiento =
                new SimpleDateFormat(FORMATO_FECHA_DB).format(entidad.getFechaNac());
        nuevoRegistro.put("fechaDeNacimiento", fechaNacimiento);
        nuevoRegistro.put("activo", entidad.getActivo());

        //Insertamos el registro en la base de datos
        db.insert("Cliente", null, nuevoRegistro);
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
        ArrayList<Cliente> clientes = new ArrayList<>();
        if(db != null)
        {
            String[] campos = new String[] {
                    "clienteId", "usuario", "email", "password", "fechaDeNacimiento", "activo" };
            Cursor c = db.query("Cliente", campos, null, null, null, null, null);

            if (c.moveToFirst()) { // Verifica que exista al menos un registro.
                //Recorremos el cursor por los todos los registros que trae la query
                do {
                    // Casteamos todos los datos del registro y lo guardamos en un objeto Cliente
                    // para almacenarlo en la lista clientes.
                    Cliente cliente = new Cliente();

                    int clienteId = c.getInt(0);
                    cliente.setId(clienteId);

                    String usuario = c.getString(1);
                    cliente.setUsuario(usuario);

                    String email = c.getString(2);
                    cliente.setEmail(email);

                    String password = c.getString(3);
                    cliente.setPassword(password);

                    String fechaDeNacimiento = c.getString(4);
                    SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA_DB);
                    Date fechaNac;
                    try {
                        fechaNac = formato.parse(fechaDeNacimiento);

                    } catch (ParseException e) {
                        fechaNac = new Date(1900,01,01);
                    }
                    cliente.setFechaNac(fechaNac);

                    int activoData = c.getInt(5);
                    boolean activo = activoData == 1;
                    cliente.setActivo(activo);

                    clientes.add(cliente);
                } while(c.moveToNext());
            }
        }
        return clientes;
    }
}
