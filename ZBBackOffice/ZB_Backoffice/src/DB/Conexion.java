package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//*Clase que establece la conexión con un patrón Singleton */
public class Conexion {
    // Instancia única
    private static Conexion instance;
    private Connection connection;

    // Constructor privado para evitar la creación de más instancias
    private Conexion() {
        try {
            // Aquí se inicializa la conexión
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/karbo_imartinez", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la única instancia de la conexión
    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        return connection;
    }

    // Método para cerrar la conexión (opcional)
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
