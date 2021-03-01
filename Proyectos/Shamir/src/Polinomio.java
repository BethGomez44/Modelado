import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Clase para modelar un polinomio en un campo finito.
 */
public class Polinomio {

    private BigInteger[] coeficientes;
    protected SecureRandom valor = new SecureRandom();
    protected int grado;
    protected BigInteger primo = new BigInteger(
            "208351617316091241234326746312124448251235562226470491514186331217050270460481");

    /**
     * Constructor por omisión de un polinomio.
     */
    public Polinomio() {

    }

    /**
     * Constructor por parámetros de un polinomio.
     * 
     * @param t
     * @param key
     */
    public Polinomio(int t, String key) {
        BigInteger k = new BigInteger(key, 16);
        if (t < 0) {
            BigInteger aux1 = BigInteger.valueOf(0);
            coeficientes = new BigInteger[0];
            coeficientes[0] = aux1;
            grado = 0;
        } else {
            coeficientes = new BigInteger[t + 1];
            coeficientes[0] = k;
            generarValores();
            for (int i = 1; i <= t; i++) {
                coeficientes[i] = BigInteger.valueOf(valor.nextInt());
                grado = t;
            }
        }
    }

    /**
     * Método que genera los valores aleatorios que tomaran los coeficientes.
     */
    private void generarValores() {
        SecureRandom valor = null;
        try {
            // El segundo parametro es una nueva semilla para garantizar el caracter
            // aleatorio.
            valor = SecureRandom.getInstance("SHA1PRNG", "PANDA");
        } catch (NoSuchAlgorithmException e) {
            System.out.print("Ocurrio un error al escoger el algoritmo de cifrado.");
        } catch (NoSuchProviderException e) {
            System.out.print("Ocurrio un error al escoger una semilla.");
        }
        valor.nextBytes(new byte[1]);
    }

}