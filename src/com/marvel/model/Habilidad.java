package com.marvel.model;

public class Habilidad {
    private String nombre;
    private String tipo;

    public Habilidad(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}