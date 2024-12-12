package Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import Static.Carta;

public class ProcessHandler {

    public String[] elegirPlatos() {
        String datos = Carta.numeroEntrantes + "," + Carta.numeroPrimeros + "," + Carta.numeroPostres + ","
                + Carta.numeroBebidas;
        String[] argumentoProceso = { "java", "EligePlato", datos };

        try {
            ProcessBuilder pb = new ProcessBuilder(argumentoProceso);
            Process procesoPremios = pb.start();
            BufferedReader br = procesoPremios.inputReader();
            String linea = br.readLine();
            String[] elecciones = linea.split(",");
            // System.out.println(elecciones[0] + "," + elecciones[1] + "," + elecciones[2]
            // + "," + elecciones[3]);
            return elecciones;
        } catch (IOException ex) {
            System.err.println("Excepción de E/S!!");
            System.exit(-1);
        }
        return null;
    }

}
