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

public class SeleccionarEquipo extends AppCompatActivity {

    Spinner spinnerID;
    Button btnAceptar, btnRegresar;
    RequestQueue requestQueue;
    ArrayList<String> namesList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static final String TAG = "SeleccionarEquipo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_equipo);

        spinnerID = findViewById(R.id.spinnerID);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        requestQueue = Volley.newRequestQueue(this);

        // Load Names into Spinner
        loadNames();

        btnRegresar.setOnClickListener(v -> finish());

        btnAceptar.setOnClickListener(v -> {
            int selectedPosition = spinnerID.getSelectedItemPosition();
            String selectedName = namesList.get(selectedPosition);
            Toast.makeText(this, "Bienvenido al equipo: " + selectedName, Toast.LENGTH_SHORT).show();
            sendSelectedTeamToServer(selectedName);
        });
    }

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
                            adapter = new ArrayAdapter<>(SeleccionarEquipo.this, android.R.layout.simple_spinner_item, namesList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerID.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SeleccionarEquipo.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SeleccionarEquipo.this, "Error loading team names", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error loading team names: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void sendSelectedTeamToServer(String teamName) {
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String usuario = preferences.getString("Usuario", "");

        String url = "http://192.168.56.1/developeru/actualizar_equipo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(SeleccionarEquipo.this, response, Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(SeleccionarEquipo.this, "Error updating team: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
