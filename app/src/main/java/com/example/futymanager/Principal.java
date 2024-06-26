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

/**
 * Principal es la actividad principal del usuario una vez que ha iniciado sesión.
 * Proporciona opciones de navegación para diferentes funcionalidades basadas en el tipo de usuario.
 */
public class Principal extends AppCompatActivity {

    // Componentes de la interfaz de usuario
    Button inscribirJugador, inscribirEquipo, modificarInfo, cerrarSesion, consultas, torneo, eliminar;
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

        // Inicialización de los componentes de la interfaz de usuario
        inscribirJugador = findViewById(R.id.btnInscribirJugador);
        inscribirEquipo = findViewById(R.id.btnInscribirEquipo);
        modificarInfo = findViewById(R.id.btnModificarInfo);
        cerrarSesion = findViewById(R.id.btnCerrarSesion);
        consultas = findViewById(R.id.btnConsultas);
        torneo = findViewById(R.id.btnTorneo);
        eliminar = findViewById(R.id.btnEliminar);

        // Verificar si el usuario es administrador basado en la presencia del DNI
        boolean esAdmin = !TextUtils.isEmpty(dni);

        // Deshabilitar botones para usuarios que no son administradores
        if (!esAdmin) {
            inscribirJugador.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
            inscribirEquipo.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
            eliminar.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
        }

        // Configuración de los listeners para los botones
        inscribirJugador.setOnClickListener(v -> {
            if (esAdmin) {
                // Si es administrador, permite inscribir jugador
                Intent intent = new Intent(Principal.this, RegistrarJugador.class);
                startActivity(intent);
            } else {
                // Muestra mensaje de permisos insuficientes
                Toast.makeText(Principal.this, "No tienes permisos de administrador", Toast.LENGTH_SHORT).show();
            }
        });

        inscribirEquipo.setOnClickListener(v -> {
            if (esAdmin) {
                // Si es administrador, permite inscribir equipo
                Intent intent = new Intent(Principal.this, RegistrarEquipo.class);
                startActivity(intent);
            } else {
                // Muestra mensaje de permisos insuficientes
                Toast.makeText(Principal.this, "No tienes permisos de administrador",
                        Toast.LENGTH_SHORT).show();
            }
        });

        modificarInfo.setOnClickListener(v -> {
            // Permite modificar información, sin importar el tipo de usuario
            Intent intent = new Intent(Principal.this, SeleccionarModificacion.class);
            startActivity(intent);
        });

        consultas.setOnClickListener(v -> {
            // Permite realizar consultas, sin importar el tipo de usuario
            Intent intent = new Intent(Principal.this, SeleccionarConsulta.class);
            startActivity(intent);
        });

        torneo.setOnClickListener(v -> {
            // Permite consultar el torneo, sin importar el tipo de usuario
            Intent intent = new Intent(Principal.this, ConsultaTorneo.class);
            startActivity(intent);
        });

        eliminar.setOnClickListener(v -> {
            if (esAdmin) {
                // Si es administrador, permite eliminar
                Intent intent = new Intent(Principal.this, SeleccionarEliminar.class);
                startActivity(intent);
            } else {
                // Muestra mensaje de permisos insuficientes
                Toast.makeText(Principal.this, "No tienes permisos de administrador",
                        Toast.LENGTH_SHORT).show();
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
            finish(); // Finaliza la actividad actual para evitar volver con el botón de retroceso

            // Mostrar mensaje de "Hasta luego"
            Toast.makeText(Principal.this, "Hasta luego", Toast.LENGTH_SHORT).show();
        });
    }
}
