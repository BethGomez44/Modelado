import java.util.Random;

/**
 * Clase para modelar una canica.
 * 
 * @version 17/10/2020
 * @author Gómez de la Torre Heidi Lizbeth
 */
public class Canica {

    public int numeroCan;
    private Random valor = new Random();

    /**
     * Constructor por omisión de una Canica
     */
    public Canica() {

        numeroCan = valor.nextInt(10);

    }

    /**
     * Constructor por parametros que se le pasa el intervalo de valores del número
     * de la Canica.class
     * 
     * @param nuevoRango - Rango del número de la canica.
     */
    public Canica(int nuevoRango) {

        numeroCan = valor.nextInt(nuevoRango);

    }

    /**
     * Método que nos regresa el número de nuestra canica.
     */
    public int obtenerNumero() {

        return this.numeroCan;

    }
    
    /**
     * Método para comparar un objeto Canica con otro.
     */
    public boolean comparteTo(Canica o) {

        Canica aux = o;
        if (this.numeroCan == aux.numeroCan) {
            return true;
        }
        return false;
    }

}