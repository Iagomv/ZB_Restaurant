package Manager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modulo.Prestamo;
import Modulo.Libro;
import Modulo.Socio;


//*Esta clase se encarga de gestionar los métodos necesarios para la comunicación
//* Con la BBDD siendo isntanciada y llamada donde sea necesario */
public class PrestamoManager {
    private Connection connection;
    private SocioManager socioManager = new SocioManager();
    private LibroManager libroManager = new LibroManager();

    public PrestamoManager() {
        this.connection = Conexion.getInstance().getConnection();
    }

    // Método para agregar un préstamo
    public void agregarPrestamo(Prestamo prestamo) throws SQLException {
        // Asegurarse de que el socio y los libros existan
        if (!socioExiste(prestamo.getSocio().getId())) {
            throw new IllegalArgumentException("El socio no existe.");
        }
    
        // Validar el libro
        if (prestamo.getLibro() == null || prestamo.getLibro().getId() == 0) {
            throw new IllegalArgumentException("El libro no está disponible.");
        }
    
        // Validar las fechas
        if (prestamo.getFechaInicio() == null || prestamo.getFechaFin() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin deben ser válidas.");
        }
    
        // Asegurarse de que la lista de libros no sea nula
        if (prestamo.getListaLibros() == null) {
            prestamo.setListaLibros(new ArrayList<>()); // Inicializar si es null
        }
    
        String sql = "INSERT INTO biblioteca_moncho_prestamos (libro_id, socio_id, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, prestamo.getLibro().getId()); // ID del libro
            stmt.setInt(2, prestamo.getSocio().getId()); // ID del socio
            stmt.setDate(3, new Date(prestamo.getFechaInicio().getTime())); // Fecha inicio
            stmt.setDate(4, new Date(prestamo.getFechaFin().getTime())); // Fecha fin
            stmt.executeUpdate();

        }
    }

    // Métodos para verificar la existencia de libros y socios
    @SuppressWarnings("unused")
    private boolean libroExiste(int libroId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM biblioteca_moncho_libros WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, libroId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    private boolean socioExiste(int socioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM biblioteca_moncho_socios WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, socioId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    // Método para obtener todos los préstamos
    public List<Prestamo> obtenerPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT * FROM biblioteca_moncho_prestamos";
        try (Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                List<Libro> listaLibros = new ArrayList<>();

                Prestamo prestamo = new Prestamo();
                prestamo.setId(rs.getInt("id"));


                // Recuperar los libros asociados al préstamo                
                listaLibros.add(libroManager.obtenerLibroPorId(rs.getInt("libro_id")));
                prestamo.setListaLibros(listaLibros);

                // Recuperar el socio asociado
                Socio socio = socioManager.obtenerSocioPorId(rs.getInt("socio_id"));
                prestamo.setSocio(socio);

                prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
                prestamo.setFechaFin(rs.getDate("fecha_fin"));
                
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }


    // Método cuando devuelven el libro
public void finalizarPrestamo(Prestamo prestamo) throws SQLException {
    String sql = "UPDATE biblioteca_moncho_prestamos SET fecha_fin=?, Devuelto=1 WHERE id=?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setDate(1, new Date(prestamo.getFechaFin().getTime())); // Establecer la fecha de fin
        stmt.setInt(2, prestamo.getId()); // Establecer el ID del préstamo

        // Ejecutar la actualización y obtener el número de filas afectadas
        int filasAfectadas = stmt.executeUpdate(); 
        System.out.println("Filas afectadas: " + filasAfectadas);

        // Verificar si se modificó exactamente una fila
        if (filasAfectadas == 1) {
            System.out.println("Préstamo finalizado correctamente.");
        } else {
            System.out.println("No se encontró el préstamo con el ID proporcionado.");
        }
    }
}


    // Método para actualizar un préstamo
    public void actualizarPrestamo(Prestamo prestamo) throws SQLException {
        String sql = "UPDATE biblioteca_moncho_prestamos SET socio_id=?, fecha_inicio=?, fecha_fin=?, Devuelto=?, WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getSocio().getId());
            stmt.setDate(2, new Date(prestamo.getFechaInicio().getTime()));
            stmt.setDate(3, new Date(prestamo.getFechaFin().getTime()));
            stmt.setBoolean(4, prestamo.getDevuelto());
            stmt.setInt(5, prestamo.getId());
            stmt.executeUpdate();
        }
    }
    
    //Obtener préstamos de morosos
    public List<Prestamo> obtenerPrestamosSuperadosSinDevolver() throws SQLException {
        List<Prestamo> prestamosSuperados = new ArrayList<>();
        String sql = "SELECT * FROM biblioteca_moncho_prestamos WHERE fecha_fin < CURDATE() AND Devuelto = 0";
    
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(rs.getInt("id")); // Suponiendo que hay una columna 'id' para el préstamo
                prestamo.setLibro(libroManager.obtenerLibroPorId(rs.getInt("libro_id"))); 
                prestamo.setSocio(socioManager.obtenerSocioPorId(rs.getInt("socio_id"))); 
                prestamo.setFechaInicio(rs.getDate("fecha_inicio")); // Asegúrate de que el tipo sea correcto
                prestamo.setFechaFin(rs.getDate("fecha_fin")); // Asegúrate de que el tipo sea correcto
                prestamo.setDevuelto(rs.getBoolean("Devuelto")); // Asegúrate de que el tipo sea correcto
    
                prestamosSuperados.add(prestamo);
            }
        }
        return prestamosSuperados; // Devuelve la lista de préstamos
    }

    //Obtener Histórico de préstamos
    public List<Prestamo> obtenerHistoricoPrestamos() throws SQLException {
        List<Prestamo> prestamosSuperados = new ArrayList<>();
        String sql = "SELECT * FROM biblioteca_moncho_prestamos WHERE fecha_fin <= CURDATE() AND Devuelto = 1 ORDER BY fecha_fin";
    
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(rs.getInt("id")); // Suponiendo que hay una columna 'id' para el préstamo
                prestamo.setLibro(libroManager.obtenerLibroPorId(rs.getInt("libro_id"))); // Cambia 'libro_id' según tu esquema
                prestamo.setSocio(socioManager.obtenerSocioPorId(rs.getInt("socio_id"))); // Cambia 'socio_id' según tu esquema
                prestamo.setFechaInicio(rs.getDate("fecha_inicio")); // Asegúrate de que el tipo sea correcto
                prestamo.setFechaFin(rs.getDate("fecha_fin")); // Asegúrate de que el tipo sea correcto
                prestamo.setDevuelto(rs.getBoolean("Devuelto")); // Asegúrate de que el tipo sea correcto
                System.out.println(prestamo);
                prestamosSuperados.add(prestamo);
            }
        }
        return prestamosSuperados; // Devuelve la lista de préstamos
    }
    //Obtener Histórico de préstamos
    public List<Prestamo> prestamosPendientes() throws SQLException {
        List<Prestamo> prestamosSuperados = new ArrayList<>();
        String sql = "SELECT * FROM biblioteca_moncho_prestamos WHERE fecha_fin >= CURDATE() AND Devuelto = 0 ORDER BY fecha_fin";
    
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(rs.getInt("id")); // Suponiendo que hay una columna 'id' para el préstamo
                prestamo.setLibro(libroManager.obtenerLibroPorId(rs.getInt("libro_id"))); // Cambia 'libro_id' según tu esquema
                prestamo.setSocio(socioManager.obtenerSocioPorId(rs.getInt("socio_id"))); // Cambia 'socio_id' según tu esquema
                prestamo.setFechaInicio(rs.getDate("fecha_inicio")); // Asegúrate de que el tipo sea correcto
                prestamo.setFechaFin(rs.getDate("fecha_fin")); // Asegúrate de que el tipo sea correcto
                prestamo.setDevuelto(rs.getBoolean("Devuelto")); // Asegúrate de que el tipo sea correcto
                System.out.println(prestamo);
                prestamosSuperados.add(prestamo);
            }
        }
        return prestamosSuperados; // Devuelve la lista de préstamos
    }
    
    // Obtener préstamos por libro ID donde Devuelto es 0
    public List<Prestamo> obtenerPrestamosPorLibroId(int libroId) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM biblioteca_moncho_prestamos WHERE libro_id = ? AND Devuelto = 0";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, libroId); // Establecer el ID del libro en la consulta
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(rs.getInt("id")); // Asegúrate de que hay una columna 'id' en la tabla
                prestamo.setLibro(libroManager.obtenerLibroPorId(rs.getInt("libro_id"))); // Obtener el libro correspondiente
                prestamo.setSocio(socioManager.obtenerSocioPorId(rs.getInt("socio_id"))); // Obtener el socio correspondiente
                prestamo.setFechaInicio(rs.getDate("fecha_inicio"));
                prestamo.setFechaFin(rs.getDate("fecha_fin"));
                prestamo.setDevuelto(rs.getBoolean("Devuelto"));

                prestamos.add(prestamo);
            }
        }
        return prestamos; // Devuelve la lista de préstamos
    }

    
    
}
