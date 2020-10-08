package main.lib;

import java.io.*;
import java.util.AbstractMap;
import java.util.HashMap;
import main.lib.Lugar;

public class Lector {

    public static void scaner1(HashMap<String, Lugar> catalogo) {
        
        HashMap<String, String> ciudades = obtenerCiudades();
        String data1 = "files/dataset1.csv";


       try {
        BufferedReader in = new BufferedReader(new FileReader(data1));
        String linea = in.readLine(); // encabezado

        while ((linea = in.readLine())!= null) {
            String[] campos = linea.split(",", 0);
            String origen =  ciudades.get(campos[0]).replaceAll("[0-9]*", "").trim();;
            String destino = ciudades.get(campos[1]).replaceAll("[0-9]*", "").trim();;

            if(origen!= null && catalogo.get(origen) == null){
                catalogo.put(origen, new Lugar(origen, "", campos[2], campos[3], "", ""));
            }
            if(destino!= null && catalogo.get(destino) == null){
                catalogo.put(destino, new Lugar(destino, "", campos[2], campos[3], "", ""));
            }
        }
        in.close();
       } catch (Exception e) {
           //error
       }

    }

    public static void scaner2(HashMap<String, Lugar> catalogo) {
        String data2 = "files/dataset2.csv";
        try {
            BufferedReader in = new BufferedReader(new FileReader(data2));
            String linea = in.readLine(); // encabezado
    
            while ((linea = in.readLine())!= null) {
                String[] campos = linea.split(",", 0);
                String ciudad = campos[0].replaceAll("[0-9]*", "").trim();

                if(catalogo.get(ciudad)==null){
                    catalogo.put(ciudad,new Lugar(ciudad, "","","", "", ""));
                }
    
            }
            in.close();
           } catch (Exception e) {
               //error
           }
    
    }
    



    /**
     * Metodo que obtiene un diccionario con el codigo IATA como clave y la ciudad donde se encuentra el aeropuerto
     * @return
     */
    private static HashMap<String, String> obtenerCiudades() {
        String rutaGuardado = "files/systemFiles/iatakey.dat";
        String rutaExtraccion = "files/systemFiles/iatakey.csv";
        // intentar leer archivo
        

        try {
            
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaGuardado));
            @SuppressWarnings("unchecked")
            HashMap<String, String> ciudades = (HashMap<String, String>) entrada.readObject();
            entrada.close();

            return ciudades;
        } catch (Exception e) {
            try {
                HashMap<String, String> ciudades = new HashMap<String, String>();
                BufferedReader in = new BufferedReader(new FileReader(rutaExtraccion));
                String linea = in.readLine(); // encabezado

                while ((linea = in.readLine())!= null) {
                    String[] campos = linea.split(",", 0);
                    ciudades.put(campos[3], campos[1]);
                }

                if (ciudades.isEmpty()) {
                    throw new Exception("archivo vacio");
                } else {
                    ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaGuardado));
                    salida.writeObject(ciudades);
                    salida.close();

                    return ciudades;
                }

            } catch (Exception m) {
               
                System.out.println(
                        "Falla fatal E-LEC001: se han dañado o eliminado archivos esenciales para el correcto funcionamiento del programa, contacte inmediatamente a soporte.");
                System.exit(-1);
            }
        }
        return null;
    }


}