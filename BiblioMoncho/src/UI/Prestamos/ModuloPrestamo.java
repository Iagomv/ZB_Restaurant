package UI.Prestamos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import Manager.LibroManager;
import Manager.PrestamoManager;
import Manager.SocioManager;
import Modulo.Prestamo;
import UI.BotonBonito;
import UI.Libros.ModificarLibroFrame;
import UI.Socios.ModificarSocioFrame;
import java.awt.event.MouseEvent;

public class ModuloPrestamo extends JPanel {
    private List<Prestamo> listaPrestamos = new ArrayList<>();
    private URL urlImagen;   
    private JPanel panelPrestamos; // Panel que contendrá los préstamos y será envuelto por el JScrollPane
    private PrestamoManager manager;
    private LibroManager libroManager;
    private SocioManager socioManager;
    @SuppressWarnings("unused")
    private Date fechaInicioPrestamos; 
    private Font fuentePequeña = new Font("Serif", Font.PLAIN, 20);
    private JPanel lineaPrestamo;
    private String texto;
    Boolean booleanBotonFinalizar = false;

    //* Constructor */
    public ModuloPrestamo() throws SQLException {
        fechaInicioPrestamos = new Date();
        // Crear el manager de préstamos y obtener la lista de préstamos
        manager = new PrestamoManager();
        listaPrestamos = manager.prestamosPendientes();
        booleanBotonFinalizar = true;
        this.setBackground(new Color(245, 245, 245));
        this.setLayout(new BorderLayout()); // Establece el layout del panel principal
        texto = "PRESTAMOS EN CURSO";
        getPanelSuperior();
        // Agrega el JScrollPane en la parte central
        JScrollPane scrollPane = getScrollPane();
        scrollPane.setBorder(new EmptyBorder(10, 150, 10, 150));
        this.add(scrollPane, BorderLayout.CENTER);
    }
    //* Constructor para Prestamos fuera de plazo*/
    public ModuloPrestamo(String morososorLibro) throws SQLException {
        fechaInicioPrestamos = new Date();
        // Crear el manager de préstamos y obtener la lista de préstamos
        manager = new PrestamoManager();
        //Igualamos la lista de préstamos según el String recibido
        //En caso de necesitar buscar libros pasamos el Id y parseamos
        this.listaPrestamos = (morososorLibro == "morosos") ? manager.obtenerPrestamosSuperadosSinDevolver()
                                                            : manager.obtenerPrestamosPorLibroId(Integer.parseInt(morososorLibro));
        booleanBotonFinalizar = true;
        this.setBackground(new Color(245, 245, 245));
        this.setLayout(new BorderLayout()); // Establece el layout del panel principal
        texto = "PRÉSTAMOS FUERA DE PLAZO";
        getPanelSuperior();
        // Agrega el JScrollPane en la parte central
        JScrollPane scrollPane = getScrollPane();
        scrollPane.setBorder(new EmptyBorder(10, 150, 10, 150));
        this.add(scrollPane, BorderLayout.CENTER);
    }
    //* Constructor para el histórico*/
    public ModuloPrestamo(int fecha) throws SQLException {
        fechaInicioPrestamos = new Date();
        // Crear el manager de préstamos y obtener la lista de préstamos
        manager = new PrestamoManager();
        listaPrestamos = manager.obtenerHistoricoPrestamos();
        this.setBackground(new Color(245, 245, 245));
        this.setLayout(new BorderLayout()); // Establece el layout del panel principal
        texto = "HISTÓRICO PRÉSTAMOS";
        getPanelSuperior();
        
        // Agrega el JScrollPane en la parte central
        JScrollPane scrollPane = getScrollPane();
        scrollPane.setBorder(new EmptyBorder(10, 150, 10, 150));
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void getPanelSuperior() {
        // Agrega el botón "Nuevo Préstamo" en la parte superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(245, 245, 245));
        panelSuperior.add(labelSuperior(texto));
        this.add(panelSuperior, BorderLayout.NORTH);
    }

    
    //Panel central se llama a itemLibro()
    private JScrollPane getScrollPane() throws SQLException {
        // Crear el panel donde se agregarán las líneas de los préstamos
        panelPrestamos = new JPanel();
        panelPrestamos.setLayout(new BoxLayout(panelPrestamos, BoxLayout.Y_AXIS));

        // Llamar al método para cargar los préstamos en el panel de préstamos
        itemPrestamo();

        // Agregar el panel de préstamos dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelPrestamos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Desactivar scroll horizontal
        scrollPane.setBorder(null);
        return scrollPane;
    }

    //*ForEach Préstamo -> lineaModuloPrestamo -> panelPrestamos */
    public void itemPrestamo() {
        for (Prestamo prestamo : listaPrestamos) {
            JPanel panelTemp = lineaModuloPrestamo(prestamo);
            System.out.println(prestamo.toString());
            panelPrestamos.add(panelTemp); // Agregar cada línea generada al panel de préstamos
        }
        panelPrestamos.revalidate(); // Notifica al panel que ha habido un cambio
        panelPrestamos.repaint();    // Fuerza a que el panel se repinte
    }

    //GridBagConstraints para posicionamiento dentro de lineaPrestamo 
    private GridBagConstraints gbcImagenSocio() {
        GridBagConstraints gbclineaPrestamo = new GridBagConstraints();
        gbclineaPrestamo.gridx = 0;
        gbclineaPrestamo.gridy = 0;
        gbclineaPrestamo.weightx = 1;
        gbclineaPrestamo.weighty = 1; // Más peso para que ocupe la mayor parte del espacio
        gbclineaPrestamo.gridheight = 3;
        gbclineaPrestamo.fill = GridBagConstraints.VERTICAL;
        return gbclineaPrestamo;
    }
    private GridBagConstraints gbcSocioPrestamo() {
        GridBagConstraints gbclineaPrestamo = new GridBagConstraints();
        gbclineaPrestamo.gridx = 1;
        gbclineaPrestamo.gridy = 0;
        gbclineaPrestamo.weightx = 0.5;
        gbclineaPrestamo.weighty = 0.5; // Más peso para que ocupe la mayor parte del espacio
        gbclineaPrestamo.anchor = GridBagConstraints.CENTER;
        gbclineaPrestamo.fill = GridBagConstraints.BOTH;
        return gbclineaPrestamo;
    }
    private GridBagConstraints gbcInfoPrestamo() {
        GridBagConstraints gbclineaPrestamo = new GridBagConstraints();
        gbclineaPrestamo.gridx = 1;
        gbclineaPrestamo.gridy = 1;
        gbclineaPrestamo.weightx = 0.5;  //Titulo = 0.4
        gbclineaPrestamo.weighty = 0.5; // Titulo = 0.2
        gbclineaPrestamo.anchor = GridBagConstraints.CENTER;
        return gbclineaPrestamo;
    }
    private GridBagConstraints gbcBotonFinalizar() {
        GridBagConstraints gbclineaPrestamo = new GridBagConstraints();
        gbclineaPrestamo.gridx = 1;
        gbclineaPrestamo.gridy = 2;
        gbclineaPrestamo.weightx = 0.5;  //Titulo = 0.4
        gbclineaPrestamo.weighty = 0.5; // Titulo = 0.2
        gbclineaPrestamo.anchor = GridBagConstraints.CENTER;
        return gbclineaPrestamo;
    }
    private GridBagConstraints gbcLibroPrestamo(){
        GridBagConstraints gbclineaPrestamo = new GridBagConstraints();
        gbclineaPrestamo.gridx = 2;
        gbclineaPrestamo.gridy = 0;
        gbclineaPrestamo.weightx = 1;  //Titulo = 0.4
        gbclineaPrestamo.weighty = 1; // Titulo = 0.2
        gbclineaPrestamo.gridheight = 3;
        gbclineaPrestamo.fill = GridBagConstraints.VERTICAL;
        return gbclineaPrestamo;
    }
    
    //*Linea a mostrar para cada préstamo */
    private JPanel lineaModuloPrestamo(Prestamo prestamo){
        lineaPrestamo = new JPanel();
        lineaPrestamo.setLayout(new GridBagLayout());
        lineaPrestamo.setBackground(new Color(245, 245, 245));

        // Agregar los detalles del préstamo
        imagenSocioPrestamo(prestamo);
        socioPrestamo(prestamo);
        infoPrestamo(prestamo);
        libroPrestamo(prestamo);
        botonFinalizar(prestamo);
        
        // Añadir un borde alrededor del panel
        lineaPrestamo.setBorder(new EmptyBorder(25, 20, 25, 20));
        
        return lineaPrestamo;
    }
    
    // Detalles del libro/s prestado -> lineaPrestamo 
    @SuppressWarnings("deprecation")
    private void libroPrestamo(Prestamo prestamo){
        
        try {
        JLabel label = new JLabel();
        if (prestamo.getListaLibros()==null) {
        urlImagen = new URL(prestamo.getLibro().getUrlImagen());
        }
        else{ urlImagen = new URL(prestamo.getListaLibros().getFirst().getUrlImagen());}
        ImageIcon imgIcon = new ImageIcon(urlImagen);
        //Redimensionar
        Image img = imgIcon.getImage();
        Image resizedImage = img.getScaledInstance(150,210, Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(resizedImage);
        label.setIcon(imgIcon);
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
                new ModificarLibroFrame(prestamo.getLibro(), ModuloPrestamo.this, "actualizar");

                }
            }); 
        lineaPrestamo.add(label, gbcLibroPrestamo());
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen en ModuloPrestamo");
        }
    }

    // Detalles del libro/s prestado -> lineaPrestamo 
    @SuppressWarnings("deprecation")
    private void imagenSocioPrestamo(Prestamo prestamo){
        
        try {
        JLabel label = new JLabel();
        urlImagen = new URL(prestamo.getSocio().getUrlImagen());
        ImageIcon imgIcon = new ImageIcon(urlImagen);
        //Redimensionar
        Image img = imgIcon.getImage();
        Image resizedImage = img.getScaledInstance(150,210, Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(resizedImage);
        label.setIcon(imgIcon);
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
                    new ModificarSocioFrame(prestamo.getSocio(), ModuloPrestamo.this, "actualizar");
                }
            }); 
        lineaPrestamo.add(label, gbcImagenSocio());
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen en ModuloPrestamo");
        }
    }
    // Detalles del socio -> lineaPrestamo
    private void socioPrestamo(Prestamo prestamo){
        JLabel label = new JLabel();  
        label.setText("Socio: " + prestamo.getSocio().getNombre() + "    " +"id: "+prestamo.getSocio().getId());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(fuentePequeña);
        lineaPrestamo.add(label, gbcSocioPrestamo());
    }

    // Información adicional sobre el préstamo
    private void infoPrestamo(Prestamo prestamo){
        JPanel panelInfo = new JPanel();
        JLabel labelInicio = new JLabel();  
        labelInicio.setText("Prestado el: " + prestamo.getFechaInicio());
        labelInicio.setHorizontalAlignment(SwingConstants.CENTER);
        labelInicio.setFont(fuentePequeña);
        JLabel labelFinal = new JLabel();  
        labelFinal.setText("Fecha devolución: " + prestamo.getFechaFin());
        labelFinal.setHorizontalAlignment(SwingConstants.CENTER);
        labelFinal.setFont(fuentePequeña);
        panelInfo.setLayout(new GridLayout(2 , 1));
        panelInfo.add(labelInicio);
        panelInfo.add(labelFinal);
        lineaPrestamo.add(panelInfo, gbcInfoPrestamo());
    }

    // Botón para finalizar el préstamo
    private void botonFinalizar(Prestamo prestamo){
        BotonBonito botonFinalizar  = new BotonBonito("Finalizar Préstamo");
        if (booleanBotonFinalizar) {
            lineaPrestamo.add(botonFinalizar, gbcBotonFinalizar());
            botonFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirmación de finalización
                int respuesta = JOptionPane.showConfirmDialog(
                        null, 
                        "¿Estás seguro de que quieres finalizar el préstamo del libro: " + prestamo.getLibro().toString() + " al usuario " + prestamo.getSocio()+"?", 
                        "Confirmar finalización", 
                        JOptionPane.YES_NO_OPTION);
        
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        prestamo.setFechaFin(Date.from(Instant.now())); // Establecer fecha de fin
                        manager.finalizarPrestamo(prestamo); // Finalizar el préstamo en la base de datos
                        listaPrestamos.remove(prestamo); // Remover de la lista local
                        actualizarPanel(); // Actualizar la interfaz gráfica
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al finalizar el préstamo: " + ex.getMessage());
                    }
                }
            }
        });
        }
    }

    private JLabel labelSuperior(String texto){
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(Color.DARK_GRAY);
        label.setBackground(new Color(245, 245, 245));
        return label;
    }

    // Método para actualizar el panel después de modificar préstamos
    public void actualizarPanel() throws SQLException {
        panelPrestamos.removeAll(); // Limpiar el panel actual
        if (texto == "PRESTAMOS EN CURSO") {
            listaPrestamos = manager.prestamosPendientes();
        }else if (texto == "PRÉSTAMOS FUERA DE PLAZO") {
            listaPrestamos = manager.obtenerPrestamosSuperadosSinDevolver();
        }else if (texto == "HISTÓRICO PRÉSTAMOS") {
            listaPrestamos = manager.obtenerHistoricoPrestamos();
        }
        itemPrestamo(); // Cargar los préstamos en el panel
    }
    public PrestamoManager getManager() {
        return manager;
    }
    public void setListaPrestamos(List<Prestamo> listaPrestamos) {
        this.listaPrestamos = listaPrestamos;
    }
    public LibroManager getLibroManager() {
        return libroManager;
    }
    public SocioManager getSocioManager() {
        return socioManager;
    }

}




// Método para añadir nuevo préstamo 
    //*Eliminado por nueva pestaña que se encarga de generar nuevos préstamos */
  //private JButton botonAgregarPrestamo() {
  //    BotonBonito botonAgregarPrestamo  = new BotonBonito("Nuevo Préstamo");
  //    botonAgregarPrestamo.addActionListener(new ActionListener() {
  //        @Override
  //        public void actionPerformed(ActionEvent e) {
  //            Date fecha = (Date) new Calendar.Builder().setDate(2000, 0, 1).build().getTime();
  //            Prestamo prestamoTemp = new Prestamo(100,100,100,fecha,java.util.Date.from(Instant.now()));
  //            new ModificarPrestamoFrame(prestamoTemp,ModuloPrestamo.this, "agregar");
  //        }
  //    });

  //    return botonAgregarPrestamo;
  //}


