package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    boolean fieldsChecks = false;
    boolean allFieldsChecked= false;
    EditText emailLogin, passwordLogin;
     Button inicio;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLogin=findViewById(R.id.emailLogin);
        passwordLogin=findViewById(R.id.passwordLogin);
        inicio=findViewById(R.id.inicio);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {

                boolean fieldsChecked = allFieldsChecked;

                if (allFieldsChecked){
                    Intent i = new Intent(MainActivity.this, Home.class);
                    startActivity(i);
                }
            }
        });
    }

    public boolean FieldsChecks(){
        if (emailLogin.length()== 0){
            emailLogin.setError("Ingrese un Email valido");
            return false;
        }
        if (passwordLogin.length()==0){
            passwordLogin.setError("La contraseña debe contener al menos 6 digitos");
            return false;
        }
        return true;
    }

    public void iraregistro(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }



}

