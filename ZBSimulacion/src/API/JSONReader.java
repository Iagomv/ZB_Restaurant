package API;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONReader {

    public JSONReader(String url) {
        try {
            obtenerDatos(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // * leearchivo -> obtenerPokemons -> obtenemos Pokemons y URLS*/
    private void obtenerDatos(String url) throws Exception {
        // pokeList = new ArrayList<>();
        JSONObject json = leerArchivo(url);
        if (json != null) {
            // Extraer Pokémon y URLs
            JSONArray jsonArray = (JSONArray) json.get("results");
            // obtenerPokemons(pokeList, jsonArray);
            // previousUrl = (String) json.get("previous");
            // nextUrl = (String) json.get("next");
        }

        else {
            System.out.println("wtf no hay JSON?");
        }
    }

    // Método Moncho URL -> JSONObject/
    public static JSONObject leerArchivo(String url) throws Exception {
        InputStream is = null;
        BufferedReader br = null;
        try {
            String linea;
            StringBuilder jsonText = new StringBuilder();

            is = new URL(url).openStream();
            br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            while ((linea = br.readLine()) != null) {
                jsonText.append(linea);
            }

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(jsonText.toString());
        } catch (Exception e) {
            System.out.println("Hubo un error al obtener el JSON de la URL: " + e.getMessage());
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

}
