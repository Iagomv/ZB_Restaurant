package Handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Bebida;
import Model.Plato;
import Static.Carta;

public class CartaHandler {
    Plato[] platosCarta;
    Plato[] primeros;
    Plato[] postres;

    Consultas.ConsultasSQL SQL = new Consultas.ConsultasSQL();

    public List<Bebida> getBebidas() {
        try {
            return SQL.obtenerbebidas();
        } catch (SQLException e) {
            System.out.println("Hubo un error al obtener las bebidas Handler");
            e.printStackTrace();
        }
        return null;
    }

    // Metodos para obtener los platos distintols platos de la carta

    public Plato[] getPlatos() {
        try {
            return SQL.obtenerPlatos().toArray(new Plato[0]);
        } catch (Exception e) {
            System.out.println("Hubo un error al obtener los platos Handler");
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Plato> getEntrantes(Plato[] platosCarta) {
        ArrayList<Plato> entrantes = new ArrayList<>();
        for (Plato plato : platosCarta) {
            if (plato.getTipo().equals("Entrante")) {
                entrantes.add(plato);
            }
        }
        return entrantes;
    }

    public ArrayList<Plato> getPrimeros(Plato[] platosCarta) {
        ArrayList<Plato> primeros = new ArrayList<>();
        for (Plato plato : platosCarta) {
            if (plato.getTipo().equals("Primero")) {
                primeros.add(plato);
            }
        }
        return primeros;
    }

    public ArrayList<Plato> getPostres(Plato[] platosCarta) {
        ArrayList<Plato> postres = new ArrayList<>();
        for (Plato plato : platosCarta) {
            if (plato.getTipo().equals("Postre")) {
                postres.add(plato);
            }
        }
        return postres;
    }

    public int getNumeroEntrantes() {
        return Carta.entrantes.size();
    }

    public int getNumeroPrimeros() {
        return Carta.primeros.size();
    }

    public int getNumeroPostres() {
        return Carta.postres.size();
    }

    public int getNumeroBebidas() {
        return Carta.bebidas.size();
    }

}
