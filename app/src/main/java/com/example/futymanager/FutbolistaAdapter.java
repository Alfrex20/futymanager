package com.example.futymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter personalizado para mostrar una lista de objetos Futbolistas.
 */
public class FutbolistaAdapter extends ArrayAdapter<Futbolistas> {

    /**
     * Constructor de la clase FutbolistaAdapter.
     *
     * @param context El contexto de la aplicación.
     * @param futbolistas La lista de objetos Futbolistas que se va a mostrar.
     */
    public FutbolistaAdapter(Context context, ArrayList<Futbolistas> futbolistas) {
        super(context, 0, futbolistas);
    }

    /**
     * Método que proporciona una vista para un elemento de la lista.
     *
     * @param position La posición del elemento en la lista de datos.
     * @param convertView La vista reutilizable.
     * @param parent El grupo de vistas al que pertenece esta vista.
     * @return La vista para el elemento en la posición especificada.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el objeto Futbolistas de la posición actual
        Futbolistas futbolista = getItem(position);

        // Inflar una nueva vista si no se está reutilizando una existente
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_futbolista, parent,
                    false);
        }

        // Obtener referencias a los TextView en el layout
        TextView tvNombreApellidos = convertView.findViewById(R.id.tvNombreApellidos);
        TextView tvDetalles = convertView.findViewById(R.id.tvDetalles);
        TextView tvEquipo = convertView.findViewById(R.id.tvEquipo); // Nuevo TextView para el equipo

        // Configurar los TextView con los datos del futbolista
        tvNombreApellidos.setText(futbolista.getNombre() + " " + futbolista.getApellidos());
        tvDetalles.setText("Edad: " + futbolista.getEdad() + " | Posición: " + futbolista.getPosicion() + " | Dorsal: " +
                futbolista.getDorsal());
        tvEquipo.setText("Equipo: " + futbolista.getEquipo()); // Mostrar el equipo

        // Devolver la vista configurada para el elemento en la posición actual
        return convertView;
    }
}
