package Static;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Camarero;
import Model.Cocinero;
import Model.TareaCocina;
import Threads.CamareroThread;
import Threads.CocineroThread;

public class Hilos {
    public static HashMap<Camarero, CamareroThread> hilosCamareros = new HashMap<>();
    public static ArrayList<CocineroThread> hilosCocineros = new ArrayList<>();

    // public static SommelierThread hiloSommelier;
    public static ArrayList<TareaCocina> listaTareasCocineros = new ArrayList<>();

}
