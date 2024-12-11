package Model;

public class Camarero {
    private int idCamarero;
    private String estado;
    private Pedido pedido; 
    private Plato plato;


    public Camarero(int idCamarero, String estado, Pedido pedido, Plato plato) {
        this.idCamarero = idCamarero;
        this.estado = estado;
        this.pedido = pedido;
        this.plato = plato;
    }


    
    public int getIdCamarero() {
        return idCamarero;
    }
    public void setIdCamarero(int idCamarero) {
        this.idCamarero = idCamarero;
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


}