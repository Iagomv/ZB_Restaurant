package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Personal;
import DB.Conexion;

public class Personal_Controller {
    private Connection connection;
    private String nombreTabla = "zero_bugs_personal"; // Nombre de la tabla en la base de datos

    public Personal_Controller() {
        this.connection = Conexion.getInstance().getConnection();
    }

    // Auxiliar para preparar sentencias pasando parámetros en un array
    private PreparedStatement consultaPreparada(String sql, Personal personal) throws SQLException {
        String[] parametros = personal.getParametros();
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
    private void obtenerId(Personal personal, PreparedStatement stmt) throws SQLException {
        // Obtener el ID generado automáticamente
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                personal.setDni(generatedKeys.getString(1)); // Establecer el DNI generado en el objeto personal
            }
        }
    }

    // Método para agregar un personal
    public void agregarPersonal(Personal personal) throws SQLException {
        try {
            if (connection == null) {
                throw new SQLException("No se pudo conectar a la base de datos.");
            }
            String sql = "INSERT INTO " + nombreTabla
                    + " (nombre, apellidos, dni, edad, fecha_inicio, fecha_fin_contrato, horas_semanales, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Utilizamos PreparedStatement y ejecutamos la consulta
            try (PreparedStatement stmt = consultaPreparada(sql, personal)) {
                stmt.executeUpdate();

                obtenerId(personal, stmt);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar el personal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Obtener todos los registros de personal
    public List<Personal> obtenerPersonal() throws SQLException {
        try {
            List<Personal> personalList = new ArrayList<>();
            String sql = "SELECT * FROM " + nombreTabla;

            try (Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    int id = rs.getInt("id_personal");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String dni = rs.getString("dni");
                    String edad = rs.getString("edad");
                    String fechaInicio = rs.getString("fecha_inicio");
                    String fechaFinContrato = rs.getString("fecha_fin_contrato");
                    String horasSemanales = rs.getString("horas_semanales");
                    String rol = rs.getString("rol");
                    String imagen = rs.getString("imagen");

                    personalList.add(new Personal(id, nombre, apellidos, dni, edad, fechaInicio, fechaFinContrato,
                            horasSemanales, rol));
                }
            }
            return personalList;
        } catch (SQLException e) {
            System.out.println("Error al obtener el personal: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Obtener personal por DNI
    public Personal obtenerPersonalPorDni(String dni) throws SQLException {
        Personal personal = null;
        String sql = "SELECT * FROM " + nombreTabla + " WHERE dni = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dni);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String edad = rs.getString("edad");
                    String fechaInicio = rs.getString("fecha_inicio");
                    String fechaFinContrato = rs.getString("fecha_fin_contrato");
                    String horasSemanales = rs.getString("horas_semanales");
                    String rol = rs.getString("rol");

                    personal = new Personal(nombre, apellidos, dni, edad, fechaInicio, fechaFinContrato, horasSemanales,
                            rol);
                }
            }
        }
        return personal;
    }

    // Actualizar un registro de personal
    public void actualizarPersonal(int id, Personal personal) throws SQLException {
        try {
            if (connection == null) {
                throw new SQLException("No se pudo conectar a la base de datos.");
            }
            String sql = "UPDATE " + nombreTabla
                    + " SET nombre = ?, apellidos = ?, dni = ?, edad = ?, fecha_inicio = ?, fecha_fin_contrato = ?, horas_semanales = ?, rol = ? WHERE id_personal = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, personal.getNombre());
                stmt.setString(2, personal.getApellidos());
                stmt.setString(3, personal.getDni());
                stmt.setString(4, personal.getEdad());
                stmt.setString(5, personal.getFechaInicio());
                stmt.setString(6, personal.getFechaFinContrato());
                stmt.setString(7, personal.getHorasSemanales());
                stmt.setString(8, personal.getRol());
                stmt.setInt(9, id); // Especificamos el DNI para saber qué actualizar
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el personal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Eliminar un registro de personal
    public void eliminarPersonal(String dni) throws SQLException {
        try {
            if (connection == null) {
                throw new SQLException("No se pudo conectar a la base de datos.");
            }
            String sql = "DELETE FROM " + nombreTabla + " WHERE dni = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, dni);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el personal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
