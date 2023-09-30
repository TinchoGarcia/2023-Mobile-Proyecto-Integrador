package com.example.hotelcalifornia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


    }
    public void goToHome(View view) {

        //Intent intent = new Intent(this, Home.class);
        //startActivity(intent);
    }
    public void goToEdition(View view) {

        Intent intent = new Intent(this, Edition.class);
        startActivity(intent);
    }
    //lleva a editar mi perfil
    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //lleva a login a traves de boton eliminar cuenta y cerrar sesion

    public void goToContact(View view) {
        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }//lleva a contacto atraves del boton contactanos






}

