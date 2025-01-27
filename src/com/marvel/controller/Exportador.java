package com.marvel.controller;

import com.marvel.model.Heroe;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Exportador {

    public static void exportarHeroes(List<Heroe> heroes, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Heroe heroe : heroes) {
                writer.write("Nombre: " + heroe.getNombre() + "\n");
                writer.write("Alias: " + heroe.getAlias() + "\n");
                writer.write("Identificación: " + heroe.getIdentificacion() + "\n");
                writer.write("Nacionalidad: " + heroe.getActor().getNacionalidad() + "\n");
                writer.write("Edad: " + heroe.getEdad() + "\n");
                writer.write("Habilidades: \n");
                heroe.getHabilidades().forEach(habilidad -> {
                    try {
                        writer.write("  - " + habilidad.getNombre() + " (" + habilidad.getTipo() + ")\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}