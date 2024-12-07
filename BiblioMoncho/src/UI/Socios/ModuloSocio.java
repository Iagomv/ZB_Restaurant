package UI.Socios;

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
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import Manager.SocioManager;
import Modulo.Socio;
import UI.BotonBonito;
import UI.ModificarObjetoFrame;

public class ModuloSocio extends JPanel {
    private List<Socio> listaSocios = new ArrayList<>();
    List<Socio> listaSociosBuscados = new ArrayList<>();
    private JPanel panelSocios; // Panel que contendrá los libros y será envuelto por el JScrollPane
    private JPanel lineaSocio;
    private SocioManager manager;
    private JPanel panelBuscaSocios;
    private JTextField tfBuscaSocios;
    private BotonBonito botonBuscaSocios;
    private JComboBox<String> comboBuscaSocios;
    Boolean isBlackList= false;
    String activosOInactivos;
    JLabel label;

public ModuloSocio(String activosOInactivos) throws SQLException {
    backgroundyLayout();
    // Agrega el panel de búsqueda en la parte superior
    getPanelSuperioryPanelBuscaSocios();
    // Crear el manager y obtener la lista de socios
    manager = new SocioManager();
    this.activosOInactivos = activosOInactivos;
    listaSocios = (activosOInactivos == "activos") ? manager.obtenerSociosActivos() : manager.obtenerSociosInactivos();
    JScrollPane scrollPane = getScrollPane();
    scrollPane.setBorder(new EmptyBorder(10, 150, 10, 150));
    this.add(scrollPane, BorderLayout.CENTER);
}
    //*Constructor BLACKLIST */
public ModuloSocio(List<Socio> blackList) throws SQLException {
    backgroundyLayout();
    manager = new SocioManager();
    isBlackList= true;
    listaSocios = blackList;
    getPanelSuperioryPanelBuscaSocios();
    // Agrega el JScrollPane en la parte central
    JScrollPane scrollPane = getScrollPane();
    scrollPane.setBorder(new EmptyBorder(10, 150, 10, 150));
    this.add(scrollPane, BorderLayout.CENTER);
}

    // Panel de la parte Superior (BorderLayout North)
    private void getPanelSuperioryPanelBuscaSocios() {
        JPanel panelSuperior = new JPanel();
        if (!isBlackList) {
            panelSuperior.setBackground(new Color(245, 245, 245));
            panelSuperior.add(botonAlta());
            panelBuscaSocios();
            panelSuperior.add(panelBuscaSocios);
        }else{
            panelSuperior.setBackground(Color.DARK_GRAY);
            JLabel label = new JLabel("BLACKLIST");
            label.setFont(new Font("Arial",Font.PLAIN,26));
            label.setForeground(Color.LIGHT_GRAY);
            panelSuperior.add(label);
        }
        
        this.add(panelSuperior, BorderLayout.NORTH);
    }
    private void backgroundyLayout() {
        this.setBackground(new Color(245, 245, 245));
        this.setLayout(new BorderLayout()); // Establece el layout del panel principal
    }
    private JScrollPane getScrollPane() throws SQLException {
        // Crear el panel donde se agregarán las lineas de los libros
        panelSocios = new JPanel();
        panelSocios.setLayout(new BoxLayout(panelSocios, BoxLayout.Y_AXIS));
        
        // Llamar al método para cargar los libros en el panel de libros
        itemSocio();

        // Agregar el panel de libros dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelSocios);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desactivar scroll horizontal
        scrollPane.setBorder(null);
        return scrollPane;
    }
    
    //*ForEach Libro -> lineaModuloSocio -> panelSocios */
    public void itemSocio() {
        List<Socio> sociosAImprimir = (listaSociosBuscados.isEmpty()) ? listaSocios : listaSociosBuscados;
        for (Socio socio : sociosAImprimir) {
            JPanel panelTemp = lineaModuloSocio(socio);
            panelSocios.add(panelTemp); // Agregar cada linea generada al panel de libros
        }
        panelSocios.revalidate(); // Notifica al panel que ha habido un cambio
        panelSocios.repaint();    // Fuerza a que el panel se repinte
    }

