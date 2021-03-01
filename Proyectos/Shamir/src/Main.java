
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import seguridad.Encriptar;
import seguridad.Desencriptar;

public class Main {

    public static void main(String[] args) {
        try {

            if (args.length >= 3 && (args[0].equals("-d") || args[0].equals("-c"))) {
                switch (args[0]) {
                    case "-c":
                        encriptado(args);
                        System.out.println("Solcitud procesada");
                        break;
                    case "-d":
                        desencriptado(args);
                        System.out.println("Solcitud procesada");
                        break;
                    default:
                        System.out.println(
                                "Panda esta teniendo problemas para entenderte, porfavor intentalo de nuevo :c");
                }

            } else {
                if (args.length < 3)
                    System.out.println("Pocos argumentos, para una descripción mas detallada por favor lea el README");
                else
                    System.out.println("Modo de operacion no indentificado, verifique invocacion");
            }

        } catch (FileNotFoundException a) {
            System.out.println(
                    "La ruta no apunta a un archivo apto para encriptacion o este no se encontró, favor de verificar");
        } catch (IOException b) {
            System.out.println(
                    "Ha ocurrido un error al intentar acceder al archivo o al escribirlo, favor de intentar más tarde o de verificar la claves de acceso");
        } catch (IllegalArgumentException c) {
            System.out.println(c.getMessage());
        } catch (Exception d) {
            System.out.println("Ha ocurrido un error desconocido, el programa finalizara su ejecucion");
        }

    }

    private static void encriptado(String[] args) throws FileNotFoundException, IOException, IllegalArgumentException {
        String psw = new String(System.console().readPassword("Contraseña:"));
        String psw2 = new String(System.console().readPassword("Verificar contraseña:"));
        if (psw.equals(psw2)) {
            Encriptar.encriptarFile(args[1], args[2], psw, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        } else {
            System.out.println("Las contraseñas no coinciden, favor de verificar");
        }
    }

    private static void desencriptado(String[] args)
            throws FileNotFoundException, IOException, IllegalArgumentException {
        Desencriptar.desencriptarFile(args[1], args[2]);
    }

}