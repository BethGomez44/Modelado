package main.lib;

import main.lib.Lugar;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Conexion {

    private static final String API_KEY = "3efeff5fc6143eb4c78527b4a7b08f5f";
    private static final String ONLINE_SERVICE = "http://api.openweathermap.org";
    private static final String CONFIGURACIONES = "&units=metric&lang=es&appid=";
    private static int Num_conexiones = 0;

    public static int num_conexiones() {
        return Num_conexiones;
    }

    public static void actualizarCiudad(Lugar ciudad) {

        String peticion_ciudad = ONLINE_SERVICE + "/data/2.5/weather?q=" + ciudad.getNombre() + CONFIGURACIONES
                + API_KEY;

        String peticion_coord = ONLINE_SERVICE + "/data/2.5/weather?lat=" + ciudad.getCoorLat() + "&lon="
                + ciudad.getCoorLong() + CONFIGURACIONES + API_KEY;

        try {
            String linea = peticion(peticion_ciudad);
            if (linea == null && !ciudad.getCoorLat().isEmpty() && !ciudad.getCoorLong().isEmpty()) {
                linea = peticion(peticion_coord);
            }

            String[] updt = descompresor(linea);
            actualizacion(ciudad, updt);
        } catch (Exception e) {

            ciudad.setClima(" No disponible");
        }
    }

    private static String peticion(String urlp) {
        Num_conexiones++;
        try {

            URL url = new URL(urlp);
            URLConnection conexion = url.openConnection();
            BufferedReader lectura = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            return lectura.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 
     * sonObj String que representa un objeto Json con la informacion obtenida como
     * respuestaa del servidor
     * 
     * @return Arreglo [0]-pais [1]-longitud [2]-latitud [3]-descripcion
     *         [4]-temperatura[5]-sensacion termica, null en caso de que no se
     *         pudiera descomprimir el json de respuesta
     */
    private static String[] descompresor(String jsonObj) {

        try {
            Gson gson = new Gson();
            JsonElement elemento = gson.fromJson(jsonObj, JsonElement.class);
            JsonObject respuesta = elemento.getAsJsonObject();

            JsonElement clima = respuesta.get("weather");
            JsonObject estadoClima = clima.getAsJsonArray().get(0).getAsJsonObject();
            JsonObject main = respuesta.get("main").getAsJsonObject();
            JsonObject coordenadas = respuesta.get("coord").getAsJsonObject();
            JsonObject sys = respuesta.get("sys").getAsJsonObject();

            String temp = main.get("temp").getAsString();
            String sensacion_termica = main.get("feels_like").getAsString();
            String descripcion = estadoClima.get("description").getAsString();

            String longitud = coordenadas.get("lon").getAsString();
            String latitud = coordenadas.get("lat").getAsString();

            String pais = sys.get("country").getAsString();

            return new String[] { pais, longitud, latitud, descripcion, temp, sensacion_termica };

        } catch (Exception e) {
            return null;
        }
    }

    private static void actualizacion(Lugar ciudad, String[] mejora) {

        if (mejora != null) {

            ciudad.setTemperatura("Temperatura: " + rellenito(mejora[4],4) + "°C  Sensacion Termica: " +rellenito( mejora[5],4)+ "°C");
            ciudad.setClima(mejora[3]);

            if (ciudad.getPais().isEmpty())
                ciudad.setPais(mejora[0]);
            if (ciudad.getCoorLong().isEmpty())
                ciudad.setCoorLong(mejora[1]);
            if (ciudad.getCoorLat().isEmpty())
                ciudad.setCoorLat(mejora[2]);

        } else {
            ciudad.setClima(" No disponible");
        }

    }

    private static String rellenito(String campo, int n) {
        String aux = campo;
        for (int i = 0; i < n - campo.length(); i++) {
            aux += " ";
        }
        return aux;

    }

}