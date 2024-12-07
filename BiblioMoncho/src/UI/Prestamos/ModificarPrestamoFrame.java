package UI.Prestamos;


import Modulo.Prestamo;
import UI.ModificarObjetoFrame;
import Manager.PrestamoManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarPrestamoFrame extends ModificarObjetoFrame<Prestamo> {
    private PrestamoManager prestamoManager;

    // Constructor que recibe el objeto préstamo y el PrestamoManager
    public ModificarPrestamoFrame(Prestamo prestamo, ModuloPrestamo moduloPrestamo, String actualizaroAgregar) {
        super(prestamo);  // Llamar al constructor de la clase base
        this.prestamoManager = moduloPrestamo.getManager();

        // Crear y añadir botones para modificar el préstamo
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Intentar actualizar el préstamo en la base de datos
                try {
                    modificarAtributos();  // Llamar al método para modificar atributos

                    // Actualizar o agregar según el valor del parámetro
                    if (actualizaroAgregar.equals("actualizar")) {
                        prestamoManager.actualizarPrestamo(prestamo);  // Actualizar en la base de datos  
                    } else if (actualizaroAgregar.equals("agregar")) {
                        prestamoManager.agregarPrestamo(prestamo);  // Agregar nuevo préstamo en la base de datos  
                    }

                    moduloPrestamo.actualizarPanel();  // Actualizar el panel del módulo
                    JOptionPane.showMessageDialog(null, "Préstamo modificado correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al " + actualizaroAgregar + ": " + ex.getMessage());
                }
                dispose();  // Cerrar la ventana después de modificar
            }
        });
        
        // Añadir el botón a la interfaz
        add(modificarButton);
        pack();
        setLocationRelativeTo(null);  // Centrar la ventana
        setVisible(true);  // Hacer visible la ventana
    }

    
}
