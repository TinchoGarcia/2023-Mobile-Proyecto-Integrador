package com.example.hotelcalifornia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Contact extends AppCompatActivity {
    public void goToProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);

    }
    public void goToHome(View view) {
        EditText mensajeEditText = findViewById(R.id.editTextText3);
        String mensaje = mensajeEditText.getText().toString();

        // Crear un Intent para enviar el mensaje a través de una aplicación de envío
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        sendIntent.setType("text/plain");

        // Mostrar el selector de aplicaciones para que el usuario elija
        Intent chooserIntent = Intent.createChooser(sendIntent, "Enviar mensaje con...");

        // Verificar si hay aplicaciones disponibles para manejar el Intent
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        } else {
            // Manejar el caso donde no hay aplicaciones disponibles
            Toast.makeText(this, "No hay aplicaciones disponibles para enviar el mensaje.", Toast.LENGTH_SHORT).show();
        }
    }

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.compra) {
                    Intent intent = new Intent(getApplicationContext(), Reservas.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.notificaciones) {
                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.perfil) {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });

    }

}