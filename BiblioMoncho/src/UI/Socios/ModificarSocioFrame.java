package UI.Socios;

import Modulo.Socio;
import UI.ModificarObjetoFrame;
import UI.Prestamos.ModuloAgregarPrestamo;
import UI.Prestamos.ModuloPrestamo;
import Manager.SocioManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//*Clase que permite actualizar los datos de un Socio a partir de los atributos del objeto
//* Utiliza ModificarObjetoFrame por debajo como base */

public class ModificarSocioFrame extends ModificarObjetoFrame<Socio> {
    private SocioManager socioManager;
    // Constructor que recibe el objeto socio y el socioManager del moduloAgregarPrestamo
    public ModificarSocioFrame(Socio socio, ModuloAgregarPrestamo moduloAgregarPrestamo, String actualizaroAgregar) {
        super(socio);  // Llamar al constructor de la clase base
        this.socioManager = moduloAgregarPrestamo.getSocioManager();

        // Modificar atributos al presionar el botón en el JFrame de la clase base
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Intentar actualizar el socio en la base de datos
                try {
                    modificarAtributos();  // Llamar al método para modificar atributos
                    if (actualizaroAgregar == "actualizar") {
                        socioManager.actualizarSocio(socio);  // Actualizar en la base de datos  
                    }
                    else if (actualizaroAgregar == "agregar") {
                        socioManager.agregarSocio(socio); // Actualizar en la base de datos  
                    }
                    moduloAgregarPrestamo.actualizarPanelSocios();
                    JOptionPane.showMessageDialog(null, "socio modificado correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al "+actualizaroAgregar+": " + ex.getMessage());
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
    // Constructor que recibe el objeto socio y el socioManager
    public ModificarSocioFrame(Socio socio, ModuloSocio moduloSocio, String actualizaroAgregar) {
        super(socio);  // Llamar al constructor de la clase base
        this.socioManager = moduloSocio.getManager();

        // Modificar atributos al presionar el botón en el JFrame de la clase base
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Intentar actualizar el socio en la base de datos
                try {
                    modificarAtributos();  // Llamar al método para modificar atributos
                    if (actualizaroAgregar == "actualizar") {
                        socioManager.actualizarSocio(socio);  // Actualizar en la base de datos  
                    }
                    else if (actualizaroAgregar == "agregar") {
                        socioManager.agregarSocio(socio); // Actualizar en la base de datos  
                    }
                    moduloSocio.actualizarPanel();
                    JOptionPane.showMessageDialog(null, "socio modificado correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al "+actualizaroAgregar+": " + ex.getMessage());
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

    // Constructor que recibe el objeto socio y el socioManager del moduloAgregarPrestamo
    public ModificarSocioFrame(Socio socio, ModuloPrestamo moduloPrestamo, String actualizaroAgregar) {
        super(socio);  // Llamar al constructor de la clase base
        this.socioManager = moduloPrestamo.getSocioManager();

        // Modificar atributos al presionar el botón en el JFrame de la clase base
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Intentar actualizar el socio en la base de datos
                try {
                    modificarAtributos();  // Llamar al método para modificar atributos
                    if (actualizaroAgregar == "actualizar") {
                        socioManager.actualizarSocio(socio);  // Actualizar en la base de datos  
                    }
                    else if (actualizaroAgregar == "agregar") {
                        socioManager.agregarSocio(socio); // Actualizar en la base de datos  
                    }
                    moduloPrestamo.actualizarPanel();
                    JOptionPane.showMessageDialog(null, "socio modificado correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al "+actualizaroAgregar+": " + ex.getMessage());
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
