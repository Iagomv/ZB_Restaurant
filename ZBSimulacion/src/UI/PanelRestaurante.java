package UI;

import javax.swing.*;
import java.awt.*;
import Model.*;

public class PanelRestaurante extends JPanel {
    private final int anchoMesa = 100;
    private final int altoMesa = 100;
    private final int espacio = 20;
    private Restaurante restaurante;

    public PanelRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
        this.setBackground(new Color(255, 255, 255)); // Fondo blanco
        this.setPreferredSize(new Dimension(800, 600)); // Tamaño del panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarMesas(g);
        dibujarCamareros(g);
        dibujarCocineros(g);
    }

    private void dibujarMesas(Graphics g) {
        Mesa[] mesas = restaurante.getMesas(); // Obtener las mesas desde Restaurante
        for (int i = 0; i < mesas.length; i++) {
            Mesa mesa = mesas[i];
            int x = (i % 5) * (anchoMesa + espacio);
            int y = (i / 5) * (altoMesa + espacio);

            if (mesa.getEstado().equals("Ocupada")) {
                g.setColor(Color.RED); // Mesa ocupada
            } else {
                g.setColor(Color.GREEN); // Mesa libre
            }

            g.fillRect(x, y, anchoMesa, altoMesa);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, anchoMesa, altoMesa); // Borde de la mesa
            g.drawString("Mesa " + mesa.getNumeroMesa(), x + 10, y + 50);
        }
    }

    private void dibujarCamareros(Graphics g) {
        Camarero[] camareros = restaurante.getCamareros(); // Obtener los camareros desde Restaurante
        for (Camarero camarero : camareros) {
            g.setColor(Color.BLUE);
            g.fillOval(50 + camarero.getIdCamarero() * 100, 500, 30, 30); // Posición de los camareros
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(camarero.getIdCamarero()), 50 + camarero.getIdCamarero() * 100, 540);
        }
    }

    private void dibujarCocineros(Graphics g) {
        Cocinero[] cocineros = restaurante.getCocineros(); // Obtener los cocineros desde Restaurante
        for (Cocinero cocinero : cocineros) {
            g.setColor(Color.ORANGE);
            g.fillOval(50 + cocinero.getIdCocinero() * 100, 550, 30, 30); // Posición de los cocineros
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(cocinero.getIdCocinero()), 50 + cocinero.getIdCocinero() * 100, 590);
        }
    }
}
