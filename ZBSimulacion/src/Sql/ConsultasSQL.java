package Sql;

import java.lang.Thread.State;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.Bebida;
import Model.Plato;
import Connection.Conexion;

public class ConsultasSQL {
    private Connection connection;
    private String tablaBebidas = "Zero_Bugs_Bebidas";
    private String tablaPlatos = "Zero_Bugs_Comidas";

    public ConsultasSQL() {
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
        String sql = "INSERT INTO " + tablaBebidas + " (nombre, tipo, precio, stock) VALUES (?, ?, ?, ?)";
        // Utilizamos PreparedStatement y ejecutamos la consulta
        try (PreparedStatement stmt = consultaPreparada(sql, bebida)) {
            stmt.executeUpdate();

            obtenerId(bebida, stmt);
        }
    }

    public List<Bebida> obtenerbebidas() throws SQLException {
        List<Bebida> bebidas = new ArrayList<>();
        String sql = "SELECT * FROM " + tablaBebidas;

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_bebida");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                String precio = rs.getString("precio");
                String stock = rs.getString("stock");

                bebidas.add(new Bebida(id, nombre, tipo, precio, stock, "pendiente"));
            }
        }
        return bebidas;
    }

    public List<Plato> obtenerPlatos() throws SQLException {
        List<Plato> platos = new ArrayList<>();
        String sql = "SELECT * FROM " + tablaPlatos;

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_comida");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                String precio = rs.getString("precio");
                String stock = rs.getString("stock");

                platos.add(new Plato(id, nombre, tipo, precio, stock, "pendiente"));
            }
        }
        return platos;
    }

    public Bebida obtenerbebidaPorId(int id) throws SQLException {
        Bebida bebida = null;
        String sql = "SELECT * FROM " + tablaBebidas + " WHERE id_bebida = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String tipo = rs.getString("tipo");
                    String precio = rs.getString("precio");
                    String stock = rs.getString("stock");
                    bebida = new Bebida(id, nombre, tipo, precio, stock, "pendiente");
                }
            }
        }
        return bebida;
    }

    public void actualizarbebida(int id, Bebida bebida) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }
        String sql = "UPDATE " + tablaBebidas + " SET nombre = ?, tipo = ?, precio = ?, stock = ? WHERE id_bebida = ?";

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
        String sql = "DELETE FROM " + tablaBebidas + " WHERE id_bebida = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void agregarIngreso(double importe) throws SQLException {
        if (connection == null) {
            throw new SQLException("No se pudo conectar a la base de datos.");
        }

        String sql = "INSERT INTO ingresos (Ingresos) VALUES (?)"; // Cambié "ingreso" por "Ingresos"

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, importe); // Establecemos el importe que queremos agregar
            stmt.executeUpdate();
        }
    }

}
