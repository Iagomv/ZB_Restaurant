package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DB.Conexion;

public class Ingreso_Controller {

    private Connection connection;

    public Ingreso_Controller() {
        this.connection = Conexion.getInstance().getConnection();
    }

    // Método para obtener todos los ingresos
    public List<Double> obtenerTodosLosIngresos() throws SQLException {
        List<Double> ingresos = new ArrayList<>();
        String sql = "SELECT Ingresos FROM Ingresos"; // Seleccionamos solo la columna 'ingreso'

        try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                double ingreso = rs.getDouble("Ingresos"); // Obtener el valor de la columna 'ingreso'
                System.out.println(ingreso);
                ingresos.add(ingreso); // Agregarlo a la lista de ingresos
            }
        }
        return ingresos;
    }

    // Método para obtener la suma de los ingresos manualmente
    public double obtenerSumaIngresos() throws SQLException {
        List<Double> ingresos = obtenerTodosLosIngresos(); // Obtener todos los ingresos
        double sumaIngresos = 0.0;

        // Sumar los ingresos manualmente
        for (double ingreso : ingresos) {
            sumaIngresos += ingreso;
        }

        return sumaIngresos;
    }
}
