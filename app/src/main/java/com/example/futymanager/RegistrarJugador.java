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

/**
 * La actividad RegistrarJugador permite a los administradores registrar nuevos jugadores en la base de datos.
 */
public class RegistrarJugador extends AppCompatActivity {

    // Campos de texto para ingresar los datos del jugador
    EditText edtUsuario, edtContrasena, edtNombre, edtApellidos, edtEdad, edtPosicion, edtDorsal, edtLesiones;
    // Botones para aceptar el registro o regresar a la pantalla principal
    Button btnAceptar, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilita el modo Edge-to-Edge en la actividad
        EdgeToEdge.enable(this);
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_registrar_jugador);

        // Inicialización de los campos de texto
        edtUsuario = findViewById(R.id.etUsuario);
        edtContrasena = findViewById(R.id.etcontrasena);
        edtNombre = findViewById(R.id.etnombre);
        edtApellidos = findViewById(R.id.etapellidos);
        edtEdad = findViewById(R.id.etedad);
        edtPosicion = findViewById(R.id.etposicion);
        edtDorsal = findViewById(R.id.etdorsal);
        edtLesiones = findViewById(R.id.etlesion);

        // Inicialización de los botones
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Configuración del listener para el botón Aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ejecuta el servicio para registrar el jugador
                ejecutarServicio("http://192.168.56.1/developeru/registrarJugador.php");
            }
        });

        // Configuración del listener para el botón Regresar
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la actividad Principal
                Intent intent = new Intent(RegistrarJugador.this, Principal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Método para ejecutar el servicio de registro del jugador enviando una solicitud POST a la URL especificada.
     * @param URL La URL del servicio de registro del jugador.
     */
    private void ejecutarServicio(String URL) {
        // Crear una solicitud de tipo StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Mostrar el mensaje de respuesta
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
                // Mostrar mensaje de error
                Toast.makeText(RegistrarJugador.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Parámetros a enviar en la solicitud POST
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

        // Crear una cola de solicitudes y agregar la solicitud
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
