package UI;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.border.MatteBorder;
import UI.Libros.ModuloLibro;
import UI.Prestamos.ModuloPrestamo;
import UI.Socios.ModuloSocio;
import UI.Prestamos.ModuloAgregarPrestamo;
import Manager.SocioManager;

public class PanelModulo extends JPanel {
    private JTextArea legal;
    private JScrollPane scrollPane;

    // Constructor
    public PanelModulo(int panelSeleccionado) {
        inicializarPanel();
        agregarPanelLegal();  // Método para agregar el área de texto legal
        setBackground(new Color(245,245,245));
        cargarModuloSeleccionado(panelSeleccionado);  // Cargar el módulo especificado según la entrada
    }

    // Inicializar las propiedades del panel principal
    private void inicializarPanel() {
        this.setBackground(new Color(245, 245, 245));
        this.setLayout(new GridBagLayout());
    }

    // Agregar el panel con la información legal
    private void agregarPanelLegal() {
        legal = new JTextArea("© 2024 BiblioMoncho. Todos los derechos reservados. | Dirección: Calle del libro | Teléfono: 699374860 | Correo: legal@bibliomoncho.com");
        legal.setBackground(new Color(245, 245, 245));
        legal.setLineWrap(true);
        legal.setWrapStyleWord(true);
        
        legal.setEditable(false);
        
        scrollPane = new JScrollPane(legal);
        scrollPane.setBorder(new MatteBorder(2, 0, 0, 0, Color.black));
        
        // Establecer las restricciones de diseño y agregar el JScrollPane
        GridBagConstraints gbc = crearGridBagConstraints(0, 1, 1.0, 0.05);
        this.add(scrollPane, gbc);
    }

    // Cargar el módulo según el panel seleccionado
    private void cargarModuloSeleccionado(int panelSeleccionado) {
        JPanel panelAMostrar = null;

        try {
            switch (panelSeleccionado) {
                case 1:
                    panelAMostrar = new ModuloLibro("enCatalogo");
                    break;
                case 8:
                    panelAMostrar = new ModuloLibro("descatalogados");
                    break;
                case 9:
                    panelAMostrar = new ModuloLibro("pendientesDevolucion");
                    break;
                case 2:
                    panelAMostrar = new ModuloSocio("activos");
                    break;
                case 10:
                    panelAMostrar = new ModuloSocio("inactivos");
                    break;
                case 3:
                    panelAMostrar = new ModuloPrestamo();
                    break;
                case 4:
                    panelAMostrar = new ModuloAgregarPrestamo();
                    break;
                case 5:
                    panelAMostrar = new ModuloPrestamo(1990);
                    break;
                case 6:
                    panelAMostrar = new ModuloPrestamo("morosos");
                    break;
                case 7:
                    SocioManager socioManager = new SocioManager();
                    panelAMostrar = new ModuloSocio(socioManager.obtenerSociosConLibrosSinDevolver());
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Panel no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Salida anticipada si no se encuentra un panel válido
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el módulo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Salida anticipada en caso de excepción
        }

        // Agregar el módulo seleccionado al panel
        agregarModuloAlPanel(panelAMostrar);
    }

    // Agregar el módulo seleccionado al panel
    public void agregarModuloAlPanel(JPanel modulo) {
        GridBagConstraints gbc = crearGridBagConstraints(0, 0, 1.0, 0.9);
        this.add(modulo, gbc);
        revalidate();  // Refresca el diseño
        repaint();     // Repaint the panel
    }

    // Método auxiliar para crear GridBagConstraints
    private GridBagConstraints crearGridBagConstraints(int gridX, int gridY, double weightX, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        gbc.fill = GridBagConstraints.BOTH;  // Llenar el espacio disponible
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes
        return gbc;
    }
}
