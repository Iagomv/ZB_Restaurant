package Static;

import java.util.Random;

public class TiemposEspera {
    static Random random = new Random();

    // CAMARERO
    public static final int tiempoTomarPedido = 2;
    public static final int tiempoLlevarBebida = 2;
    public static final int tiempoLlevarPlato = 2;
    public static final int tiempoCobrarPedido = 2;

    // COCINERO
    public static final int tiempoPrepararEntrante = random.nextInt(1, 10) + 1;
    public static final int tiempoPrepararPrimero = random.nextInt(1, 12) + 1;
    public static final int tiempoPrepararPostre = random.nextInt(1, 8) + 1;

    // SOMMELIER
    public static final int tiempoServirVino = 4;

    // RESTAURANTE
    public static final int tiempoEntreClientes = 60;
    public static final int cantidadCamareros = 4;
    public static final int cantidadCocineros = 2;
    public static final int cantidadMesas = 10;

}
