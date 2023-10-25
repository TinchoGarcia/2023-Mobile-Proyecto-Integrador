package com.example.hotelcalifornia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hotelcaliforniaModelo.Reserva;
import com.example.hotelcaliforniaNegocio.GestorDeReservas;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Reservas extends AppCompatActivity {
    Button buttonEliminar, buttonPagar;
    ImageButton buttonPrevReserva, buttonNextReserva;
    TextView textViewCheckin, textViewCheckout, textViewTipoHabitacion, textViewMontoTotal, textNoHayReservas;
    BottomNavigationView bottomNavigationView;
    GestorDeReservas gestorDeReservas;
    private int reservaActualIndex = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        // Inicializamos los elementos visuales
        inicializarEventosVisuales();

        gestorDeReservas = new GestorDeReservas(this);
        if (gestorDeReservas.usuarioTieneReservas()){
            // Mostramos la reserva actual o la última hecha por el cliente.
            mostrarReserva(reservaActualIndex);
            mostrarElementosVisuales(true);
        } else {
            textNoHayReservas.setText(R.string.usuario_sin_reservas);
            mostrarElementosVisuales(false);
        }

        activarBotonesPrevAndNext();
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

    private void mostrarElementosVisuales(boolean mostrar) {
        int visibility = mostrar ? View.VISIBLE : View.INVISIBLE;
        int visibilityNoHayReservas = mostrar ? View.INVISIBLE : View.VISIBLE;

        List<View> elementosVisuales = Arrays.asList(
                textViewTipoHabitacion,
                textViewCheckin,
                textViewCheckout,
                textViewMontoTotal,
                buttonEliminar,
                buttonPagar,
                buttonPrevReserva,
                buttonNextReserva
        );

        for (View view : elementosVisuales) {
            view.setVisibility(visibility);
        }
        textNoHayReservas.setVisibility(visibilityNoHayReservas);
    }

    private void inicializarEventosVisuales() {
        // TextView
        textViewTipoHabitacion = findViewById(R.id.textTipoHab);
        textViewCheckin = findViewById(R.id.textCheckIn);
        textViewCheckout = findViewById(R.id.textCheckout);
        textViewMontoTotal = findViewById(R.id.textMontoTotal);
        textNoHayReservas = findViewById(R.id.textNoHayReservas);

        // Botones
        buttonEliminar = (Button) findViewById(R.id.buttonEliminar);
        buttonPagar = findViewById(R.id.buttonPagar);
        buttonPrevReserva = findViewById(R.id.buttonPrevReserva);
        buttonNextReserva = findViewById(R.id.buttonNextReserva);
    }

    private void mostrarReserva(int reservaActualIndex) {
        Reserva reserva = gestorDeReservas.obtenerReserva(reservaActualIndex);

        // Seteamos los textos de la info
        textViewTipoHabitacion.setText(reserva.getHabitacion().getHabTipo());
        SimpleDateFormat formato = new SimpleDateFormat(Utils.FORMATO_FECHA_FORMULARIO, Locale.getDefault());
        String fechaCheckin = formato.format(reserva.getCheckIn());
        String fechaCheckout = formato.format(reserva.getCheckOut());
        textViewCheckin.setText(fechaCheckin);
        textViewCheckout.setText(fechaCheckout);
        // TODO: Cambiar por valor que da la función de Fer
        textViewMontoTotal.setText(String.valueOf(reserva.getHabitacion().getHabPrecio()) + "FER");

        // Seteamos si los botones están habilitados o no.
        if (reserva.isPagada()){
            buttonPagar.setText(R.string.pagada);
            buttonPagar.setClickable(false);
            buttonPagar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gris));
        } else{
            buttonPagar.setText(R.string.pagar);
            buttonPagar.setClickable(true);
            buttonPagar.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.naranja));
        }
    }

    private void activarBotonesPrevAndNext() {
        // Obtenemos todas las reservas del usuario logueado
        ArrayList<Reserva> listaDeReservas = gestorDeReservas.obtenerReservasClienteLogueado();

        // Ocultamos los botones si es la primer reserva o la ultima
        if (reservaActualIndex == 0){
            buttonPrevReserva.setVisibility(View.INVISIBLE);
        } else {
            buttonPrevReserva.setVisibility(View.VISIBLE);
        }
        if (reservaActualIndex == listaDeReservas.size() - 1){
            buttonNextReserva.setVisibility(View.INVISIBLE);
        } else {
            buttonNextReserva.setVisibility(View.VISIBLE);
        }
        // Para que los cambios se reflejen
        invalidateOptionsMenu();

        // Manejar el clic en el botón "Prev"
        buttonPrevReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reservaActualIndex > 0) {
                    reservaActualIndex--;
                    mostrarReserva(reservaActualIndex);
                    activarBotonesPrevAndNext();
                }
            }
        });

        // Manejar el clic en el botón "Next"
        buttonNextReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reservaActualIndex < listaDeReservas.size() - 1) {
                    reservaActualIndex++;
                    mostrarReserva(reservaActualIndex);
                    activarBotonesPrevAndNext();
                }
            }
        });
    }

    public void pago(View view) {
        //TODO: Completar el intent con el id de la Reserva para usarla en la vista siguiente
        Intent pagar = new Intent(this, Detalle.class);
        startActivity(pagar);
    }

    public void IraHome(View view) {
        Intent intent = new Intent(this, Home.class);
        finish();
    } //flechita para volver

    public void eliminar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro que desea eliminar la reserva?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: Completar con un update a la Db de modificar como anulada = true la reserva actual:
                //  Reserva reserva = gestorDeReservas.obtenerReserva(reservaActualIndex);
                Intent intent =new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

}