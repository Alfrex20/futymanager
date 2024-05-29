package com.example.futymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistrarJugador extends AppCompatActivity {
    EditText edtUsuario, edtContrasena, edtNombre, edtApellidos, edtEdad, edtPosicion, edtDorsal, edtLesiones;
    Button btnAceptar, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_jugador);

        edtUsuario = findViewById(R.id.etUsuario);
        edtContrasena = findViewById(R.id.etcontrasena);
        edtNombre = findViewById(R.id.etnombre);
        edtApellidos = findViewById(R.id.etapellidos);
        edtEdad = findViewById(R.id.etedad);
        edtPosicion = findViewById(R.id.etposicion);
        edtDorsal = findViewById(R.id.etdorsal);
        edtLesiones = findViewById(R.id.etlesion);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://192.168.56.1/developeru/registrarJugador.php");
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarJugador.this, Principal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                // Comprobar si la respuesta contiene el mensaje de éxito
                if (response.trim().equals("Registro insertado correctamente")) {
                    // Cambiar a la ventana ConsultaFutbolistas después de mostrar el mensaje
                    Intent intent = new Intent(RegistrarJugador.this, ConsultaFutbolistas.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegistrarJugador.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Usuario", edtUsuario.getText().toString());
                parametros.put("Contrasena", edtContrasena.getText().toString());
                parametros.put("Nombre", edtNombre.getText().toString());
                parametros.put("Apellidos", edtApellidos.getText().toString());
                parametros.put("Edad", edtEdad.getText().toString());
                parametros.put("Posicion", edtPosicion.getText().toString());
                parametros.put("Dorsal", edtDorsal.getText().toString());
                parametros.put("Lesiones", edtLesiones.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
