package UI.Libros;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import Manager.LibroManager;
import Modulo.Libro;
import UI.BotonBonito;
import UI.ModificarObjetoFrame;
import UI.PanelModulo;
import UI.Prestamos.ModuloPrestamo;

import java.awt.image.BufferedImage;

public class ModuloLibro extends JPanel {
    private List<Libro> listaLibros = new ArrayList<>();
    List<Libro> listaLibrosBuscados = new ArrayList<>();
    private JPanel panelLibros; // Panel que contendrá los libros y será envuelto por el JScrollPane
    private JPanel lineaLibro;
    private LibroManager manager;
    private JPanel panelBuscaLibros;
    private JTextField tfBuscaLibros;
    private BotonBonito botonBuscaLibros;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBuscaLibros;
    private String librosaMostrar;
    JLabel label;
    
    //* Constructor */
    public ModuloLibro(String librosaMostrar) throws SQLException {
        this.setBackground(new Color(245, 245, 245));
        this.setLayout(new BorderLayout()); // Establece el layout del panel principal
        this.librosaMostrar = librosaMostrar;
        // Crear el manager de libros y obtener la lista de libros
        manager = new LibroManager();
        if (listaLibros.isEmpty()) {
            if (librosaMostrar == "enCatalogo") {
                listaLibros = manager.obtenerLibrosNoDescatalogados();
            }else if (librosaMostrar == "descatalogados") {
                listaLibros = manager.obtenerLibrosDescatalogados();
            }else if(librosaMostrar == "pendientesDevolucion"){
                listaLibros = manager.obtenerLibrosPendientesDevolución();
            }
        }
        getPanelSuperior();

        // Agrega el JScrollPane en la parte central
        JScrollPane scrollPane = getScrollPane();
        scrollPane.setBorder(new EmptyBorder(10, 150, 10, 150));
        this.add(scrollPane, BorderLayout.CENTER);
    }

    //Gestión del panel superior con opciones según los paneles a mostrar
    private void getPanelSuperior() {
        // Agrega el botón "Nuevo Libro" en la parte superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(245, 245, 245));
        if (librosaMostrar == "enCatalogo") {
            panelSuperior.add(botonAlta());
        }else if (librosaMostrar == "descatalogados" || librosaMostrar == "pendientesDevolucion") {
            String textoaMostrarEnTitulo = (librosaMostrar == "descatalogados") ? "Libros descatalogados" : "Pendientes de devolución";
            JLabel label = new JLabel(textoaMostrarEnTitulo);
            label.setFont(new Font("Serif",Font.BOLD,26));
            Color colorTexto = (librosaMostrar == "descatalogados") ? Color.DARK_GRAY : Color.RED; 
            label.setForeground(colorTexto);
            panelSuperior.add(label);
        }
        panelBuscaLibros();
        panelSuperior.add(panelBuscaLibros);
        this.add(panelSuperior, BorderLayout.NORTH);
    }
    
    public ModuloLibro(List<Libro> listaLibros){
        this.listaLibros = listaLibros;
    }

    private JScrollPane getScrollPane() throws SQLException {
        // Crear el panel donde se agregarán las lineas de los libros
        panelLibros = new JPanel();
        panelLibros.setLayout(new BoxLayout(panelLibros, BoxLayout.Y_AXIS));

        // Llamar al método para cargar los libros en el panel de libros
        itemLibro();

        // Agregar el panel de libros dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelLibros);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Desactivar scroll horizontal
        scrollPane.setBorder(null);
        return scrollPane;
    }
    
    //*ForEach Libro -> lineaModuloLibro -> panelLibros */
    public void itemLibro() {

        List<Libro> librosAImprimir = (listaLibrosBuscados.isEmpty()) ? listaLibros : listaLibrosBuscados;
        for (Libro libro : librosAImprimir) {
            JPanel panelTemp = lineaModuloLibro(libro);
            panelLibros.add(panelTemp); // Agregar cada linea generada al panel de libros
        }
        panelLibros.revalidate(); // Notifica al panel que ha habido un cambio
        panelLibros.repaint();    // Fuerza a que el panel se repinte
    }

