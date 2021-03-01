package seguridad;

import seguridad.PolinomioSeguro;

import java.util.HashMap;

import java.math.BigInteger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Desencriptar {
    private static final String ALGORITMO = "AES";

    /**
     * Este metodo se encarga de desencriptar un archivo pasado por parametro usando
     * como claves , las llaves proporcionadas en carpeta a la cual señala la ruta
     * pasda por parametro
     * 
     * @param rutaFile ruta relativa al archivo a desencriptar
     * @param rutaKeys ruta realtiva a la carpeta contenedora de las llaves
     * @throws FileNotFoundException    Si no se encuentra el archivo o la carpeta
     *                                  contenedora
     * @throws IOException              Si exisitio un error en la apertura o
     *                                  lectura del archivo u directorio contenedor
     *                                  de llaves
     * @throws IllegalArgumentException Si la ruta pasada rutaKeys no corresponde a
     *                                  un directorio
     */
    public static void desencriptarFile(String rutaFile, String rutaKeys)
            throws FileNotFoundException, IOException, IllegalArgumentException {
        File cerradura = new File(rutaKeys);

        if (!cerradura.isDirectory())
            throw new IllegalArgumentException(
                    "La ruta a las llaves debe ser una carpeta que contenga las llaves disponibles");

        HashMap<BigInteger, BigInteger> keys = getKeys(cerradura);

        BigInteger hashedKey = PolinomioSeguro.formaLagrange(keys, BigInteger.ZERO);
        // se recupera la clave
        SecretKey key = new SecretKeySpec(hashedKey.toByteArray(), ALGORITMO);
        StringBuffer rutaSalida = new StringBuffer(rutaFile);
        // Se prepara ruta de salida
        rutaSalida.insert(rutaSalida.lastIndexOf("."), "_DES");
        // Se realiza el desencriptado
        desencriptar(rutaFile, rutaSalida.toString(), key);

    }

    /**
     * Metodo que se encarga de realizar la desencriptacion de la informacion
     * contenida en el archivo
     * 
     * @param ruta       ruta fuente del archivo
     * @param rutaSalida ruta destino del archivo
     * @param key        key para desencriptado
     * @throws FileNotFoundException si el archivo fuente no es encotrado
     * @throws IOException           si existe algun tipo de error en la escritura o
     *                               lectura del archivo
     */
    private static void desencriptar(String ruta, String rutaSalida, SecretKey key)
            throws FileNotFoundException, IOException {
        File archivoOrigen = new File(ruta);
        FileInputStream fuenteDatos = new FileInputStream(archivoOrigen);

        byte[] bytesEntrada = new byte[(int) archivoOrigen.length()];
        fuenteDatos.read(bytesEntrada);
        try {
            Cipher cifrador = Cipher.getInstance(ALGORITMO);
            cifrador.init(Cipher.DECRYPT_MODE, key);
            byte[] bytesSalida = cifrador.doFinal(bytesEntrada);

            File archivoDestino = new File(rutaSalida);
            FileOutputStream escrituraEncriptada = new FileOutputStream(archivoDestino);
            escrituraEncriptada.write(bytesSalida);

            fuenteDatos.close();
            escrituraEncriptada.close();

        } catch (Exception e) {
            // todos los errores causados por esta excepcion son consecuencia de una llave
            // incorrecta
            throw new IOException("No puede ser completado el descifrado, intente mas tarde");
        }
    }

    /**
     * Este metodo recibe por parametro un objeto File que representa la carpeta
     * contenedora de las llaves y devuelve un HashMap que representa los valores
     * asociados a cada llave
     * 
     * @param cerradura Objeto File que representa la carpeta contenedora de las
     *                  llaves
     * @return Un objeto de tipo HashMap que representa las llaves
     */
    private static HashMap<BigInteger, BigInteger> getKeys(File cerradura) {
        HashMap<BigInteger, BigInteger> salida = new HashMap<BigInteger, BigInteger>();
        File[] keys = cerradura.listFiles();

        for (File key : keys) {
            if (isKey(key)) {
                
                try {
                    BigInteger x = getKeyNumber(key);
                    BigInteger y = getKeyValue(key);
                    salida.put(x, y);
                } catch (Exception e) {
                    // ja que creiste? no hay info extra pa ti :p
                }
            }
        }
        return salida;
    }

    /**
     * Este metodo verifica si el objeto File pasado por parametro representa una
     * potencial key o llave para el desencriptado, se verifica que el objeto File
     * sea un archivo y la extension corresponda con las aceptadas por el programa
     * 
     * @param key objeto de tipo File a comprobar
     * @return true si la extension corresponde a un archivo de tipo key, false en
     *         caso contrario
     */
    private static boolean isKey(File key) {
        try {
            String nombre = key.getName();
            String extension = nombre.substring(nombre.lastIndexOf(".") + 1, nombre.length());
            return key.isFile() && extension.equals("key");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo que se encarga de recuperar el numero de llave a partir del nombre del
     * archivo
     * 
     * @param key objeto File que representa una potencial llave
     * @return El numero de llave correspondiente al archivo
     */
    private static BigInteger getKeyNumber(File key) {
        String nombre = key.getName();
        int i = nombre.lastIndexOf(".") - 1;
        String num = "";
        while (Character.isDigit(nombre.charAt(i))) {
            num = nombre.charAt(i) + num;
            i--;
        }
        return new BigInteger(num);
    }

    /**
     * Este metodo recupera el valor de la llave guardada en el archivo
     * 
     * @param key objeto File que representa una potencial llave
     * @return un BigInteger representando el valor de la posible llave
     * @throws FileNotFoundException Si no se encuentra el archivo
     * @throws IOException           Si existe un error durante la lectura de la
     *                               llave
     */
    private static BigInteger getKeyValue(File key) throws FileNotFoundException, IOException {
        FileInputStream lector = new FileInputStream(key);
        byte[] entrada = new byte[(int) key.length()];

        lector.read(entrada);
        lector.close();
        return new BigInteger(entrada);
    }

}