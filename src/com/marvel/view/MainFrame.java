package com.marvel.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

        setTitle("Héroes y AntiHéroes de Marvel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        // Botones de acción
        JButton addHeroeButton = new JButton("Agregar Héroe");
        addHeroeButton.addActionListener(e -> agregarHeroe());
        buttonPanel.add(addHeroeButton);

        JButton addAntihéroeButton = new JButton("Agregar Antihéroe");
        addAntihéroeButton.addActionListener(e -> agregarAntihéroe());
        buttonPanel.add(addAntihéroeButton);

        JButton addActorButton = new JButton("Agregar Actor");
        addActorButton.addActionListener(e -> agregarActor());
        buttonPanel.add(addActorButton);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> editarPersonaje());
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> eliminarPersonaje());
        buttonPanel.add(deleteButton);

        JButton exportButton = new JButton("Exportar a TXT");
        exportButton.addActionListener(e -> exportarATxt());
        buttonPanel.add(exportButton);

        // Campos de entrada para Héroes y Antihéroes
        inputPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);

        inputPanel.add(new JLabel("Alias:"));
        aliasField = new JTextField();
        inputPanel.add(aliasField);

        inputPanel.add(new JLabel("Identificación:"));
        identificacionField = new JTextField();
        inputPanel.add(identificacionField);

        inputPanel.add(new JLabel("Nacionalidad:"));
        nacionalidadField = new JTextField();
        inputPanel.add(nacionalidadField);

        inputPanel.add(new JLabel("Edad:"));
        edadField = new JTextField();
        inputPanel.add(edadField);

        inputPanel.add(new JLabel("Habilidad:"));
        habilidadField = new JTextField();
        inputPanel.add(habilidadField);

        inputPanel.add(new JLabel("Categoría de Habilidad:"));
        categoriaHabilidadCombo = new JComboBox<>(new String[]{"Común", "Especial", "Épico", "Legendaria"});
        inputPanel.add(categoriaHabilidadCombo);

        inputPanel.add(new JLabel("Actor:"));
        actorCombo = new JComboBox<>();
        inputPanel.add(actorCombo);

        // Añadir paneles al marco principal
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Nombre", "Alias"}, 0);
        personajesTable = new JTable(tableModel);

        add(mainPanel, BorderLayout.NORTH);
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String tipo = (String) tableModel.getValueAt(i, 0);
                    String nombre = (String) tableModel.getValueAt(i, 1);
                    String alias = (String) tableModel.getValueAt(i, 2);
                    writer.write(tipo + ": " + nombre + " (" + alias + ")");
                    writer.newLine();

                    if (tipo.equals("Héroe")) {
                        for (Heroe heroe : heroes) {
                            if (heroe.getNombre().equals(nombre)) {
                                writer.write("  Identificación: " + heroe.getIdentificacion());
                                writer.newLine();
                                writer.write("  Nacionalidad: " + heroe.getActor().getNacionalidad());
                                writer.newLine();
                                writer.write("  Edad: " + heroe.getEdad());
                                writer.newLine();
                                writer.write("  Habilidades: ");
                                for (Habilidad habilidad : heroe.getHabilidades()) {
                                    writer.write(habilidad.getNombre() + ", ");
                                }
                                writer.newLine();
                                break;
                            }
                        }
                    } else if (tipo.equals("Antihéroe")) {
                        for (Antihéroe antihéroe : antihéroes) {
                            if (antihéroe.getNombre().equals(nombre)) {
                                writer.write("  Identificación: " + antihéroe.getIdentificacion());
                                writer.newLine();
                                writer.write("  Nacionalidad: " + antihéroe.getActor().getNacionalidad());
                                writer.newLine();
                                writer.write("  Edad: " + antihéroe.getEdad());
                                writer.newLine();
                                writer.write("  Habilidades: ");
                                for (Habilidad habilidad : antihéroe.getHabilidades()) {
                                    writer.write(habilidad.getNombre() + ", ");
                                }
                                writer.newLine();
                                break;
                            }
                        }
                    } else if (tipo.equals("Actor")) {
                        for (Actor actor : actores) {
                            if (actor.getNombre().equals(nombre)) {
                                writer.write("  Identificación: " + actor.getIdentificacion());
                                writer.newLine();
                                writer.write("  Nacionalidad: " + actor.getNacionalidad());
                                writer.newLine();
                                writer.write("  Edad: " + actor.getEdad());
                                writer.newLine();
                                break;
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Datos exportados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al exportar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
            int maxHabilidades = getMaxHabilidades(categoriaHabilidad);
            if (heroe.getHabilidades().size() < maxHabilidades) {
                heroe.agregarHabilidad(new Habilidad(habilidadNombre + " (" + categoriaHabilidad + ")", ""));
                heroes.add(heroe);
                tableModel.addRow(new Object[]{"Héroe", heroe.getNombre(), heroe.getAlias()});
            } else {
                JOptionPane.showMessageDialog(this, "Este héroe ya tiene el número máximo de habilidades.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
            int maxHabilidades = getMaxHabilidades(categoriaHabilidad);
            if (antihéroe.getHabilidades().size() < maxHabilidades) {
                antihéroe.agregarHabilidad(new Habilidad(habilidadNombre + " (" + categoriaHabilidad + ")", ""));
                antihéroes.add(antihéroe);
                tableModel.addRow(new Object[]{"Antihéroe", antihéroe.getNombre(), antihéroe.getAlias()});
            } else {
                JOptionPane.showMessageDialog(this, "Este antihéroe ya tiene el número máximo de habilidades.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getMaxHabilidades(String categoria) {
        switch (categoria) {
            case "Común":
                return 2;
            case "Especial":
                return 3;
            case "Épico":
                return 4;
            case "Legendaria":
                return 5;
            default:
                return 1;
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