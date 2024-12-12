package Model;

public class Tarea {
    private String tipoTarea;
    private Pedido pedido;
    private Cliente cliente;
    private boolean esBebida;

    // Constructor para tareas genéricas
    public Tarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    // Constructor para tareas relacionadas con un pedido
    public Tarea(String tipoTarea, Pedido pedido) {
        this.tipoTarea = tipoTarea;
        this.pedido = pedido;
    }

    // Constructor para llevar bebida/plato
    public Tarea(String tipoTarea, Pedido pedido, boolean esBebida) {
        this.tipoTarea = tipoTarea;
        this.pedido = pedido;
        this.esBebida = esBebida;
    }

    // Getters y setters
    public String getTipoTarea() {
        return tipoTarea;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isEsBebida() {
        return esBebida;
    }
}
