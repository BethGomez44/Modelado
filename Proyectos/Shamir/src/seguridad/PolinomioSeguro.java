package seguridad;

import java.util.HashMap;

import java.math.BigInteger;

import java.security.SecureRandom;



public class PolinomioSeguro {

    private static final BigInteger M = new BigInteger(
            "57896044618658097711785492504343953926634992332820282019728792003956564819949");

    // tamaño en bits de los coeficientes aleatorios
    private static final int NUM_SIZE = 255;
    private BigInteger coeficientes[];

    /**
     * Constructor que recibe como parametro el grado del polinomio.
     * 
     * @param n n debe ser un entero positivo mayor a cero
     * @throws NumberFormatException si n <= 0
     */
    public PolinomioSeguro(int n) throws NumberFormatException {
        if (n <= 0) {
            throw new NumberFormatException("n debe ser mayor a cero");
        }

        coeficientes = new BigInteger[n + 1];

        for (int i = 0; i < (n + 1); i++) {
            coeficientes[i] = getRandomBigInteger();
        }

    }

    /**
     * Constructor que genera un polinomio aleatorio con termino constante X0 pasado
     * por parametro
     * 
     * @param n  n debe ser un entero positivo mayor a cero
     * @param x0 Objeto BigInteger que representa el coeficiente X0 del polinomio
     * @throws NumberFormatException si n <= 0
     */
    public PolinomioSeguro(int n, BigInteger x0) throws NumberFormatException {
        if (n <= 0) {
            throw new NumberFormatException("n debe ser mayor a cero");
        }

        coeficientes = new BigInteger[n + 1];
        coeficientes[0] = x0.mod(M);

        for (int i = 1; i < (n + 1); i++) {
            coeficientes[i] = getRandomBigInteger();
        }
    }

    /**
     * Este metodo evalua el polinomio en el valor pasado como x, considere que el
     * resultado esta ideado para caer dentro de un campo finito entero definido por
     * un numero primo
     * 
     * @param x valor en el cual evaluar el polinomio
     * @return evaluacion del polinomio en el punto dado, modulo un gran primo
     */
    public BigInteger evaluar(BigInteger x) {

        BigInteger resultado = BigInteger.ZERO;
        for (int i = coeficientes.length - 1; i >= 0; i--) {
            resultado = resultado.multiply(x).add(coeficientes[i]);
        }
        return resultado.mod(M);
    }

    /**
     * Este metodo obtiene la forma de lagrange del polinomio generado por los
     * puntos pasados por parametro en el HashMap, y lo evalua en el valor n
     * proporcionado. Considere que el metodo toma la llave como el valor en "x" y
     * el valor asociado como su evaluacion "y"
     * 
     * @param puntos HasMap que representa los puntos sobre los cuales se desea
     *               aplicar la forma de lagrange
     * @param n      valor en el cual se evalua la forma de Lagrange
     * @return el valor de la evaluacion del polinomio en n modulo un gran primo
     * @throws IllegalArgumentException si el tamaño del hashMap es cero o este es
     *                                  nulo
     */
    public static BigInteger formaLagrange(HashMap<BigInteger, BigInteger> puntos, BigInteger n)
            throws IllegalArgumentException {

        if (puntos == null || puntos.size() == 0) {
            throw new IllegalArgumentException("Debe existir al menos un punto o clave");
        }
        // Darle formato a los puntos para facil manejo
        BigInteger[] xCords = new BigInteger[puntos.size()];
        BigInteger[] yCords = new BigInteger[puntos.size()];
        int c = 0;
        for (HashMap.Entry<BigInteger, BigInteger> punto : puntos.entrySet()) {
            xCords[c] = punto.getKey();
            yCords[c] = punto.getValue();
            c++;
        }

        BigInteger resultado = BigInteger.ZERO;

        for (int i = 0; i < yCords.length; i++) {
            BigInteger numerador = BigInteger.ONE;
            BigInteger denominador = BigInteger.ONE;
            // Se calcula las bases Pi
            for (int j = 0; j < xCords.length; j++) {
                if (j != i) {
                    numerador = numerador.multiply(n.subtract(xCords[j]));
                    denominador = denominador.multiply(xCords[i].subtract(xCords[j]));
                }
            }
            BigInteger aux = numerador.multiply(denominador.modInverse(M));
            // yi*pi
            resultado = resultado.add(aux.multiply(yCords[i]));
        }

        return resultado.mod(M);
    }

    /**
     * Metodo que genera un bigInteger aleatorio de tamaño NUM_SIZE
     * 
     * @return BigInteger uniformemente distribuido entre 0 y 2^NUM_SIZE
     */
    private BigInteger getRandomBigInteger() {
        return new BigInteger(NUM_SIZE, new SecureRandom());
    }

}