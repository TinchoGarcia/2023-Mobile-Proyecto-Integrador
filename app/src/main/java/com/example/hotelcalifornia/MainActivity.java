package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hotelcaliforniaDatos.HotelSQLiteHelper;
import com.example.hotelcaliforniaNegocio.GestorDeClientes;

public class MainActivity extends AppCompatActivity {

    EditText emailLogin, passwordLogin;
    Button inicio;
    SQLiteDatabase db;
    private static final String DB = "SqliteDb";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        inicio = findViewById(R.id.inicio);

        // Creamos o hacemos conexión a la DB
        db = HotelSQLiteHelper.getInstance(this).getDatabase();
    }

    public void iraregistro(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    public void home(View view){
        if (loginExitoso(emailLogin, passwordLogin)){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        } else {
            // TODO: debería mostrar algún mensaje en rojo que las credenciales no son válidas
        }
    }

    private boolean loginExitoso(EditText email, EditText password){
        GestorDeClientes gestorDeClientes = new GestorDeClientes(db);
        // TODO: agregar validaciones para cada campo con mensajes para el usuario
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        return gestorDeClientes.login(mail, pass);
    }
}
