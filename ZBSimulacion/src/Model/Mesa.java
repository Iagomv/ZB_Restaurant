package Model;

public class Mesa {
    private int numeroMesa;
    private int capacidadMesa;
    private String estadoMesa;
    private Pedido pedido;
    private Camarero camareroAsignado;
    private Cliente cliente;
    private boolean ocupada;

    public Mesa(int numeroMesa, int capacidadMesa, String estadoMesa, Pedido pedido, Camarero camareroAsignado,
            Cliente cliente) {
        this.numeroMesa = numeroMesa;
        this.capacidadMesa = capacidadMesa;
        this.estadoMesa = estadoMesa;
        this.pedido = pedido;
        this.camareroAsignado = camareroAsignado;
        this.cliente = cliente;
        // System.out.println("Mesa " + this.numeroMesa + " creada, con capacidad de " +
        // this.capacidadMesa + " comensales");
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getCapacidadMesa() {
        return capacidadMesa;
    }

    public void setCapacidadMesa(int capacidadMesa) {
        this.capacidadMesa = capacidadMesa;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Camarero getCamareroAsignado() {
        return camareroAsignado;
    }

    public void setCamareroAsignado(Camarero camareroAsignado) {
        this.camareroAsignado = camareroAsignado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isOcupada() {
        return (this.cliente == null) ? false : true;
    }

    @Override
    public String toString() {
        return "Mesa [numeroMesa=" + numeroMesa + ", capacidadMesa=" + capacidadMesa + ", estadoMesa=" + estadoMesa
                + ", pedido=" + pedido + ", camareroAsignado=" + camareroAsignado + ", cliente=" + cliente
                + ", ocupada=" + ocupada + "]";
    }
}
