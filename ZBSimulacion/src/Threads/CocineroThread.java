package Threads;

import java.util.ArrayList;
import java.util.Random;
import API.InsertarAPI;
import Model.Cocinero;
import Model.Pedido;
import Model.Plato;
import Model.TareaCocina;
import Static.Estados;
import Static.Hilos;
import Static.TiemposEspera;

public class CocineroThread implements Runnable {

    private InsertarAPI api = new InsertarAPI();
    private Cocinero cocinero;
    private ArrayList<TareaCocina> listaTareas;
    private final Random random = new Random();
    private String estadoPreparado = Estados.estadoPreparado;
    private NotificadorCamarero notificador;

    public CocineroThread(Cocinero cocinero, NotificadorCamarero notificador) {
        this.cocinero = cocinero;
        this.listaTareas = Hilos.listaTareasCocineros;
        this.notificador = notificador; // Instancia del notificador compartido
    }

    @Override
    public void run() {
        while (true) {
            try {
                TareaCocina tarea;

                synchronized (listaTareas) {
                    while (listaTareas.isEmpty()) {
                        listaTareas.wait(); // Espera a que se añadan tareas
                    }
                    tarea = listaTareas.remove(0);
                }
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
            cocinarPlato(plato, pedido);
            notificarPedidoActualizado(pedido);
            api.actualizarPedido(pedido.getIdPedido(), pedido);
        }
    }

    private void cocinarPlato(Plato plato, Pedido pedido) {
        System.out.println(
                "Cocinero " + cocinero.getIdCocinero() + " cocinando " + plato.getTipo() + ": " + plato.getNombre()
                        + " para la mesa " + pedido.getMesa().getNumeroMesa());
        tiempoPreparacion(plato);
        actualizarEstadoPlato(plato);
    }

    private void actualizarEstadoPlato(Plato plato) {
        plato.setEstado(estadoPreparado);
        System.out.println("Plato " + plato.getTipo() + ": " + plato.getNombre() + " preparado.");
    }

    private void notificarPedidoActualizado(Pedido pedido) {
        notificador.agregarPedido(pedido); // Notifica al NotificadorCamarero
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
            case "entrante" -> TiemposEspera.tiempoPrepararEntrante;
            case "primero" -> TiemposEspera.tiempoPrepararPrimero;
            case "postre" -> TiemposEspera.tiempoPrepararPostre;
            default -> 1;
        };
    }

    public void agregarTarea(TareaCocina tarea) {
        synchronized (listaTareas) {
            listaTareas.add(tarea);
            listaTareas.notify();
        }
    }

    public Cocinero getCocinero() {
        return cocinero;
    }

    public void setCocinero(Cocinero cocinero) {
        this.cocinero = cocinero;
    }
}
