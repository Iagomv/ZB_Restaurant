package Model;

public class Bebida {

    private int id_bebida;
    private String nombre;
    private String tipo;
    private String precio;
    private String stock;
    private boolean entregada;

    public Bebida(String nombre, String tipo, String precio, String stock, boolean entregada) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.entregada = entregada;
    }

    public Bebida(int id_bebida, String nombre, String tipo, String precio, String stock, boolean entregada) {
        this.id_bebida = id_bebida;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.entregada = entregada;
    }

    public String[] getParametrosBebida() {
        return new String[] { this.nombre, this.tipo, this.precio, this.stock };
    }

    public int getId() {
        return id_bebida;
    }

    public void setId(int id_bebida) {
        this.id_bebida = id_bebida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getId_bebida() {
        return id_bebida;
    }

    public void setId_bebida(int id_bebida) {
        this.id_bebida = id_bebida;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    @Override
    public String toString() {
        return "Bebida nombre=" + nombre + ", tipo=" + tipo + ", precio=" + precio + ", stock=" + stock
                + "]";
    }
}
