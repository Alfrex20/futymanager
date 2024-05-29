package com.example.futymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SeleccionarModificacion extends AppCompatActivity {

    String tipoUsuario, dni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seleccionar_modificacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener el tipo de usuario y el DNI almacenados en las preferencias compartidas
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        tipoUsuario = preferences.getString("Usuario", "");
        dni = preferences.getString("Dni", "");

        // Configurar los botones y sus listeners
        Button btnAdministradores = findViewById(R.id.btnAdministradores);
        Button btnEquipos = findViewById(R.id.btnEquipos);
        Button btnFutbolistas = findViewById(R.id.btnFutbolistas);
        Button btnCancelar= findViewById(R.id.btnCancelar);
        Button btnSeleccionar= findViewById(R.id.btnSeleccionarEquipo);

        // Verificar si el DNI está vacío
        boolean esAdmin = !TextUtils.isEmpty(dni);

        if (!esAdmin) {
            // Deshabilitar visualmente los botones
            btnAdministradores.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
            btnEquipos.setAlpha(0.5f); // Reduce la opacidad para mostrar que está deshabilitado
        }
        btnAdministradores.setOnClickListener(v -> {
            if (esAdmin) {
                Intent intent = new Intent(SeleccionarModificacion.this, ModificarInfoAdmins.class);
                startActivity(intent);
            }else{
                Toast.makeText(SeleccionarModificacion.this, "No tienes permisos de administrador", Toast.LENGTH_SHORT).show();
            }
        });

        btnEquipos.setOnClickListener(v -> {
            if(esAdmin) {
                Intent intent = new Intent(SeleccionarModificacion.this, ModificarInfoEquipos.class);
                startActivity(intent);
            }else{
                Toast.makeText(SeleccionarModificacion.this, "No tienes permisos de administrador", Toast.LENGTH_SHORT).show();
            }
        });

        btnFutbolistas.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarModificacion.this, ModificarInfo.class);
            startActivity(intent);
        });

        btnSeleccionar.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarModificacion.this, SeleccionarEquipo.class);
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarModificacion.this, Principal.class);
            startActivity(intent);
        });
    }
}
