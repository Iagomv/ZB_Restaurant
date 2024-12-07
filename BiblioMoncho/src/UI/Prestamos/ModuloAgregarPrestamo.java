package UI.Prestamos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Modulo.*;
import UI.BotonBonito;
import UI.Libros.ModificarLibroFrame;
import UI.Socios.ModificarSocioFrame;
import Manager.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JOptionPane;
import org.jdatepicker.impl.JDatePickerImpl;


//*Clase que muestra un Frame con los libros y socios ofrece la posibilidad
    //* de seleccionar una instancia de cada uno para iniciar un nuevo préstamo 
    //* con fecha inicial y final */
public class ModuloAgregarPrestamo extends JPanel{
    
    private JPanel panelMostrarSocio;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBoxEligeSocio;
    private JPanel panelMostrarLibro;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBoxEligeLibro;
    private List<Socio> listaDeSocios = new ArrayList<>();
    private List<Libro> listaDeLibros = new ArrayList<>();
    private SocioManager socioManager= new SocioManager();
    private LibroManager libroManager = new LibroManager();
    private PrestamoManager prestamoManager = new PrestamoManager();
    private BotonBonito botonAgregarPrestamo;
    private JPanel panelInferior;
    private JLabel labelLibro;
    private JLabel labelSocio;
    private JDatePickerImpl datePickerFinPrestamo;
    private JPanel panelParaCalendario;
    private Font fuentePequeña = new Font("Serif", Font.ITALIC, 20);


    public ModuloAgregarPrestamo(){
        this.setBackground(new Color(245, 245, 245));
        this.setLayout(new BorderLayout()); // Establece el layout del panel principal
        this.add(labelSuperior(), BorderLayout.NORTH);
        obtenerSociosyLibros();
        llenarComboBoxes();
        mostrarPaneles();
        addBotonMasCalendario();
    }
    // Crear un panel intermedio que contenga ambos paneles de socio y libro
    public void mostrarPaneles() {
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(1, 2));  // Usamos GridLayout aquí para que los paneles estén lado a lado

        panelMostrarSocio();
        panelMostrarLibro();

        // Añadimos los paneles de Socio y Libro al panel intermedio
        panelCentro.add(panelMostrarSocio);
        panelCentro.add(panelMostrarLibro);

