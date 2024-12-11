package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import controller.Personal_Controller;
import model.Personal;

public class PanelPersonal extends JPanel {

    Personal_Controller personalController = new Personal_Controller();
    List<Personal> personalList = new ArrayList<>();
    DefaultTableModel modeloTabla;
    JTable table;

    public PanelPersonal() {
        this.setLayout(new BorderLayout());
        mostrarPersonal();
    }

    public void mostrarPersonal() {
        try {
            personalList = personalController.obtenerPersonal();

            String[] columnas = {
                    "Nombre", "Apellidos", "DNI", "Edad",
                    "Fecha Inicio", "Fecha Fin", "Horas Semanales",
                    "Rol", "Modificar", "Borrar"
            };
            modeloTabla = new DefaultTableModel(columnas, 0);

            // Llenar la tabla con los datos de personal
            for (Personal personal : personalList) {
                Object[] fila = {
                        personal.getNombre(), personal.getApellidos(), personal.getDni(),
                        personal.getEdad(), personal.getFechaInicio(), personal.getFechaFinContrato(),
                        personal.getHorasSemanales(), personal.getRol(), "Editar", "Eliminar"
                };
                modeloTabla.addRow(fila);
            }

            // Agregar una fila vacía para nuevas entradas
            modeloTabla.addRow(new Object[] { "", "", "", "", "", "", "", "", "Agregar", "" });
            table = new JTable(modeloTabla);
            aplicarEstilosTable();
            // Listeners de la tabla
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());

                    if (row == personalList.size() && column == 8) {
                        // "Agregar" clicked
                        try {
                            agregarPersonal(row);
                        } catch (SQLException ex) {
                            mostrarErrorSQL(ex, "Error al agregar el personal.");
                        }
                    } else if (column == 8) {
                        // "Editar" clicked
                        try {
                            Personal personalSeleccionado = personalList.get(row);
                            modificarPersonal(personalSeleccionado, row);
                        } catch (SQLException ex) {
                            mostrarErrorSQL(ex, "Error al modificar el personal.");
                        }
                    } else if (column == 9) {
                        // "Eliminar" clicked
                        try {
                            Personal personalSeleccionado = personalList.get(row);
                            eliminarPersonal(personalSeleccionado);
                        } catch (SQLException ex) {
                            mostrarErrorSQL(ex, "Error al eliminar el personal.");
                        }
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            this.add(scrollPane, BorderLayout.CENTER);

            this.revalidate();
            this.repaint();

        } catch (SQLException e) {
            mostrarErrorSQL(e, "Error al obtener la lista de personal.");
        }
    }

    public void eliminarPersonal(Personal personal) throws SQLException {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este registro?");
        if (confirm == JOptionPane.YES_OPTION) {
            personalController.eliminarPersonal(personal.getDni());
            personalList.remove(personal);
            actualizarInterfaz();
        }
    }

    public void modificarPersonal(Personal personal, int row) throws SQLException {

        String nombre = table.getValueAt(row, 0).toString();
        String apellidos = table.getValueAt(row, 1).toString();
        String dni = table.getValueAt(row, 2).toString();
        String edad = table.getValueAt(row, 3).toString();
        String fechaInicio = table.getValueAt(row, 4).toString();
        String fechaFin = table.getValueAt(row, 5).toString();
        String horasSemanales = table.getValueAt(row, 6).toString();
        String rol = table.getValueAt(row, 7).toString();

        Personal personalActualizado = new Personal(personal.getId(),
                nombre, apellidos, dni, edad, fechaInicio, fechaFin, horasSemanales, rol);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de modificar este registro?");
        if (confirm == JOptionPane.YES_OPTION) {
            personalController.actualizarPersonal(personal.getId(), personalActualizado);
            int index = personalList.indexOf(personal);
            personalList.set(index, personalActualizado);
            actualizarInterfaz();
        }
    }

    public void agregarPersonal(int row) throws SQLException {
        String nombre = table.getValueAt(row, 0).toString();
        String apellidos = table.getValueAt(row, 1).toString();
        String dni = table.getValueAt(row, 2).toString();
        String edad = table.getValueAt(row, 3).toString();
        String fechaInicio = table.getValueAt(row, 4).toString();
        String fechaFin = table.getValueAt(row, 5).toString();
        String horasSemanales = table.getValueAt(row, 6).toString();
        String rol = table.getValueAt(row, 7).toString();
        if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || edad.isEmpty()
                || fechaInicio.isEmpty() || fechaFin.isEmpty() || horasSemanales.isEmpty() || rol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Personal nuevoPersonal = new Personal(nombre, apellidos, dni, edad, fechaInicio, fechaFin, horasSemanales, rol);
        personalController.agregarPersonal(nuevoPersonal);
        personalList.add(nuevoPersonal);
        actualizarInterfaz();
    }

    private void mostrarErrorSQL(SQLException ex, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    public void actualizarInterfaz() {
        modeloTabla.setRowCount(0);

        for (Personal personal : personalList) {
            Object[] fila = {
                    personal.getNombre(), personal.getApellidos(), personal.getDni(),
                    personal.getEdad(), personal.getFechaInicio(), personal.getFechaFinContrato(),
                    personal.getHorasSemanales(), personal.getRol(), "Editar", "Eliminar"
            };
            modeloTabla.addRow(fila);
        }

        modeloTabla.addRow(new Object[] { "", "", "", "", "", "", "", "", "Agregar", "" });

        this.revalidate();
        this.repaint();
    }

    public void aplicarEstilosTable() {
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setIntercellSpacing(new Dimension(5, 5));
        // Desactivar la reordenación de columnas
        table.getTableHeader().setReorderingAllowed(false);

        // Cambiar el color de fondo al pasar el mouse sobre las filas
        table.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    table.setRowSelectionInterval(row, row); // Seleccionar la fila
                    table.setBackground(new Color(240, 240, 240)); // Fondo claro para la fila cuando el mouse pasa
                                                                   // sobre ella
                } else {
                    table.setBackground(Color.WHITE); // Restablecer el fondo cuando el mouse no está sobre ninguna fila
                }
            }
        });
    }
}
