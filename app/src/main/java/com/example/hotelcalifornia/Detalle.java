package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Detalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
    }

    public void realizarPago(View view) {
        Intent intent = new Intent(this, Tarjeta.class);
        startActivity(intent);
    }

    public void iraReservas(View view) {
        Intent intent = new Intent(this, Reservas.class);
        startActivity(intent);
    }
}