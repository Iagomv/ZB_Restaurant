package Helpers;

import java.util.Random;
import Model.*;

public class Instanciador {

    public Cocinero[] generarCocineros(int cantidadCocineros) {
        Cocinero[] cocineros = new Cocinero[cantidadCocineros];
        for (int i = 0; i < cantidadCocineros; i++) {
            cocineros[i] = new Cocinero();
        }
        return cocineros;
    }

    // * Devuelve el camarero de sala @cantidadMesas
    public CSala generarCSala(int cantidadMesas) {
        CSala CSala = new CSala(cantidadMesas);
        return CSala;
    }

    // * Devuelve array de camareros
    public Camarero[] generarCamareros(int cantidadCamareros) {
        Camarero[] camareros = new Camarero[cantidadCamareros];
        for (int i = 0; i < cantidadCamareros; i++) {
            camareros[i = 1] = new Camarero(i, null, null, null);
        }
        return camareros;
    }

    // Generamos las mesas
    public Mesa generarMesa(int numeroMesa, int capacidadMesa, String estadoMesa, Pedido pedido,
            Camarero camareroAsignado, Cliente cliente) {
        Mesa mesa = new Mesa(numeroMesa, capacidadMesa, estadoMesa, pedido, camareroAsignado, cliente);
        return mesa;
    }

    // Genera un cliente con datos aleatorios
    public Cliente generarCliente(int numeroDeCliente, int tiempoEntreClientes, String estado) {

        Random random = new Random();
        int numeroPersonas = random.nextInt(4) + 1; // Genera un número aleatorio entre 1 y 4

        Cliente cliente = new Cliente(numeroDeCliente, numeroPersonas, 0, estado, null);
        System.out.println("Se ha generado un cliente con " + numeroPersonas + " personas");
        return cliente;
    }

    public Restaurante generarRestaurante(Camarero[] camareros, Cocinero[] cocineros, CSala cSala,
            Sommelier sommelier, Mesa[] mesas) {
        Restaurante restaurante = new Restaurante(camareros, cocineros, cSala, sommelier, mesas);
        return restaurante;
    }

    public Sommelier generarSommelier(String estado, Pedido pedido, Bebida bebida) {
        Sommelier sommelier = new Sommelier(estado, pedido, bebida);
        return sommelier;
    }
}
