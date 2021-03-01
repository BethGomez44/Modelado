import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Clase que proporciona la cobertura nubosa de una imagen circular del cielo
 * contenida en un archivo de 4,368 x 2,912
 */

public class Cobertura {
    public static void main(String args[]) {

        if (args.length != 0) {
            BufferedImage imagen = leerImagen(args[0]);
            if (imagen != null) {
                double coberturaNubosa = procesarImagen(imagen, 2184, 1456, 1324);
                System.out.println("La cobertura nubosa de la imagen " + args[0] + " es:");
                System.out.println("Cobertura nubosa: " + coberturaNubosa);

                if (args.length == 2 && args[1].equalsIgnoreCase("s")) {
                    escribirImagen(imagen, args[0]);
                } else {
                    System.out.println("Bandera no reconocida valida: s o S");
                }
            }
        } else {
            System.out.println("Porfavor proporcione un archivo para procesar");
        }

    }

    /**
     * Lee una ruta y si corresponde a un archivo de tipo imagen retorno un
     * BufferedImage
     * 
     * @param ruta ruta del archivo
     * @return Objeto BufferedImage que apunta a la imagen de la ruta
     */
    public static BufferedImage leerImagen(String ruta) {

        try {
            File archivo = new File(ruta);
            return ImageIO.read(archivo);
        } catch (IOException e) {
            System.out.println("Verificar que la ruta sea correcta: " + ruta + " no es valida.");
            return null;
        }
    }

    /**
     * Metodo que procesa una imagen para obtener su cobertura nubosa
     * 
     * @param img     objeto BufferedImage que representa una imagen de la boveda
     *                celeste
     * @param centroX centro en x de la circunferencia formada por el lente en la
     *                imagen
     * @param centroY centro en y de la circunferencia formada por el lente en la
     *                imagen
     * @param radio   radio de la circunferencia formada por el lente en la imagen
     * @return indice de cobertura nubosa (Nota*) la imagen en el objeto
     *         BufferedImage se tranforma a una en blanco y negro , donde las nubes
     *         aparecen de color blanco y el cielo negro
     */
    public static double procesarImagen(BufferedImage img, int centroX, int centroY, int radio) {

        int ancho = img.getWidth();
        int alto = img.getHeight();
        int pixelesDeNube = 0;

        radio = (int) Math.pow(radio, 2);
        try {
            for (int y = 0; y < alto; y++) {
                for (int x = 0; x < ancho; x++) {
                    boolean adentroCirculo = Math.pow(x - centroX, 2) + Math.pow(y - centroY, 2) <= radio;
                    int p = img.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    b = b == 0 ? 1 : b;
                    boolean esNube = ((double) r / (double) b) > 0.95 && ((r + g + b) / 3) != 255;
                    if (esNube && adentroCirculo) {
                        p = (255 << 24) | (255 << 16) | (255 << 8) | 255;
                        pixelesDeNube++;
                    } else {
                        p = adentroCirculo ? 255<<24 : (a << 24) | (150 << 16) | (150 << 8) | 150;
                    }
                    img.setRGB(x, y, p);
                }
            }
        } catch (Exception ArrayOutOfBoundsException) {
            System.out.println("Tenemos problemas para procesar su imagen, probablemente algunos de los prerequisitos "
                    + "en cuanto a la dimension  de la imagen no estan siendo cumplidos. Porfavor verifique la entrada");
        }

        return (double) pixelesDeNube / Math.ceil((Math.PI * radio));

    }

    /**
     * Método que escribe la imagen post-procesada para identificar la cobertura
     * nubosa
     * 
     * @param img  Objeto BufferedImage que corresponde a la imagen de salida
     * @param ruta ruta del archivo original
     */
    public static void escribirImagen(BufferedImage img, String ruta) {

        try {
            StringBuilder rutaEscape = new StringBuilder(ruta);
            int i = ruta.lastIndexOf(".");
            rutaEscape.insert(i, "-seg");
            File archivo = new File(rutaEscape.toString());
            ImageIO.write(img, ruta.substring(i + 1), archivo);
            System.out.println("Archivo de salida generado con exito en: " + rutaEscape.toString());
        } catch (IOException e) {
            System.out.println("No se ha generado una imagen de salida.");
        }

    }
}