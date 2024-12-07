import javax.swing.*;

import UI.PanelModulo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfaz extends JFrame implements ActionListener {
    
    private JMenuBar menuBar;
    private JMenu opciones, tamaño, icono, libros, socios, prestamos;
    private JMenuItem tamañoPequeño, tamañoMediano, tamañoGrande, icono1, icono2, icono3;
    private JMenuItem libroModuloItem, librosDescatalogados,librosPendientesDevolucion, sociosActivosItem,sociosAntiguosItem, prestamoModuloItem, agregarPrestamoItem, historicoPrestamoItem, morososPrestamo, blackListItem; // Add menu items for libros and socios
    private PanelModulo panelModulo;
    private Font fuentePequeña = new Font("Serif", Font.PLAIN, 16);
    private Font fuenteGrande = new Font("Serif", Font.PLAIN, 20);


    public Interfaz() {
        menuBar();
        initPantalla();
    }

    private void menuBar() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuOpciones();
        menuLibros();
        menuSocios();
        menuPrestamos();
        // Aplicar fuente a la barra de menú
        menuBar.setFont(fuentePequeña);
    }
    
    private void menuLibros() {
        libros = new JMenu("Libros");
        aplicarFuenteGrande(libros); // Aplicar fuente al menú "Libros"
        
        libroModuloItem = new JMenuItem("Libros en catálogo");
        libroModuloItem.addActionListener(this);
        aplicarFuente(libroModuloItem); // Aplicar fuente al ítem
        librosDescatalogados = new JMenuItem("Libros descatalogados");
        librosDescatalogados.addActionListener(this);
        aplicarFuente(librosDescatalogados); // Aplicar fuente al ítem

        librosPendientesDevolucion = new JMenuItem("Libros pendientes de devolución");
        librosPendientesDevolucion.addActionListener(this);
        aplicarFuente(librosPendientesDevolucion); // Aplicar fuente al ítem
        
        libros.add(libroModuloItem);
        libros.add(librosDescatalogados);
        libros.add(librosPendientesDevolucion);
        menuBar.add(libros);
    }
    
    private void menuSocios() {
        socios = new JMenu("Socios");
        aplicarFuenteGrande(socios); // Aplicar fuente al menú "Socios"
        
        sociosActivosItem = new JMenuItem("Socios activos");
        sociosActivosItem.addActionListener(this);
        aplicarFuente(sociosActivosItem); // Aplicar fuente al ítem

        sociosAntiguosItem = new JMenuItem("Antiguos socios");
        sociosAntiguosItem.addActionListener(this);
        aplicarFuente(sociosAntiguosItem);
        
        blackListItem = new JMenuItem("BlackList");
        blackListItem.addActionListener(this);
        aplicarFuente(blackListItem); // Aplicar fuente al ítem
        
        socios.add(blackListItem);
        socios.add(sociosActivosItem);
        socios.add(sociosAntiguosItem);
        menuBar.add(socios);
    }
    
    private void menuPrestamos() {
        prestamos = new JMenu("Préstamos");
        aplicarFuenteGrande(prestamos);
        
        prestamoModuloItem = new JMenuItem("Préstamos en curso");
        prestamoModuloItem.addActionListener(this);
        aplicarFuente(prestamoModuloItem); 
        
        agregarPrestamoItem = new JMenuItem("Agregar nuevo préstamo");
        agregarPrestamoItem.addActionListener(this);
        aplicarFuente(agregarPrestamoItem); // Aplicar fuente al ítem
        
        historicoPrestamoItem = new JMenuItem("Histórico de préstamos");
        historicoPrestamoItem.addActionListener(this);
        aplicarFuente(historicoPrestamoItem); // Aplicar fuente al ítem
        
        morososPrestamo = new JMenuItem("Préstamos fuera de plazo");
        morososPrestamo.addActionListener(this);
        aplicarFuente(morososPrestamo); // Aplicar fuente al ítem
        
        prestamos.add(agregarPrestamoItem);
        prestamos.add(historicoPrestamoItem);
        prestamos.add(morososPrestamo);
        prestamos.add(prestamoModuloItem);
        menuBar.add(prestamos);
    }

    private void menuOpciones() {
        opciones = new JMenu("Opciones");
        aplicarFuenteGrande(opciones); // Aplicar fuente al menú "Opciones"
        
        menuBar.add(opciones);
        
        tamaño = new JMenu("Tamaño de la ventana");
        aplicarFuente(tamaño); // Aplicar fuente al menú "Tamaño"
        opciones.add(tamaño);
        
        icono = new JMenu("Cambiar icono");
        aplicarFuente(icono); // Aplicar fuente al menú "Cambiar icono"
        opciones.add(icono);

        tamañoPequeño = new JMenuItem("1024x768");
        tamaño.add(tamañoPequeño);
        tamañoPequeño.addActionListener(this);
        aplicarFuente(tamañoPequeño); // Aplicar fuente al ítem
        
        tamañoMediano = new JMenuItem("1368x920");
        tamaño.add(tamañoMediano);
        tamañoMediano.addActionListener(this);
        aplicarFuente(tamañoMediano); // Aplicar fuente al ítem
        
        tamañoGrande = new JMenuItem("Full HD");
        tamaño.add(tamañoGrande);
        tamañoGrande.addActionListener(this);
        aplicarFuente(tamañoGrande); // Aplicar fuente al ítem

        icono1 = new JMenuItem("Icono 1");
        icono.add(icono1);
        icono1.addActionListener(this);
        aplicarFuente(icono1); // Aplicar fuente al ítem
        
        icono2 = new JMenuItem("Icono 2");
        icono.add(icono2);
        icono2.addActionListener(this);
        aplicarFuente(icono2); // Aplicar fuente al ítem
        
        icono3 = new JMenuItem("Icono 3");
        icono.add(icono3);
        icono3.addActionListener(this);
        aplicarFuente(icono3); // Aplicar fuente al ítem
    }

    private void initPantalla() {
        setTitle("BiblioMoncho");
        getContentPane().setBackground(new Color(245, 245, 245));
        setSize(1368, 920);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
    }

    private void añadirIcono(int numeroIcono) {
        String rutaIcono = "imagenes\\book" + numeroIcono + ".png";
        ImageIcon icon = new ImageIcon(rutaIcono);
        setIconImage(icon.getImage());
        repaint();
    }


    //Gestión de actionPerformed -> Paneles
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tamañoPequeño) {
            setSize(1024, 768);
        } else if (e.getSource() == tamañoMediano) {
            setSize(1368, 920);
            this.setLocationRelativeTo(null);
        } else if (e.getSource() == tamañoGrande) {
            setSize(1920, 1080);
            this.setLocationRelativeTo(null);
        } else if (e.getSource() == libroModuloItem) {
            cambiarPanel(1);
        } else if (e.getSource() == librosDescatalogados) {
            cambiarPanel(8);
        } else if (e.getSource() == librosPendientesDevolucion) {
            cambiarPanel(9);
        } else if (e.getSource() == sociosActivosItem) {
            cambiarPanel(2);
        } else if (e.getSource() == sociosAntiguosItem) {
            cambiarPanel(10);
        } else if (e.getSource() == blackListItem) {
            cambiarPanel(7);
        } else if (e.getSource() == prestamoModuloItem) {
            cambiarPanel(3);
        } else if (e.getSource() == agregarPrestamoItem) {
            cambiarPanel(4);
        } else if (e.getSource() == historicoPrestamoItem) {
            cambiarPanel(5);
        } else if (e.getSource() == morososPrestamo) {
            cambiarPanel(6);
        } else if (e.getSource() == icono1) {
            añadirIcono(1);
        } else if (e.getSource() == icono2) {
            añadirIcono(2);
        } else if (e.getSource() == icono3) {
            añadirIcono(3);
        }
    }

    //Método singleton para el panel + Actualización
    private void cambiarPanel(int tipo) {
        if (panelModulo != null) {
            this.remove(panelModulo);
        }
        panelModulo = new PanelModulo(tipo);
        this.add(panelModulo, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void aplicarFuente(JComponent componente) {
        componente.setFont(fuentePequeña);
    }
    private void aplicarFuenteGrande(JComponent componente) {
        componente.setFont(fuenteGrande);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interfaz());
    }
}
