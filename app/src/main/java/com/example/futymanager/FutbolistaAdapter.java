package com.example.futymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FutbolistaAdapter extends ArrayAdapter<Futbolistas> {

    public FutbolistaAdapter(Context context, ArrayList<Futbolistas> futbolistas) {
        super(context, 0, futbolistas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Futbolistas futbolista = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_futbolista, parent, false);
        }

        TextView tvNombreApellidos = convertView.findViewById(R.id.tvNombreApellidos);
        TextView tvDetalles = convertView.findViewById(R.id.tvDetalles);
        TextView tvEquipo = convertView.findViewById(R.id.tvEquipo); // Nuevo TextView para el equipo

        tvNombreApellidos.setText(futbolista.getNombre() + " " + futbolista.getApellidos());
        tvDetalles.setText("Edad: " + futbolista.getEdad() + " | Posición: " + futbolista.getPosicion() + " | Dorsal: " + futbolista.getDorsal());
        tvEquipo.setText("Equipo: " + futbolista.getEquipo()); // Mostrar el equipo

        return convertView;
    }
}
