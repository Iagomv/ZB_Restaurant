package UI;

import javax.swing.*;

import model.Bebida;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private JMenu Bebidas, Comidas, Personal;
    private JPanel panelPrincipal;
    public MainFrame() {
        propiedadesFrame();
        menuBar();
        this.setVisible(true);
    }

    private void propiedadesFrame() {
        this.setTitle("Zero Bugs");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 780);
        this.setResizable(false);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
    }

    // Método para crear el menú de Bebidas con MouseListener
    public void setBebidas() {
        this.Bebidas = new JMenu("Bebidas");

        // Usamos MouseListener para detectar el clic en el menú
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

        // Usamos MouseListener para detectar el clic en el menú
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

        // Usamos MouseListener para detectar el clic en el menú
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
        setBebidas();    // Inicializa el menú Bebidas
        setComidas();    // Inicializa el menú Comidas
        setPersonal();   // Inicializa el menú Personal

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

        // Aquí agregamos paneles reales para Bebidas, Comidas y Personal
        switch (tipo) {
            case "bebidas":
                if (panelPrincipal instanceof PanelBebidas) {
                    System.out.println("El panel Bebidas ya estaba cargado.");
                }else{panelPrincipal = new PanelBebidas();  // Aquí puedes agregar tu panel específico para Bebidas
                    panelPrincipal.setBackground(Color.GREEN); // Ejemplo de color}
                }
                break;
            case "comidas":
                panelPrincipal = new JPanel();  // Aquí puedes agregar tu panel específico para Comidas
                panelPrincipal.setBackground(Color.YELLOW); // Ejemplo de color
                break;
            case "personal":
                panelPrincipal = new JPanel();  // Aquí puedes agregar tu panel específico para Personal
                panelPrincipal.setBackground(Color.BLUE); // Ejemplo de color
                break;
            default:
                panelPrincipal = new JPanel();
                break;
        }

        this.add(panelPrincipal, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}