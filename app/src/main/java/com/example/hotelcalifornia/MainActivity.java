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

public class MainActivity extends AppCompatActivity {

    EditText emailLogin, passwordLogin;
    Button inicio;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLogin=findViewById(R.id.emailLogin);
        passwordLogin=findViewById(R.id.passwordLogin);
        inicio=findViewById(R.id.inicio);

        // Creamos o hacemos conexión a la DB
        HotelSQLiteHelper hotelSqlHelper = new HotelSQLiteHelper(this, "DbHotelCalifornia", null, 1);
        SQLiteDatabase db = hotelSqlHelper.getReadableDatabase();
        // continuar con el código para obtener datos de la db y poder loguearse.
    }

    public void iraregistro(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    public void home(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}