    //GridBagConstraints para posicionamiento dentro de LineaLibro 
    private GridBagConstraints gbcImagenLibro() {
        GridBagConstraints gbcLineaLibro = new GridBagConstraints();
        gbcLineaLibro.gridx = 0;
        gbcLineaLibro.gridy = 0;
        gbcLineaLibro.weightx =    1;
        gbcLineaLibro.weighty = 1; // Más peso para que ocupe la mayor parte del espacio
        gbcLineaLibro.gridheight = 3;
        gbcLineaLibro.fill = GridBagConstraints.VERTICAL;
        return gbcLineaLibro;
    }
    private GridBagConstraints gbcTituloLibro() {
        GridBagConstraints gbcLineaLibro = new GridBagConstraints();
        gbcLineaLibro.gridx = 1;
        gbcLineaLibro.gridy = 0;
        gbcLineaLibro.weightx = 0.5;
        gbcLineaLibro.weighty = 0.5; // Más peso para que ocupe la mayor parte del espacio
        gbcLineaLibro.anchor = GridBagConstraints.CENTER;
        gbcLineaLibro.fill = GridBagConstraints.HORIZONTAL;
        return gbcLineaLibro;
    }
    private GridBagConstraints gbcInfoLibro() {
        GridBagConstraints gbcLineaLibro = new GridBagConstraints();
        gbcLineaLibro.gridx = 1;
        gbcLineaLibro.gridy = 1;
        gbcLineaLibro.weightx = 0.5;
        gbcLineaLibro.weighty = 0.5; // Más peso para que ocupe la mayor parte del espacio
        gbcLineaLibro.anchor = GridBagConstraints.CENTER;
        gbcLineaLibro.fill = GridBagConstraints.HORIZONTAL;
        return gbcLineaLibro;
    }
    private GridBagConstraints gbcBotonBaja() {
        GridBagConstraints gbcLineaLibro = new GridBagConstraints();
        gbcLineaLibro.gridx = 1;
        gbcLineaLibro.gridy = 2;
        gbcLineaLibro.weightx = 0.5;
        gbcLineaLibro.weighty = 0.5;
        gbcLineaLibro.anchor = GridBagConstraints.CENTER;
        return gbcLineaLibro;
    }
    
    //*Linea a mostrar para cada libro */
    private JPanel lineaModuloLibro(Libro libro) {
        lineaLibro = new JPanel();
        lineaLibro.setLayout(new GridBagLayout());
        lineaLibro.setBackground(new Color(245, 245, 245));
    
        imagenLabel(libro); // Agregar la imagen
        tituloLibro(libro); // Agregar el título
        infoLibro(libro); // Agragar informacion adicional
        if (librosaMostrar == "enCatalogo") {
            botonBaja(libro);// Agregar el botón de baja
        }else if (librosaMostrar == "pendientesDevolucion") {
            botonPrestamoLibro(libro);
        }else if (librosaMostrar == "descatalogados") {
            botonRecatalogar(libro);
        }
        // Añadir un borde alrededor del panel
        lineaLibro.setBorder(new EmptyBorder(25, 20, 25, 20));
        
        return lineaLibro;
    }
    

