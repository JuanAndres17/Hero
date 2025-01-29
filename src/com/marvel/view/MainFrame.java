package com.marvel.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        JButton addHeroeButton = new JButton("Agregar Héroe");
        addHeroeButton.addActionListener(e -> agregarHeroe());
        panel.add(addHeroeButton);

        JButton addAntihéroeButton = new JButton("Agregar Antihéroe");
        addAntihéroeButton.addActionListener(e -> agregarAntihéroe());
        panel.add(addAntihéroeButton);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> editarPersonaje());
        panel.add(editButton);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> eliminarPersonaje());
        panel.add(deleteButton);

        tableModel = new DefaultTableModel(new Object[]{"Tipo", "Nombre", "Alias"}, 0);
        personajesTable = new JTable(tableModel);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(personajesTable), BorderLayout.CENTER);
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

            if (nombre.isEmpty() || alias.isEmpty() || identificacion.isEmpty() || nacionalidad.isEmpty() || habilidadNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Actor actor = new Actor(nombre, identificacion, nacionalidad, edad);
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

            if (nombre.isEmpty() || alias.isEmpty() || identificacion.isEmpty() || nacionalidad.isEmpty() || habilidadNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Actor actor = new Actor(nombre, identificacion, nacionalidad, edad);
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
                        nacionalidadField.setText(heroe.getActor().getNacionalidad());
                        edadField.setText(String.valueOf(heroe.getEdad()));
                        habilidadField.setText(heroe.getHabilidades().get(0).getNombre());
                        break;
                    }
                }
            } else if (tipo.equals("Antihéroe")) {
                for (Antihéroe antihéroe : antihéroes) {
                    if (antihéroe.getNombre().equals(nombre)) {
                        nombreField.setText(antihéroe.getNombre());
                        aliasField.setText(antihéroe.getAlias());
                        identificacionField.setText(antihéroe.getIdentificacion());
                        nacionalidadField.setText(antihéroe.getActor().getNacionalidad());
                        edadField.setText(String.valueOf(antihéroe.getEdad()));
                        habilidadField.setText(antihéroe.getHabilidades().get(0).getNombre());
                        break;
                    }
                }
            }
        }
    }

    private void eliminarPersonaje() {
        int selectedIndex = personajesTable.getSelectedRow();
        if (selectedIndex != -1) {
            tableModel.removeRow(selectedIndex);
            if (selectedIndex < heroes.size()) {
                heroes.remove(selectedIndex);
            } else {
                antihéroes.remove(selectedIndex - heroes.size());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

class Heroe {
    private String nombre;
    private String identificacion;
    private String alias;
    private Actor actor;
    private int edad;
    private List<Habilidad> habilidades;

    public Heroe(String nombre, String identificacion, String alias, Actor actor, int edad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.alias = alias;
        this.actor = actor;
        this.edad = edad;
        this.habilidades = new ArrayList<>();
    }

    public void agregarHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getAlias() {
        return alias;
    }

    public Actor getActor() {
        return actor;
    }

    public int getEdad() {
        return edad;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }
}

class Antihéroe {
    private String nombre;
    private String identificacion;
    private String alias;
    private Actor actor;
    private int edad;
    private List<Habilidad> habilidades;

    public Antihéroe(String nombre, String identificacion, String alias, Actor actor, int edad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.alias = alias;
        this.actor = actor;
        this.edad = edad;
        this.habilidades = new ArrayList<>();
    }

    public void agregarHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getAlias() {
        return alias;
    }

    public Actor getActor() {
        return actor;
    }

    public int getEdad() {
        return edad;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }
}

class Actor {
    private String nombre;
    private String identificacion;
    private String nacionalidad;
    private int edad;

    public Actor(String nombre, String identificacion, String nacionalidad, int edad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
}

class Habilidad {
    private String nombre;
    private String descripcion;

    public Habilidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }
}