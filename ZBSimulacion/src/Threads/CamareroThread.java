package Threads;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import API.*;
import Model.Camarero;
import Model.Cliente;
import Model.Pedido;
import Model.Tarea;
import Static.Pedidos;

public class CamareroThread implements Runnable {

    // Semáforo para sincronizar el acceso del camarero a las acciones
    private Semaphore semaforo;
    ArrayList<Tarea> listaTareas = new ArrayList();
    InsertarAPI api = new InsertarAPI();
    private int tiempoTomarPedido = 2; // Tiempo estimado en segundos para tomar un pedido
    private int tiempoLlevarBebida = 2; // Tiempo estimado en segundos para levar una bebida
    private int tiempoLlevarPlato = 4; // Tiempo estimado en segundos para llevar el plato
    private Camarero camarero;

    public CamareroThread(Camarero camarero) {
        this.camarero = camarero;
        this.semaforo = new Semaphore(1);
    }

    @Override
    public void run() {
        realizarAccionesCamarero();
    }

    // Método que contiene la lógica de las acciones del camarero
    private synchronized void realizarAccionesCamarero() {
        while (true) {
            try {
                // Esperar mientras no haya tareas
                synchronized (this) {
                    while (listaTareas.isEmpty()) {
                        wait();
                    }
                }
                semaforo.acquire();
                Tarea tarea = listaTareas.remove(0);
                semaforo.release();

                switch (tarea.getTipoTarea()) {
                    case "tomarPedido":
                        tomarPedido(tarea.getCliente(), tarea.getPedido());
                        break;
                    case "llevarBebida":
                        llevarAMesa(tarea.getPedido(), true);
                        break;
                    case "llevarPlato":
                        llevarAMesa(tarea.getPedido(), false);
                        break;
                    case "actualizarEstado":
                        actualizarEstadoPedido(tarea.getPedido());
                        break;
                    default:
                        System.err.println("Tipo de tarea desconocido: " + tarea.getTipoTarea());
                }
            } catch (InterruptedException e) {
                System.err.println("Error en el manejo de tareas: " + e.getMessage());
            }
        }
    }

    // Simula el tiempo estimado que tarda en realizarse una acción
    private void tiempoEstimado(int tiempo) {
        try {
            Thread.sleep(tiempo * 1000); // El tiempo de espera está en segundos
        } catch (InterruptedException e) {
            System.err.println("Interruted Exception en CamareroThread");
            e.printStackTrace();
        }
    }

    // Métodos para realizar las acciones del camarero
    public void tomarPedido(Cliente cliente, Pedido pedido) {
        tiempoEstimado(tiempoTomarPedido);
        Pedido pedidoActualizadoConID = api.enviarPedido(pedido);
        pedido = pedidoActualizadoConID;
    }

    public void llevarAMesa(Pedido pedido, boolean esBebida) {
        tiempoEstimado(esBebida ? tiempoLlevarBebida : tiempoLlevarPlato);
    }

    public boolean comprobarEstadoPedido(Pedido pedido) {
        if (pedido.getBebidaPedido().isEntregada() && pedido.getEntrante().isEntregado()
                && pedido.getPrimero().isEntregado() && pedido.getPostre().isEntregado()) {
            pedido.setEstado("Completado");
            return true;
        }
        return false;
    }

    public void actualizarEstadoPedido(Pedido pedido) {
        api.actualizarEstadoPedido(pedido.getIdPedido(), pedido.getEstado());
    }

    // Nuevas Tareas
    public synchronized void agregarTarea(Tarea tarea) {
        listaTareas.add(tarea);
        notify(); // Despertar el hilo cuando se agrega una nueva tarea
    }

}
