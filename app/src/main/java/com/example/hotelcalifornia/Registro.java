package com.example.hotelcalifornia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hotelcaliforniaDatos.HotelSQLiteHelper;
import com.example.hotelcaliforniaNegocio.GestorDeClientes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registro extends AppCompatActivity {

    EditText usuarioRegistro, fechaNacRegistro, emailRegistro, passwordRegistro;
    Button crear;
    GestorDeClientes gestorDeClientes;
    private static final String FORMATO_FECHA_FORMULARIO = "dd/MM/yyyy";
    private static final int LONG_MIN_PASS = 6;
    private static final String TAG_ERROR_REGISTRO = "Registro no logrado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Obtenemos la instancia de la db:
        SQLiteDatabase db = HotelSQLiteHelper.getInstance(this).getDatabase();
        gestorDeClientes = new GestorDeClientes(db);

        // Inicializamos los elementos dinámicos.
        usuarioRegistro = findViewById(R.id.IngresarUsuario);
        fechaNacRegistro = findViewById(R.id.editfechaNacimiento);
        emailRegistro = findViewById(R.id.introducirEmail);
        passwordRegistro = findViewById(R.id.introducirPass);

        fechaNacRegistro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario implementar esta función.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario implementar esta función.
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (input.length() == 2 || input.length() == 5) {
                    input += "/";
                    fechaNacRegistro.setText(input);
                    fechaNacRegistro.setSelection(input.length());
                }
            }
        });

        crear = findViewById(R.id.CrearRegistro);
    }

    public void iraMainActivity (View view){
        Pair<Boolean, String> resultadoRegistro
                = registrar(usuarioRegistro, fechaNacRegistro, emailRegistro, passwordRegistro);
        if (resultadoRegistro.first) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            String mjeError = resultadoRegistro.second;
            Log.e(TAG_ERROR_REGISTRO, mjeError);
            // TODO: Mostrar algun mensaje en pantalla con el mensaje que contiene
            //  la info de por qué no se pudo registrar.
        }
    }

    private Pair<Boolean, String> registrar(EditText usuario, EditText fechaNac, EditText email, EditText password) {
        String usu = Utils.getStringFromEditText(usuario);
        String fecha = Utils.getStringFromEditText(fechaNac);
        String mail = Utils.getStringFromEditText(email);
        String pass = Utils.getStringFromEditText(password);

        String mjeError;
        // Validamos campos completos.
        if (usu.isEmpty() || fecha.isEmpty() || mail.isEmpty() || pass.isEmpty()){
            mjeError = "Debe completar todos los campos.";
            return Pair.create(false, mjeError);
        }

        // Validamos mail nuevo en Db
        if (gestorDeClientes.esEmailExistente(mail)){
            mjeError = "El email ingresado ya existe.";
            return Pair.create(false, mjeError);
        }

        // Validamos longitud de password mayor o igual a 6.
        if (pass.length()<LONG_MIN_PASS){
            mjeError = "Su contraseña debe contener al menos 6 caracteres.";
            return Pair.create(false, mjeError);
        }

        // Validamos y parseamos fecha a tipo Date.
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA_FORMULARIO, Locale.getDefault());
        Date fechaNacimiento;
        try {
            fechaNacimiento = formato.parse(fecha);

        } catch (ParseException e) {
            mjeError = e.getMessage();
            return Pair.create(false, mjeError);
        }

        if (gestorDeClientes.registrar(usu, fechaNacimiento, mail, pass)){
            return Pair.create(true, "");
        } else {
            return Pair.create(false, "No fue posible su registro, intente nuevamente.");
        }
    }
}