package com.example.futymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtPass, edtDni;
    Button bnIniciar;

    String Usuario, Contrasena, Dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsuario = findViewById(R.id.etUsuario);
        edtPass = findViewById(R.id.etPass);
        edtDni = findViewById(R.id.etDni);
        bnIniciar = findViewById(R.id.btnIniciar);
        recuperarPreferencias();

        bnIniciar.setOnClickListener(v -> {
            // Obtener los valores de los campos de texto
            Usuario = edtUsuario.getText().toString();
            Contrasena = edtPass.getText().toString();
            Dni = edtDni.getText().toString();

            // Verificar si los campos están vacíos
            if (TextUtils.isEmpty(Usuario) || TextUtils.isEmpty(Contrasena)) {
                Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Si no están vacíos, realizar la validación del usuario
                validarUsuario("http://192.168.56.1/developeru/validar_usuario.php");
            }
        });
    }

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            switch (response) {
                case "OK":
                case "Futbolista":
                case "Entrenador":
                    // Credenciales válidas, inicia sesión
                    Log.d("MainActivity", "Tipo de usuario recibido del servidor: " + response);
                    guardarPreferencias(response.equals("OK"));
                    Intent intent = new Intent(getApplicationContext(), Principal.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    // Credenciales incorrectas, muestra mensaje al usuario
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    break;
            }
        }, volleyError -> {
            Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Usuario", Usuario);
                parametros.put("Contrasena", Contrasena);
                if (!TextUtils.isEmpty(Dni)) {
                    parametros.put("Dni", Dni);
                }
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void guardarPreferencias(boolean isAdmin) {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Usuario", Usuario);
        editor.putString("Contrasena", Contrasena);
        editor.putString("TipoUsuario", isAdmin ? "Admin" : "User"); // Guarda el tipo de usuario
        if (isAdmin) {
            editor.putString("Dni", Dni);
        } else {
            editor.remove("Dni");
        }
        editor.putBoolean("Sesion", true);
        editor.apply();
    }

    private void recuperarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        edtUsuario.setText(preferences.getString("Usuario", ""));
        edtPass.setText(preferences.getString("Contrasena", ""));
        edtDni.setText(preferences.getString("Dni", ""));
    }
}
