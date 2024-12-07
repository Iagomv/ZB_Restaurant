package UI.Libros;

import Modulo.Libro;
import UI.ModificarObjetoFrame;
import UI.Prestamos.ModuloAgregarPrestamo;
import UI.Prestamos.ModuloPrestamo;
import Manager.LibroManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//*Clase que se encarga de generar un nuevo Frame que muestra los atributos del objeto
//* y sus valores, con la posibilidad de actualizar los mismos. */
public class ModificarLibroFrame extends ModificarObjetoFrame<Libro> {
    private LibroManager libroManager;

    // Constructor que recibe el objeto Libro y el LibroManager
    public ModificarLibroFrame(Libro libro, ModuloLibro moduloLibro, String actualizaroAgregar) {
        super(libro);  // Llamar al constructor de la clase base
        this.libroManager = moduloLibro.getManager();

        // Modificar atributos al presionar el botón en el JFrame de la clase base
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Intentar actualizar el libro en la base de datos
                try {
                    modificarAtributos();  // Llamar al método para modificar atributos
                    if (actualizaroAgregar == "actualizar") {
                        libroManager.actualizarLibro(libro);  // Actualizar en la base de datos
                    }
                    else if (actualizaroAgregar == "agregar") {
                        libroManager.agregarLibro(libro); // Actualizar en la base de datos  
                    }
                    moduloLibro.actualizarPanel();
                    JOptionPane.showMessageDialog(null, "Libro modificado correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al "+actualizaroAgregar+" el libro: " + ex.getMessage());
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
    // Constructor que recibe el objeto Libro y el LibroManager
    public ModificarLibroFrame(Libro libro, ModuloAgregarPrestamo moduloAgregarPrestamo, String actualizaroAgregar) {
        super(libro);  // Llamar al constructor de la clase base
        this.libroManager = moduloAgregarPrestamo.getLibroManager();

        // Modificar atributos al presionar el botón en el JFrame de la clase base
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Intentar actualizar el libro en la base de datos
                try {
                    modificarAtributos();  // Llamar al método para modificar atributos
                    if (actualizaroAgregar == "actualizar") {
                        libroManager.actualizarLibro(libro);  // Actualizar en la base de datos
                    }
                    else if (actualizaroAgregar == "agregar") {
                        libroManager.agregarLibro(libro); // Actualizar en la base de datos  
                    }
                    moduloAgregarPrestamo.actualizarPanelLibros();
                    JOptionPane.showMessageDialog(null, "Libro modificado correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al "+actualizaroAgregar+" el libro: " + ex.getMessage());
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

    // Constructor que recibe el objeto Libro y el LibroManager
    public ModificarLibroFrame(Libro libro, ModuloPrestamo moduloPrestamo, String actualizaroAgregar) {
        super(libro);  // Llamar al constructor de la clase base
        this.libroManager = moduloPrestamo.getLibroManager();

        // Modificar atributos al presionar el botón en el JFrame de la clase base
        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Intentar actualizar el libro en la base de datos
                try {
                    modificarAtributos();  // Llamar al método para modificar atributos
                    if (actualizaroAgregar == "actualizar") {
                        libroManager.actualizarLibro(libro);  // Actualizar en la base de datos
                    }
                    else if (actualizaroAgregar == "agregar") {
                        libroManager.agregarLibro(libro); // Actualizar en la base de datos  
                    }
                    moduloPrestamo.actualizarPanel();
                    JOptionPane.showMessageDialog(null, "Libro modificado correctamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al "+actualizaroAgregar+" el libro: " + ex.getMessage());
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
