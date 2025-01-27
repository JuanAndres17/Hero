package com.marvel.model;

public class Actor extends Persona {
    private String nacionalidad;
    private int edad;

    public Actor(String nombre, String identificacion, String nacionalidad, int edad) {
        super(nombre, identificacion);
        this.nacionalidad = nacionalidad;
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public int getEdad() {
        return edad;
    }
}