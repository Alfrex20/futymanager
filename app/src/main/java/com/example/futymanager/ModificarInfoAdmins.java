package com.example.futymanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

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

public class ModificarInfoAdmins extends AppCompatActivity {

    Spinner spinnerID;
    EditText etUsuario, etcontrasena, etdni;
    Button btnAceptar, btnRegresar;
    RequestQueue requestQueue;
    ArrayList<String> idsList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_info_admins);

        spinnerID = findViewById(R.id.spinnerID);
        etUsuario = findViewById(R.id.etUsuario);
        etcontrasena = findViewById(R.id.etcontrasena);
        etdni = findViewById(R.id.etdni);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        requestQueue = Volley.newRequestQueue(this);

        // Load IDs into Spinner
        loadIDs();

        // Set item selected listener on spinner
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

        btnRegresar.setOnClickListener(v -> {
            // Handle back button click
            finish();
        });

        btnAceptar.setOnClickListener(v -> {
            // Handle accept button click
            updateUser();
        });
    }

    private void loadIDs() {
        String url = "http://192.168.56.1/developeru/get_ids.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            idsList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("ID");
                                idsList.add(id);
                            }
                            adapter = new ArrayAdapter<>(ModificarInfoAdmins.this, android.R.layout.simple_spinner_item, idsList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerID.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ModificarInfoAdmins.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ModificarInfoAdmins.this, "Error loading IDs", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    private void loadUserData(String id) {
        String url = "http://192.168.56.1/developeru/get_user_data.php?ID=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String usuario = response.getString("Usuario");
                            String contrasena = response.getString("Contrasena");
                            String dni = response.getString("Dni");

                            etUsuario.setText(usuario);
                            etcontrasena.setText(contrasena);
                            etdni.setText(dni);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ModificarInfoAdmins.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ModificarInfoAdmins.this, "Error loading user data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void updateUser() {
        String url = "http://192.168.56.1/developeru/actualizarAdmin.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ModificarInfoAdmins.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ModificarInfoAdmins.this, "Error updating data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ID", spinnerID.getSelectedItem().toString());
                params.put("Usuario", etUsuario.getText().toString());
                params.put("Contrasena", etcontrasena.getText().toString());
                params.put("Dni", etdni.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
