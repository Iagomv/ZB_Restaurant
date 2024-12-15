package Model;

public class Tarea {
    private String tipoTarea;
    private Pedido pedido;
    private Cliente cliente;
    private Plato plato;
    private Bebida bebida;

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

    // Constructor para tareas relacionadas con un pedido
    public Tarea(String tipoTarea, Pedido pedido, Plato plato) {
        this.tipoTarea = tipoTarea;
        this.pedido = pedido;
        this.plato = plato;
    }

    public Tarea(String tipoTarea, Pedido pedido, Bebida bebida) {
        this.tipoTarea = tipoTarea;
        this.pedido = pedido;
        this.bebida = bebida;
    }

    public Tarea(String tipoTarea, Plato plato) {
        this.tipoTarea = tipoTarea;
        this.plato = plato;
    }

    public Tarea(String tipoTarea, Bebida bebida) {
        this.tipoTarea = tipoTarea;
        this.bebida = bebida;
    }

    // Constructor para llevar bebida
    public Tarea(String tipoTarea, Pedido pedido, boolean esBebida) {
        this.tipoTarea = tipoTarea;
        this.pedido = pedido;
        this.esBebida = esBebida;
    }

    // Constructor para tomar pedido
    public Tarea(String tipoTarea, Cliente cliente, Pedido pedido) {
        this.tipoTarea = tipoTarea;
        this.cliente = cliente;
        this.pedido = pedido;
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

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Bebida getBebida() {
        return bebida;
    }

    public void setBebida(Bebida bebida) {
        this.bebida = bebida;
    }
}
