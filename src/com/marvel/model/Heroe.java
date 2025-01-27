package com.marvel.model;

import java.util.ArrayList;
import java.util.List;

public class Heroe extends Persona {
    private String alias;
    private Actor actor;
    private List<Habilidad> habilidades;
    private int edad;

    public Heroe(String nombre, String identificacion, String alias, Actor actor, int edad) {
        super(nombre, identificacion);
        this.alias = alias;
        this.actor = actor;
        this.edad = edad;
        this.habilidades = new ArrayList<>();
    }

    public void agregarHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }

    public String getAlias() {
        return alias;
    }

    public Actor getActor() {
        return actor;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    public int getEdad() {
        return edad;
    }
}