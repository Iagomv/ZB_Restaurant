package UI;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import controller.Bebida_Controller;
import model.Bebida;

public class PanelBebidas extends JPanel{
    
    Bebida_Controller bebidaController = new Bebida_Controller();
    List<Bebida> bebidas = new ArrayList<>();
    JTable table = new JTable();


    public PanelBebidas() {
        mostrarBebidas();
    }

    public void mostrarBebidas() {
        try {
            bebidas = bebidaController.obtenerbebidas();
            System.out.println("Mostrando bebidas: " + bebidas);
        } catch (SQLException e) {
            System.out.println("Error al obtener las bebidas");
            e.printStackTrace();
        }  
    }

    public void mostrarTablaBebidas() {
        // Aquí puedes implementar la tabla de bebidas con los datos obtenidos de la base de datos
        for (Bebida bebida : bebidas) {
        
        }
    }

}
