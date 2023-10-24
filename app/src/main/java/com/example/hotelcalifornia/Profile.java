package com.example.hotelcalifornia;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelcaliforniaDatos.HotelSQLiteHelper;
import com.example.hotelcaliforniaNegocio.GestorDeClientes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Switch recibeNotSwitch;
    GestorDeClientes gestorDeClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        gestorDeClientes = new GestorDeClientes(this);

        // Inicalizamos switch notificaciones y modificamos datos en la DB segun la selección
        recibeNotSwitch = findViewById(R.id.switchNotificaciones);
        setRecibeNotificacionesSegun(recibeNotSwitch);

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

    private void setRecibeNotificacionesSegun(Switch sw) {
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // El Switch está encendido, el cliente recibe notificaciones.
                    recibeNotificaciones(true);
                } else {
                    recibeNotificaciones(false);
                }
            }
        });
    }

    private void recibeNotificaciones(boolean recibeNotif) {
        gestorDeClientes.modificarDatosCliente(recibeNotif);
    }

    public void goToHome(View view) {

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void goToEdition(View view) {

        Intent intent = new Intent(this, Edition.class);
        startActivity(intent);
    }
    //lleva a editar mi perfil

    // Eliminar Cuenta implica: settear el cliente como NO activo  + logout.
    public void onEliminarCuenta(View view){
        gestorDeClientes.eliminarCliente();
        onLogout(view);
    }

    // Cerrar sesión implica: que el cliente logueado sea null + cerrar la conexión a la db.
    public void onLogout(View view){
        gestorDeClientes.logout();
        HotelSQLiteHelper.getInstance(this).close();
        goToLogin(view);
    }

    private void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToContact(View view) {
        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }//lleva a contacto atraves del boton contactanos

}