package Static;

import java.util.ArrayList;
import java.util.List;

import Model.Bebida;
import Model.Plato;
import Sql.ConsultasSQL;

public class Carta {
    public static Plato[] platosCarta;
    public static ArrayList<Plato> entrantes;
    public static ArrayList<Plato> primeros;
    public static ArrayList<Plato> postres;
    public static List<Bebida> bebidas;
    public static int numeroEntrantes = 0, numeroPrimeros = 0, numeroPostres = 0, numeroBebidas = 0;
}
