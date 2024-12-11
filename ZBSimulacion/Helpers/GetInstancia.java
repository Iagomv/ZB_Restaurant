package Helpers;

import java.util.Random;
import Model.*;
import Model.Cliente;

    public class GetInstancia{

        
        public Cocinero[] generarCocineros(int cantidadCocineros) {
            Cocinero[] cocineros = new Cocinero[cantidadCocineros];
            for (int i = 0; i < cantidadCocineros; i++) {
                cocineros[i] = new Cocinero();
            }
            return cocineros;
        }
        //* Devuelve el camarero de sala @cantidadMesas
        public CSala generarCSala(int cantidadMesas) {
            CSala CSala = new CSala(cantidadMesas);
            return CSala;
        }

        //* Devuelve array de camareros
        public Camarero[] generarCamareros(int cantidadCamareros) {
            Camarero[] camareros = new Camarero[cantidadCamareros];
            for (int i = 0; i < cantidadCamareros; i++) {
                camareros[i] = new Camarero(i, null, null, null);
            }
            return camareros;
        }  

        //* Generación eterna de clientes cada @tiempoEntreClientes Devuelve el cliente creado
        public Cliente generarClientes(int tiempoEntreClientes){
            Random random = new Random();
            
            while(true){
                int numeroPersonas = random.nextInt(4) + 1; // Genera un número aleatorio entre 1 y 4
                int contador = 0;
                try {
                    Thread.sleep(tiempoEntreClientes * 1000);
                    Cliente cliente = new Cliente(contador, numeroPersonas, 0, "En cola", null);
                    contador++;
                    System.out.println("Se ha generado un cliente con " + numeroPersonas + " personas");
                    return cliente;
                } catch (InterruptedException e) {
                    Helpers.ErrorMensajeConsola.error(e, "Error al generar clientes");
                }
            }
        }
    }
