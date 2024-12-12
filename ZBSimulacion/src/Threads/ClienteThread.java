package Threads;

import API.InsertarAPI;
import Handlers.PedidoHandler;
import Handlers.PedidoToJSON;
import Handlers.ProcessHandler;
import Model.Cliente;
import Model.Pedido;
import Static.Personal;

public class ClienteThread extends Cliente implements Runnable {
    PedidoHandler pedidoHandler = new PedidoHandler();
    InsertarAPI APIinsert = new InsertarAPI();

    public ClienteThread(Cliente cliente) {
        super(cliente.getNumeroCliente(), cliente.getNumeroComensales(), cliente.getNumeroMesa(), cliente.getEstado(),
                cliente.getPedido(), cliente.getMesa());
    }

    public void run() {
        hablarConCamareroSala();
        realizarPedido();
        APIinsert.enviarPedido(this.getPedido());

    }

    // Adquirimos semaforo y obtenemos la mesa
    private void hablarConCamareroSala() {
        if (Personal.cSala.asignarMesa(this) != null) {
            this.setMesa(Personal.cSala.asignarMesa(this));
        }
    }

    private void realizarPedido() {
        ProcessHandler ph = new ProcessHandler(); // Proceso para elegir platos
        String[] elecciones = ph.elegirPlatos(); // Devuelve los platos elegidos
        Pedido pedido = pedidoHandler.crearPedido(Integer.parseInt(elecciones[0]), Integer.parseInt(elecciones[1]),
                Integer.parseInt(elecciones[2]),
                Integer.parseInt(elecciones[3]), this); // Creamos el pedido
        this.setPedido(pedido);
    }

}
