package Model;

public class Mesa {
    private int numeroMesa;
    private int capacidadMesa;
    private String estadoMesa;
    private Pedido pedido;
    private Camarero camarero;
    private Cliente cliente;

    public Mesa(int numeroMesa, int capacidadMesa, String estadoMesa, Pedido pedido, Camarero camarero, Cliente cliente) {
        this.numeroMesa = numeroMesa;
        this.capacidadMesa = capacidadMesa;
        this.estadoMesa = estadoMesa;
        this.pedido = pedido;
        this.camarero = camarero;
        this.cliente = cliente;
    }


}
