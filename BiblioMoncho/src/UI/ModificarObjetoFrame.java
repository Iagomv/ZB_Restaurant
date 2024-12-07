package UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class ModificarObjetoFrame<T> extends JFrame {
    private T objeto; // El objeto a modificar
    private Map<String, JTextField> textFields = new HashMap<>(); // Mapa para guardar JTextFields

    // Constructor que recibe el objeto a modificar
    public ModificarObjetoFrame(T objeto) {
        this.objeto = objeto;
        setTitle("Modificar " + objeto.getClass().getSimpleName()); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new GridLayout(0, 2));
        

        // Mostrar los atributos del objeto en JTextFields
        mostrarAtributos();

        // Botón para modificar el objeto
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarAtributos(); // Modificar atributos al presionar el botón
                JOptionPane.showMessageDialog(null, objeto.getClass().getSimpleName() + " modificado exitosamente.");
                dispose(); // Cerrar el JFrame después de modificar
            }
        });

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    // Método para mostrar los atributos del objeto en JTextFields
    public void mostrarAtributos() {
        Field[] fields = objeto.getClass().getDeclaredFields(); // Obtener los campos del objeto

        for (Field field : fields) {
            field.setAccessible(true); // Hacer accesible el campo, incluso si es privado
            try {
                // Crear un borde vacío para el interior
                Border bordeVacio = BorderFactory.createEmptyBorder(15, 10, 15, 10);

                // Crear un borde de línea para el exterior
                Border bordeLinea = BorderFactory.createLineBorder(Color.BLACK, 1);

                // Crear el borde compuesto (interior: borde vacío, exterior: borde de línea)
                Border bordeCompuesto = BorderFactory.createCompoundBorder(bordeVacio,bordeLinea );

                // Crear JLabel y JTextField para cada atributo
                JLabel label = new JLabel(field.getName(), SwingConstants.CENTER); // Crear una etiqueta con el nombre del campo
                JTextField textField = new JTextField(field.get(objeto).toString(), SwingConstants.CENTER); // Obtener el valor actual del atributo y establecerlo en el JTextField
                label.setBorder(bordeCompuesto);
                label.setFont(new Font("Arial", Font.PLAIN, 18));
                textField.setBorder(bordeCompuesto);textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setFont(new Font("Arial", Font.PLAIN, 18));


                add(label); 
                add(textField);

                // Guardar el JTextField en el mapa con el nombre del campo como clave
                textFields.put(field.getName(), textField);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para modificar los atributos del objeto según los valores en los JTextFields
    public void modificarAtributos() {
        Field[] fields = objeto.getClass().getDeclaredFields(); // Obtener los campos del objeto

        for (Field field : fields) {
            field.setAccessible(true); // Hacer accesible el campo
            try {
                JTextField textField = textFields.get(field.getName()); // Obtener el JTextField correspondiente
                // Modificar el atributo con el valor del JTextFieldf
                if (field.getType() == String.class) {
                    field.set(objeto, textField.getText()); // Si el campo es de tipo String
                } else if (field.getType() == int.class) {
                    field.set(objeto, Integer.parseInt(textField.getText())); // Si el campo es de tipo int
                }
                // Aquí puedes añadir más tipos de datos según sea necesario
            } catch (IllegalAccessException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    //Método para aplicar filtro gris
    public static BufferedImage aplicarFiltroGrises(ImageIcon imgIcon) {
        // Convertir ImageIcon a BufferedImage
        BufferedImage bufferedImage = new BufferedImage(imgIcon.getIconWidth(), imgIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(imgIcon.getImage(), 0, 0, null);
        g.dispose();

        // Aplicar el filtro de escala de grises
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                Color pixelColor = new Color(bufferedImage.getRGB(x, y));
                int grayValue = (int) (pixelColor.getRed() * 0.2989 + pixelColor.getGreen() * 0.5870 + pixelColor.getBlue() * 0.1140);
                Color grayColor = new Color(grayValue, grayValue, grayValue);
                bufferedImage.setRGB(x, y, grayColor.getRGB());
            }
        }

        return bufferedImage;
    }

}
