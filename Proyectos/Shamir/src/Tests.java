
import seguridad.PolinomioSeguro;
import seguridad.Encriptar;
import seguridad.Desencriptar;

import java.io.File;
import java.util.Random;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

public class Tests {
    private static String carpetaSalida = "testFiles/muestrasEncriptado/";
    private static String carpetaFuente = "testFiles/muestras/";
    private static String[] extensiones = { ".jpg", ".mp3", ".txt", ".pdf", ".gif" };

    public static void main(String[] args) {
        
        // Tests clase PolinomioSeguro
        encabezadoPrueba("PolinomioSeguro.evaluar()");
        boolean resultadoEvaluacion = testEvaluacion();
        estadoPrueba(resultadoEvaluacion);
        
        encabezadoPrueba("PolinomioSeguro.formaLagrange()");
        boolean resultadoLagrange = resultadoEvaluacion && testLagrange();
        estadoPrueba(resultadoLagrange);

        // Tests clases Encriptar/Desencriptar
        Random p = new Random();
        int k = p.nextInt(10) + 5;
        int n = p.nextInt(10) + k;
        encabezadoPrueba("Encriptar.encriptar");
        boolean resultadoEncriptar = resultadoLagrange && testEncriptar(n, k);
        estadoPrueba(resultadoEncriptar);

        encabezadoPrueba("Desencriptar.desencriptar()");
        boolean resultadoDesencriptar = resultadoEncriptar && testDesencriptar(n, k);
        estadoPrueba(resultadoDesencriptar);
    }

    /**
     * Metodo que se encarga de probar el metodo: evaluar(BigInteger x) de la clase
     * PolinomioSeguro
     * 
     * @return True si la prueba se paso con exito, false en caso contrario
     */
    private static boolean testEvaluacion() {
        int n = 1000; // Intensidad de prueba
        int exito = 0;
        for (int i = 0; i < n; i++) {
            if (testAuxEvaluacion())
                exito++;
        }

        System.out.println("Porcentaje de exito: " + (exito != 0 ? n * 100 / exito : 0) + "%");
        return exito == n;
    }

    /**
     * Metodo Auxiliar para probar el metdo evaluacion, Crea un polinomio con
     * ternimo constante aleatorio X0 y determina si al evaluar el polinomio en 0 se
     * obtiene el termino constante
     * 
     * @return true si el resultado concuerda con lo esperado, false en caso
     *         contrario
     */
    private static boolean testAuxEvaluacion() {
        Random p = new Random();
        BigInteger x0 = new BigInteger(255, new SecureRandom());
        PolinomioSeguro polinomio = new PolinomioSeguro(p.nextInt(1000) + 1, x0);

        return polinomio.evaluar(BigInteger.ZERO).equals(x0);
    }

    /**
     * Metodo que se encarga de probar el metodo: formaLagrange(puntos, n) de la
     * clase PolinomioSeguro
     * 
     * @return True si la prueba se paso con exito, false en caso contrario
     */
    private static boolean testLagrange() {
        int n = 1000; // Intensidad de prueba
        int exito = 0;
        for (int i = 0; i < n; i++) {
            if (testAuxLagrange())
                exito++;
        }
        System.out.println("Porcentaje de exito: " + (exito != 0 ? n * 100 / exito : 0) + "%");
        return exito == n;
    }

