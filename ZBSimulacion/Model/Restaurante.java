package Model;

public class Restaurante {
    private CSala CSala = new CSala(10);
    private Camarero[] camareros;
    private Cocinero[] cocineros;
    private Sommelier sommelier;
    private Mesa[] mesas;

    public Restaurante(Camarero[] camareros, Cocinero[] cocineros, CSala CSala, Sommelier sommelier, Mesa[] mesas) {
        this.camareros = camareros;
        this.cocineros = cocineros;
        this.CSala = CSala;
        this.sommelier = sommelier;
        this.mesas = mesas;
    }
}
