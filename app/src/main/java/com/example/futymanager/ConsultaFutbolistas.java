package com.example.futymanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * La actividad ConsultaFutbolistas muestra la lista de todos los futbolistas existentes en la BD
 */
public class ConsultaFutbolistas extends AppCompatActivity {

    private static final String TAG = "ConsultaFutbolistas"; // Para loguear información
    private ListView listView;
    private ArrayList<Futbolistas> futbolistasList;
    private FutbolistaAdapter adapter;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_futbolistas);

        // Configurar EdgeToEdge para manejar los bordes de la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar ListView y su adaptador
        listView = findViewById(R.id.listView);
        futbolistasList = new ArrayList<>();
        adapter = new FutbolistaAdapter(this, futbolistasList);
        listView.setAdapter(adapter);

        // Inicializar el botón Regresar y su listener
        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(ConsultaFutbolistas.this, SeleccionarConsulta.class);
            startActivity(intent);
        });

        // Obtener datos desde el servidor
        obtenerDatos();
    }

    /**
     * Método para obtener los datos de los futbolistas desde el servidor.
     */
    private void obtenerDatos() {
        String url = "http://192.168.56.1/developeru/consultaFutbolistas.php"; // URL del servicio web
        Log.d(TAG, "URL: " + url); // Loguear la URL

        RequestQueue queue = Volley.newRequestQueue(this);

        // Crear una solicitud para obtener un array de JSON
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Response received"); // Loguear recepción de respuesta
                        futbolistasList.clear(); // Limpiar la lista antes de agregar nuevos datos
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject futbolistaJson = response.getJSONObject(i);

                                String nombre = futbolistaJson.getString("Nombre");
                                String apellidos = futbolistaJson.getString("Apellidos");
                                String posicion = futbolistaJson.getString("Posicion");
                                int edad = futbolistaJson.getInt("Edad");
                                int dorsal = futbolistaJson.getInt("Dorsal");
                                String equipo = futbolistaJson.getString("Equipo"); // Obtener el nombre del equipo

                                // Loguear cada futbolista
                                Log.d(TAG, "Futbolista: " + nombre + " " + apellidos + ", " + posicion + ", " + edad + ", " +
                                        "" + dorsal + ", " + equipo);

                                // Crear un objeto Futbolistas y agregarlo a la lista
                                Futbolistas futbolista = new Futbolistas(nombre, apellidos, edad, posicion, dorsal, equipo);
                                futbolistasList.add(futbolista);
                            }
                            adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "JSON Exception: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "Error: " + error.toString()); // Loguear error
            }
        });

        // Agregar la solicitud a la cola de solicitudes
        queue.add(jsonArrayRequest);
    }
}
