package com.example.hotelcalifornia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hotelcaliforniaDatos.ReservaDataAccess;
import com.example.hotelcaliforniaModelo.Reserva;
import com.example.hotelcaliforniaNegocio.GestorDeClientes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Reservas extends AppCompatActivity {
    Button buttonEliminar;
    BottomNavigationView bottomNavigationView;
    SQLiteDatabase db;
    ReservaDataAccess reservaDA;
    GestorDeClientes gestorDeClientes;
    TextView textViewCheckin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);
        buttonEliminar = (Button) findViewById(R.id.buttonEliminar);

        textViewCheckin = findViewById(R.id.textCheckIn);

        gestorDeClientes = new GestorDeClientes(this);

        reservaDA = new ReservaDataAccess(this);
        int idCliente = Integer.parseInt(gestorDeClientes.getDatosClienteLogueado().get(gestorDeClientes.KEY_ID_CLIENTE));
        ArrayList<Reserva> reservas = reservaDA.getAll(idCliente);
        Reserva primerreserva = reservas.get(0);
        textViewCheckin.setText(primerreserva.getCheckIn().toString());


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

    public void pago(View view) {
        Intent pagar = new Intent(this, Detalle.class);
        startActivity(pagar);
    }


    public void IraHome(View view) {
        Intent intent = new Intent(this, Home.class);
        finish();
    } //flechita para volver

    public void modificar(View view) {
        Intent modificar = new Intent(this, Home.class);
        startActivity(modificar);
    }

    public void eliminar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro que desea eliminar la reserva?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              Intent intent =new Intent(getApplicationContext(), Home.class);
              startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

}