    /**
     * Metodo auxiliar para probar el metodo formaLagrange(), se crea un polinomio
     * aleatorio con termino aleatorio constante X0, despues se procede a evaluar el
     * polinomio en m < (grado+1) puntos, grado+1 puntos y grado+k+1 puntos.Donde
     * grado y k son enteros generados de manera aleatoria. Si el metodo
     * formaLagrange() opera de manera adecuada el resultado de aplicar el metodo de
     * lagrange con evaluacion x=0 deberia arrojar un numero distinto a X0 en el
     * primer caso y X0 en los casos restantes.
     * 
     * @return true si el metodo opera de manera correcta para una iteracion, false
     *         en caso contrario
     */
    private static boolean testAuxLagrange() {
        Random p = new Random();
        BigInteger x0 = new BigInteger(255, new SecureRandom());
        int grado = p.nextInt(100) + 1;
        PolinomioSeguro polinomio = new PolinomioSeguro(grado, x0);
        boolean lessPoints = false, nPoints = false;
        HashMap<BigInteger, BigInteger> puntos = new HashMap<BigInteger, BigInteger>();
        grado++;

        int k = p.nextInt(100) + 1;
        int menos = p.nextInt(grado - 1) + 1;

        for (int i = 1; i <= grado + k; i++) {
            BigInteger aux = new BigInteger(Integer.toString(i));
            puntos.put(aux, polinomio.evaluar(aux));
            if (i == menos) {
                lessPoints = !x0.equals(PolinomioSeguro.formaLagrange(puntos, BigInteger.ZERO));
            }
            if (i == grado) {
                nPoints = x0.equals(PolinomioSeguro.formaLagrange(puntos, BigInteger.ZERO));
            }
        }
        boolean nPlusKpoints = x0.equals(PolinomioSeguro.formaLagrange(puntos, BigInteger.ZERO));
        return lessPoints && nPoints && nPlusKpoints;
    }

    /**
     * Metodo que se encarga de probar el metodo Encriptar.encriptar()
     * 
     * @param n numero de llaves disponibles
     * @param k numero de llaves esenciales
     * @return true si la prueba se supero con exito, false en caso contrario
     */
    private static boolean testEncriptar(int n, int k) {
        File muestrasEncriptadas = new File("testFiles/muestrasEncriptado/");
        if (!muestrasEncriptadas.exists())
            muestrasEncriptadas.mkdir();
        int exito = 0;
        for (int i = 0; i < extensiones.length; i++) {
            exito += testAuxEncriptar("muestra" + (i + 1), extensiones[i], n, k) ? 1 : 0;
        }
        System.out.println("Porcentaje de exito: " + (exito != 0 ? extensiones.length * 100 / exito : 0) + "%");
        return extensiones.length == exito;
    }

    /**
     * Este metodo auxiliar prueba el metodo Encriptar.encriptar(), una vez
     * especificado un archivo a cifrar, se confirma que se haya generado el
     * criptograma de manera exitosa asi como el numero de llaves que fueron
     * solicitadas, si alguno de estos requerimentos no se cumple se determina que
     * el metodo fallo la prueba.
     * 
     * @param muestra   archivo a cifrar
     * @param extension extension del archivo a cifrar
     * @param n         numero de llaves solicitadas
     * @param k         numero de llaves esenciales
     * @return true si la prueba se aprobo con exito, false en caso contrario
     */
    private static boolean testAuxEncriptar(String muestra, String extension, int n, int k) {

        File archivoFuente = new File(carpetaFuente + muestra + extension);
        if (archivoFuente.isFile()) {
            try {
                Encriptar.encriptarFile(carpetaFuente + muestra + extension, carpetaSalida + muestra + extension,
                        generarPassword(12), n, k);
                File llaves = new File(carpetaSalida + muestra + "Keys");
                File criptograma = new File(carpetaSalida + muestra + extension);
                if (criptograma.isFile() && llaves.isDirectory()) {
                    File[] keys = llaves.listFiles();
                    return keys.length == n;
                }
            } catch (Exception e) {
                System.out.println("Algo no funciono de manera correcta");
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Es posible que se hayan modificado o movido los archivos de prueba.\nNo se encontro: "
                    + archivoFuente.getName());
        }
        return false;
    }

    /**
     * Metodo que se encarga de probar el metodo Desencriptar.desencriptar()
     * 
     * @param n numero de llaves disponibles
     * @param k numero de llaves esenciales
     * @return true si la prueba se supero con exito, false en caso contrario
     */
    private static boolean testDesencriptar(int n, int k) {
        int exito = 0;
        for (int i = 0; i < extensiones.length; i++) {
            exito += testAuxDesencriptar("muestra" + (i + 1), extensiones[i], n, k) ? 1 : 0;
        }
        System.out.println("Porcentaje de exito: " + (exito != 0 ? extensiones.length * 100 / exito : 0) + "%");
        return extensiones.length == exito;
    }

