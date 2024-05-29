package com.example.futymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SeleccionarConsulta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seleccionar_consulta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnEquipos = findViewById(R.id.btnEquipos);
        Button btnFutbolistas = findViewById(R.id.btnFutbolistas);
        Button btnCancelar= findViewById(R.id.btnCancelar);

        btnEquipos.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarConsulta.this, ConsultaEquipos.class);
            startActivity(intent);
        });

        btnFutbolistas.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarConsulta.this, ConsultaFutbolistas.class);
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(SeleccionarConsulta.this, Principal.class);
            startActivity(intent);
        });
    }
}