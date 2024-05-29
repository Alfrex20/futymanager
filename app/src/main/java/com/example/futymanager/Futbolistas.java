package com.example.futymanager;

public class Futbolistas {
    private String nombre;
    private String apellidos;
    private int edad;
    private String posicion;
    private int dorsal;
    private String equipo; // Nuevo campo

    public Futbolistas(String nombre, String apellidos, int edad, String posicion, int dorsal, String equipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.equipo = equipo; // Inicialización del nuevo campo
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getDorsal() {
        return dorsal;
    }

    public String getEquipo() {
        return equipo;
    }
}
