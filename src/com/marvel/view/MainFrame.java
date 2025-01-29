package com.marvel.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.marvel.model.Actor;
import com.marvel.model.Heroe;
import com.marvel.model.Antihéroe;
import com.marvel.model.Habilidad;

public class MainFrame extends JFrame {
    private List<Heroe> heroes;
    private List<Antihéroe> antihéroes;
    private List<Actor> actores;

    private JTextField nombreField;
    private JTextField aliasField;
    private JTextField identificacionField;
    private JTextField nacionalidadField;
    private JTextField edadField;
    private JTextField habilidadField;
    private JComboBox<String> categoriaHabilidadCombo;
    private JComboBox<Actor> actorCombo;
    private JTable personajesTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        heroes = new ArrayList<>();
        antihéroes = new ArrayList<>();
        actores = new ArrayList<>();

        setTitle("Marvel Character Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        nombreField = new JTextField();
        aliasField = new JTextField();
        identificacionField = new JTextField();
        nacionalidadField = new JTextField();
        edadField = new JTextField();
        habilidadField = new JTextField();
        categoriaHabilidadCombo = new JComboBox<>(new String[]{"Común", "Especial", "Épico", "Legendaria"});
        actorCombo = new JComboBox<>();

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Alias:"));
        panel.add(aliasField);
        panel.add(new JLabel("Identificación:"));
        panel.add(identificacionField);
        panel.add(new JLabel("Nacionalidad:"));
        panel.add(nacionalidadField);
        panel.add(new JLabel("Edad:"));
        panel.add(edadField);
        panel.add(new JLabel("Habilidad:"));
        panel.add(habilidadField);
        panel.add(new JLabel("Categoría de Habilidad:"));
        panel.add(categoriaHabilidadCombo);
        panel.add(new JLabel("Actor:"));
        panel.add(actorCombo);

        JButton addHeroeButton = new JButton("Agregar Héroe");
        addHeroeButton.addActionListener(e -> agregarHeroe());
        panel.add(addHeroeButton);

        JButton addAntihéroeButton = new JButton("Agregar Antihéroe");
        addAntihéroeButton.addActionListener(e -> agregarAntihéroe());
        panel.add(addAntihéroeButton);

        JButton addActorButton = new JButton("Agregar Actor");
        addActorButton.addActionListener(e -> agregarActor());
        panel.add(addActorButton);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> editarPersonaje());
        panel.add(editButton);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> eliminarPersonaje());
        panel.add(deleteButton);

        JButton exportButton = new JButton("Exportar a TXT");
        exportButton.addActionListener(e -> exportarATxt());
        panel.add(exportButton);

        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Nombre", "Alias"}, 0);
        personajesTable = new JTable(tableModel);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(personajesTable), BorderLayout.CENTER);
    }

    private void agregarActor() {
        try {
            String nombre = nombreField.getText().trim();
            String identificacion = identificacionField.getText().trim();
            String nacionalidad = nacionalidadField.getText().trim();
            int edad = Integer.parseInt(edadField.getText().trim());

            if (nombre.isEmpty() || identificacion.isEmpty() || nacionalidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Actor actor = new Actor(nombre, identificacion, nacionalidad, edad);
            actores.add(actor);
            actorCombo.addItem(actor);
            tableModel.addRow(new Object[]{"Actor", actor.getNombre(), ""});
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarATxt() {
        String desktopPath = Paths.get(System.getProperty("user.home"), "Desktop", "personajes.txt").toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(desktopPath))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String tipo = (String) tableModel.getValueAt(i, 0);
                String nombre = (String) tableModel.getValueAt(i, 1);
                String alias = (String) tableModel.getValueAt(i, 2);
                writer.write(tipo + ": " + nombre + " (" + alias + ")");
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Datos exportados exitosamente a " + desktopPath, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al exportar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarHeroe() {
        try {
            String nombre = nombreField.getText().trim();
            String alias = aliasField.getText().trim();
            String identificacion = identificacionField.getText().trim();
            String nacionalidad = nacionalidadField.getText().trim();
            int edad = Integer.parseInt(edadField.getText().trim());
            String habilidadNombre = habilidadField.getText().trim();
            String categoriaHabilidad = (String) categoriaHabilidadCombo.getSelectedItem();
            Actor actor = (Actor) actorCombo.getSelectedItem();

            if (nombre.isEmpty() || alias.isEmpty() || identificacion.isEmpty() || nacionalidad.isEmpty() || habilidadNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Heroe heroe = new Heroe(nombre, identificacion, alias, actor, edad);
            heroe.agregarHabilidad(new Habilidad(habilidadNombre + " (" + categoriaHabilidad + ")", ""));
            heroes.add(heroe);
            tableModel.addRow(new Object[]{"Héroe", heroe.getNombre(), heroe.getAlias()});
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarAntihéroe() {
        try {
            String nombre = nombreField.getText().trim();
            String alias = aliasField.getText().trim();
            String identificacion = identificacionField.getText().trim();
            String nacionalidad = nacionalidadField.getText().trim();
            int edad = Integer.parseInt(edadField.getText().trim());
            String habilidadNombre = habilidadField.getText().trim();
            String categoriaHabilidad = (String) categoriaHabilidadCombo.getSelectedItem();
            Actor actor = (Actor) actorCombo.getSelectedItem();

            if (nombre.isEmpty() || alias.isEmpty() || identificacion.isEmpty() || nacionalidad.isEmpty() || habilidadNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Antihéroe antihéroe = new Antihéroe(nombre, identificacion, alias, actor, edad);
            antihéroe.agregarHabilidad(new Habilidad(habilidadNombre + " (" + categoriaHabilidad + ")", ""));
            antihéroes.add(antihéroe);
            tableModel.addRow(new Object[]{"Antihéroe", antihéroe.getNombre(), antihéroe.getAlias()});
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPersonaje() {
        int selectedIndex = personajesTable.getSelectedRow();
        if (selectedIndex != -1) {
            String tipo = (String) tableModel.getValueAt(selectedIndex, 0);
            String nombre = (String) tableModel.getValueAt(selectedIndex, 1);

            if (tipo.equals("Héroe")) {
                for (Heroe heroe : heroes) {
                    if (heroe.getNombre().equals(nombre)) {
                        nombreField.setText(heroe.getNombre());
                        aliasField.setText(heroe.getAlias());
                        identificacionField.setText(heroe.getIdentificacion());
                        nacionalidadField.setText(heroe.getActor() != null ? heroe.getActor().getNacionalidad() : "");
                        edadField.setText(String.valueOf(heroe.getEdad()));
                        habilidadField.setText(heroe.getHabilidades().get(0).getNombre());
                        actorCombo.setSelectedItem(heroe.getActor());
                        break;
                    }
                }
            } else if (tipo.equals("Antihéroe")) {
                for (Antihéroe antihéroe : antihéroes) {
                    if (antihéroe.getNombre().equals(nombre)) {
                        nombreField.setText(antihéroe.getNombre());
                        aliasField.setText(antihéroe.getAlias());
                        identificacionField.setText(antihéroe.getIdentificacion());
                        nacionalidadField.setText(antihéroe.getActor() != null ? antihéroe.getActor().getNacionalidad() : "");
                        edadField.setText(String.valueOf(antihéroe.getEdad()));
                        habilidadField.setText(antihéroe.getHabilidades().get(0).getNombre());
                        actorCombo.setSelectedItem(antihéroe.getActor());
                        break;
                    }
                }
            } else if (tipo.equals("Actor")) {
                for (Actor actor : actores) {
                    if (actor.getNombre().equals(nombre)) {
                        nombreField.setText(actor.getNombre());
                        identificacionField.setText(actor.getIdentificacion());
                        nacionalidadField.setText(actor.getNacionalidad());
                        edadField.setText(String.valueOf(actor.getEdad()));
                        break;
                    }
                }
            }
        }
    }

    private void eliminarPersonaje() {
        int selectedIndex = personajesTable.getSelectedRow();
        if (selectedIndex != -1) {
            String tipo = (String) tableModel.getValueAt(selectedIndex, 0);
            String nombre = (String) tableModel.getValueAt(selectedIndex, 1);

            if (tipo.equals("Héroe")) {
                heroes.removeIf(heroe -> heroe.getNombre().equals(nombre));
            } else if (tipo.equals("Antihéroe")) {
                antihéroes.removeIf(antihéroe -> antihéroe.getNombre().equals(nombre));
            } else if (tipo.equals("Actor")) {
                actores.removeIf(actor -> actor.getNombre().equals(nombre));
            }

            tableModel.removeRow(selectedIndex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}