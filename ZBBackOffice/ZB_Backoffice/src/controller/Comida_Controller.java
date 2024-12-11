package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Comida;
import DB.Conexion;

public class Comida_Controller {
    private Connection connection;
    private String nombreTabla = "Zero_Bugs_Comidas";

    public Comida_Controller() {
        this.connection = Conexion.getInstance().getConnection();
    }

    // Auxiliar para preparar sentencias pasando parámetros en un array
    private PreparedStatement consultaPreparada(String sql, Comida comida) throws SQLException {
        String[] parametros = comida.getParametrosComida();
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }

        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < parametros.length; i++) {
            stmt.setString(i + 1, parametros[i]);
        }
        return stmt;
    }

    // Método para obtener el ID generado automáticamente
    private void obtenerId(Comida comida, PreparedStatement stmt) throws SQLException {
        // Obtener el ID generado automáticamente
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                comida.setId(generatedKeys.getInt(1)); // Establecer el ID generado en el objeto comida
            }
        }
    }

    // Metodo para agregar una comida
    public void agregarComidas(Comida comida) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "INSERT INTO " + nombreTabla + " (nombre, tipo, precio, stock) VALUES (?, ?, ?, ?)";
        // Utilizamos PreparedStatement y ejecutamos la consulta
        try (PreparedStatement stmt = consultaPreparada(sql, comida)) {
            stmt.executeUpdate();

            obtenerId(comida, stmt);
        }
    }

    public List<Comida> obtenerComidas() throws SQLException {
        List<Comida> comidas = new ArrayList<>();
        String sql = "SELECT * FROM " + nombreTabla;

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_comida");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                String precio = rs.getString("precio");
                String stock = rs.getString("stock");

                comidas.add(new Comida(id, nombre, tipo, precio, stock));
            }
        }
        return comidas;
    }

    public Comida obtenerComidaPorId(int id) throws SQLException {
        Comida comida = null;
        String sql = "SELECT * FROM " + nombreTabla + " WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String tipo = rs.getString("tipo");
                    String precio = rs.getString("precio");
                    String stock = rs.getString("stock");
                    comida = new Comida(id, nombre, tipo, precio, stock);
                }
            }
        }
        return comida;
    }

    public void actualizarComida(int id, Comida comida) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "UPDATE " + nombreTabla + " SET nombre = ?, tipo = ?, precio = ?, stock = ? WHERE id_comida = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, comida.getNombre());
            stmt.setString(2, comida.getTipo());
            stmt.setString(3, comida.getPrecio());
            stmt.setString(4, comida.getStock());
            stmt.setInt(5, id); // Especificamos el ID para saber qué actualizar
            stmt.executeUpdate();
        }
    }

    public void eliminarComida(int id) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "DELETE FROM " + nombreTabla + " WHERE id_bebida = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /*
     * //Obtener un libro por la id
     * public Libro obtenerLibroPorId(int id) throws SQLException {
     * Libro libro = null; // Inicializamos a null
     * String sql = "SELECT * FROM biblioteca_moncho_libros WHERE id=?";
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setInt(1, id); // Establecer el ID del libro en el PreparedStatement
     * ResultSet rs = stmt.executeQuery();
     * 
     * // Si hay un resultado, creamos el objeto Libro
     * if (rs.next()) {
     * libro = new Libro();
     * libro.setId(rs.getInt("id"));
     * libro.setTitulo(rs.getString("titulo"));
     * libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
     * libro.setStock(rs.getInt("stock"));
     * libro.setEditorial(rs.getString("editorial"));
     * libro.setNumeroPaginas(rs.getInt("numero_paginas"));
     * libro.setAnoEdicion(rs.getInt("ano_edicion"));
     * libro.setUrlImagen(rs.getString("url_imagen_libro"));
     * libro.setDescatalogado(rs.getBoolean("Descatalogado"));
     * }
     * }
     * return libro; // Devuelve el objeto Libro o null si no se encuentra
     * }
     * 
     * // Obtener libros por parte del título
     * public List<Libro> obtenerLibrosPorTitulo(String titulo) throws SQLException
     * {
     * List<Libro> libros = new ArrayList<>(); // Inicializamos una lista de libros
     * String tituloLowerCase = "%" + titulo.toLowerCase() + "%"; // Agregamos los
     * comodines para la búsqueda
     * String sql =
     * "SELECT * FROM biblioteca_moncho_libros WHERE LOWER(titulo) LIKE ?";
     * 
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setString(1, tituloLowerCase); // Establecer el título como parámetro
     * ResultSet rs = stmt.executeQuery();
     * 
     * // Iteramos sobre los resultados y creamos objetos Libro
     * while (rs.next()) {
     * Libro libro = new Libro();
     * libro.setId(rs.getInt("id"));
     * libro.setTitulo(rs.getString("titulo"));
     * libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
     * libro.setStock(rs.getInt("stock"));
     * libro.setEditorial(rs.getString("editorial"));
     * libro.setNumeroPaginas(rs.getInt("numero_paginas"));
     * libro.setAnoEdicion(rs.getInt("ano_edicion"));
     * libro.setUrlImagen(rs.getString("url_imagen_libro"));
     * libros.add(libro); // Añadimos el libro a la lista
     * }
     * }
     * return libros; // Devuelve la lista de libros o una lista vacía si no se
     * encuentran
     * }
     * 
     * // Obtener libros por parte de la editorial
     * public List<Libro> obtenerLibrosPorEditorial(String editorial) throws
     * SQLException {
     * List<Libro> libros = new ArrayList<>(); // Inicializamos una lista de libros
     * String editorialLowerCase = "%" + editorial.toLowerCase() + "%"; // Agregamos
     * los comodines para la búsqueda
     * String sql =
     * "SELECT * FROM biblioteca_moncho_libros WHERE LOWER(editorial) LIKE ?";
     * 
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setString(1, editorialLowerCase); // Establecer la editorial como
     * parámetro
     * ResultSet rs = stmt.executeQuery();
     * 
     * // Iteramos sobre los resultados y creamos objetos Libro
     * while (rs.next()) {
     * Libro libro = new Libro();
     * libro.setId(rs.getInt("id"));
     * libro.setTitulo(rs.getString("titulo"));
     * libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
     * libro.setStock(rs.getInt("stock"));
     * libro.setEditorial(rs.getString("editorial"));
     * libro.setNumeroPaginas(rs.getInt("numero_paginas"));
     * libro.setAnoEdicion(rs.getInt("ano_edicion"));
     * libro.setUrlImagen(rs.getString("url_imagen_libro"));
     * libros.add(libro); // Añadimos el libro a la lista
     * }
     * }
     * return libros; // Devuelve la lista de libros o una lista vacía si no se
     * encuentran
     * }
     * 
     * // Todos los libros
     * public List<Libro> obtenerLibros() throws SQLException {
     * if (connection == null) {
     * throw new SQLException("No se pudo conectar a la base de datos.");
     * }
     * 
     * List<Libro> libros = new ArrayList<>();
     * String sql = "SELECT * FROM biblioteca_moncho_libros";
     * try (Statement stmt = connection.createStatement();
     * ResultSet rs = stmt.executeQuery(sql)) {
     * while (rs.next()) {
     * Libro libro = new Libro();
     * libro.setId(rs.getInt("id"));
     * libro.setTitulo(rs.getString("titulo"));
     * libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
     * libro.setEditorial(rs.getString("editorial"));
     * libro.setNumeroPaginas(rs.getInt("numero_paginas"));
     * libro.setAnoEdicion(rs.getInt("ano_edicion"));
     * libro.setUrlImagen(rs.getString("url_imagen_libro"));
     * libros.add(libro);
     * }
     * }
     * return libros;
     * }
     * // Todos los libros Descatalogados
     * public List<Libro> obtenerLibrosDescatalogados() throws SQLException {
     * if (connection == null) {
     * throw new SQLException("No se pudo conectar a la base de datos.");
     * }
     * 
     * List<Libro> libros = new ArrayList<>();
     * String sql =
     * "SELECT * FROM biblioteca_moncho_libros WHERE Descatalogado = 1";
     * try (Statement stmt = connection.createStatement();
     * ResultSet rs = stmt.executeQuery(sql)) {
     * while (rs.next()) {
     * Libro libro = new Libro();
     * libro.setId(rs.getInt("id"));
     * libro.setTitulo(rs.getString("titulo"));
     * libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
     * libro.setStock(rs.getInt("Stock"));
     * libro.setEditorial(rs.getString("editorial"));
     * libro.setNumeroPaginas(rs.getInt("numero_paginas"));
     * libro.setAnoEdicion(rs.getInt("ano_edicion"));
     * libro.setUrlImagen(rs.getString("url_imagen_libro"));
     * libro.setDescatalogado(rs.getBoolean("Descatalogado"));
     * libros.add(libro);
     * }
     * }
     * return libros;
     * }
     * // Todos los libros No Descatalogados
     * public List<Libro> obtenerLibrosNoDescatalogados() throws SQLException {
     * if (connection == null) {
     * throw new SQLException("No se pudo conectar a la base de datos.");
     * }
     * 
     * List<Libro> libros = new ArrayList<>();
     * String sql =
     * "SELECT * FROM biblioteca_moncho_libros WHERE Descatalogado = 0";
     * try (Statement stmt = connection.createStatement();
     * ResultSet rs = stmt.executeQuery(sql)) {
     * while (rs.next()) {
     * Libro libro = new Libro();
     * libro.setId(rs.getInt("id"));
     * libro.setTitulo(rs.getString("titulo"));
     * libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
     * libro.setStock(rs.getInt("Stock"));
     * libro.setEditorial(rs.getString("editorial"));
     * libro.setNumeroPaginas(rs.getInt("numero_paginas"));
     * libro.setAnoEdicion(rs.getInt("ano_edicion"));
     * libro.setUrlImagen(rs.getString("url_imagen_libro"));
     * libro.setDescatalogado(rs.getBoolean("Descatalogado"));
     * libros.add(libro);
     * }
     * }
     * return libros;
     * }
     * 
     * // Todos los libros pendientes de devolución
     * public List<Libro> obtenerLibrosPendientesDevolución() throws SQLException {
     * if (connection == null) {
     * throw new SQLException("No se pudo conectar a la base de datos.");
     * }
     * 
     * List<Libro> libros = new ArrayList<>();
     * String sql = "SELECT l.* " +
     * "FROM biblioteca_moncho_libros l " +
     * "JOIN biblioteca_moncho_prestamos p ON l.id = p.libro_id " +
     * "WHERE p.Devuelto = 0"; // Asegúrate de que esta columna exista
     * 
     * try (Statement stmt = connection.createStatement();
     * ResultSet rs = stmt.executeQuery(sql)) {
     * while (rs.next()) {
     * Libro libro = new Libro();
     * libro.setId(rs.getInt("id"));
     * libro.setTitulo(rs.getString("titulo"));
     * libro.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
     * libro.setStock(rs.getInt("Stock"));
     * libro.setEditorial(rs.getString("editorial"));
     * libro.setNumeroPaginas(rs.getInt("numero_paginas"));
     * libro.setAnoEdicion(rs.getInt("ano_edicion"));
     * libro.setUrlImagen(rs.getString("url_imagen_libro"));
     * libro.setDescatalogado(rs.getBoolean("Descatalogado"));
     * libros.add(libro);
     * }
     * }
     * return libros;
     * }
     * 
     * // Update Libros
     * public void actualizarLibro(Libro libro) throws SQLException {
     * if (connection == null) {
     * throw new SQLException("No se pudo conectar a la base de datos.");
     * }
     * 
     * String sql =
     * "UPDATE biblioteca_moncho_libros SET titulo=?, numero_ejemplares=?,Stock=?, editorial=?, numero_paginas=?, ano_edicion=?, url_imagen_libro=?, Descatalogado=? WHERE id=?"
     * ;
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setString(1, libro.getTitulo());
     * stmt.setInt(2, libro.getNumeroEjemplares());
     * stmt.setInt(3, libro.getStock());
     * 
     * stmt.setString(4, libro.getEditorial());
     * stmt.setInt(5, libro.getNumeroPaginas());
     * stmt.setInt(6, libro.getAnoEdicion());
     * String urlImagen = libro.getUrlImagen();
     * if (urlImagen == null || urlImagen.isEmpty() ||
     * (!urlImagen.startsWith("http://") && !urlImagen.startsWith("https://"))) {
     * throw new MalformedURLException("La URL de la imagen no es válida: " +
     * urlImagen);
     * }
     * stmt.setString(7, urlImagen);
     * stmt.setBoolean(8, libro.getDescatalogado());
     * stmt.setInt(9, libro.getId());
     * stmt.executeUpdate();
     * } catch (SQLException e) {
     * // Manejar la SQLException
     * System.err.println("Error SQL: " + e.getMessage());
     * throw e; // Vuelve a lanzar la excepción si es necesario
     * } catch (MalformedURLException e) {
     * // Manejar la MalformedURLException
     * System.err.println("URL inválida: " + e.getMessage());
     * throw new SQLException("URL de imagen no válida: " + e.getMessage());
     * } catch (Exception e) {
     * // Manejar otras excepciones
     * System.err.println("Error inesperado: " + e.getMessage());
     * }
     * }
     * 
     * //Recatalogar libro descatalogado
     * public void recatalogarLibro(int id) throws SQLException {
     * if (connection == null) {
     * throw new SQLException("No se pudo conectar a la base de datos.");
     * }
     * 
     * String sql =
     * "UPDATE biblioteca_moncho_libros SET Descatalogado = ? WHERE id = ?";
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setBoolean(1, false); // Cambiar el campo Descatalogado a True
     * stmt.setInt(2, id); // Indicar el ID del libro a actualizar
     * stmt.executeUpdate();
     * } catch (SQLException e) {
     * System.err.println("Error SQL al marcar el libro como descatalogado: " +
     * e.getMessage());
     * throw e;
     * }
     * }
     * 
     * // DescatalogarLibro
     * public void descatalogarLibro(int id) throws SQLException {
     * if (connection == null) {
     * throw new SQLException("No se pudo conectar a la base de datos.");
     * }
     * 
     * String sql =
     * "UPDATE biblioteca_moncho_libros SET Descatalogado = ? WHERE id = ?";
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setBoolean(1, true); // Cambiar el campo Descatalogado a True
     * stmt.setInt(2, id); // Indicar el ID del libro a actualizar
     * stmt.executeUpdate();
     * } catch (SQLException e) {
     * System.err.println("Error SQL al marcar el libro como descatalogado: " +
     * e.getMessage());
     * throw e;
     * }
     * }
     */
}
