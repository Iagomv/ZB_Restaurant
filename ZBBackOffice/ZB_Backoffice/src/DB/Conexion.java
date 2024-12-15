package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Conexion {
    // Instancia única
    private static Conexion instance;
    private Connection connection;

    // Constructor privado para evitar la creación de más instancias
    private Conexion() {
        try {
            // Variables para almacenar los datos de conexión
            String url = "";
            String usuario = "";
            String clave = "";

            // Cargar el archivo XML de configuración
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resources/config.xml");

            // Verificar si el archivo se encuentra
            if (inputStream == null) {
                System.out.println("No se pudo cargar el archivo config.xml. Revisa la ubicación del archivo.");
                throw new IllegalArgumentException("Caca al cargar el archivo de configuración.");
            } else {
                System.out.println("Archivo config.xml cargado exitosamente.");
            }

            // Crear el DocumentBuilder y parsear el archivo XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            // Obtener los elementos de conexión del archivo XML
            NodeList nodeList = document.getElementsByTagName("conexion");
            if (nodeList.getLength() > 0) {
                Element conexionElement = (Element) nodeList.item(0);

                // Leer los valores de los elementos XML
                url = conexionElement.getElementsByTagName("url").item(0).getTextContent();
                usuario = conexionElement.getElementsByTagName("usuario").item(0).getTextContent();
                clave = conexionElement.getElementsByTagName("clave").item(0).getTextContent();
            }

            // Establecer la conexión usando los valores obtenidos
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, usuario, clave);

        } catch (Exception e) {
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
