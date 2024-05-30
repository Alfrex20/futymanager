package com.example.futymanager;

/**
 * La clase Futbolistas obtiene los detalles del futbolista directamente de la base de datos
 */
public class Futbolistas {
    // Atributos privados de la clase
    private String nombre;
    private String apellidos;
    private int edad;
    private String posicion;
    private int dorsal;
    private String equipo; // Nuevo campo para almacenar el equipo del jugador

    /**
     * Constructor de la clase Futbolistas.
     *
     * @param nombre El nombre del futbolista.
     * @param apellidos Los apellidos del futbolista.
     * @param edad La edad del futbolista.
     * @param posicion La posición en la que juega el futbolista.
     * @param dorsal El número de dorsal del futbolista.
     * @param equipo El equipo al que pertenece el futbolista.
     */
    public Futbolistas(String nombre, String apellidos, int edad, String posicion, int dorsal, String equipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.equipo = equipo; // Inicialización del nuevo campo
    }

    /**
     * Obtiene el nombre del futbolista.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene los apellidos del futbolista.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Obtiene la edad del futbolista.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Obtiene la posición en la que juega el futbolista.
     */
    public String getPosicion() {
        return posicion;
    }

    /**
     * Obtiene el número de dorsal del futbolista.
     */
    public int getDorsal() {
        return dorsal;
    }

    /**
     * Obtiene el equipo al que pertenece el futbolista.
     */
    public String getEquipo() {
        return equipo;
    }
}
