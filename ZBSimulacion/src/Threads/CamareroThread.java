/**
 * Clase que implementa el comportamiento de un hilo de camarero.
 * Se encarga de gestionar y procesar tareas relacionadas con la atención al cliente.
 */
package Threads;

import java.sql.SQLException;
import java.util.ArrayList;
import API.*;
import Model.*;
import Sql.ConsultasSQL;
import Static.*;

/**
 * Clase que representa un hilo para gestionar las tareas de un camarero.
 */
public class CamareroThread implements Runnable {

    /**
     * Lista de tareas pendientes del camarero.
     */
    private ArrayList<Tarea> listaTareas = new ArrayList<>();

    /**
     * API utilizada para interactuar con el sistema de pedidos.
     */
    private InsertarAPI api = new InsertarAPI();

    /**
     * Tiempo estimado para tomar un pedido, definido en TiemposEspera.
     */
    private int tiempoTomarPedido = TiemposEspera.tiempoTomarPedido;

    /**
     * Tiempo estimado para llevar una bebida, definido en TiemposEspera.
     */
    private int tiempoLlevarBebida = TiemposEspera.tiempoLlevarBebida;

    /**
     * Tiempo estimado para llevar un plato, definido en TiemposEspera.
     */
    private int tiempoLlevarPlato = TiemposEspera.tiempoLlevarPlato;

    /**
     * Tiempo estimado para cobrar un pedido, definido en TiemposEspera.
     */
    private int tiempoCobrarPedido = TiemposEspera.tiempoCobrarPedido;

    /**
     * Estado del pedido cuando ha sido servido.
     */
    private String estadoServido = Estados.estadoServido;

    /**
     * Estado del pedido cuando ha sido cobrado.
     */
    private String estadoCobrado = Estados.estadoCobrado;

    /**
     * Instancia del camarero que ejecuta este hilo.
     */
    private Camarero camarero;

    /**
     * Constructor de la clase.
     * 
     * @param camarero Camarero asignado a este hilo.
     */
    public CamareroThread(Camarero camarero) {
        this.camarero = camarero;
    }

    /**
     * Método principal del hilo. Procesa las tareas asignadas al camarero.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Tarea tarea;
                synchronized (listaTareas) {
                    while (listaTareas.isEmpty()) {
                        listaTareas.wait();
                    }
                    tarea = listaTareas.remove(0);
                }
                procesarTarea(tarea);
            } catch (InterruptedException e) {
                System.err.println("Error en el hilo del camarero: " + e.getMessage());
            }
        }
    }

    /**
     * Procesa una tarea específica.
     * 
     * @param tarea Tarea a procesar.
     */
    private void procesarTarea(Tarea tarea) {
        switch (tarea.getTipoTarea()) {
            case "tomarPedido":
                tomarPedido(tarea.getCliente(), tarea.getPedido());
                break;
            case "llevarBebida":
                llevarAMesa(tarea.getPedido(), tarea.getBebida());
                break;
            case "llevarPlato":
                llevarAMesa(tarea.getPedido(), tarea.getPlato());
                break;
            case "cobrarPedido":
                cobrarPedido(tarea.getPedido());
                break;
            case "actualizarEstado":
                actualizarEstadoPedido(tarea.getPedido());
                break;
            default:
                System.err.println("Tipo de tarea desconocido: " + tarea.getTipoTarea());
        }
    }

    /**
     * Toma un pedido de un cliente.
     * 
     * @param cliente Cliente al que se toma el pedido.
     * @param pedido  Pedido que se toma.
     */
    private void tomarPedido(Cliente cliente, Pedido pedido) {
        tiempoEstimado(tiempoTomarPedido);
        Pedido pedidoActualizadoConID = api.enviarPedido(pedido);
        pedidoActualizadoConID.setIdPedido(pedidoActualizadoConID.getIdPedido().replace("\"", ""));
        pedido = pedidoActualizadoConID;
        if (!pedido.getBebidaPedido().getTipo().toLowerCase().equals("vino")) {
            agregarTarea(new Tarea("llevarBebida", pedido, pedido.getBebidaPedido()));
        } else {
            Hilos.hiloSommelier.agregarTarea(new Tarea("llevarBebida", pedido, pedido.getBebidaPedido()));
        }
        System.out.println("Tomando pedido" + pedido.toString());
        avisarCocinero(pedido);
    }

