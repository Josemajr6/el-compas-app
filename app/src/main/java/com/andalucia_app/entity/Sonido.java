package com.andalucia_app.entity;


public class Sonido {

    private final String titulo;
    private final String artista;
    private final String duracion;
    private final String categoria;
    private final int    portadaResId;
    private final int    audioResId;

    public Sonido(String titulo, String artista, String duracion,
                  String categoria, int portadaResId, int audioResId) {
        this.titulo       = titulo;
        this.artista      = artista;
        this.duracion     = duracion;
        this.categoria    = categoria;
        this.portadaResId = portadaResId;
        this.audioResId   = audioResId;
    }

    public String getTitulo()       { return titulo; }
    public String getArtista()      { return artista; }
    public String getDuracion()     { return duracion; }
    public String getCategoria()    { return categoria; }
    public int    getPortadaResId() { return portadaResId; }
    public int    getAudioResId()   { return audioResId; }
}