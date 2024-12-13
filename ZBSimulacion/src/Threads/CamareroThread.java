package Threads;

import java.util.ArrayList;
import API.*;
import Model.Bebida;
import Model.Camarero;
import Model.Cliente;
import Model.Pedido;
import Model.Plato;
import Model.Tarea;
import Model.TareaCocina;
import Static.Hilos;

public class CamareroThread implements Runnable {

    private ArrayList<Tarea> listaTareas = new ArrayList<>();
    private InsertarAPI api = new InsertarAPI();
    private final int tiempoTomarPedido = 2; // Tiempo estimado en segundos para tomar un pedido
    private final int tiempoLlevarBebida = 2; // Tiempo estimado en segundos para llevar una bebida
    private final int tiempoLlevarPlato = 1; // Tiempo estimado en segundos para llevar el plato
    private Camarero camarero;

    public CamareroThread(Camarero camarero) {
        this.camarero = camarero;
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

    private void procesarTarea(Tarea tarea) {
        switch (tarea.getTipoTarea()) {
            case "tomarPedido":
                tomarPedido(tarea.getCliente(), tarea.getPedido());
                break;
            case "llevarBebida":
                llevarAMesa(tarea.getBebida());
                break;
            case "llevarPlato":
                llevarAMesa(tarea.getPedido(), tarea.getPlato());
                break;
            case "actualizarEstado":
                actualizarEstadoPedido(tarea.getPedido());
                break;
            default:
                System.err.println("Tipo de tarea desconocido: " + tarea.getTipoTarea());
        }
    }

    private void tomarPedido(Cliente cliente, Pedido pedido) {
        tiempoEstimado(tiempoTomarPedido);
        Pedido pedidoActualizadoConID = api.enviarPedido(pedido);
        pedidoActualizadoConID.setIdPedido(pedidoActualizadoConID.getIdPedido().replace("\"", ""));
        pedido = pedidoActualizadoConID;
        avisarCocinero(pedido);
    }

    private void avisarCocinero(Pedido pedido) {
        int cocineroAsignado = (this.camarero.getIdCamarero() > 1) ? 1 : 0;
        CocineroThread cocineroThread = Hilos.hilosCocineros.get(cocineroAsignado);

        synchronized (cocineroThread) {
            cocineroThread.agregarTarea(new TareaCocina(pedido));
            cocineroThread.notify(); // Notifica al cocinero
        }
    }

    private void llevarAMesa(Pedido pedido, Plato plato) {
        System.out.println("El camarero " + camarero.getIdCamarero() + " lleva el plato: " + plato.getNombre());
        tiempoEstimado(tiempoLlevarPlato);
        plato.setEstado("servido");
        api.actualizarPedido(pedido.getIdPedido(), pedido);
    }

    private void llevarAMesa(Bebida bebida) {
        tiempoEstimado(tiempoLlevarBebida);
    }

    private void actualizarEstadoPedido(Pedido pedido) {
        api.actualizarEstadoPedido(pedido.getIdPedido(), pedido.getEstado());
    }

    public synchronized void agregarTarea(Tarea tarea) {
        synchronized (listaTareas) {
            listaTareas.add(tarea);
            listaTareas.notify(); // Despierta el hilo cuando se agrega una nueva tarea
        }
    }

    private void tiempoEstimado(int tiempo) {
        try {
            Thread.sleep(tiempo * 1000);
        } catch (InterruptedException e) {
            System.err.println("Error en tiempo estimado: " + e.getMessage());
        }
    }

    public Camarero getCamarero() {
        return camarero;
    }

    public void setCamarero(Camarero camarero) {
        this.camarero = camarero;
    }
}
