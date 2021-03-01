import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.Random;

public class TestCasesGenerator {
    private static int width = 4368;
    private static int height = 2912;
    private static int centroX = 2184;
    private static int centroY = 1456;
    private static int radio = 1324;

    public static void main(String[] args) {
        int pixelesNube = 0;
        int areaInteres = (int) Math.ceil( Math.PI  * Math.pow(radio, 2));
        BufferedImage imagen_salida = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixelesNube = pintarNubes(imagen_salida, 100, 500);
        pintarCielo(imagen_salida);
        String ruta = args.length == 1 ? args[0] : "ejemplo";
        escribirImagen(imagen_salida, ruta);
        Double cci = (double) pixelesNube / (double) areaInteres;
        System.out.println("Pixeles de nube: " + pixelesNube);
        System.out.println("Area total: " + areaInteres);
        System.out.println("CCI: "+ cci );
        

    }

    public static int pintarNubes(BufferedImage img, int n, int maxSize) {
        int totalPixelesNube = 0;
        for (int i = 0; i < n; i++) {
            totalPixelesNube += pintarNube(img, (int) Math.floor(Math.random() * (double) width),
                    (int) Math.floor(Math.random() * (double) height), new Random().nextInt(maxSize) + 10);
        }
        return totalPixelesNube;

    }

    public static void pintarCielo(BufferedImage img) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!esNube(i, j, img) && dentroCirculo(centroX, centroY, radio, i, j)) {
                    pintarPixelCielo(img, i, j);
                }
            }
        }
    }

    public static int pintarNube(BufferedImage img, int x, int y, int r) {
        int pixelesNube = 0;
        for (int i = x - r; i <= x + r; i++) {
            for (int j = y - r; j <= y + r; j++) {

                if (i >= 0 && i < width && j >= 0 && j < height) {
                    double probaSerNube = (0.80 * (r - distanciaPuntos(i, j, x, y)) / r);
                    if (hacerNube(probaSerNube) && !esNube(i, j, img) && dentroCirculo(centroX, centroY, radio, i, j)) {
                        pintarPixelNube(img, i, j);
                        pixelesNube++;
                    }
                }

            }
        }
        return pixelesNube;

    }

    public static void pintarPixelCielo(BufferedImage img, int x, int y) {
        Random valores = new Random();
        int g = valores.nextInt(201);
        int b = g + valores.nextInt(256 - g);
        int p = (255 << 24) | (0 << 16) | (g << 8) | b;
        img.setRGB(x, y, p);
    }

    public static void pintarPixelNube(BufferedImage img, int x, int y) {
        Random valores = new Random();
        int b = (int) Math.floor((150 + valores.nextInt(100)) * 0.95);
        int r = valores.nextInt(250 - b) / 2 + b;
        int p = (255 << 24) | (r << 16) | (b << 8) | b;
        img.setRGB(x, y, p);
    }

    /**
     * Método que escribe la imagen pasada por parametro en la ruta especificada
     * 
     * @param img  Objeto BufferedImage que corresponde a la imagen de salida
     * @param ruta nombre que se le quiere dar al archivo
     */
    public static void escribirImagen(BufferedImage img, String ruta) {
        try {
            File archivo = new File(ruta + ".png");
            ImageIO.write(img, "png", archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean esNube(int x, int y, BufferedImage img) {
        int aux[] = arrayRGB(x, y, img);
        aux[3] = aux[3] == 0 ? 1 : aux[3];
        return (double) aux[1] / (double) aux[3] > 0.95;
    }

    public static int[] arrayRGB(int x, int y, BufferedImage img) {
        int p = img.getRGB(x, y);
        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        return new int[] { a, r, g, b };
    }

    public static boolean hacerNube(double p) {
        return Math.random() <= p;
    }

    public static int distanciaPuntos(int xi, int yi, int xf, int yf) {
        return (int) Math.floor(Math.sqrt(Math.pow(xi - xf, 2) + Math.pow(yi - yf, 2)));
    }

    public static boolean dentroCirculo(int centroX, int centroY, int radio, int x, int y) {
        return (int) Math.pow(x - centroX, 2) + (int) Math.pow(y - centroY, 2) <= Math.pow(radio, 2);
    }

}