package com.example.hotelcalifornia;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelcaliforniaDatos.HotelSQLiteHelper;
import com.example.hotelcaliforniaNegocio.GestorDeClientes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SQLiteDatabase db;
    GestorDeClientes gestorDeClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        // Creamos o hacemos conexión a la DB
        db = HotelSQLiteHelper.getInstance(this).getDatabase();
        gestorDeClientes = new GestorDeClientes(db);

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
        db.close();
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