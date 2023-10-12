package com.example.hotelcalifornia;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class Home extends AppCompatActivity {

    String []habitaciones = {"Habitación Single/Doble","Habitación Triple","Suite Exclusiva","Suite Junior"};
    int []fotos = {R.drawable.individualbeds,R.drawable.singledoble,R.drawable.suite,R.drawable.suiteii};
    ViewPager2 viewPagerHabitaciones; //para el viewpager de las habitaciones

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

        //viewPagerHabitaciones= findViewById(R.id.viewPagerHabitaciones);
        //viewPagerHabitaciones.setAdapter(new Adaptadorhabitaciones());

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
                    Intent intent = new Intent(getApplicationContext(), Notification.class);
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
        startActivity(reservas);
    } //leva a reservas

    //para en ViewPager -carousel
   /* private class Adaptadorhabitaciones extends RecyclerView.Adapter<Adaptadorhabitaciones.AdaptadorhabitacionesHolder> {
        @NonNull
        @Override
        public AdaptadorhabitacionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorhabitacionesHolder(getLayoutInflater().inflate(R.layout.layouthabitaciones,parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorhabitacionesHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return habitaciones.length;
        }

        class AdaptadorhabitacionesHolder extends RecyclerView.ViewHolder{
            ImageButton hab1;
            TextView textHab;
            public AdaptadorhabitacionesHolder(@NonNull View itemView) {
                super(itemView);
                hab1= itemView.findViewById(R.id.imageHab);
                textHab=itemView.findViewById(R.id.RoomSingleD);
            }

            public void imprimir(int position) {
                hab1.setImageResource(fotos[position]);
                textHab.setText(habitaciones[position]);
            }

        }


    }

*/
}