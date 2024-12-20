package Threads;

import API.InsertarAPI;
import Handlers.PedidoHandler;
import Handlers.PedidoToJSON;
import Handlers.ProcessHandler;
import Model.Camarero;
import Model.Cliente;
import Model.Mesa;
import Model.Pedido;
import Model.Tarea;
import Static.Hilos;
import Static.Personal;

public class ClienteEntidad extends Cliente implements Runnable {
    PedidoHandler pedidoHandler = new PedidoHandler();
    InsertarAPI APIinsert = new InsertarAPI();
    Cliente cliente;
    Camarero camarero;
    CamareroThread camareroThread;
    Mesa mesa;

    public ClienteEntidad(Cliente cliente) {
        super(cliente.getNumeroCliente(), cliente.getNumeroComensales(), cliente.getNumeroMesa(), cliente.getEstado(),
                cliente.getPedido(), cliente.getMesa());
        this.cliente = cliente;

        if (pedirMesaCamareroSala()) {
            System.out.println(this.getMesa().getNumeroMesa());
            System.out.println(
                    "\tEl cliente " + this.getNumeroCliente() + " ha ocupado la mesa "
                            + this.getMesa().getNumeroMesa());
            presentacionCamarero();
            realizarPedido();
            camareroTomaPedido();
        }

    }

    // Se agrega la tarea de tomar pedido al camarero
    private void camareroTomaPedido() {
        camareroThread.agregarTarea(new Tarea("tomarPedido", this, this.getPedido()));
    }

    private void presentacionCamarero() {
        this.camarero = this.getMesa().getCamareroAsignado();
        this.camareroThread = Hilos.hilosCamareros.get(this.camarero);
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
        // System.out.println(this.getMesa().getNumeroMesa());
        this.setPedido(pedido);
    }

    public Camarero getCamarero() {
        return camarero;
    }

    public void setCamarero(Camarero camarero) {
        this.camarero = camarero;
    }

    public CamareroThread getCamareroThread() {
        return camareroThread;
    }

    public void setCamareroThread(CamareroThread camareroThread) {
        this.camareroThread = camareroThread;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
}
