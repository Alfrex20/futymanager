package com.example.futymanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Principal extends AppCompatActivity {

    Button inscribirJugador, inscribirEquipo, modificarInfo, cerrarSesion,consultas,torneo,eliminar;
    String tipoUsuario, dni;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Obtener el tipo de usuario y el DNI almacenados en las preferencias compartidas
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        tipoUsuario = preferences.getString("Usuario", "");
        dni = preferences.getString("Dni", "");

        inscribirJugador = findViewById(R.id.btnInscribirJugador);
        inscribirEquipo = findViewById(R.id.btnInscribirEquipo);
        modificarInfo = findViewById(R.id.btnModificarInfo);
        cerrarSesion = findViewById(R.id.btnCerrarSesion);
        consultas = findViewById(R.id.btnConsultas);
        torneo = findViewById(R.id.btnTorneo);
        eliminar = findViewById(R.id.btnEliminar);

        // Verificar si el DNI está vacío
        boolean esAdmin = !TextUtils.isEmpty(dni);

        if (!esAdmin) {
            // Deshabilitar visualmente los botones
            inscribirJugador.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
            inscribirEquipo.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
            eliminar.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
        }

        inscribirJugador.setOnClickListener(v -> {
            if (esAdmin) {
                Intent intent = new Intent(Principal.this, RegistrarJugador.class);
                startActivity(intent);
            } else {
                Toast.makeText(Principal.this, "No tienes permisos de administrador", Toast.LENGTH_SHORT).show();
            }
        });

        inscribirEquipo.setOnClickListener(v -> {
            if (esAdmin) {
                Intent intent = new Intent(Principal.this, RegistrarEquipo.class);
                startActivity(intent);
            } else {
                Toast.makeText(Principal.this, "No tienes permisos de administrador", Toast.LENGTH_SHORT).show();
            }
        });

        modificarInfo.setOnClickListener(v -> {
            if(esAdmin) {
                Intent intent = new Intent(Principal.this, SeleccionarModificacion.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Principal.this, SeleccionarModificacion.class);
                startActivity(intent);
            }
        });
        consultas.setOnClickListener(v -> {
                Intent intent = new Intent(Principal.this, SeleccionarConsulta.class);
                startActivity(intent);
        });
        torneo.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, ConsultaTorneo.class);
            startActivity(intent);
        });
        eliminar.setOnClickListener(v -> {
            if(esAdmin) {
                Intent intent = new Intent(Principal.this, SeleccionarEliminar.class);
                startActivity(intent);
            }else{
                Toast.makeText(Principal.this, "No tienes permisos de administrador", Toast.LENGTH_SHORT).show();
            }
        });
        cerrarSesion.setOnClickListener(v -> {
            // Limpiar las preferencias compartidas
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            // Redirigir al MainActivity
            Intent intent = new Intent(Principal.this, MainActivity.class);
            startActivity(intent);
            finish(); // Opcional: finalizar la actividad actual para que no se pueda volver con el botón de retroceso

            // Mostrar mensaje de "Hasta luego"
            Toast.makeText(Principal.this, "Hasta luego", Toast.LENGTH_SHORT).show();
        });
    }
}
