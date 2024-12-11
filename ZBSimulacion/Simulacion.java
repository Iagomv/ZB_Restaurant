import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.CSS;

import Model.CSala;
import Model.Cliente;
import Static.Personal;
import Threads.ClienteThread;

public class Simulacion {
    private int tiempoEntreClientes;
    private int cantidadCamareros;
    private int cantidadCocineros;
    private int cantidadMesas;
    private int numeroDeCliente;
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    Helpers.Instanciador getInstancia = new Helpers.Instanciador();

    public Simulacion() {
        setVariables();
        // Bucle principal
        while (true) {
            clientes.add(getInstancia.generarCliente(numeroDeCliente, tiempoEntreClientes, "En espera"));
            numeroDeCliente++;
            Cliente cliente = clientes.remove(0);
            new ClienteThread(cliente).run("generarCliente");
        }
    }

    private void setVariables() {
        this.tiempoEntreClientes = 1;
        this.cantidadCamareros = 4;
        this.cantidadCocineros = 2;
        this.cantidadMesas = 10;
    }

    private void generarInstanciasRestaurante() {
        Personal.cSala = getInstancia.generarCSala(cantidadMesas);
        Personal.camareros = getInstancia.generarCamareros(cantidadCamareros);
        Personal.cocineros = getInstancia.generarCocineros(cantidadCocineros);
        Personal.sommelier = getInstancia.generarSommelier("Libre", null, null);
        Personal.restaurante = getInstancia.generarRestaurante(Personal.Camareros, Personal.Cocineros, Personal.CSala,
                Personal.sommelier, Personal.Mesas);
    }

}
