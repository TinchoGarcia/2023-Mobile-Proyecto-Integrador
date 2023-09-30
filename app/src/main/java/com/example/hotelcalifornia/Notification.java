package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Notification extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
    }

    public void goToHome(View view) {

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}