    /**
     * Este metodo auxiliar para probar el metodo Desencripta.desencriptar() toma el
     * criptograma de un archivo y su carpeta contenedora de llaves, borra
     * aleatoriamente una a una las llaves y verifica que el documento solo puede
     * desbloquerse mientras se hayan usado k o mas llave, en caso de que el
     * documento no se desencripte teniendo las suficientes llaves o que se
     * desencripte con menos de la k llaves la prueba falla
     * 
     * @param muestra   criptograma sobre el cual se esta trabajando
     * @param extension extension del criptograma
     * @param n         numero de llaves disponibles
     * @param k         numero de llaves esenciales
     * @return true si la prueba se supero con exito, false en caso contrario
     */
    private static boolean testAuxDesencriptar(String muestra, String extension, int n, int k) {
        File directorioKeys = new File(carpetaSalida + muestra + "Keys");
        if (!directorioKeys.isDirectory()) {
            System.out.println("no se encontro el directorio");
            return false;
        }

        for (int i = n; i > 0; i--) {
            try {
                Desencriptar.desencriptarFile(carpetaSalida + muestra + extension, carpetaSalida + muestra + "Keys");
                File archivoDesencriptado = new File(carpetaSalida + muestra + "_DES" + extension);
                if (archivoDesencriptado.exists()) {
                    archivoDesencriptado.delete();
                    if (i < k) {
                        return false;
                    }
                }
            } catch (Exception e) {
                if (i >= k) {
                    return false;
                }
            }
            borrarKeyAleatoria(directorioKeys.listFiles(), i);
        }
        return true;
    }

    /**
     * Este metodo borra de manera aleatoria uno de los archivos files pasado por
     * parametro en el arreglo keys
     * 
     * @param keys     arreglo del cual borrar el archivo
     * @param realSize tamaño real del arreglo
     * @return true si la operacion se ejecuto con exito, false en caso contrario
     */
    private static boolean borrarKeyAleatoria(File[] keys, int realSize) {
        if (keys.length >= 1) {
            Random p = new Random();
            int borrarI = realSize > 1 ? p.nextInt(realSize - 1) : 0;
            if (borrarI != realSize - 1) {
                // intercambio
                File aux = keys[realSize - 1];
                keys[realSize - 1] = keys[borrarI];
                keys[borrarI] = aux;
            }
            return keys[realSize - 1].delete();
        }
        return false;
    }

    /**
     * Genera una contraseña de longitud especificada en el parametro largo
     * 
     * @param largo longitud de la contraseña
     * @return una cadena de caracteres aleatorios que respresentan una contraseña
     */
    private static String generarPassword(int largo) {
        String fuente = "abcdefghijklmnopqrstuvwxyz123456890@_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String psw = "";
        Random p = new Random();
        for (int i = 0; i < largo; i++) {
            psw += fuente.charAt(p.nextInt(fuente.length() - 1));
        }
        return psw;
    }

    /**
     * Metodo que imprime un encabezado para la prueba (uso de system.out.println())
     * 
     * @param prueba nombre de la prueba a ejecutar
     */
    private static void encabezadoPrueba(String prueba) {
        System.out.println("\u001B[0mPrueba:\u001B[37m\u001B[40m" + prueba + "\u001B[0m");
    }

    /**
     * Metodo que imprime el estado en el que termino la prueba
     * 
     * @param status estado final de la prueba true:Aprobado, false:Fallido
     */
    private static void estadoPrueba(boolean status) {
        System.out.println("\u001B[0mEstado: \u001B[37m" + (status ? "\u001B[42m APROBADO " : "\u001B[41m FALLIDO ")
                + "\u001B[0m");
    }

}