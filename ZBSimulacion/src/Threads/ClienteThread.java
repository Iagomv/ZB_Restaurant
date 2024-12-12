package Threads;

import java.util.logging.Handler;

import Handlers.ProcessHandler;
import Model.Cliente;
import Model.Mesa;
import Model.Pedido;
import Static.Personal;

public class ClienteThread extends Cliente implements Runnable {

    public ClienteThread(Cliente cliente) {
        super(cliente.getNumeroCliente(), cliente.getNumeroComensales(), cliente.getNumeroMesa(), cliente.getEstado(),
                cliente.getPedido(), cliente.getMesa());
    }

    public void run() {
        hablarConCamareroSala();
        realizarPedido();
    }

    // Adquirimos semaforo y obtenemos la mesa
    private void hablarConCamareroSala() {
        if (Personal.cSala.asignarMesa(this) != null) {
            this.setMesa(Personal.cSala.asignarMesa(this));
        }
    }

    private void realizarPedido() {
        ProcessHandler ph = new ProcessHandler();
        String[] elecciones = ph.elegirPlatos();
    }

}