        // Añadimos el panel intermedio en el centro del layout principal
        this.add(panelCentro, BorderLayout.CENTER);
    }
    // gestionar el panelMostrarLibro
    public void panelMostrarLibro(){
        panelMostrarLibro = new JPanel();
        panelMostrarLibro.setLayout(new BorderLayout());
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout());  
        JLabel label = new JLabel("Libro: ");
        label.setFont(fuentePequeña);
        imagenLabelLibro((Libro) comboBoxEligeLibro.getSelectedItem()); 
        tempPanel.add(label); tempPanel.add(comboBoxEligeLibro);
        panelMostrarLibro.add(tempPanel, BorderLayout.NORTH);
        panelMostrarLibro.add(labelLibro,BorderLayout.CENTER);
        panelMostrarLibro.setBorder(new EmptyBorder(30, 60, 30, 60));
        this.add(panelMostrarLibro); 
    }
    //gestionar el panelMostrarSocio
    public void panelMostrarSocio(){
        panelMostrarSocio = new JPanel();
        panelMostrarSocio.setLayout(new BorderLayout());
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Socio: ");
        label.setFont(fuentePequeña);
        imagenLabelSocio((Socio) comboBoxEligeSocio.getSelectedItem()); 
        tempPanel.add(label); tempPanel.add(comboBoxEligeSocio);
        panelMostrarSocio.add(tempPanel,BorderLayout.NORTH);
        panelMostrarSocio.add(labelSocio,BorderLayout.CENTER);
        panelMostrarSocio.setBorder(new EmptyBorder(30, 60, 30, 60));
        this.add(panelMostrarSocio);  
    }
    //obtener y rellenar los valores de listaDeSocios y listaDeLibros
    public void obtenerSociosyLibros(){
        try {
            listaDeLibros = libroManager.obtenerLibrosNoDescatalogados();
        } catch (SQLException e) {
            System.out.println("Error al obtener los libros en ModuloAgregarPrestamo");
            e.printStackTrace();
        }
        try {
            listaDeSocios = socioManager.obtenerSociosActivos();
        } catch (SQLException e) {
            System.out.println("Error al obtener los socios en ModuloAgregarPrestamo");
            e.printStackTrace();
        }

    }
    // botón que agrega el préstamo al programa y BBDD
    public void botonAñadirPrestamo(){
        botonAgregarPrestamo = new BotonBonito("Confirmar");
        listenerBotonAgregarPrestamo();
    }
    private void listenerBotonAgregarPrestamo() {
        botonAgregarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.sql.Date fechaFinPrestamo = (java.sql.Date) datePickerFinPrestamo.getModel().getValue();
    
                // Obtener el libro seleccionado
                Libro esteLibro = (Libro) comboBoxEligeLibro.getSelectedItem();
                if (esteLibro == null) {
                    System.out.println("Error: No se ha seleccionado ningún libro.");
                    return;  // Salir si no se ha seleccionado un libro
                }
    
                int libroId = esteLibro.getId(); // Obtener el id del libro
    
                // Obtener el socio seleccionado
                Socio esteSocio = (Socio) comboBoxEligeSocio.getSelectedItem();
                if (esteSocio == null) {
                    System.out.println("Error: No se ha seleccionado ningún socio.");
                    return;  // Salir si no se ha seleccionado un socio
                }
    
                int socioId = esteSocio.getId();  // Obtener el id del socio
    
                // Crear el objeto Prestamo
                Prestamo prestamo = new Prestamo(libroId, socioId, Date.from(Instant.now()), fechaFinPrestamo);
                
                try {
                    prestamoManager.agregarPrestamo(prestamo);
                    // Mostrar mensaje de confirmación
                    JOptionPane.showMessageDialog(ModuloAgregarPrestamo.this, 
                        "El préstamo se ha agregado exitosamente.", 
                        "Préstamo Agregado", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e1) {
                    System.out.println("Error al agregar el préstamo");
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(ModuloAgregarPrestamo.this, 
                        "Error al agregar el préstamo: " + e1.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    //BorderLayout South Calendario y boton
    public void addBotonMasCalendario(){
        agregarCalendario();
        botonAñadirPrestamo();
        panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());
        panelInferior.add(panelParaCalendario);
        panelInferior.add(botonAgregarPrestamo);
        this.add(panelInferior,BorderLayout.SOUTH);
    }
    // Método para agregar el calendario en la parte inferior
    public void agregarCalendario() {
        datePickerFinPrestamo = CalendarioUtil.crearDatePicker(); // Utilizamos la clase CalendarioUtil
        
        // Panel para el selector de la fecha
        panelParaCalendario = new JPanel();
        panelParaCalendario.setLayout(new FlowLayout());
        JLabel label = new JLabel("Fecha fin de préstamo: ");
        label.setFont(fuentePequeña);
        panelParaCalendario.add(label);
        panelParaCalendario.add(datePickerFinPrestamo);

    }
    //Método que llena los comboboxes con los socios y libros
    //Iniciamos el combobox con un item seleccionado
    @SuppressWarnings("unchecked")
    public void llenarComboBoxes(){
        comboBoxEligeLibro = new JComboBox<Libro>();
        for (Libro libro : listaDeLibros) {
            comboBoxEligeLibro.addItem(libro);
        }
        comboBoxEligeLibro.setSelectedItem(comboBoxEligeLibro.getItemAt(0)); //Seleccion
        comboBoxEligeSocio = new JComboBox<Socio>();
        for (Socio socio : listaDeSocios) {
            comboBoxEligeSocio.addItem(socio);
        }
        comboBoxEligeSocio.setSelectedItem(comboBoxEligeSocio.getItemAt(0));
        actionListeners();
    }

    //Métodos para mostrar las imágenes seleccionadas en el combobox
    private void imagenLabelLibro(Libro libro) {
        try {
            //Eliminamos label anterior para refrescar la imagen
            if (labelLibro != null) {
                panelMostrarLibro.remove(labelLibro); 
            }
            
            labelLibro = new JLabel();
            @SuppressWarnings("deprecation")
            URL urlImagen = new URL(libro.getUrlImagen()); 
            ImageIcon imgIcon = new ImageIcon(urlImagen);  
            
            // Reescalado de la imagen
            Image img = imgIcon.getImage();
            Image resizedImage = img.getScaledInstance(250, 290, Image.SCALE_SMOOTH); 
            imgIcon = new ImageIcon(resizedImage);
            labelLibro.setIcon(imgIcon);
            labelLibro.setHorizontalAlignment(SwingConstants.CENTER);
            labelLibro.setBorder(new LineBorder(Color.BLACK, 1));
            // Añadir imagen al  libro
            panelMostrarLibro.add(labelLibro);
    
            // Cambio del cursor y listener para el click -> ModificarLibroFrame
            labelLibro.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    new ModificarLibroFrame(libro, ModuloAgregarPrestamo.this, "actualizar");
                }
            });
    
            // Refrescar
            panelMostrarLibro.revalidate();
            panelMostrarLibro.repaint();
    
        } catch (IOException e) {
            e.printStackTrace();
            labelLibro.setText(libro.getTitulo() + " (Imagen no disponible)");
        }
    }
    private void imagenLabelSocio(Socio socio) {
        try {
            if (labelSocio != null) {
                panelMostrarSocio.remove(labelSocio);
            }
            
            labelSocio = new JLabel();
            @SuppressWarnings("deprecation")
            URL urlImagen = new URL(socio.getUrlImagen());
            ImageIcon imgIcon = new ImageIcon(urlImagen); 
        
            Image img = imgIcon.getImage();
            Image resizedImage = img.getScaledInstance(250, 290, Image.SCALE_SMOOTH); 
            imgIcon = new ImageIcon(resizedImage);
            labelSocio.setIcon(imgIcon);
            labelSocio.setHorizontalAlignment(SwingConstants.CENTER);
            labelSocio.setBorder(new LineBorder(Color.BLACK, 1));

            panelMostrarSocio.add(labelSocio);

            labelSocio.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    new ModificarSocioFrame(socio, ModuloAgregarPrestamo.this, "actualizar");
                }
            });
            panelMostrarSocio.revalidate();
            panelMostrarSocio.repaint();
    
        } catch (IOException e) {
            e.printStackTrace();
            labelSocio.setText(socio.getNombre() + " (Imagen no disponible)");
        }
    }
    private JLabel labelSuperior(){
        JLabel label = new JLabel("NUEVO PRÉSTAMO");
        label.setFont(new Font("Arial", Font.BOLD, 26));
        label.setForeground(Color.DARK_GRAY);
        label.setBackground(new Color(245, 245, 245));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    
    public void actionListeners() {
        // ActionListener para comboBoxEligeLibro
        comboBoxEligeLibro.addActionListener(e -> {
            // Obtener el libro seleccionado
            Libro selectedLibro = (Libro) comboBoxEligeLibro.getSelectedItem();
            // Actualizar la imagen del libro correspondiente
            imagenLabelLibro(selectedLibro);
            // Redibujar el panel con el nuevo libro
            this.revalidate();
            this.repaint();
        });
    
        // ActionListener para comboBoxEligeSocio
        comboBoxEligeSocio.addActionListener(e -> {
            // Obtener el socio seleccionado
            Socio selectedSocio = (Socio) comboBoxEligeSocio.getSelectedItem();
            // Actualizar la imagen del socio correspondiente
            imagenLabelSocio(selectedSocio);
            // Redibujar el panel con el nuevo socio
            this.revalidate();
            this.repaint();
        });
    }
    


    @SuppressWarnings("rawtypes")
    public JComboBox getComboBoxEligeSocio() {
        return comboBoxEligeSocio;
    }
    @SuppressWarnings("rawtypes")
    public void setComboBoxEligeSocio(JComboBox comboBoxEligeSocio) {
        this.comboBoxEligeSocio = comboBoxEligeSocio;
    }
    @SuppressWarnings("rawtypes")
    public JComboBox getComboBoxEligeLibro() {
        return comboBoxEligeLibro;
    }
    @SuppressWarnings("rawtypes")
    public void setComboBoxEligeLibro(JComboBox comboBoxEligeLibro) {
        this.comboBoxEligeLibro = comboBoxEligeLibro;
    }
    public JPanel getPanelMostrarSocio() {
        return panelMostrarSocio;
    }
    public void setPanelMostrarSocio(JPanel panelMostrarSocio) {
        this.panelMostrarSocio = panelMostrarSocio;
    }
    public JPanel getPanelMostrarLibro() {
        return panelMostrarLibro;
    }
    public void setPanelMostrarLibro(JPanel panelMostrarLibro) {
        this.panelMostrarLibro = panelMostrarLibro;
    }
    public SocioManager getSocioManager() {
        return socioManager;}
    public LibroManager getLibroManager() {
        return libroManager;
    }
    // Método para actualizar la interfaz gráfica después de eliminar un libro
    public void actualizarPanelLibros() {
        panelMostrarLibro.removeAll();
        panelMostrarLibro.revalidate(); // Recalcula el layout
        panelMostrarLibro.repaint();    // Redibuja el panel
    }

    public void actualizarPanelSocios() {
        panelMostrarSocio.removeAll();
        panelMostrarSocio.revalidate(); // Recalcula el layout
        panelMostrarSocio.repaint();    // Redibuja el panel
    }
    
}


