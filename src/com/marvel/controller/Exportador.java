package com.marvel.controller;

import com.marvel.model.Habilidad;
import com.marvel.model.Heroe;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Exportador {

    public static void exportarHeroes(List<Heroe> heroes) {
        // Construct the path to the desktop
        String desktopPath = Paths.get(System.getProperty("user.home"), "Desktop", "heroes.txt").toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(desktopPath))) {
            for (Heroe heroe : heroes) {
                writer.write("Nombre: " + heroe.getNombre() + "\n");
                writer.write("Alias: " + heroe.getAlias() + "\n");
                writer.write("Identificación: " + heroe.getIdentificacion() + "\n");
                writer.write("Nacionalidad: " + heroe.getActor().getNacionalidad() + "\n");
                writer.write("Edad: " + heroe.getEdad() + "\n");
                writer.write("Habilidades: \n");
                for (Habilidad habilidad : heroe.getHabilidades()) {
                    writer.write("  - " + habilidad.getNombre() + " (" + habilidad.getTipo() + ")\n");
                }
                writer.write("\n");
            }
            JOptionPane.showMessageDialog(null, "Héroes exportados exitosamente a " + desktopPath, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al exportar héroes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}