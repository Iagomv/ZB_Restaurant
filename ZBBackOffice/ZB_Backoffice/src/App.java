
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
            controller.comidaController comidaController = new controller.comidaController();
            comidaController.agregarComidas(new model.Comida("Shushi", "japonesa", "Parametro 2", "Parametro 3"));

        } else {
            System.out.println("Fallo en la conexión a la base de datos.");
        }
    }
}
