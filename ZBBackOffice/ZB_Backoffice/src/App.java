
import DB.Conexion;
import java.sql.Connection;
import controller.*;
import model.*;
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Conexion conexion = Conexion.getInstance();
        Connection conn = conexion.getConnection();
        // Verifica si la conexión es válida
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos.");
            UI.MainFrame mainFrame = new UI.MainFrame();

        } else {
            System.out.println("Fallo en la conexión a la base de datos.");
        }
    }
}
