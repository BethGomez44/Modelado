package main.app;

import main.lib.*;

import java.util.AbstractMap;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        // prueba de hash map

        HashMap<String, Lugar> cache = new HashMap<String, Lugar>();
        Lector.scaner1(cache);
        Lector.scaner2(cache);

      
        /* 
        String ciudad = "Paris";
        Conexion.actualizarCiudad(cache.get(ciudad));
        System.out.println(cache.get(ciudad));*/

        int i = 1;
        for(Lugar e : cache.values()){
            
            Conexion.actualizarCiudad(e);
            System.out.println("***"+i+") "+e.toString());
            i++;
            if(Conexion.num_conexiones() % 59== 0){
                
                try {
                    Thread.sleep(65*1000);
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        }
        System.out.println("Num conexiones " + Conexion.num_conexiones() );

    }
}