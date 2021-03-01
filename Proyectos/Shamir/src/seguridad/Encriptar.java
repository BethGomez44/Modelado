package seguridad;

import seguridad.PolinomioSeguro;

import java.math.BigInteger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encriptar {

    private static final String ALGORITMO = "AES";

    /**
     * Metodo que encripta un documento con el estandar de shamir generando n keys a
     * traves del algoritmo AES
     * 
     * @param ruta       ruta del archivo a encriptar
     * @param rutaSalida ruta donde escribir el archivo encriptado
     * @param psw        contraseña usada para la encriptacion
     * @param n          numero de claves a generar
     * @param k          numero minimo de participantes necesarios para desencriptar
     * @throws IllegalArgumentException si n <2 , k > n o k<2, o si psw no puede ser
     *                                  hasheado
     * @throws FileNotFoundException    Si ruta no apunta ningun archivo o este no
     *                                  puede ser encontrado
     * @throws IOException              si hubo algun error en la escritura o
     *                                  lectura del archivo
     */
    public static void encriptarFile(String ruta, String rutaSalida, String psw, int n, int k)
            throws IllegalArgumentException, FileNotFoundException, IOException {

        if (n < 2 || k < 2 || k > n)
            throw new IllegalArgumentException("verificar la cantidad de keys totales o keys minimas");

        BigInteger hashKey = getHashKey(psw);
        if (hashKey == null)
            throw new IllegalArgumentException(
                    "No es posible usar esta contraseña, porfavor intente con alguna distinta");
        // se obtiene la llave
        SecretKey key = new SecretKeySpec(hashKey.toByteArray(), ALGORITMO);
        // encripta datos
        encriptar(ruta, rutaSalida, key);
        // generan llaves
        generarMiniKeys(hashKey, n, k, rutaSalida);
    }

    /**
     * Este metodo recibe una cadena por parametro que representa la contraseña para
     * generar la encriptacion
     * 
     * @param psw cadena que se lee como contraseña
     * @return objeto big integer que representa el proceso de aplicar SHA-256 a la
     *         contraseña
     */
    private static BigInteger getHashKey(String psw) {

        try {
            MessageDigest comelon256 = MessageDigest.getInstance("SHA-256");
            byte[] hashCodificado = comelon256.digest(psw.getBytes());
            return new BigInteger(hashCodificado).abs();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Metodo que se encarga de realizar la encriptacion de la informacion contenida
     * en el archivo
     * 
     * @param ruta       ruta fuente del archivo
     * @param rutaSalida ruta destino del archivo
     * @param key        key para encriptado
     * @throws FileNotFoundException si el archivo fuente no es encotrado
     * @throws IOException           si existe algun tipo de error en la escritura o
     *                               lectura del archivo
     */
    private static void encriptar(String ruta, String rutaSalida, SecretKey key)
            throws FileNotFoundException, IOException {
        File archivoOrigen = new File(ruta);
        FileInputStream fuenteDatos = new FileInputStream(archivoOrigen);

        byte[] bytesEntrada = new byte[(int) archivoOrigen.length()];
        fuenteDatos.read(bytesEntrada);
        try {
            Cipher cifrador = Cipher.getInstance(ALGORITMO);
            cifrador.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytesSalida = cifrador.doFinal(bytesEntrada);

            File archivoDestino = new File(rutaSalida);
            FileOutputStream escrituraEncriptada = new FileOutputStream(archivoDestino);
            escrituraEncriptada.write(bytesSalida);

            fuenteDatos.close();
            escrituraEncriptada.close();

        } catch (Exception e) {
            System.out.println("No es posible completar el encriptado, intente mas tarde o con otro archivo");
        }
    }

    /**
     * Este metodo se encarga de dividir la clave original en n trozos de tal forma
     * que se pueda recuperar con al menos k
     * 
     * @param key  llave a fracmentar
     * @param n    cantidad de mini claves
     * @param k    cantidad minima de claves para recuperar la key original
     * @param ruta ruta de donde se encuentra el archivo encriptado
     */
    private static void generarMiniKeys(BigInteger key, int n, int k, String ruta) {
        int indexLastbar = ruta.lastIndexOf("/");
        String nombreDirectorio = ruta.substring(indexLastbar == -1 ? 0 : indexLastbar + 1, ruta.indexOf("."));

        PolinomioSeguro polinomioReparticion = new PolinomioSeguro(k - 1, key);

        try {
            // crear directorio
            String direccionCompleta = indexLastbar == -1 ? "" : ruta.substring(0, indexLastbar);
            File contenedor = new File(direccionCompleta + (direccionCompleta.equals("") ? "" : "/") + nombreDirectorio + "Keys");
            if (!contenedor.exists()) {
                if (!contenedor.mkdir())
                    throw new Exception("No sepuede crear carpeta contenedora de llaves");
            }
            // creacion de las n evaluaciones
            for (int i = 0; i < n; i++) {
                String rutaEscape = direccionCompleta + (direccionCompleta.equals("") ? "" : "/") + nombreDirectorio + "Keys/" + nombreDirectorio + "key"
                        + (i + 1) + ".key";
                File archivoSalida = new File(rutaEscape);
                FileOutputStream escritor = new FileOutputStream(archivoSalida);
                BigInteger aux = new BigInteger(Integer.toString(i + 1));
                byte[] miniKeyBites = polinomioReparticion.evaluar(aux).toByteArray();
                escritor.write(miniKeyBites);
                escritor.close();

            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error critico durante la encriptacion, por favor intente mas tarde");
        }
        polinomioReparticion = null;
    }

}
