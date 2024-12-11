package Model;

import java.util.Arrays;

public class Pedido {
    private int idPedido;
    private String estado;
    private Bebida bebidaPedido;
    private Plato[] platosPedido;
    private Mesa mesa;
    private Cliente cliente;

    public Pedido(int idPedido, String estado, Bebida bebidaPedido, Plato[] platosPedido, Mesa mesa, Cliente cliente) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.bebidaPedido = bebidaPedido;
        this.platosPedido = platosPedido;
        this.mesa = mesa;
        this.cliente = cliente;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Bebida getBebidaPedido() {
        return bebidaPedido;
    }

    public void setBebidaPedido(Bebida bebidaPedido) {
        this.bebidaPedido = bebidaPedido;
    }

    public Plato[] getPlatosPedido() {
        return platosPedido;
    }

    public void setPlatosPedido(Plato[] platosPedido) {
        this.platosPedido = platosPedido;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", estado=" + estado + ", bebidaPedido=" + bebidaPedido
                + ", platosPedido=" + Arrays.toString(platosPedido) + ", mesa=" + mesa + ", cliente=" + cliente + "]";
    }
}
