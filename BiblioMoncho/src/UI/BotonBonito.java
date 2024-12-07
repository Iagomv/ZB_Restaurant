package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonBonito extends JButton {
    
    // Constructor que recibe el texto del botón
    public BotonBonito(String texto) {
        super(texto);

        // Configuración del estilo del botón
        if (texto.startsWith("Nuevo")) {
            setFont(new Font("Arial", Font.BOLD, 26)); // Fuente para botones de agregar
        }
        setFont(new Font("Arial", Font.PLAIN, 20)); // Fuente para el boton si no empieza por Nuevo
        setForeground(Color.BLACK);           
        setBorderPainted(true);             
        setFocusPainted(false);             
        setContentAreaFilled(false);        
        setOpaque(true);             

        // Establecer padding utilizando setMargin
        //setMargin(new Insets(20, 40, 20, 40)); // Padding: top, left, bottom, right

        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));; // Establecer borde inicial
        setBackground(new Color(251, 251, 251));     // Fondo blanco

        // Efecto Hover (cambiar color al pasar el ratón)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (texto.startsWith("Nuevo") || texto.startsWith("Recatalogar")) {
                    setBackground(new Color(161, 255, 173)); // Cambiar color de fondo al hacer hover   
                }else{
                    setBackground(new Color(245, 98, 98));
                }
                
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                //setBorder(new LineBorder(Color.BLACK, 2)); // Aumentar el grosor del borde al hacer hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(251, 251, 251)); // Restaurar color de fondo original
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (texto.startsWith("Nuevo")) {
                    setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.BLACK, 1), 
                            BorderFactory.createEmptyBorder(5, 10, 5, 10)));; // Establecer borde inicial
                }
                //setBorder(new LineBorder(Color.BLACK, 1)); // Restaurar el borde al salir del hover
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Llamada al método padre para dibujar el botón
    }}