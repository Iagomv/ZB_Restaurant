package Threads;

import java.util.ArrayList;

import Model.Bebida;
import Model.Pedido;
import Model.Sommelier;
import Model.Tarea;
import Static.Estados;
import Static.TiemposEspera;

public class SommelierThread extends Sommelier implements Runnable {

    private ArrayList<Tarea> listaTareas = new ArrayList<>();
    private API.InsertarAPI api = new API.InsertarAPI();

    public SommelierThread(Sommelier sommelier) {
        super(sommelier.getEstado(), sommelier.getPedido(), sommelier.getBebida());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Tarea tarea;
                // Espera hasta que haya tareas en la lista
                synchronized (listaTareas) {
                    while (listaTareas.isEmpty()) {
                        listaTareas.wait();
                    }
                    // Toma la primera tarea
                    tarea = listaTareas.remove(0);
                }
                // Procesar la tarea fuera del bloque sincronizado
                procesarTarea(tarea);

            } catch (InterruptedException e) {
                System.err.println("Error en el hilo del camarero: " + e.getMessage());
            }
        }
    }

    private void llevarAMesa(Pedido pedido, Bebida bebida) {
        try {
            Thread.sleep(TiemposEspera.tiempoServirVino * 1000);
        } catch (InterruptedException e) {
            System.err.println("Error en tiempo estimado: " + e.getMessage());
        }
        bebida.setEstado(Estados.estadoServido);
        api.actualizarPedido(pedido.getIdPedido(), pedido);
        System.out.println("El sommelier esta sirviendo: " + bebida.getNombre());
    }

    private void procesarTarea(Tarea tarea) {
        switch (tarea.getTipoTarea()) {
            case "llevarBebida":
                llevarAMesa(tarea.getPedido(), tarea.getBebida());
                break;
            default:
                System.err.println("Tipo de tarea desconocido: " + tarea.getTipoTarea());
        }
    }

    public synchronized void agregarTarea(Tarea tarea) {
        synchronized (listaTareas) {
            listaTareas.add(tarea);
            listaTareas.notify(); // Despierta el hilo cuando se agrega una nueva tarea
        }
    }

}
