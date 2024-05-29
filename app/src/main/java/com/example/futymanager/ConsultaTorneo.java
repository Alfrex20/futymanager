package com.example.futymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.Comparator;

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
import java.util.List;

public class ConsultaTorneo extends AppCompatActivity {

    private static final String TAG = "ConsultaTorneo";
    private ListView listView;
    private List<Torneo> equiposList;
    private TorneoAdapter adapter;
    private Button btnRegresar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_torneo);

        listView = findViewById(R.id.listViewEquipos);
        equiposList = new ArrayList<>();
        adapter = new TorneoAdapter(this, equiposList);
        listView.setAdapter(adapter);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultaTorneo.this, Principal.class);
                startActivity(intent);
            }
        });

        obtenerDatos();
    }

    private void obtenerDatos() {
        String url = "http://192.168.56.1/developeru/consultaEquipos.php";
        Log.d(TAG, "URL: " + url); // Log URL

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Response received"); // Log response
                        equiposList.clear(); // Limpia la lista antes de agregar nuevos datos
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject equipo = response.getJSONObject(i);

                                String nombre = equipo.getString("Nombre");
                                String entrenador = equipo.getString("Entrenador");
                                int partidosJugados = equipo.getInt("partidosJugados");
                                int partidosGanados = equipo.getInt("partidosGanados");
                                int partidosPerdidos = equipo.getInt("partidosPerdidos");
                                int partidosEmpatados = equipo.getInt("partidosEmpatados");
                                int golesMarcados = equipo.getInt("golesMarcados");
                                int golesEnContra = equipo.getInt("golesEnContra");
                                int puntos = equipo.getInt("Puntos");

                                Log.d(TAG, "Equipo: " + nombre + ", Entrenador: " + entrenador + ", PJ: " + partidosJugados + ", PG: " + partidosGanados + ", PP: " + partidosPerdidos + ", PE: " + partidosEmpatados + ", GM: " + golesMarcados + ", GE: " + golesEnContra + ", Puntos: " + puntos); // Log cada equipo

                                equiposList.add(new Torneo(nombre, entrenador, partidosJugados, partidosGanados, partidosPerdidos, partidosEmpatados, golesMarcados, golesEnContra, puntos));
                            }

                            Collections.sort(equiposList, new Comparator<Torneo>() {
                                @Override
                                public int compare(Torneo equipo1, Torneo equipo2) {
                                    // Ordenar de mayor a menor número de puntos
                                    return Integer.compare(equipo2.getPuntos(), equipo1.getPuntos());
                                }
                            });


                            adapter.notifyDataSetChanged(); // Notificar al adaptador sobre el cambio
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "JSON Exception: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "Error: " + error.toString()); // Log error
            }
        });

        queue.add(jsonArrayRequest);
    }
}
