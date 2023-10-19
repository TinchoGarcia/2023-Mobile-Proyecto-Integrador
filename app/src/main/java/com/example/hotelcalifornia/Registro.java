package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hotelcaliforniaDatos.HotelSQLiteHelper;
import com.example.hotelcaliforniaNegocio.GestorDeClientes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro extends AppCompatActivity {

    EditText usuarioRegistro, fechaNacRegistro, emailRegistro, passwordRegistro;
    Button crear;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Obtenemos la instancia de la db:
        db = HotelSQLiteHelper.getInstance(this).getDatabase();

        // Inicializamos los elementos dinámicos.
        usuarioRegistro = findViewById(R.id.IngresarUsuario);
        fechaNacRegistro = findViewById(R.id.editfechaNacimiento);
        emailRegistro = findViewById(R.id.introducirEmail);
        passwordRegistro = findViewById(R.id.introducirPass);

        crear = findViewById(R.id.CrearRegistro);
    }

    public void iraMainActivity (View view){
        if (registroExitoso(usuarioRegistro, fechaNacRegistro, emailRegistro, passwordRegistro)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // TODO: Mostrar algun mensaje que no se pudo registrar.
        }
    }

    private boolean registroExitoso(EditText usuario, EditText fechaNac, EditText email, EditText password) {
        GestorDeClientes gestorDeClientes = new GestorDeClientes(db);
        // TODO: agregar validaciones para cada campo
        String usu = usuario.getText().toString();

        String fecha = fechaNac.getText().toString();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNacimiento;
        try {
            fechaNacimiento = formato.parse(fecha);

        } catch (ParseException e) {
            fechaNacimiento = new Date(1900,01,01);
        }

        String mail = email.getText().toString();

        String pass = password.getText().toString();

        return gestorDeClientes.registrar(usu, fechaNacimiento, mail, pass);
    }
}
