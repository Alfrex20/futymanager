package com.example.futymanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModificarInfoEquipos extends AppCompatActivity {

    private Spinner spinnerID;
    private EditText etNombre, etEntrenador, etPartidosJugados, etPartidosGanados, etPartidosPerdidos, etPartidosEmpatados, etGolesMarcados, etGolesEnContra, etPuntos;
    private Button btnAceptar, btnRegresar;
    private RequestQueue requestQueue;
    private ArrayList<String> idsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_info_equipos);

        // Initialize UI elements
        spinnerID = findViewById(R.id.spinnerID);
        etNombre = findViewById(R.id.etnombre);
        etEntrenador = findViewById(R.id.etentrenador);
        etPartidosJugados = findViewById(R.id.etpj);
        etPartidosGanados = findViewById(R.id.etpg);
        etPartidosPerdidos = findViewById(R.id.etpp);
        etPartidosEmpatados = findViewById(R.id.etpe);
        etGolesMarcados = findViewById(R.id.etgMarcados);
        etGolesEnContra = findViewById(R.id.etgContra);
        etPuntos = findViewById(R.id.etPuntos);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        requestQueue = Volley.newRequestQueue(this);

        loadIDs();

        spinnerID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedID = idsList.get(position);
                loadUserData(selectedID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnRegresar.setOnClickListener(v -> finish());

        btnAceptar.setOnClickListener(v -> updateTeamData());
    }

    private void loadIDs() {
        String url = "http://192.168.56.1/developeru/get_idsequipos.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        idsList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String id = jsonObject.getString("Id");
                            idsList.add(id);
                        }
                        adapter = new ArrayAdapter<>(ModificarInfoEquipos.this, android.R.layout.simple_spinner_item, idsList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerID.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ModificarInfoEquipos.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(ModificarInfoEquipos.this, "Error loading IDs", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void loadUserData(String id) {
        String url = "http://192.168.56.1/developeru/get_user_dataequipos.php?Id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        String nombre = response.getString("Nombre");
                        String entrenador = response.getString("Entrenador");
                        String partidosJugados = response.getString("partidosJugados");
                        String partidosGanados = response.getString("partidosGanados");
                        String partidosPerdidos = response.getString("partidosPerdidos");
                        String partidosEmpatados = response.getString("partidosEmpatados");
                        String golesMarcados = response.getString("golesMarcados");
                        String golesEnContra = response.getString("golesEnContra");
                        String puntos = response.getString("Puntos");

                        etNombre.setText(nombre);
                        etEntrenador.setText(entrenador);
                        etPartidosJugados.setText(partidosJugados);
                        etPartidosGanados.setText(partidosGanados);
                        etPartidosPerdidos.setText(partidosPerdidos);
                        etPartidosEmpatados.setText(partidosEmpatados);
                        etGolesMarcados.setText(golesMarcados);
                        etGolesEnContra.setText(golesEnContra);
                        etPuntos.setText(puntos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ModificarInfoEquipos.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(ModificarInfoEquipos.this, "Error loading user data", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void updateTeamData() {
        String url = "http://192.168.56.1/developeru/actualizarEquipos.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ModificarInfoEquipos.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ModificarInfoEquipos.this, "Error updating data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Id", spinnerID.getSelectedItem().toString());
                params.put("Nombre", etNombre.getText().toString());
                params.put("Entrenador", etEntrenador.getText().toString());
                params.put("partidosJugados", etPartidosJugados.getText().toString());
                params.put("partidosGanados", etPartidosGanados.getText().toString());
                params.put("partidosPerdidos", etPartidosPerdidos.getText().toString());
                params.put("partidosEmpatados", etPartidosEmpatados.getText().toString());
                params.put("golesMarcados", etGolesMarcados.getText().toString());
                params.put("golesEnContra", etGolesEnContra.getText().toString());
                params.put("Puntos", etPuntos.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}