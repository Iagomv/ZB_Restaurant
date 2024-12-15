package Threads;

import API.InsertarAPI;
import Model.Camarero;
import Model.Pedido;
import Model.Plato;
import Model.Tarea;
import Static.Estados;
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
                // Procesamos todos los pedidos en seguimiento
                for (Pedido pedido : pedidosEnSeguimiento) {
                    verificarPedido(pedido);
                }
                Thread.sleep(5000); // Consultar cada 5 segundos
            } catch (InterruptedException e) {
                System.err.println("Notificador de camareros interrumpido.");
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción
            } catch (Exception e) {
                System.err.println("Error en la ejecución del notificador: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void verificarPedido(Pedido pedido) {
        try {
            // Hacemos la consulta de la API para obtener el pedido actualizado
            Pedido pedidoActualizado = api.obtenerPedido(pedido.getIdPedido());

            if (pedidoActualizado == null) {
                System.err.println(
                        "No se pudo obtener información actualizada para el pedido con ID: " + pedido.getIdPedido());
                return;
            }

            // Verificación de los platos del pedido actualizado
            for (Plato plato : pedidoActualizado.getPlatos()) {
                // Si el plato está preparado y aún no ha sido notificado, lo notificamos
                if (Estados.estadoEntregar.equals(plato.getEstado()) && !platoYaNotificado(plato)) {
                    plato.setEstado(Estados.estadoNotificado);
                    System.out.println(plato.getEstado());
                    api.actualizarPedido(pedidoActualizado.getIdPedido(), pedido);
                    notificarCamarero(pedido, plato);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al verificar el pedido con ID: " + pedido.getIdPedido());
            e.printStackTrace(); // Detalles más específicos sobre el error
        }
    }

    private boolean platoYaNotificado(Plato plato) {
        return plato.isNotificado();
    }

    private void notificarCamarero(Pedido pedido, Plato plato) {
        try {
            // Obtenemos al camarero asignado al pedido
            Camarero camarero = pedido.getMesa().getCamareroAsignado();

            // Verificamos si el camarero está registrado en los hilos activos
            if (Hilos.hilosCamareros.containsKey(camarero)) {
                // Sincronizamos el acceso al hilo del camarero
                synchronized (Hilos.hilosCamareros.get(camarero)) {
                    // Agregamos una tarea para llevar el plato
                    Hilos.hilosCamareros.get(camarero).agregarTarea(new Tarea("llevarPlato", pedido, plato));
                    Hilos.hilosCamareros.get(camarero).notify(); // Notificamos al camarero asignado
                }
                System.out.println("Notificando camarero: " + camarero.getIdCamarero() + " para llevar el plato: "
                        + plato.getNombre() + "estado del plato: " + plato.getEstado());
                plato.setNotificado(true); // Marcamos el plato como notificado
            } else {
                System.err.println("Camarero no encontrado en hilos activos: " + camarero);
            }
        } catch (Exception e) {
            System.err.println("Error al notificar al camarero: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
