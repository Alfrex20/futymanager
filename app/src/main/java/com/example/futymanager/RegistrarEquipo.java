package com.example.futymanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

public class RegistrarEquipo extends AppCompatActivity {
    EditText edtNombre,edtEntrenador,edtpj,edtpg,edtpp,edtpe,edtgMarcados,edtgRecibidos,edtPuntos;
    Button btnAceptar,btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_equipo);

        // Asignación de vistas
        edtNombre = findViewById(R.id.etnombre);
        edtEntrenador = findViewById(R.id.etentrenador);
        edtpj = findViewById(R.id.etpj);
        edtpg = findViewById(R.id.etpg);
        edtpp = findViewById(R.id.etpp);
        edtpe = findViewById(R.id.etpe);
        edtgMarcados = findViewById(R.id.etgMarcados);
        edtgRecibidos = findViewById(R.id.etgContra);
        edtPuntos = findViewById(R.id.etPuntos);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Configuración del OnClickListener para el botón Aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://192.168.56.1/developeru/registrarEquipo.php");
            }
        });

        // Configuración del OnClickListener para el botón Regresar
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iniciamos la actividad de la clase Principal
                Intent intent = new Intent(RegistrarEquipo.this, Principal.class);
                startActivity(intent);
                // Cerramos la actividad actual para que no se apile en la pila de actividades
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
                    Intent intent = new Intent(RegistrarEquipo.this, ConsultaEquipos.class);
                    startActivity(intent);
                    finish();
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegistrarEquipo.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("Nombre",edtNombre.getText().toString());
                parametros.put("Entrenador",edtEntrenador.getText().toString());
                parametros.put("partidosJugados",edtpj.getText().toString());
                parametros.put("partidosGanados",edtpg.getText().toString());
                parametros.put("partidosPerdidos",edtpp.getText().toString());
                parametros.put("partidosEmpatados",edtpe.getText().toString());
                parametros.put("golesMarcados",edtgMarcados.getText().toString());
                parametros.put("golesEnContra",edtgRecibidos.getText().toString());
                parametros.put("Puntos",edtPuntos.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}