    /**
     * Cobra un pedido.
     * 
     * @param pedido Pedido a cobrar.
     */
    private void cobrarPedido(Pedido pedido) {
        tiempoEstimado(tiempoCobrarPedido);
        pedido.setEstado(estadoCobrado);
        api.actualizarPedido(pedido.getIdPedido(), pedido);
        System.out.println("Cobrando pedido");
        agregarIngreso(Double.parseDouble(pedido.getPrecio()));
        limpiarMesa(pedido);
    }

    /**
     * Limpia una mesa después de que el pedido ha sido cobrado.
     * 
     * @param pedido Pedido asociado a la mesa.
     */
    private void limpiarMesa(Pedido pedido) {
        pedido.getMesa().limpiarMesa();
        Personal.cSala.release();
    }

    /**
     * Verifica si un pedido está completo.
     * 
     * @param pedido Pedido a verificar.
     * @return true si el pedido está completo, false en caso contrario.
     */
    private boolean pedidoCompleto(Pedido pedido) {
        System.out.println("Estado servido: " + estadoServido);
        boolean bebidaEntregada = (pedido.getBebidaPedido().getEstado().equals(estadoServido));
        boolean entranteEntregado = (pedido.getEntrante().getEstado().equals(estadoServido));
        boolean primeroEntregado = (pedido.getPrimero().getEstado().equals(estadoServido));
        boolean postreEntregado = (pedido.getPostre().getEstado().equals(estadoServido));
        return bebidaEntregada && entranteEntregado && primeroEntregado && postreEntregado;
    }

    /**
     * Notifica al cocinero que prepare un pedido.
     * 
     * @param pedido Pedido que debe prepararse.
     */
    private void avisarCocinero(Pedido pedido) {
        int cocineroAsignado = (this.camarero.getIdCamarero() > 1) ? 1 : 0;
        CocineroThread cocineroThread = Hilos.hilosCocineros.get(cocineroAsignado);

        synchronized (cocineroThread) {
            cocineroThread.agregarTarea(new TareaCocina(pedido));
            cocineroThread.notify();
        }
    }

    /**
     * Lleva un plato a una mesa.
     * 
     * @param pedido Pedido asociado al plato.
     * @param plato  Plato que se lleva a la mesa.
     */
    private void llevarAMesa(Pedido pedido, Plato plato) {
        plato.setEstado(estadoServido);
        System.out.println("El camarero " + camarero.getIdCamarero() + " lleva el plato: " + plato.toString());
        tiempoEstimado(tiempoLlevarPlato);
        plato.setNotificado(true);
        if (pedidoCompleto(pedido)) {
            cobrarPedido(pedido);
        }
        api.actualizarPedido(pedido.getIdPedido(), pedido);
    }

    /**
     * Lleva una bebida a una mesa.
     * 
     * @param pedido Pedido asociado a la bebida.
     * @param bebida Bebida que se lleva a la mesa.
     */
    private void llevarAMesa(Pedido pedido, Bebida bebida) {
        tiempoEstimado(tiempoLlevarBebida);
        System.out.println("El camarero " + camarero.getIdCamarero() + " lleva la bebida: " + bebida.getNombre());
        bebida.setEstado(estadoServido);
        api.actualizarPedido(pedido.getIdPedido(), pedido);
    }

    private void agregarIngreso(double importe) {
        ConsultasSQL consultasSQL = new ConsultasSQL();
        try {
            consultasSQL.agregarIngreso(importe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado de un pedido.
     * 
     * @param pedido Pedido cuyo estado se actualiza.
     */
    private void actualizarEstadoPedido(Pedido pedido) {
        api.actualizarEstadoPedido(pedido.getIdPedido(), pedido.getEstado());
    }

    /**
     * Agrega una tarea a la lista de tareas del camarero.
     * 
     * @param tarea Tarea a agregar.
     */
    public synchronized void agregarTarea(Tarea tarea) {
        synchronized (listaTareas) {
            listaTareas.add(tarea);
            listaTareas.notify();
        }
    }

    /**
     * Simula un tiempo estimado de espera.
     * 
     * @param tiempo Tiempo en segundos.
     */
    private void tiempoEstimado(int tiempo) {
        try {
            Thread.sleep(tiempo * 1000);
        } catch (InterruptedException e) {
            System.err.println("Error en tiempo estimado: " + e.getMessage());
        }
    }

    /**
     * Obtiene el camarero asociado a este hilo.
     * 
     * @return Camarero asociado.
     */
    public Camarero getCamarero() {
        return camarero;
    }

    /**
     * Establece el camarero asociado a este hilo.
     * 
     * @param camarero Nuevo camarero.
     */
    public void setCamarero(Camarero camarero) {
        this.camarero = camarero;
    }
}
