package Manager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modulo.Libro;
import Modulo.Socio;

//*Esta clase se encarga de gestionar los métodos necesarios para la comunicación
//* Con la BBDD siendo isntanciada y llamada donde sea necesario */
public class SocioManager {
    private Connection connection;

    public SocioManager() {
        this.connection = Conexion.getInstance().getConnection();
    }

    //Método para agregar un nuevo socio a BBDD 
    public void agregarSocio(Socio socio) throws SQLException {
        String sql = "INSERT INTO biblioteca_moncho_socios (nombre, apellidos, edad, direccion, telefono, url_imagen_socio, Activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, socio.getNombre());
            stmt.setString(2, socio.getApellidos());
            stmt.setInt(3, socio.getEdad());
            stmt.setString(4, socio.getDireccion());
            stmt.setString(5, socio.getTelefono());
            stmt.setString(6, socio.getUrlImagen());
            stmt.setBoolean(7, true);
            stmt.executeUpdate();
    
            // Obtener el ID generado automáticamente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    socio.setId(generatedKeys.getInt(1));  // Establecer el ID generado en el objeto socio
                }
            }
        }
    }
    
    //Método para obtener socio por la id
    public Socio obtenerSocioPorId(int id) throws SQLException {
        Socio socio = null; // Inicializamos a null
        String sql = "SELECT * FROM biblioteca_moncho_socios WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Establecer el ID del socio en el PreparedStatement
            ResultSet rs = stmt.executeQuery();
            
            // Si hay un resultado, creamos el objeto Socio
            if (rs.next()) {
                socio = new Socio();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEdad(rs.getInt("edad"));
                socio.setDireccion(rs.getString("direccion"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setUrlImagen(rs.getString("url_imagen_socio"));
                socio.setActivo(rs.getBoolean("Activo"));
            }
        }
        return socio; // Devuelve el objeto Socio o null si no se encuentra
    }
    
    // Obtener todos los socios
    public List<Socio> obtenerSocios() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM biblioteca_moncho_socios";
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Socio socio = new Socio();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEdad(rs.getInt("edad"));
                socio.setDireccion(rs.getString("direccion"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setUrlImagen(rs.getString("url_imagen_socio"));
                socio.setActivo(rs.getBoolean("Activo"));
                socios.add(socio);
            }
        }
        return socios;
    }
    // Obtener todos los socios activos e inactivos
    public List<Socio> obtenerSociosActivos() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM biblioteca_moncho_socios WHERE Activo= 1";
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Socio socio = new Socio();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEdad(rs.getInt("edad"));
                socio.setDireccion(rs.getString("direccion"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setUrlImagen(rs.getString("url_imagen_socio"));
                socio.setActivo(rs.getBoolean("Activo"));
                socios.add(socio);
            }
        }
        return socios;
    }
    public List<Socio> obtenerSociosInactivos() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM biblioteca_moncho_socios WHERE Activo= 0";
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Socio socio = new Socio();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEdad(rs.getInt("edad"));
                socio.setDireccion(rs.getString("direccion"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setUrlImagen(rs.getString("url_imagen_socio"));
                socio.setActivo(rs.getBoolean("Activo"));
                socios.add(socio);
            }
        }
        return socios;
    }
    
    
    public void actualizarSocio(Socio socio) throws SQLException {
        String sql = "UPDATE biblioteca_moncho_socios SET nombre=?, apellidos=?, edad=?, direccion=?, telefono=?, url_imagen_socio=?, Activo=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, socio.getNombre());
            stmt.setString(2, socio.getApellidos());
            stmt.setInt(3, socio.getEdad());
            stmt.setString(4, socio.getDireccion());
            stmt.setString(5, socio.getTelefono());
            stmt.setString(6, socio.getUrlImagen());
            stmt.setBoolean(7, socio.getActivo());
            stmt.setInt(8, socio.getId());
            stmt.executeUpdate();
        }
    }

    //Obtener un libro por la id
    public Libro obtenerLibroPorId(int id) throws SQLException {
        Libro libro = null; // Inicializamos a null
        String sql = "SELECT * FROM biblioteca_moncho_libros WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Establecer el ID del libro en el PreparedStatement
            ResultSet rs = stmt.executeQuery();
            
            // Si hay un resultado, creamos el objeto Libro
            if (rs.next()) {
                libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
                libro.setStock(rs.getInt("stock"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setNumeroPaginas(rs.getInt("numero_paginas"));
                libro.setAnoEdicion(rs.getInt("ano_edicion"));
                libro.setUrlImagen(rs.getString("url_imagen_libro"));
            }
        }
        return libro; // Devuelve el objeto Libro o null si no se encuentra
    }
    
    // Obtener Socios por parte del apellido
    public List<Socio> obtenerSociosPorApellido(String apellido) throws SQLException {
        List<Socio> socios = new ArrayList<>(); // Inicializamos una lista de libros
        String nombreLowerCase = "%" + apellido.toLowerCase() + "%"; // Agregamos los comodines para la búsqueda
        String sql = "SELECT * FROM biblioteca_moncho_socios WHERE LOWER(apellidos) LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreLowerCase); // Establecer la editorial como parámetro
            ResultSet rs = stmt.executeQuery();
            
            // Iteramos sobre los resultados y creamos objetos Libro
            while (rs.next()) {
                Socio socio = new Socio();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEdad(rs.getInt("edad"));
                socio.setDireccion(rs.getString("direccion"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setUrlImagen(rs.getString("url_imagen_socio"));
                socios.add(socio); // Añadimos el libro a la lista
            }
        }
        return socios; // Devuelve la lista de libros o una lista vacía si no se encuentran
    }

    // Obtener Socios por parte del nombre
    public List<Socio> obtenerSociosPorNombre(String nombre) throws SQLException {
        List<Socio> socios = new ArrayList<>(); // Inicializamos una lista de libros
        String nombreLowerCase = "%" + nombre.toLowerCase() + "%"; // Agregamos los comodines para la búsqueda
        String sql = "SELECT * FROM biblioteca_moncho_socios WHERE LOWER(nombre) LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreLowerCase); // Establecer la editorial como parámetro
            ResultSet rs = stmt.executeQuery();
            
            // Iteramos sobre los resultados y creamos objetos Libro
            while (rs.next()) {
                Socio socio = new Socio();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEdad(rs.getInt("edad"));
                socio.setDireccion(rs.getString("direccion"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setUrlImagen(rs.getString("url_imagen_socio"));
                socios.add(socio); // Añadimos el libro a la lista
            }
        }
        return socios; // Devuelve la lista de libros o una lista vacía si no se encuentran
    }
    
    // Set socio como inactivo
    public void eliminarSocio(Socio socio) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "UPDATE biblioteca_moncho_socios SET Activo = ? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, false); // Cambiar el campo activo a False
            stmt.setInt(2, socio.getId()); 
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error SQL al marcar el socio como inactivo: " + e.getMessage());
            throw e;
        }
    }
    
    public int contarLibrosPrestadosPorSocio(int socioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM biblioteca_moncho_prestamos WHERE socio_id = ? AND Devuelto = 0";
        int count = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, socioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count; // Devuelve el número de libros prestados
    }
    // Prestamos sin devolver con fecha superada
    public List<Integer> obtenerLibrosSuperadosSinDevolver() throws SQLException {
        List<Integer> librosSuperados = new ArrayList<>();
        String sql = "SELECT libro_id FROM biblioteca_moncho_prestamos WHERE fecha_fin_prestamo < CURDATE AND Devuelto = 0";
    
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                librosSuperados.add(rs.getInt("libro_id")); // Suponiendo que la columna se llama libro_id
            }
        }
        return librosSuperados; // Devuelve la lista de IDs de libros
    }
    
    //Socios en la blacklist ordenados por libros que faltan por devolver
    public List<Socio> obtenerSociosConLibrosSinDevolver() throws SQLException {
        List<Socio> sociosConLibrosSinDevolver = new ArrayList<>();
        String sql = "SELECT s.*, COUNT(p.id) AS cantidad_prestamos " +
             "FROM biblioteca_moncho_socios s " +
             "JOIN biblioteca_moncho_prestamos p ON s.id = p.socio_id " +
             "WHERE p.fecha_fin < CURDATE() AND p.Devuelto = 0 " +
             "GROUP BY s.id " +
             "ORDER BY cantidad_prestamos DESC";


    
        System.out.println("Consulta SQL: " + sql); // Para ver la consulta que se ejecuta
    
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Socio socio = new Socio();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEdad(rs.getInt("edad"));
                socio.setDireccion(rs.getString("direccion"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setUrlImagen(rs.getString("url_imagen_socio"));
                sociosConLibrosSinDevolver.add(socio);
            }
        }
        System.out.println("Número de socios encontrados: " + sociosConLibrosSinDevolver.size());
        return sociosConLibrosSinDevolver; // Devuelve la lista de socios
    }
    
    

}
