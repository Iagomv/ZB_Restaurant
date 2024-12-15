/**
 * Clase que implementa el comportamiento de un hilo de cocinero.
 * Gestiona y procesa tareas relacionadas con la preparación de pedidos.
 */
package Threads;

import java.util.ArrayList;
import java.util.Random;
import API.InsertarAPI;
import Model.*;
import Static.*;

/**
 * Clase que representa un hilo para gestionar las tareas de un cocinero.
 */
public class CocineroThread implements Runnable {

    /**
     * Instancia de la API utilizada para interactuar con el sistema de pedidos.
     */
    private InsertarAPI api = new InsertarAPI();

    /**
     * Cocinero asociado a este hilo.
     */
    private Cocinero cocinero;

    /**
     * Lista compartida de tareas de cocina.
     */
    private ArrayList<TareaCocina> listaTareas;

    /**
     * Generador de números aleatorios utilizado para simular tiempos de
     * preparación.
     */
    private final Random random = new Random();

    /**
     * Estado que representa que un plato ha sido preparado.
     */
    private String estadoPreparado = Estados.estadoPreparado;

    /**
     * Notificador utilizado para informar a los camareros sobre actualizaciones de
     * pedidos.
     */
    private NotificadorCamarero notificador;

    /**
     * Constructor de la clase.
     * 
     * @param cocinero    Cocinero asignado a este hilo.
     * @param notificador Instancia del notificador compartido entre cocineros y
     *                    camareros.
     */
    public CocineroThread(Cocinero cocinero, NotificadorCamarero notificador) {
        this.cocinero = cocinero;
        this.listaTareas = Hilos.listaTareasCocineros;
        this.notificador = notificador;
    }

    /**
     * Método principal del hilo. Procesa las tareas asignadas al cocinero.
     */
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

    /**
     * Procesa una tarea específica de cocina.
     * 
     * @param tarea Tarea de cocina a procesar.
     */
    private void procesarTarea(TareaCocina tarea) {
        prepararPedido(tarea.getPedido());
    }

    /**
     * Prepara los platos de un pedido.
     * 
     * @param pedido Pedido cuyos platos se deben preparar.
     */
    private void prepararPedido(Pedido pedido) {
        Plato[] platos = pedido.getPlatos();
        for (Plato plato : platos) {
            cocinarPlato(plato, pedido);
            notificarPedidoActualizado(pedido);
            api.actualizarPedido(pedido.getIdPedido(), pedido);
        }
    }

    /**
     * Cocina un plato específico de un pedido.
     * 
     * @param plato  Plato a cocinar.
     * @param pedido Pedido asociado al plato.
     */
    private void cocinarPlato(Plato plato, Pedido pedido) {
        System.out.println(
                "Cocinero " + cocinero.getIdCocinero() + " cocinando " + plato.getTipo() + ": " + plato.getNombre()
                        + " para la mesa " + pedido.getMesa().getNumeroMesa());
        tiempoPreparacion(plato);
        actualizarEstadoPlato(plato);
    }

    /**
     * Actualiza el estado de un plato a "preparado".
     * 
     * @param plato Plato cuyo estado se debe actualizar.
     */
    private void actualizarEstadoPlato(Plato plato) {
        plato.setEstado(estadoPreparado);
        System.out.println("Plato " + plato.getTipo() + ": " + plato.getNombre() + " preparado.");
    }

    /**
     * Notifica a los camareros que un pedido ha sido actualizado.
     * 
     * @param pedido Pedido actualizado que se notifica.
     */
    private void notificarPedidoActualizado(Pedido pedido) {
        notificador.agregarPedido(pedido);
    }

    /**
     * Simula el tiempo de preparación de un plato.
     * 
     * @param plato Plato que se está preparando.
     */
    private void tiempoPreparacion(Plato plato) {
        try {
            Thread.sleep(tiempoEstimado(plato) * 1000);
        } catch (InterruptedException e) {
            System.err.println("Error durante la preparación del plato: " + e.getMessage());
        }
    }

    /**
     * Calcula el tiempo estimado de preparación de un plato en función de su tipo.
     * 
     * @param plato Plato cuyo tiempo de preparación se calcula.
     * @return Tiempo estimado en segundos.
     */
    private int tiempoEstimado(Plato plato) {
        String tipo = plato.getTipo().toLowerCase();
        return switch (tipo) {
            case "entrante" -> TiemposEspera.tiempoPrepararEntrante;
            case "primero" -> TiemposEspera.tiempoPrepararPrimero;
            case "postre" -> TiemposEspera.tiempoPrepararPostre;
            default -> 1;
        };
    }

    /**
     * Agrega una tarea de cocina a la lista de tareas.
     * 
     * @param tarea Tarea de cocina a agregar.
     */
    public void agregarTarea(TareaCocina tarea) {
        synchronized (listaTareas) {
            listaTareas.add(tarea);
            listaTareas.notify();
        }
    }

    /**
     * Obtiene el cocinero asociado a este hilo.
     * 
     * @return Cocinero asociado.
     */
    public Cocinero getCocinero() {
        return cocinero;
    }

    /**
     * Establece el cocinero asociado a este hilo.
     * 
     * @param cocinero Nuevo cocinero a asociar.
     */
    public void setCocinero(Cocinero cocinero) {
        this.cocinero = cocinero;
    }
}
