package Static;

import java.util.Random;

public class TiemposEspera {
    static Random random = new Random();

    // CAMARERO
    public static final int tiempoTomarPedido = 8;
    public static final int tiempoLlevarBebida = 4;
    public static final int tiempoLlevarPlato = 6;
    public static final int tiempoCobrarPedido = 8;

    // COCINERO
    public static final int tiempoPrepararEntrante = random.nextInt(6, 10) + 1;
    public static final int tiempoPrepararPrimero = random.nextInt(10, 12) + 1;
    public static final int tiempoPrepararPostre = random.nextInt(4, 8) + 1;

    // SOMMELIER
    public static final int tiempoServirVino = 14;

    // RESTAURANTE
    public static final int tiempoEntreClientes = 1;
    public static final int cantidadCamareros = 4;
    public static final int cantidadCocineros = 2;
    public static final int cantidadMesas = 10;

}
