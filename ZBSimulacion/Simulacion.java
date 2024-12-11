import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.CSS;

import Model.CSala;
import Model.Cliente;

public class Simulacion {
    private int tiempoEntreClientes;
    private int cantidadCamareros;
    private int cantidadCocineros;
    private int cantidadMesas;
    private int tiempoEntreCamareros;
    private int tiempoEntreCocineros;
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    Helpers.GetInstancia getInstancia = new Helpers.GetInstancia();

    public Simulacion() {
        while(true){
            clientes.add(getInstancia.generarClientes(1));
            CSala cSala = getInstancia.generarCSala(10);
            //System.out.println("Se han generado " + clientes.size() + " clientes");
            try {
                Cliente cliente = clientes.removeFirst();
                clientes.removeFirst();
                System.out.println(cliente.getNumeroCliente() + " ha llegado a la sala");
                cSala.acquire();
                System.out.println("El cliente " + cliente.getNumeroCliente() + "se sienta a la mesa");
            } catch (InterruptedException e) {
                Helpers.ErrorMensajeConsola.error(e, "Error al generar clientes");
            }
        } 


    }

    
}
