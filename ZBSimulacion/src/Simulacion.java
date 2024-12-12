import Model.Cliente;
import Static.Carta;
import Static.Hilos;
import Static.Personal;
import Threads.CamareroThread;
import Threads.ClienteThread;

public class Simulacion {
    private int tiempoEntreClientes;
    private int cantidadCamareros;
    private int cantidadCocineros;
    private int cantidadMesas;
    private int numeroDeCliente;

    Handlers.Instanciador getInstancia = new Handlers.Instanciador();
    Handlers.CartaHandler cartaHandler = new Handlers.CartaHandler();

    public Simulacion() {
        setVariables();
        generarInstanciasRestaurante();
        generarHilos();
        generarCarta();

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

    private void generarCarta() {
        Carta.bebidas = cartaHandler.getBebidas();
        Carta.platosCarta = cartaHandler.getPlatos();
        Carta.entrantes = cartaHandler.getEntrantes(Carta.platosCarta);
        Carta.primeros = cartaHandler.getPrimeros(Carta.platosCarta);
        Carta.postres = cartaHandler.getPostres(Carta.platosCarta);
        Carta.numeroBebidas = cartaHandler.getNumeroBebidas();
        Carta.numeroEntrantes = cartaHandler.getNumeroEntrantes();
        Carta.numeroPrimeros = cartaHandler.getNumeroPrimeros();
        Carta.numeroPostres = cartaHandler.getNumeroPostres();
    }

    private void generarHilos() {
        for (int i = 0; i < Personal.camareros.length; i++) {
            Hilos.hilosCamareros.put(Personal.camareros[i], new CamareroThread(Personal.camareros[i]));
        }
        for (int i = 0; i < Personal.cocineros.length; i++) {
            // new Thread(Personal.cocineros[i]).start();
        }
    }
}
