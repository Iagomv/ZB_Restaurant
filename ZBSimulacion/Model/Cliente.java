package Model;

public class Cliente extends Thread {
    private int numeroCliente;
    private int numeroComensales;
    private int numeroMesa;
    private String estado;
    private Pedido pedido;

    public Cliente(int numeroCliente, int numeroComensales, int numeroMesa, String estado, Pedido pedido) {
        this.numeroCliente = numeroCliente;
        this.numeroComensales = numeroComensales;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.pedido = pedido;
    }

    public void generarPedido() {

    }

    public int getNumeroComensales() {
        return numeroComensales;
    }

    public void setNumeroComensales(int numeroComensales) {
        this.numeroComensales = numeroComensales;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(int numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    @Override
    public String toString() {
        return "Cliente [numeroCliente=" + numeroCliente + ", numeroComensales=" + numeroComensales + ", numeroMesa="
                + numeroMesa + ", estado=" + estado + ", pedido=" + pedido + "]";
    }
}
