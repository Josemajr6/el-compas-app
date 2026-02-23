package com.andalucia_app.entity;

public class Personaje {

    private final String  nombre;
    private final String  epoca;
    private final String  descripcion;
    private final String  categoria;
    private final int     imagenResId;
    /**
     * true  → CENTER_CROP: la foto rellena toda la cuadrícula (puede recortar bordes)
     * false → FIT_CENTER:  se ve la foto entera sin recorte
     */
    private final boolean rellenarImagen;

    public Personaje(String nombre, String epoca, String descripcion,
                     String categoria, int imagenResId, boolean rellenarImagen) {
        this.nombre         = nombre;
        this.epoca          = epoca;
        this.descripcion    = descripcion;
        this.categoria      = categoria;
        this.imagenResId    = imagenResId;
        this.rellenarImagen = rellenarImagen;
    }

    public String  getNombre()         { return nombre; }
    public String  getEpoca()          { return epoca; }
    public String  getDescripcion()    { return descripcion; }
    public String  getCategoria()      { return categoria; }
    public int     getImagenResId()    { return imagenResId; }
    public boolean isRellenarImagen()  { return rellenarImagen; }
}