    //GridBagConstraints para posicionamiento dentro de lineaSocio 
    private GridBagConstraints gbcImagenSocio() {
        GridBagConstraints gbclineaSocio = new GridBagConstraints();
        gbclineaSocio.gridx = 0;
        gbclineaSocio.gridy = 0;
        gbclineaSocio.weightx =    1;
        gbclineaSocio.weighty = 1; // Más peso para que ocupe la mayor parte del espacio
        gbclineaSocio.gridheight = 3;
        gbclineaSocio.fill = GridBagConstraints.VERTICAL;
        return gbclineaSocio;
    }
    private GridBagConstraints gbcNombreSocio() {
        GridBagConstraints gbclineaSocio = new GridBagConstraints();
        gbclineaSocio.gridx = 1;
        gbclineaSocio.gridy = 0;
        gbclineaSocio.weightx = 0.5;
        gbclineaSocio.weighty = 0.5; // Más peso para que ocupe la mayor parte del espacio
        gbclineaSocio.anchor = GridBagConstraints.CENTER;
        gbclineaSocio.fill = GridBagConstraints.BOTH;
        return gbclineaSocio;
    }
    private GridBagConstraints gbcInfoSocio() {
            GridBagConstraints gbclineaSocio = new GridBagConstraints();
            gbclineaSocio.gridx = 1;
            gbclineaSocio.gridy = 1;
            gbclineaSocio.weightx = 0.5;  //Titulo = 0.4
            gbclineaSocio.weighty = 0.5; // Titulo = 0.2
            gbclineaSocio.anchor = GridBagConstraints.CENTER;
            return gbclineaSocio;
        }
    private GridBagConstraints gbcBotonBaja() {
            GridBagConstraints gbclineaSocio = new GridBagConstraints();
            gbclineaSocio.gridx = 1;
            gbclineaSocio.gridy = 2;
            gbclineaSocio.weightx = 0.5;  //Titulo = 0.4
            gbclineaSocio.weighty = 0.5; // Titulo = 0.2
            gbclineaSocio.anchor = GridBagConstraints.CENTER;
            return gbclineaSocio;
        }
    
    //*Linea a mostrar para cada libro */
    private JPanel lineaModuloSocio(Socio socio) {
        lineaSocio = new JPanel();
        lineaSocio.setLayout(new GridBagLayout());
        lineaSocio.setBackground(new Color(245, 245, 245));
    
        // Agregar la imagen, nombre, info y boton para eliminar socio.
        imagenLabel(socio);
        nombreSocio(socio);
        if (isBlackList || activosOInactivos == "activos") {
            botonBaja(socio);
            
        }
        infoSocio(socio);
        
        // Añadir un borde alrededor del panel
        lineaSocio.setBorder(new EmptyBorder(25, 20, 25, 20));
        
        return lineaSocio;
    }
    
