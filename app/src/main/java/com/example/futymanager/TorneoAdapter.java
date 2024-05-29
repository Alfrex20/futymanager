package com.example.futymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.futymanager.Torneo;
import java.util.List;

public class TorneoAdapter extends ArrayAdapter<Torneo> {

    public TorneoAdapter(Context context, List<Torneo> torneo) {
        super(context, 0, torneo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Torneo torneo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_torneo, parent, false);
        }

        TextView numeroTextView = convertView.findViewById(R.id.numero);
        TextView nombreTextView = convertView.findViewById(R.id.nombre);
        TextView puntosTextView = convertView.findViewById(R.id.puntos);
        TextView partidosTextView = convertView.findViewById(R.id.partidos);

        numeroTextView.setText(String.valueOf(position + 1));

        nombreTextView.setText(torneo.getNombre());
        puntosTextView.setText("Puntos: " + torneo.getPuntos());
        partidosTextView.setText("PJ: " + torneo.getPartidosJugados() + ", PG: " + torneo.getPartidosGanados() +
                ", PP: " + torneo.getPartidosPerdidos() + ", PE: " + torneo.getPartidosEmpatados());

        return convertView;
    }
}
