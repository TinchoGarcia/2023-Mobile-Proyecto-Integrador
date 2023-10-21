package com.example.hotelcalifornia;

import static com.example.hotelcalifornia.Registro.LONG_MIN_PASS;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelcaliforniaNegocio.GestorDeClientes;
import com.example.hotelcaliforniaDatos.HotelSQLiteHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Map;


public class Edition extends AppCompatActivity {
    private static final String TAG_ERROR_EDICION = "Editar perfil error";
    EditText usuarioEditar, emailEditar, passwordEditar;
    private boolean passwordVisible = false;
    BottomNavigationView bottomNavigationView;
    GestorDeClientes gestorDeClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);

        // Obtenemos la instancia de la db:
        SQLiteDatabase db = HotelSQLiteHelper.getInstance(this).getDatabase();
        gestorDeClientes = new GestorDeClientes(db);

        // Seteamos el texto de cada editText con los datos del usuario logueado
        setearEditTextConDatosClienteLogueado();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu);


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
        ImageButton buttonShowPassword = findViewById(R.id.imageButton5);
        EditText editTextPassword = findViewById(R.id.editarPassword);

        buttonShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordVisible = !passwordVisible;
                if (passwordVisible) {
                    // Mostrar contraseña
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Ocultar contraseña
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // Mover el cursor al final del texto
                editTextPassword.setSelection(editTextPassword.getText().length());
            }
        });

    }

    private void setearEditTextConDatosClienteLogueado() {
        // Inicializamos los editText.
        usuarioEditar = findViewById(R.id.editarUsuario);
        emailEditar = findViewById(R.id.editarEmail);
        passwordEditar = findViewById(R.id.editarPassword);

        // Obtenemos los datos del cliente logueado
        Map<String, String> clienteLogueado = gestorDeClientes.getDatosClienteLogueado();

        usuarioEditar.setText(clienteLogueado.get(gestorDeClientes.KEY_USUARIO));
        emailEditar.setText(clienteLogueado.get(gestorDeClientes.KEY_EMAIL));
        passwordEditar.setText(clienteLogueado.get(gestorDeClientes.KEY_PASSWORD));
    }

    public void editProfile(View view) {
        Pair<Boolean, String> resultadoEdicion
                = editarDatos(usuarioEditar, emailEditar, passwordEditar);
        if (resultadoEdicion.first){
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        } else {
            String mjeError = resultadoEdicion.second;
            Log.e(TAG_ERROR_EDICION, mjeError);
            // TODO: Mostrar algun mensaje en pantalla con el mensaje que contiene
            //  la info de por qué no se pudo registrar.
        }
    }

    private Pair<Boolean, String> editarDatos(EditText usuario, EditText email, EditText password) {
        String usu = Utils.getStringFromEditText(usuario);
        String mail = Utils.getStringFromEditText(email);
        String pass = Utils.getStringFromEditText(password);

        String mjeError;
        // Validamos campos completos.
        if (usu.isEmpty() || mail.isEmpty() || pass.isEmpty()){
            mjeError = "Debe completar todos los campos.";
            return Pair.create(false, mjeError);
        }

        // Validamos mail nuevo en Db
        if (modificoEmail(mail) && gestorDeClientes.esEmailExistente(mail)){
            mjeError = "El email ingresado ya existe.";
            return Pair.create(false, mjeError);
        }

        // Validamos longitud de password mayor o igual a 6.
        if (pass.length()<LONG_MIN_PASS) {
            mjeError = "Su contraseña debe contener al menos 6 caracteres.";
            return Pair.create(false, mjeError);
        }

        if (gestorDeClientes.modificarDatosCliente(usu, mail, pass)){
            return Pair.create(true, "");
        } else {
            return Pair.create(false, "No fue posible editar su perfil, intente nuevamente.");
        }
    }

    private boolean modificoEmail(String email) {
        String emailOriginal = gestorDeClientes.getDatosClienteLogueado().get(gestorDeClientes.KEY_EMAIL);
        return !email.equals(emailOriginal);
    }

}