package com.example.futymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EquipoAdapter extends ArrayAdapter<Equipo> {

    public EquipoAdapter(Context context, List<Equipo> equipos) {
        super(context, 0, equipos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtén el equipo actual
        Equipo equipo = getItem(position);

        // Verifica si se está reutilizando una vista existente, de lo contrario infla la vista
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_equipo, parent, false);
        }

        // Referencias de vistas
        TextView nombreTextView = convertView.findViewById(R.id.nombre);
        TextView entrenadorTextView = convertView.findViewById(R.id.entrenador);
        TextView partidosTextView = convertView.findViewById(R.id.partidos);
        TextView golesTextView = convertView.findViewById(R.id.goles);
        TextView puntosTextView = convertView.findViewById(R.id.puntos);

        // Rellena los datos
        nombreTextView.setText(equipo.getNombre());
        entrenadorTextView.setText("Entrenador: " + equipo.getEntrenador());
        partidosTextView.setText("PJ: " + equipo.getPartidosJugados() + ", PG: " + equipo.getPartidosGanados() + ", PP: " + equipo.getPartidosPerdidos() + ", PE: " + equipo.getPartidosEmpatados());
        golesTextView.setText("Goles Marcados: " + equipo.getGolesMarcados() + ", Goles en Contra: " + equipo.getGolesEnContra());
        puntosTextView.setText("Puntos: " + equipo.getPuntos());

        return convertView;
    }
}
