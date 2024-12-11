package Model;

public class Sommelier {
    private String estado;
    private Pedido pedido;
    private Bebida bebida;

    public Sommelier(String estado, Pedido pedido, Bebida bebida) {
        this.estado = estado;
        this.pedido = pedido;
        this.bebida = bebida;
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

    public Bebida getBebida() {
        return bebida;
    }

    public void setBebida(Bebida bebida) {
        this.bebida = bebida;
    }

}
