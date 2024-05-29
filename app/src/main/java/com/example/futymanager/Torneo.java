package com.example.futymanager;

public class Torneo {
    private String nombre;
    private String entrenador;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;
    private int golesMarcados;
    private int golesEnContra;
    private int puntos;

    public Torneo(String nombre, String entrenador, int partidosJugados, int partidosGanados, int partidosPerdidos, int partidosEmpatados, int golesMarcados, int golesEnContra, int puntos) {
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.partidosJugados = partidosJugados;
        this.partidosGanados = partidosGanados;
        this.partidosPerdidos = partidosPerdidos;
        this.partidosEmpatados = partidosEmpatados;
        this.golesMarcados = golesMarcados;
        this.golesEnContra = golesEnContra;
        this.puntos = puntos;
    }

    public String getNombre() { return nombre; }
    public String getEntrenador() { return entrenador; }
    public int getPartidosJugados() { return partidosJugados; }
    public int getPartidosGanados() { return partidosGanados; }
    public int getPartidosPerdidos() { return partidosPerdidos; }
    public int getPartidosEmpatados() { return partidosEmpatados; }
    public int getGolesMarcados() { return golesMarcados; }
    public int getGolesEnContra() { return golesEnContra; }
    public int getPuntos() { return puntos; }
}