     //imagenLabel -> lineaLibro gestiona el filtro de la imagen según libros a mostrar
    private void imagenLabel(Libro libro) {
        try {
            label = new JLabel();
            @SuppressWarnings("deprecation")
            URL urlImagen = new URL(libro.getUrlImagen()); // URL de la imagen del libro
            ImageIcon imgIcon = new ImageIcon(urlImagen);  // Crear un icono de imagen
            // Redimensionar la imagen para que no sea demasiado grande
            Image img = imgIcon.getImage();
            Image resizedImage = img.getScaledInstance(150, 210, Image.SCALE_SMOOTH); // Ajustar tamaño
            imgIcon = new ImageIcon(resizedImage);

            // Imagen a mostrar según si está en catálogo o no
            if (librosaMostrar == "enCatalogo" || librosaMostrar == "pendientesDevolucion") {
                label.setIcon(imgIcon);
                    
            }else if (librosaMostrar == "descatalogados") {
                // Aplicar filtro
                BufferedImage filteredImage = ModificarObjetoFrame.aplicarFiltroGrises(imgIcon);
                // Establecer la imagen filtrada en el JLabel
                label.setIcon(new ImageIcon(filteredImage));
            }
            
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Cambio de cursor cuando se hoverea el label
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    // Cambio de cursor cuando se hoverea el label
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Codigo que se ejecuta al actualizar la imagen
                    new ModificarLibroFrame(libro, ModuloLibro.this, "actualizar");
                }
            }); 
            lineaLibro.add(label,gbcImagenLibro());
        } catch (IOException e) {
            e.printStackTrace();
            // Si no se puede cargar la imagen, mostrar solo el nombre del libro
            label.setText(libro.getTitulo() +" (Imagen no disponible)");
        }
    }
    //tituloLibro -> lineaLibro
    private void tituloLibro(Libro libro){
        JLabel label = new JLabel();  
        label.setText(libro.getTitulo());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 24));
        lineaLibro.add(label,gbcTituloLibro());
    }
    private void infoLibro(Libro libro){
        JLabel label = new JLabel();  
        label.setText("Editorial: " +libro.getEditorial() + "   Año de edicion: " +libro.getAnoEdicion());
        label.setFont(new Font("Serif", Font.ITALIC, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        lineaLibro.add(label,gbcInfoLibro());
    }      
    // botonBaja -> lineaLibro (Logica para borrar el libro y actualizar)
    private void botonBaja(Libro libro){
        BotonBonito botonBaja  = new BotonBonito("Dar de baja");
        lineaLibro.add(botonBaja ,gbcBotonBaja());
        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirmación de eliminación
                int respuesta = JOptionPane.showConfirmDialog(
                        null, 
                        "¿Estás seguro de que quieres eliminar el libro: " + libro.getTitulo() + "?", 
                        "Confirmar eliminación", 
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    // Si la respuesta es sí, eliminar el libro
                    try {
                        manager.descatalogarLibro(libro.getId()); // Llamada al método para eliminar el libro de la base de datos
                        listaLibros.remove(libro);    // Eliminar el libro de la lista local
                        actualizarPanel();            // Actualizar la interfaz gráfica
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al eliminar el libro: " + ex.getMessage());
                    }
                }
            }
        });
    }
    
    // BotonRecatalogar -> lineaLibro (Lógica para recuperar el libro y modificar su estado a catalogado)
    private void botonRecatalogar(Libro libro){
        BotonBonito botonRecatalogar  = new BotonBonito("Recatalogar");
        lineaLibro.add(botonRecatalogar ,gbcBotonBaja());
        botonRecatalogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirmación de eliminación
                int respuesta = JOptionPane.showConfirmDialog(
                        null, 
                        "¿Estás seguro de que quieres recuperar el libro: " + libro.getTitulo() + "?", 
                        "Confirmar recuperación", 
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    // Si la respuesta es sí, eliminar el libro
                    try {
                        manager.recatalogarLibro(libro.getId()); // Llamada al método para eliminar el libro de la base de datos
                        listaLibros.remove(libro);    // Eliminar el libro de la lista local
                        actualizarPanel();            // Actualizar la interfaz gráfica
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al recuperar el libro: " + ex.getMessage());
                    }
                }
            }
        });
}
    
    // Agregar un botón para gestionar el préstamo
    private void botonPrestamoLibro(Libro libro) {
        BotonBonito button = new BotonBonito("Gestionar Préstamo");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remover este módulo de su panel padre
                Container parent = ModuloLibro.this.getParent();
                if (parent instanceof PanelModulo) {
                    PanelModulo panelModulo = (PanelModulo) parent;
                    panelModulo.removeAll(); // Limpiar todos los componentes del panel

                    // Cargar el módulo de préstamo pasando el ID del libro
                    try {
                        ModuloPrestamo moduloPrestamo = new ModuloPrestamo(String.valueOf(libro.getId()));
                        panelModulo.agregarModuloAlPanel(moduloPrestamo); // Agregar el módulo de préstamo
                    } catch (SQLException e1) {
                        System.out.println("Error al añadir moduloPrestamo desde ModuloLibro");
                        e1.printStackTrace();
                    }
                    

                    panelModulo.revalidate(); // Refresca el diseño
                    panelModulo.repaint(); // Repaint the panel
                }
            }
        });
        lineaLibro.add(button, gbcBotonBaja());
    }
        
    

    // Método para añadir nuevo Libro -> ModificarLibroFrame(agregar)
    private JButton botonAlta(){
        BotonBonito botonAlta  = new BotonBonito("Nuevo Libro");
        botonAlta.setBorder(new EmptyBorder(0, 0, 0, 100));
        botonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "https://m.media-amazon.com/images/I/91XyNH557TL._SL1500_.jpg";
                Libro tempLibro = new Libro("titulo", 20,20,"editorialRandom",500,2000,url,false);
                System.out.println(tempLibro.getStock());
                listaLibros.add(tempLibro);
                new ModificarLibroFrame(tempLibro, ModuloLibro.this, "agregar");
            }
        });

        return botonAlta;
    }
    
    // Panel que contiene elementos para realizar la búsqueda
    @SuppressWarnings("unchecked")
    private void panelBuscaLibros(){
        tfBuscaLibros = new JTextField();tfBuscaLibros.setHorizontalAlignment(SwingConstants.CENTER);
        tfBuscaLibros.setToolTipText("Buscar libro por título o editorial");
        botonBuscaLibros = new BotonBonito("Buscar");
        comboBuscaLibros = new JComboBox<>();
        comboBuscaLibros.addItem("Titulo");
        comboBuscaLibros.addItem("Editorial");
        botonBuscaLibros.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoaBuscar = tfBuscaLibros.getText().toLowerCase();
                int opcionSeleccionada = comboBuscaLibros.getSelectedIndex();
                if (textoaBuscar!= null && opcionSeleccionada!=(-1)) {
                    if (opcionSeleccionada==0) {
                        buscadorDeLibros(textoaBuscar, opcionSeleccionada);
                    }else{buscadorDeLibros(textoaBuscar, opcionSeleccionada);}
                }
            }
            
        });        
        panelBuscaLibros = new JPanel();
        panelBuscaLibros.setLayout(new GridLayout(1, 3));
        panelBuscaLibros.add(comboBuscaLibros);
        panelBuscaLibros.add(tfBuscaLibros);
        panelBuscaLibros.add(botonBuscaLibros);
    }
    
    // Compara las strings del libro con las buscadas y añade los libros coincidentes a listaLibrosBuscados
    private void buscadorDeLibros(String búsqueda, int buscarPorEditorialoTitulo){
        // Crear una lista temporal para almacenar los resultados de la búsqueda
    
        if (buscarPorEditorialoTitulo == 0) { // Búsqueda por título
            for (Libro libro : listaLibros) {
                if (libro.getTitulo().toLowerCase().contains(búsqueda)) {
                    listaLibrosBuscados.add(libro);
                }  
            }
        } else { // Búsqueda por editorial
            for (Libro libro : listaLibros) {
                if (libro.getEditorial().toLowerCase().contains(búsqueda)) {
                    listaLibrosBuscados.add(libro);
                }  
            }
        }

        actualizarPanel(); // Refrescar el panel después de actualizar la lista
    }
    


    // Método para actualizar la interfaz gráfica después de eliminar un libro
    public void actualizarPanel() {
        panelLibros.removeAll();  // Elimina todos los componentes del panel de libros
        itemLibro();// Vuelve a agregar los libros (excepto el eliminado)
        listaLibrosBuscados.clear();              
        panelLibros.revalidate(); // Recalcula el layout
        panelLibros.repaint();    // Redibuja el panel
    }
    public LibroManager getManager() {
        return manager;
    }
    

    
}
