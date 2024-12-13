package Model;

public class TareaCocina {
    private Plato plato;
    private Pedido pedido;

    // Constructor para tareasCocineros
    public TareaCocina(Plato plato) {
        this.plato = plato;
    }

    public TareaCocina(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
