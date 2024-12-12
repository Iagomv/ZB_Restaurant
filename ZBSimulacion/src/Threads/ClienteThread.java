package Threads;

import API.InsertarAPI;
import Handlers.PedidoHandler;
import Handlers.PedidoToJSON;
import Handlers.ProcessHandler;
import Model.Cliente;
import Model.Mesa;
import Model.Pedido;
import Static.Personal;

public class ClienteThread extends Cliente implements Runnable {
    PedidoHandler pedidoHandler = new PedidoHandler();
    InsertarAPI APIinsert = new InsertarAPI();

    public ClienteThread(Cliente cliente) {
        super(cliente.getNumeroCliente(), cliente.getNumeroComensales(), cliente.getNumeroMesa(), cliente.getEstado(),
                cliente.getPedido(), cliente.getMesa());
    }

    // !Método principal del hilo */
    public void run() {
        if (pedirMesaCamareroSala()) {
            realizarPedido();
            Pedido pedidoActualizadoConID = APIinsert.enviarPedido(this.getPedido());
            this.setPedido(pedidoActualizadoConID);
            // Camarero.start();
        }
    }

    // Adquirimos semaforo y obtenemos la mesa
    private boolean pedirMesaCamareroSala() {
        Mesa mesa = Personal.cSala.asignarMesa(this);
        if (mesa != null) {
            this.setMesa(mesa);
            this.getMesa().setEstadoMesa("Ocupada");
            return true;
        } else {
            return false;
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

    private void actualizarEstadoPedido(String estado) {
        this.getPedido().setEstado(estado);
    }
}
