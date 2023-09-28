package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tarjeta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);
    }
    public void iraDetalle (View view){
        Intent intent = new Intent(this, Detalle.class);
        startActivity(intent);
    }
    // Descomentar lo siguiente al tener la Activity de Notificaciones
    /* public void notificaciones (View view){
        Intent intent = new Intent(this, Notificaciones.class);
        startActivity(intent);

    } */
}