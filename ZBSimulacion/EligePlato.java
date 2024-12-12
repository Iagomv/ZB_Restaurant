import java.util.ArrayList;
import java.util.Random;

public class EligePlato {

    public static void main(String[] args) {
        Random random = new Random();
        ArrayList<Integer> platoSeleccionado = new ArrayList<Integer>();
        if (args.length > 0) {
            String[] elecciones = args[0].split(",");
            for (String string : elecciones) {
                int numero = random.nextInt(Integer.parseInt(string));
                platoSeleccionado.add(numero);
            }
            System.out.println(platoSeleccionado.get(0) + "," + platoSeleccionado.get(1) + ","
                    + platoSeleccionado.get(2) + "," + platoSeleccionado.get(3));
        }

    }

}