     //imagenLabel -> lineaSocio 
    private void imagenLabel(Socio socio) {
        try {
            label = new JLabel();
            @SuppressWarnings("deprecation")
            URL urlImagen = new URL(socio.getUrlImagen()); // URL de la imagen del libro
            ImageIcon imgIcon = new ImageIcon(urlImagen);  // Crear un icono de imagen
            // Redimensionar la imagen para que no sea demasiado grande
            Image img = imgIcon.getImage();
            Image resizedImage = img.getScaledInstance(220, 260, Image.SCALE_SMOOTH); // Ajustar tamaño a 150x150
            imgIcon = new ImageIcon(resizedImage);
            // Imagen a mostrar según si está en catálogo o no
            if (!isBlackList) {
                label.setIcon(imgIcon);
            }else{
                BufferedImage filteredImage = ModificarObjetoFrame.aplicarFiltroGrises(imgIcon);// Aplicar filtro
                label.setIcon(new ImageIcon(filteredImage));// Establecer la imagen filtrada en el JLabel
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
                    // Code to be executed when the image is clicked
                    System.out.println("Image clicked!");
                    new ModificarSocioFrame(socio, ModuloSocio.this,"actualizar");
                }
            }); 
            lineaSocio.add(label,gbcImagenSocio());
        } catch (IOException e) {
            e.printStackTrace();
            // Si no se puede cargar la imagen, mostrar solo el nombre del libro
            label.setText(socio.getNombre() +" (Imagen no disponible)");
        }
    }
    //nombreSocio -> lineaSocio
    private void nombreSocio(Socio socio){
        JLabel label = new JLabel();  
        label.setText(socio.getNombre()+" "+socio.getApellidos() +"\n");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 18));
        lineaSocio.add(label,gbcNombreSocio());
    } 
    //infoSocio -> lineaSocio  
    private void infoSocio(Socio socio){
        JPanel panelInfoCentro = new JPanel();
        JLabel label = new JLabel();  
        label.setText("Edad: "+ Integer.toString(socio.getEdad()) + "\t Teléfono: " +Integer.valueOf(socio.getTelefono()));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 16));
        JLabel contadorLabel = new JLabel();
        try {
            contadorLabel.setText("Libros en posesión: " + manager.contarLibrosPrestadosPorSocio(socio.getId()));
        } catch (SQLException e) {
            System.out.println("Error al obtener la informacion del socio");
            e.printStackTrace();
        }
        contadorLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        panelInfoCentro.setLayout(new GridLayout(2, 1));
        panelInfoCentro.add(label);
        panelInfoCentro.add(contadorLabel);
        
        lineaSocio.add(panelInfoCentro,gbcInfoSocio());
    }
    // botonBaja -> lineaSocio (Logica para borrar el libro y actualizar)
    private void botonBaja(Socio socio){
        BotonBonito botonBaja  = new BotonBonito("Dar de baja");
        lineaSocio.add(botonBaja ,gbcBotonBaja());
        botonBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirmación de eliminación
                int respuesta = JOptionPane.showConfirmDialog(
                        null, 
                        "¿Estás seguro de que quieres eliminar el libro: " + socio.getNombre() + "?", 
                        "Confirmar eliminación", 
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    // Si la respuesta es sí, eliminar el libro
                    try {
                        manager.eliminarSocio(socio); // Llamada al método para eliminar el libro de la base de datos
                        listaSocios.remove(socio);    // Eliminar el libro de la lista local
                        actualizarPanel();            // Actualizar la interfaz gráfica
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al eliminar el libro: " + ex.getMessage());
                    }
                }
            }
        });
        
    }
    private void panelBuscaSocios() {
        tfBuscaSocios = new JTextField();
        tfBuscaSocios.setHorizontalAlignment(SwingConstants.CENTER);
        tfBuscaSocios.setToolTipText("Buscar socio por nombre o apellido");
        
        botonBuscaSocios = new BotonBonito("Buscar");
        
        comboBuscaSocios = new JComboBox<>();
        comboBuscaSocios.addItem("Nombre");
        comboBuscaSocios.addItem("Apellido");
        
        botonBuscaSocios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoaBuscar = tfBuscaSocios.getText().toLowerCase(); //Texto introducido por el usuario buscar en minúsculas
                int opcionSeleccionada = comboBuscaSocios.getSelectedIndex(); // Búsqueda por título o editorial
                if (textoaBuscar!= null && opcionSeleccionada!=(-1)) {   // Si hay texto y opcion seleccionada
                    if (opcionSeleccionada==0) {
                        buscadorDeSocios(textoaBuscar, opcionSeleccionada); // al ser 0 busca por títulos
                    }else{buscadorDeSocios(textoaBuscar, opcionSeleccionada);// El resto de búsquedas será editorial mientras no haya más filtros
                    }
                    }  
            }
        });
        
        panelBuscaSocios = new JPanel();
        panelBuscaSocios.setLayout(new GridLayout(1, 3));
        panelBuscaSocios.add(comboBuscaSocios);
        panelBuscaSocios.add(tfBuscaSocios);
        panelBuscaSocios.add(botonBuscaSocios);
    }
    private JButton botonAlta(){
        BotonBonito botonAlta  = new BotonBonito("Nuevo Socio");
        botonAlta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socio tempLibro = new Socio("Iago", "Martinez",30,"Sada","699374860","https://imartinez.colexio-karbo.com/2024-25/iancito.jpg", true);
                listaSocios.add(tempLibro);
                new ModificarSocioFrame(tempLibro, ModuloSocio.this, "agregar");

            } 
        });

        return botonAlta;
    }
    
    
    // Compara las strings del libro con las buscadas y añade los libros coincidentes a listaLibrosBuscados
    private void buscadorDeSocios(String búsqueda, int buscarPorEditorialoTitulo){
        // Crear una lista temporal para almacenar los resultados de la búsqueda
    
        if (buscarPorEditorialoTitulo == 0) { // Búsqueda por título
            for (Socio socio : listaSocios) {
                if (socio.getNombre().toLowerCase().contains(búsqueda)) {
                    listaSociosBuscados.add(socio);
                }  
            }
        } else { // Búsqueda por editorial
            for (Socio socio : listaSocios) {
                if (socio.getApellidos().toLowerCase().contains(búsqueda)) {
                    listaSociosBuscados.add(socio);
                }  
            }
        }

        actualizarPanel(); // Refrescar el panel después de actualizar la lista
    }
    // Método para actualizar la interfaz gráfica después de eliminar un libro
    public void actualizarPanel() {
        panelSocios.removeAll();  // Elimina todos los componentes del panel de libros
        itemSocio();  // Vuelve a agregar los libros (excepto el eliminado)
        listaSociosBuscados.clear();   //Limpieza de lista temporal para poder realizar una búsqueda         
        panelSocios.revalidate(); // Recalcula el layout
        panelSocios.repaint();    // Redibuja el panel
    }
    public SocioManager getManager() {
        return manager;
    }
    
    
}
