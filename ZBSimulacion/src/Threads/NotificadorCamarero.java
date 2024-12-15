package Threads;

import API.InsertarAPI;
import Model.Camarero;
import Model.Pedido;
import Model.Plato;
import Model.Tarea;
import Static.Hilos;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificadorCamarero implements Runnable {

    private InsertarAPI api = new InsertarAPI();
    private Queue<Pedido> pedidosEnSeguimiento = new ConcurrentLinkedQueue<>();

    public void agregarPedido(Pedido pedido) {
        pedidosEnSeguimiento.add(pedido);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Pedido pedido : pedidosEnSeguimiento) {
                    verificarPedido(pedido);
                }
                Thread.sleep(5000); // Consultar cada 5 segundos
            } catch (InterruptedException e) {
                System.err.println("Notificador de camareros interrumpido.");
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción
            }
        }
    }

    private void verificarPedido(Pedido pedido) {
        Pedido pedidoActualizado = api.obtenerPedido(pedido.getIdPedido());

        if (pedidoActualizado == null) {
            System.err.println("No se pudo obtener información actualizada para el pedido: " + pedido.getIdPedido());
            return;
        }

        for (Plato plato : pedidoActualizado.getPlatos()) {
            if ("preparado".equals(plato.getEstado()) && !platoYaNotificado(plato)) {
                notificarCamarero(pedido, plato);
            }
        }
    }

    private boolean platoYaNotificado(Plato plato) {
        return plato.isNotificado();
    }

    private void notificarCamarero(Pedido pedido, Plato plato) {
        Camarero camarero = pedido.getMesa().getCamareroAsignado();

        if (Hilos.hilosCamareros.containsKey(camarero)) {
            synchronized (Hilos.hilosCamareros.get(camarero)) {
                Hilos.hilosCamareros.get(camarero).agregarTarea(new Tarea("llevarPlato", pedido, plato));
                Hilos.hilosCamareros.get(camarero).notify(); // Notifica al camarero asignado
            }
            System.out.println("Notificando camarero: " + camarero);
            plato.setNotificado(true); // Marca el plato como notificado
        } else {
            System.err.println("Camarero no encontrado en hilos activos: " + camarero);
        }
    }
}
