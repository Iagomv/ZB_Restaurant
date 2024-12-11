package Helpers;

public class ErrorMensajeConsola {
    public static void error(Exception error, String mensaje) {
        System.out.println(mensaje);
        error.printStackTrace();
    }
}
