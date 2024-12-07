import java.sql.Connection;


public class App {

    public static Connection conn;

    public Connection getConn() {
        return conn;
    }

    public static void main(String[] args) {
        Interfaz interfaz = new Interfaz();
    }
    
}
