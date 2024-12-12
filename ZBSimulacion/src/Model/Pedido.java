package Model;

import java.util.Arrays;

public class Pedido {
    private int idPedido;
    private String estado;
    private Bebida bebidaPedido;
    private Plato entrante;
    private Plato primero;
    private Plato postre;

    private Mesa mesa;

    private Cliente cliente;
    private String precio;

    public Pedido(Integer idPedido, String estado, Bebida bebidaPedido, Plato entrante, Plato primero, Plato postre,
            Mesa mesa, Cliente cliente) {

        this.idPedido = idPedido == null ? null : idPedido;
        this.estado = estado;
        this.bebidaPedido = bebidaPedido;
        this.entrante = entrante;
        this.primero = primero;
        this.postre = postre;
        this.mesa = mesa;
        this.cliente = cliente;
        this.precio = calcularPrecioPedido();
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

    public Plato getEntrante() {
        return entrante;
    }

    public void setEntrante(Plato entrante) {
        this.entrante = entrante;
    }

    public Plato getPrimero() {
        return primero;
    }

    public void setPrimero(Plato primero) {
        this.primero = primero;
    }

    public Plato getPostre() {
        return postre;
    }

    public void setPostre(Plato postre) {
        this.postre = postre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String calcularPrecioPedido() {
        double precioTotal = 0;
        double precioEntrante = Double.parseDouble(entrante.getPrecio().replace(",", "."));
        double precioPrimero = Double.parseDouble(primero.getPrecio().replace(",", "."));
        double precioPostre = Double.parseDouble(postre.getPrecio().replace(",", "."));
        double precioBebida = Double.parseDouble(bebidaPedido.getPrecio().replace(",", "."));

        precioTotal = precioEntrante + precioPrimero + precioPostre + precioBebida;
        return String.valueOf(precioTotal);
    }

    public String getDescripcion() {
        return "Pedido [id=" + idPedido + ", bebida=" + bebidaPedido + ", estado=" + estado + "]";
    }

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", estado=" + estado + ", bebidaPedido=" + bebidaPedido + ", entrante="
                + entrante + ", primero=" + primero + ", postre=" + postre + ", mesa=" + mesa + ", cliente=" + cliente
                + ", precio=" + precio + "]";
    }
}
