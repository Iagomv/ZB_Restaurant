package Static;

import java.util.ArrayList;

import Model.CSala;
import Model.Camarero;
import Model.Cliente;
import Model.Cocinero;
import Model.Mesa;
import Model.Restaurante;
import Model.Sommelier;
import Threads.CamareroThread;

public class Personal {
    public static CSala cSala;
    public static Camarero[] camareros;
    public static Cocinero[] cocineros;
    public static Restaurante restaurante;
    public static Sommelier sommelier;
    public static Mesa[] mesas; // Restaurante --> generarMesas()
    public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public static void soutVariablesStatic() {
        System.out.println(cSala.toString() + camareros.toString() + cocineros.toString() + restaurante.toString() +
                sommelier.toString() + mesas.toString() + clientes.toString());
    }

}