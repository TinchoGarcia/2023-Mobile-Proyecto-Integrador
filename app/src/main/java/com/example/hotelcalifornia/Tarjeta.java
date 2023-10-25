package com.example.hotelcalifornia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Tarjeta extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);




        EditText codSeguridadInput = findViewById(R.id.codSeguridadInput);

        // Establecer el tipo de entrada como número
        codSeguridadInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Aplicar un InputFilter para limitar a 3 dígitos numéricos
        codSeguridadInput.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(3), // Límite de longitud
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        // Solo permite dígitos numéricos
                        for (int i = start; i < end; i++) {
                            if (!Character.isDigit(source.charAt(i))) {
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });


        // Obtener referencia al EditText de Fecha de Vencimiento
        EditText fechaVencInput = findViewById(R.id.fechaVencInput);

        fechaVencInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();

                // Aplicar lógica de formateo
                if (input.length() == 2) {
                    input += "/";
                    fechaVencInput.setText(input);
                    fechaVencInput.setSelection(input.length());
                }
            }
        });


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
    public void volverADetalle (View view){
        Intent intent = new Intent(this, Detalle.class);
        finish();
    }


    public void notificaciones (View view){
        String mensaje = "¡Reserva confirmada con éxito!";
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("mensaje", mensaje);
        startActivity(intent);

    }
}