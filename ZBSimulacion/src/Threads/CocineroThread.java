package Threads;

import java.util.ArrayList;
import java.util.Random;
import API.InsertarAPI;
import Model.Camarero;
import Model.Cocinero;
import Model.Pedido;
import Model.Plato;
import Model.Tarea;
import Model.TareaCocina;
import Static.Hilos;

public class CocineroThread implements Runnable {

    private InsertarAPI api = new InsertarAPI();
    private final int tiempoPrepararPlato = 5; // Tiempo estimado en segundos para preparar un plato
    private Cocinero cocinero;
    private ArrayList<TareaCocina> listaTareas;
    private final Random random = new Random();

    public CocineroThread(Cocinero cocinero) {
        this.cocinero = cocinero;
        this.listaTareas = Hilos.listaTareasCocineros;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TareaCocina tarea;

                // Espera hasta que haya tareas en la lista
                synchronized (listaTareas) {
                    while (listaTareas.isEmpty()) {
                        listaTareas.wait(); // Espera a que se añadan tareas
                    }
                    // Toma la primera tarea de la lista
                    tarea = listaTareas.remove(0);
                }

                // Procesar la tarea fuera del bloque sincronizado
                procesarTarea(tarea);

            } catch (InterruptedException e) {
                System.err.println("Error en el hilo del cocinero: " + e.getMessage());
            }
        }
    }

    private void procesarTarea(TareaCocina tarea) {
        prepararPedido(tarea.getPedido());
    }

    private void prepararPedido(Pedido pedido) {
        Plato[] platos = pedido.getPlatos();
        for (Plato plato : platos) {
            cocinarPlato(plato);
            notificarCamarero(pedido, plato);
        }
    }

    private void cocinarPlato(Plato plato) {
        System.out.println(
                "Cocinero " + cocinero.getIdCocinero() + " cocinando " + plato.getTipo() + ": " + plato.getNombre());
        tiempoPreparacion(plato);
        actualizarEstadoPlato(plato);
    }

    private void notificarCamarero(Pedido pedido, Plato plato) {
        Camarero camarero = pedido.getMesa().getCamareroAsignado();

        synchronized (Hilos.hilosCamareros.get(camarero)) {
            Hilos.hilosCamareros.get(camarero).agregarTarea(new Tarea("llevarPlato", pedido, plato));
            Hilos.hilosCamareros.get(camarero).notify(); // Notifica al camarero asignado
        }
    }

    private void actualizarEstadoPlato(Plato plato) {
        plato.setEstado("preparado");
    }

    public void agregarTarea(TareaCocina tarea) {
        synchronized (listaTareas) {
            listaTareas.add(tarea);
            listaTareas.notify(); // Notifica a un hilo en espera
        }
    }

    private void tiempoPreparacion(Plato plato) {
        try {
            Thread.sleep(tiempoEstimado(plato) * 1000);
        } catch (InterruptedException e) {
            System.err.println("Error durante la preparación del plato: " + e.getMessage());
        }
    }

    private int tiempoEstimado(Plato plato) {
        String tipo = plato.getTipo().toLowerCase();
        return switch (tipo) {
            case "entrante" -> random.nextInt(2, 4);
            case "primero" -> random.nextInt(4, 6);
            case "postre" -> random.nextInt(1, 2);
            default -> 1;
        };
    }

    public Cocinero getCocinero() {
        return cocinero;
    }

    public void setCocinero(Cocinero cocinero) {
        this.cocinero = cocinero;
    }
}
