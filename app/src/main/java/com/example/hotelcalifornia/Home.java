package com.example.hotelcalifornia;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hotelcaliforniaDatos.HabitacionDataAccess;
import com.example.hotelcaliforniaModelo.Habitacion;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class Home extends AppCompatActivity {


    EditText fechaIng, fechaSal; //para el calendario
    private DatePickerDialog.OnDateSetListener dateSetListener;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        fechaIng = findViewById(R.id.fechaIng);
        fechaSal = findViewById(R.id.fechaSal);
        RadioGroup radioGroup = findViewById(R.id.RadioGroup);

        // Crear una instancia de HabitacionDataAccess
        HabitacionDataAccess habitacionDataAccess = new HabitacionDataAccess(this);

        // Obtener todas las habitaciones desde la base de datos
        ArrayList<Habitacion> habitaciones = habitacionDataAccess.getAll();

        // Configurar el texto de los RadioButtons con nombre y precio
        for (int i = 0; i < habitaciones.size(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            Habitacion habitacion = habitaciones.get(i);
            String textoRadioButton = habitacion.getHabTipo() + " - $" + habitacion.getHabPrecio();
            radioButton.setText(textoRadioButton);
        }



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override  //barra de navegación
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
    public void fechaIngreso (View view){
        DatePickerDialog ing=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int DayOfMonth) {
                fechaIng.setText(DayOfMonth+"/"+(month+1)+"/"+year);
            }
        },2023, 9,1 );
        ing.show();
    }

    public void fechaSalida (View view){
        DatePickerDialog sal=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int DayOfMonth) {
                fechaSal.setText(DayOfMonth+"/"+(month+1)+"/"+year);
            }
        },2023, 9,1 );
        sal.show();
    }

    public void reservar(View view){
        Intent reservas=new Intent(this, Reservas.class);
        reservas.putExtra("idhabitacion", "habitacionId");
        startActivity(reservas);
    } //leva a reservas


}