package UI;

import javax.swing.*;

import controller.Ingreso_Controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private JMenu Bebidas, Comidas, Personal;
    private JMenuItem ingresosMenuItem; // Item para mostrar los ingresos
    private JPanel panelPrincipal;
    private Ingreso_Controller ingresoController = new Ingreso_Controller();
    private double ingresos = 0.0; // Variable para almacenar los ingresos

    public MainFrame() {
        propiedadesFrame();
        menuBar();
        actualizarIngresos();

        this.setVisible(true);
    }

    private void propiedadesFrame() {
        this.setTitle("Zero Bugs");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 780);
        this.setResizable(false);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
    }

    // Método para crear el menú de Bebidas con MouseListener
    public void setBebidas() {
        this.Bebidas = new JMenu("Bebidas");

        Bebidas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cambiarPanel("bebidas");
            }
        });
    }

    // Método para crear el menú de Comidas con MouseListener
    public void setComidas() {
        this.Comidas = new JMenu("Comidas");

        Comidas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cambiarPanel("comidas");
            }
        });
    }

    // Método para crear el menú de Personal con MouseListener
    public void setPersonal() {
        this.Personal = new JMenu("Personal");

        // Crear el JMenuItem para mostrar los ingresos
        ingresosMenuItem = new JMenuItem("Ingresos: $0.0");
        Personal.add(ingresosMenuItem);

        Personal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cambiarPanel("personal");
            }
        });
    }

    // Método para crear la barra de menús
    public void menuBar() {
        menuBar = new JMenuBar();
        setBebidas(); // Inicializa el menú Bebidas
        setComidas(); // Inicializa el menú Comidas
        setPersonal(); // Inicializa el menú Personal

        menuBar.add(Bebidas);
        menuBar.add(Comidas);
        menuBar.add(Personal);

        this.setJMenuBar(menuBar);
    }

    // Método para cambiar el panel principal en base a la opción seleccionada
    private void cambiarPanel(String tipo) {
        if (panelPrincipal != null) {
            this.remove(panelPrincipal);
        }

        switch (tipo) {
            case "bebidas":
                if (!(panelPrincipal instanceof PanelBebidas)) {
                    panelPrincipal = new PanelBebidas();
                }
                break;
            case "comidas":
                if (!(panelPrincipal instanceof PanelComidas)) {
                    panelPrincipal = new PanelComidas();
                }
                break;
            case "personal":
                if (!(panelPrincipal instanceof PanelPersonal)) {
                    panelPrincipal = new PanelPersonal();
                }
                break;
            default:
                panelPrincipal = new JPanel();
                break;
        }

        this.add(panelPrincipal, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    // Método para actualizar los ingresos en el menú
    public void actualizarIngresos() {
        try {
            double totalIngresos = ingresoController.obtenerSumaIngresos(); // Obtener los ingresos desde la base de
                                                                            // datos
            ingresosMenuItem.setText("Ingresos: $" + String.format("%.2f", totalIngresos)); // Actualizar el texto del
                                                                                            // JMenuItem
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
