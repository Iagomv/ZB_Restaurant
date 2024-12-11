import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.CSS;
import Helpers.Instanciador;
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

    Helpers.Instanciador getInstancia = new Helpers.Instanciador();

    public Simulacion() {
        setVariables();
        generarInstanciasRestaurante();

        // Bucle principal
        while (true) {
            Personal.clientes.add(getInstancia.generarCliente(numeroDeCliente, tiempoEntreClientes, "En espera"));
            numeroDeCliente++;
            Cliente cliente = Personal.clientes.remove(0);
            new Thread(new ClienteThread(cliente)).start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setVariables() {
        this.tiempoEntreClientes = 7;
        this.cantidadCamareros = 4;
        this.cantidadCocineros = 2;
        this.cantidadMesas = 10;
    }

    // Instanciamos los elementos necesarios para el restaurante y los guardamos en
    // variables estaticas
    private void generarInstanciasRestaurante() {
        Personal.cSala = getInstancia.generarCSala(cantidadMesas);
        Personal.camareros = getInstancia.generarCamareros(cantidadCamareros);
        Personal.cocineros = getInstancia.generarCocineros(cantidadCocineros);
        Personal.sommelier = getInstancia.generarSommelier("Libre", null, null);
        Personal.restaurante = getInstancia.generarRestaurante(Personal.camareros, Personal.cocineros, Personal.cSala,
                Personal.sommelier, cantidadMesas);
    }

    @Override
    public String toString() {
        return "Simulacion [tiempoEntreClientes=" + tiempoEntreClientes + ", cantidadCamareros=" + cantidadCamareros
                + ", cantidadCocineros=" + cantidadCocineros + ", cantidadMesas=" + cantidadMesas + ", numeroDeCliente="
                + numeroDeCliente + ", getInstancia=" + getInstancia + "]";
    }

}
