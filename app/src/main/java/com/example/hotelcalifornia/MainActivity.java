package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hotelcaliforniaDatos.HotelSQLiteHelper;
import com.example.hotelcaliforniaNegocio.GestorDeClientes;

public class MainActivity extends AppCompatActivity {

    EditText emailLogin, passwordLogin;
    Button inicio;
    GestorDeClientes gestorDeClientes;
    private static final String TAG_ERROR_LOGIN = "Login incorrecto";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        inicio = findViewById(R.id.inicio);

        // Creamos o hacemos conexión a la DB
        SQLiteDatabase db = HotelSQLiteHelper.getInstance(this).getDatabase();
        gestorDeClientes = new GestorDeClientes(db);
    }

    public void iraregistro(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    public void home(View view){
        Pair<Boolean, String> resultadoLogin = login(emailLogin, passwordLogin);
        if (resultadoLogin.first) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        } else {
            String mjeError = resultadoLogin.second;
            Log.e(TAG_ERROR_LOGIN, mjeError);
            // TODO: Mostrar algun mensaje en pantalla con el mensaje que contiene
            //  la info de por qué no se pudo registrar.
        }
    }

    private Pair<Boolean, String> login(EditText email, EditText password){
        String mail = Utils.getStringFromEditText(email);
        String pass = Utils.getStringFromEditText(password);

        String mjeError;
        // Validamos campos completos.
        if (mail.isEmpty() || pass.isEmpty()){
            mjeError = "Debe completar todos los campos.";
            return Pair.create(false, mjeError);
        }
        if (gestorDeClientes.login(mail, pass)){
            return Pair.create(true, "");
        } else {
            return Pair.create(false, "Error desconocido por el sistema.");
        }
    }
}