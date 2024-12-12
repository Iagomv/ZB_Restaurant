package Threads;

import java.util.concurrent.Semaphore;

import Model.Camarero;
import Model.Pedido;
import Static.Pedidos;

public class CamareroThread implements Runnable {

    // Semáforo para sincronizar el acceso del camarero a las acciones
    private Semaphore semaforo;

    // Referencia al camarero
    private Camarero camarero;

    public CamareroThread(Camarero camarero) {
        this.camarero = camarero;
        // Inicializamos el semáforo con un permiso de 1 para que solo un hilo pueda
        // ejecutarlo a la vez
        this.semaforo = new Semaphore(1);
    }

    @Override
    public void run() {
        realizarAccionesCamarero();
    }

    // Método que contiene la lógica de las acciones del camarero
    private void realizarAccionesCamarero() {
        // Ejemplo de acciones que el camarero podría realizar
        // Simula que el camarero hace una tarea, como tomar un pedido
        tiempoEstimado(3); // Simula el tiempo que tarda el camarero en tomar un pedido

        // Crear una comanda para el pedido que el camarero acaba de recibir
        crearComanda(camarero.getPedido());

        // Otra acción que podría hacer el camarero, como llevar el pedido a la cocina o
        // servir la comida
        // Aquí agregamos un ejemplo de cómo podrían continuar las acciones.
        // llevarComandaCocina(camarero.getPedido()); // Comentado por ejemplo
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

    // Método para crear una comanda para el pedido del camarero
    public void crearComanda(Pedido pedido) {
        Pedidos.pedidos.add(pedido);
        Pedidos.entrantes.add(pedido.getEntrante());
        Pedidos.primeros.add(pedido.getPrimero());
        Pedidos.postres.add(pedido.getPostre());
        Pedidos.bebidas.add(pedido.getBebidaPedido());
    }

    // Aquí puedes agregar otros métodos si el camarero necesita realizar más
    // acciones
    // Ejemplo: llevarComandaCocina, servirComida, etc.
}
