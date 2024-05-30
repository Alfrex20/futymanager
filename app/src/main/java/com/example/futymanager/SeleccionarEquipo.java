package com.example.futymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * La actividad SeleccionarEquipo permite al usuario seleccionar un equipo de una lista desplegable
 */
public class SeleccionarEquipo extends AppCompatActivity {

    private Spinner spinnerID;
    private Button btnAceptar, btnRegresar;
    private RequestQueue requestQueue;
    private ArrayList<String> namesList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private static final String TAG = "SeleccionarEquipo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_equipo);

        // Inicialización de las vistas
        spinnerID = findViewById(R.id.spinnerID);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Inicialización de la cola de solicitudes
        requestQueue = Volley.newRequestQueue(this);

        // Cargar nombres en el Spinner
        loadNames();

        // Configurar listener para el botón regresar
        btnRegresar.setOnClickListener(v -> finish());

        // Configurar listener para el botón aceptar
        btnAceptar.setOnClickListener(v -> {
            int selectedPosition = spinnerID.getSelectedItemPosition();
            String selectedName = namesList.get(selectedPosition);
            Toast.makeText(this, "Bienvenido al equipo: " + selectedName, Toast.LENGTH_SHORT).show();
            sendSelectedTeamToServer(selectedName);
        });
    }

    /**
     * Método para cargar los nombres de los equipos desde el servidor y mostrarlos en el Spinner.
     */
    private void loadNames() {
        String url = "http://192.168.56.1/developeru/get_team_names.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            namesList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("Nombre");
                                namesList.add(name);
                            }
                            adapter = new ArrayAdapter<>(SeleccionarEquipo.this,
                                    android.R.layout.simple_spinner_item, namesList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerID.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SeleccionarEquipo.this, "Error parsing JSON",
                                    Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SeleccionarEquipo.this, "Error loading team names",
                                Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error loading team names: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Método para enviar el nombre del equipo seleccionado al servidor y actualizarlo para el usuario actual.
     *
     * @param teamName El nombre del equipo seleccionado.
     */
    private void sendSelectedTeamToServer(String teamName) {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String usuario = preferences.getString("Usuario", "");

        String url = "http://192.168.56.1/developeru/actualizar_equipo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(SeleccionarEquipo.this, response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(SeleccionarEquipo.this, "Error updating team: " +
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error updating team: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Usuario", usuario);
                params.put("Equipo", teamName);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
