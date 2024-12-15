package Model;

public class Bebida {

    private int id_bebida;
    private String nombre;
    private String tipo;
    private String precio;
    private String stock;
    private String estado;

    public Bebida(String nombre, String tipo, String precio, String stock, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
    }

    public Bebida(int id_bebida, String nombre, String tipo, String precio, String stock, String estado) {
        this.id_bebida = id_bebida;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
    }

    public Bebida() {

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Bebida nombre=" + nombre + ", tipo=" + tipo + ", precio=" + precio + ", stock=" + stock
                + "]";
    }
}
