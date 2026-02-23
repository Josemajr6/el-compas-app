package com.andalucia_app.entity;

public class Personaje {

    private final String nombre;
    private final String epoca;
    private final String descripcion;
    private final String categoria;
    private final int    imagenResId;

    public Personaje(String nombre, String epoca, String descripcion,
                     String categoria, int imagenResId) {
        this.nombre      = nombre;
        this.epoca       = epoca;
        this.descripcion = descripcion;
        this.categoria   = categoria;
        this.imagenResId = imagenResId;
    }

    public String getNombre()      { return nombre; }
    public String getEpoca()       { return epoca; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria()   { return categoria; }
    public int    getImagenResId() { return imagenResId; }
}