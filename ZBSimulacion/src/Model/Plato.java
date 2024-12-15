package Model;

public class Plato {
    private int id;

    private String nombre;
    private String tipo;
    private String precio;
    private String stock;
    private String estado;
    private boolean notificado;

    public Plato(String nombre, String tipo, String precio, String stock, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
    }

    public Plato(int id, String nombre, String tipo, String precio, String stock, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
    }

    public Plato(int id, String nombre, String tipo, String precio, String stock, String estado, boolean notificado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.notificado = notificado;
    }

    public String[] getParametrosComida() {
        return new String[] { this.nombre, this.tipo, this.precio, this.stock };
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Plato [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", precio=" + precio + ", stock=" + stock
                + "]";
    }

    public boolean isNotificado() {
        return notificado;
    }

    public void setNotificado(boolean notificado) {
        this.notificado = notificado;
    }

}
