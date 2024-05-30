package com.example.futymanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

/**
 * La actividad EliminarJugador permite cargar, mostrar y eliminar jugadores por su id
 */
public class EliminarJugador extends AppCompatActivity {

    private Spinner spinnerID;
    private EditText etUsuario, etContrasena, etNombre, etApellidos, etEdad, etPosicion, etDorsal, etLesiones;
    private Button btnAceptar, btnRegresar;
    private RequestQueue requestQueue;
    private ArrayList<String> idsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_jugador);

        // Inicialización de las vistas
        spinnerID = findViewById(R.id.spinnerID);
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        etPosicion = findViewById(R.id.etPosicion);
        etDorsal = findViewById(R.id.etDorsal);
        etLesiones = findViewById(R.id.etLesiones);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Inicialización de la cola de solicitudes
        requestQueue = Volley.newRequestQueue(this);

        // Cargar IDs en el Spinner
        loadIDs();

        // Configurar listener para el Spinner
        spinnerID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedID = idsList.get(position);
                loadUserData(selectedID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Configurar listener para el botón regresar
        btnRegresar.setOnClickListener(v -> finish());

        // Configurar listener para el botón aceptar
        btnAceptar.setOnClickListener(v -> deleteUser());
    }

    /**
     * Método para cargar los IDs de los jugadores desde el servidor y mostrarlos en el Spinner.
     */
    private void loadIDs() {
        String url = "http://192.168.56.1/developeru/get_idsfutbolistas.php";
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
                            adapter = new ArrayAdapter<>(EliminarJugador.this, android.R.layout.simple_spinner_item,
                                    idsList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerID.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EliminarJugador.this, "Error parsing JSON",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("ERROR", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(EliminarJugador.this, "Error loading IDs", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", "Error loading IDs: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Método para cargar los datos del jugador seleccionado desde el servidor y mostrarlos en los campos de texto.
     */
    private void loadUserData(String id) {
        String url = "http://192.168.56.1/developeru/get_user_datafutbolistas.php?ID=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String usuario = response.getString("Usuario");
                            String contrasena = response.getString("Contrasena");
                            String nombre = response.getString("Nombre");
                            String apellidos = response.getString("Apellidos");
                            String edad = response.getString("Edad");
                            String posicion = response.getString("Posicion");
                            String dorsal = response.getString("Dorsal");
                            String lesiones = response.getString("Lesiones");

                            etUsuario.setText(usuario);
                            etContrasena.setText(contrasena);
                            etNombre.setText(nombre);
                            etApellidos.setText(apellidos);
                            etEdad.setText(edad);
                            etPosicion.setText(posicion);
                            etDorsal.setText(dorsal);
                            etLesiones.setText(lesiones);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EliminarJugador.this, "Error parsing JSON",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("ERROR", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(EliminarJugador.this, "Error loading user data",
                                Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", "Error loading user data: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Método para eliminar el jugador seleccionado de la base de datos del servidor.
     */
    private void deleteUser() {
        String url = "http://192.168.56.1/developeru/EliminarJugador.php";

        // Crear una solicitud de cadena de texto (StringRequest)
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta del servidor (cadena de texto)
                        Toast.makeText(EliminarJugador.this, response, Toast.LENGTH_SHORT).show();
                        // Opcional: actualizar la interfaz después de eliminar el usuario
                        loadIDs();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        error.printStackTrace();
                        Toast.makeText(EliminarJugador.this, "Error deleting user: " +
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Parámetros de la solicitud POST (datos a enviar al servidor)
                Map<String, String> params = new HashMap<>();
                params.put("ID", idsList.get(spinnerID.getSelectedItemPosition()));
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes (RequestQueue)
        requestQueue.add(stringRequest);
    }
}
