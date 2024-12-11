package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import controller.Comida_Controller;
import controller.Comida_Controller;
import model.Comida;

public class PanelComidas extends JPanel {

    Comida_Controller comidaController = new Comida_Controller();
    List<Comida> comidas = new ArrayList<>();
    DefaultTableModel modeloTabla;
    JTable table;

    public PanelComidas() {
        this.setLayout(new BorderLayout());
        mostrarComidas();
    }

    public void mostrarComidas() {
        try {
            comidas = comidaController.obtenerComidas();

            // Configuración del thead
            String[] columnas = { "Nombre", "Tipo", "Precio", "Stock", "Modificar", "Borrar" };
            modeloTabla = new DefaultTableModel(columnas, 0);

            // Llenar la tabla con los datos SQL
            for (Comida comida : comidas) {
                Object[] fila = { comida.getNombre(), comida.getTipo(), comida.getPrecio(), comida.getStock(), "Editar",
                        "Eliminar" };
                modeloTabla.addRow(fila);
            }

            // Agregamos la fila vacía para agregar al final de la tabla
            modeloTabla.addRow(new Object[] { "", "", "", "", "Agregar", "" });
            table = new JTable(modeloTabla);
            aplicarEstilosTable();

            // Listeners de la tabla
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());

                    if (row == comidas.size() && column == 4) {
                        // "Agregar" row clicked
                        try {
                            agregarComida(row);
                        } catch (SQLException ex) {
                            mensajeErrorSQL(ex, "Error al agregar la comida.", "Error");
                        }
                    } else if (column == 4) {
                        // "Editar" clicked
                        try {
                            Comida comidaSeleccionada = comidas.get(row);
                            modificarComida(comidaSeleccionada, row);
                        } catch (SQLException ex) {
                            mensajeErrorSQL(ex, "Error al modificar la comida.", "Error");
                        }
                    } else if (column == 5) {
                        // "Eliminar" clicked
                        try {
                            Comida comidaSeleccionada = comidas.get(row);
                            eliminarFila(comidaSeleccionada);
                        } catch (SQLException ex) {
                            mensajeErrorSQL(ex, "Error al eliminar la comida.", "Error");
                        }
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(
                    20, 20, 20, 20));
            this.add(scrollPane, BorderLayout.CENTER);

            // Refresh the panel
            this.revalidate();
            this.repaint();

        } catch (SQLException e) {
            System.out.println("Error al obtener las comidas");
            e.printStackTrace();
        }
    }

    // Eliminar la fila correspondiente
    public void eliminarFila(Comida comida) throws SQLException {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta comida?");
        if (confirm == JOptionPane.YES_OPTION) {
            comidaController.eliminarComida(comida.getId());
            comidas.remove(comida);
            actualizacionInterfaz();

        }
    }

    // Modificar una comida
    public void modificarComida(Comida comida, int row) throws SQLException {
        String nombre = table.getValueAt(row, 0).toString();
        String tipo = table.getValueAt(row, 1).toString();
        String precio = table.getValueAt(row, 2).toString();
        String stock = table.getValueAt(row, 3).toString();

        Comida comidaActualizada = new Comida(nombre, tipo, precio, stock);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de modificar esta comida?");
        if (confirm == JOptionPane.YES_OPTION) {
            comidaController.actualizarComida(comida.getId(), comidaActualizada);
            // Actualizar la lista de comidas
            int index = comidas.indexOf(comida);
            comidas.set(index, comidaActualizada); // Reemplazar la comida en la lista
            actualizacionInterfaz();
        }
    }

    // Agregar una nueva comida
    public void agregarComida(int row) throws SQLException {
        // Obtener los valores de la fila "Agregar"
        String nombre = table.getValueAt(row, 0).toString();
        String tipo = table.getValueAt(row, 1).toString();
        String precio = table.getValueAt(row, 2).toString();
        String stock = table.getValueAt(row, 3).toString();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || tipo.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear la nueva comida
        Comida nuevaComida = new Comida(nombre, tipo, precio, stock);

        // Llamar al controlador para agregar la comida
        comidaController.agregarComidas(nuevaComida);
        comidas.add(nuevaComida);

        actualizacionInterfaz();
    }

    // Mostrar el mensaje de error
    private void mensajeErrorSQL(SQLException ex, String mensaje, String titulo) {
        JOptionPane.showMessageDialog(
                PanelComidas.this,
                mensaje,
                titulo,
                JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    public void actualizacionInterfaz() {
        // Limpiar el modelo de la tabla
        modeloTabla.setRowCount(0); // Elimina todas las filas del modelo

        // Añadir las filas de la lista de comidas actualizada
        for (Comida comida : comidas) {
            Object[] fila = { comida.getNombre(), comida.getTipo(), comida.getPrecio(), comida.getStock(), "Editar",
                    "Eliminar" };
            modeloTabla.addRow(fila);
        }

        // Agregar nuevamente la fila de "Agregar"
        modeloTabla.addRow(new Object[] { "", "", "", "", "Agregar", "" });

        // Refrescar la interfaz
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
