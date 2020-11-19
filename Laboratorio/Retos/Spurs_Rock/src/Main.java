import java.lang.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Problema 1303 URI online judge resuelto en Java
 * 
 * @author Gómez de la Torre Heidi Lizbeth
 * @version 10/11/2020
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Equipo[] partidos;
        String[] cad;
        int n = 0, x = 0, y = 0, z = 0, w = 0, h = 1, juegos = 0, aux1 = 0, aux2 = 0;
        Reader lector = new Reader();
        n = lector.nextInt();
        Desempate desempate = new Desempate();

        while (n != 0) {
            partidos = new Equipo[n];
            for (int i = 0; i < n; i++) {
                partidos[i] = new Equipo();
            }
            juegos = n * (n - 1) / 2;
            // Mientras i sea menor al número de juegos, se leeran las líneas de partidas
            // de donde se obtendra la información
            for (int i = 0; i < juegos; i++) {
                cad = lector.readLine().trim().split(" ");
                x = Integer.parseInt(cad[0]);
                y = Integer.parseInt(cad[1]);
                z = Integer.parseInt(cad[2]);
                w = Integer.parseInt(cad[3]);
                aux1 = x - 1;
                aux2 = z - 1;
                partidos[aux1].identificador = x;
                partidos[aux2].identificador = z;

                partidos[aux1].puntosFavor += y;
                partidos[aux1].puntosContra += w;
                partidos[aux1].partidosJugados++;
                partidos[aux2].puntosFavor += w;
                partidos[aux2].puntosContra += y;
                partidos[aux2].partidosJugados++;

                if (y > w) {
                    partidos[aux1].puntosTotales += 2;
                    partidos[aux2].puntosTotales++;

                } else {
                    partidos[aux2].puntosTotales += 2;
                    partidos[aux1].puntosTotales++;
                }
            }
            Arrays.sort(partidos, desempate);
            if (h > 1)
                System.out.println("\n");
            System.out.println("Instancia " + h);
            h += 1;
            for (int j = 0; j < partidos.length; j++) {
                if (j == partidos.length - 1) {
                    System.out.print(partidos[j].identificador);
                } else {
                    System.out.print(partidos[j].identificador + " ");
                }
            }
            n = lector.nextInt();
            if (n == 0) {
                System.out.print("\n");
            }

        }
    }

    /**
     * Clase para modelar el problema mediante un objeto de tipo Equipo el cual
     * contiene los atributos que conforman a los equipos del problema
     */
    static class Equipo {

        int identificador, puntosTotales, puntosContra, puntosFavor, partidosJugados;

        /**
         * Constructor por omisión de un equipo
         */
        public Equipo() {
            identificador = 0;
            puntosTotales = 0;
            puntosContra = 0;
            puntosFavor = 0;
            partidosJugados = 0;
        }
    }

    /**
     * Clase Desempate que implementa la interfaz Comparator para ordenar el arreglo
     * en el que se encuentran los equipos mediante los criterios establecidos en
     * URI.
     */
    static class Desempate implements Comparator<Equipo> {

        /**
         * Método para comprobar cúal es el equipo ganador entre dos equipos mediante
         * los criterios establecidos en URI
         */
        public int compare(Equipo a, Equipo b) {
            // Ratio 1 será el valor de la canasta promedio del equipo a y
            // Ratio 2 el valor de la canasta promedio del equipo b
            double ratio1 = 0, ratio2 = 0;
            Equipo aux1 = a;
            Equipo aux2 = b;

            if (aux1.puntosTotales != aux2.puntosTotales) {
                return aux2.puntosTotales - aux1.puntosTotales;
            } else {
                ratio1 = aux1.puntosContra != 0 ? (double) aux1.puntosFavor / aux1.puntosContra
                        : (double) aux1.puntosFavor;
                ratio2 = aux2.puntosContra != 0 ? (double) aux2.puntosFavor / aux2.puntosContra
                        : (double) aux2.puntosFavor;
                if (ratio1 != ratio2) {
                    return ratio2 > ratio1 ? 1 : -1;
                } else {
                    if (aux1.puntosFavor != aux2.puntosFavor) {
                        return aux2.puntosFavor - aux1.puntosFavor;
                    } else {
                        return aux1.identificador - aux2.identificador;
                    }
                }
            }
        }

    }

    /**
     * I/O Implementada por un usuario, encontrada en un artículo de internet.
     * Créditos a su respectivo autor.
     */
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64];
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}