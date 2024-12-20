package Model;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pedido {
    @JsonIgnore // Ignora el campo "_id" del JSON
    private String _id; // Este campo no se utilizará en tu clase, se ignorará
    @JsonProperty("idPedido") // Mapea el campo "idPedido" del JSON al campo idPedido
    private String idPedido;
    public String estado;
    public Bebida bebidaPedido;
    public Plato entrante;
    public Plato primero;
    public Plato postre;
    public Mesa mesa;
    public Cliente cliente;
    public String precio;
    public Camarero camarero;

    public Pedido() {
    }

    public Pedido(String idPedido, String estado, Bebida bebidaPedido, Plato entrante, Plato primero, Plato postre,
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

    public Pedido(String idPedido, String estado, Bebida bebidaPedido, Plato entrante, Plato primero, Plato postre,
            Mesa mesa, Cliente cliente, Camarero camarero) {

        this.idPedido = idPedido == null ? null : idPedido;
        this.estado = estado;
        this.bebidaPedido = bebidaPedido;
        this.entrante = entrante;
        this.primero = primero;
        this.postre = postre;
        this.mesa = mesa;
        this.cliente = cliente;
        this.precio = calcularPrecioPedido();
        this.camarero = camarero;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
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

    public Plato[] getPlatos() {
        return new Plato[] { entrante, primero, postre };
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

    public ArrayList<Plato> getPlatosNotificados() {
        ArrayList<Plato> notificados = new ArrayList<>();
        for (Plato plato : getPlatos()) {
            if (plato != null && plato.isNotificado()) {
                notificados.add(plato);
            }
        }
        return notificados;
    }

    public String getDescripcion() {
        return "Pedido [id=" + idPedido + ", bebida=" + bebidaPedido + ", estado=" + estado + "]";
    }

    @Override
    public String toString() {
        return "Pedido idPedido=" + idPedido + ", bebidaPedido=" + bebidaPedido.getEstado()
                + ", entrante="
                + entrante.getEstado() + ", primero=" + primero.getEstado() + ", postre=" + postre.getEstado()
                + ", mesa=" + mesa.getNumeroMesa();
    }
}
