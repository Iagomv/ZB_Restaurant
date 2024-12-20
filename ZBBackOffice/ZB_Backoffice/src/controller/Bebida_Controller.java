package controller;

import java.lang.Thread.State;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Bebida;
import DB.Conexion;

public class Bebida_Controller {
    private Connection connection;
    private String nombreTabla = "Zero_Bugs_Bebidas";

    public Bebida_Controller() {
        this.connection = Conexion.getInstance().getConnection();
    }

    // Auxiliar para preparar sentencias pasando parámetros en un array
    private PreparedStatement consultaPreparada(String sql, Bebida bebida) throws SQLException {
        String[] parametros = bebida.getParametrosBebida();
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
    private void obtenerId(Bebida bebida, PreparedStatement stmt) throws SQLException {
        // Obtener el ID generado automáticamente
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                bebida.setId(generatedKeys.getInt(1)); // Establecer el ID generado en el objeto bebida
            }
        }
    }

    // Metodo para agregar una bebida
    public void agregarBebidas(Bebida bebida) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "INSERT INTO " + nombreTabla + " (nombre, tipo, precio, stock) VALUES (?, ?, ?, ?)";
        // Utilizamos PreparedStatement y ejecutamos la consulta
        try (PreparedStatement stmt = consultaPreparada(sql, bebida)) {
            stmt.executeUpdate();

            obtenerId(bebida, stmt);
        }
    }

    public List<Bebida> obtenerbebidas() throws SQLException {
        List<Bebida> bebidas = new ArrayList<>();
        String sql = "SELECT * FROM " + nombreTabla;

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_bebida");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                String precio = rs.getString("precio");
                String stock = rs.getString("stock");

                bebidas.add(new Bebida(id, nombre, tipo, precio, stock));
            }
        }
        return bebidas;
    }

    public Bebida obtenerbebidaPorId(int id) throws SQLException {
        Bebida bebida = null;
        String sql = "SELECT * FROM " + nombreTabla + " WHERE id_bebida = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String tipo = rs.getString("tipo");
                    String precio = rs.getString("precio");
                    String stock = rs.getString("stock");
                    bebida = new Bebida(id, nombre, tipo, precio, stock);
                }
            }
        }
        return bebida;
    }

    public void actualizarbebida(int id, Bebida bebida) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "UPDATE " + nombreTabla + " SET nombre = ?, tipo = ?, precio = ?, stock = ? WHERE id_bebida = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bebida.getNombre());
            stmt.setString(2, bebida.getTipo());
            stmt.setString(3, bebida.getPrecio());
            stmt.setString(4, bebida.getStock());
            stmt.setInt(5, id); // Especificamos el ID para saber qué actualizar
            stmt.executeUpdate();
        }
    }

    public void eliminarbebida(int id) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "DELETE FROM " + nombreTabla + " WHERE id_bebida = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
