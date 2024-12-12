package Model;

public class Cocinero {
    private int idCocinero;
    private String estado;
    private Pedido pedido;
    private Plato plato;

    public Cocinero(int idCocinero, String estado, Pedido pedido, Plato plato) {
        this.idCocinero = idCocinero;
        this.estado = estado;
        this.pedido = pedido;
        this.plato = plato;
        // System.out.println("Cocinero: " + idCocinero + " creado");
    }

    public int getIdCocinero() {
        return idCocinero;
    }

    public void setIdCocinero(int idCocinero) {
        this.idCocinero = idCocinero;
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

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    @Override
    public String toString() {
        return "Cocinero [idCocinero=" + idCocinero + ", estado=" + estado + ", pedido=" + pedido + ", plato=" + plato
                + "]";
    }
}