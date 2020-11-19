import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * Problema 2: Schweisen URI online 1112 con Java
 * @author Gómez de la Torre Heidi Lizbeth
 * @version 29/10/2020
 */
public class Main {

    static int arbolFenwick[][];

    public static void main(String[] args) throws IOException {

        Reader lector = new Reader();
        int X = 1, Y = 1, P = 1, Q = 0;
        String[] cad;
        
        X = lector.nextInt();
        Y = lector.nextInt();
        P = lector.nextInt();

        while (X != 0 && Y != 0 && P != 0) {
            Q = lector.nextInt();
            arbolFenwick = new int[X + 1][Y + 1];
            if (X <= 1000 && Y <= 1000 && P <= 10 && Q <= 10000) {
                int k = 0;
                while (k < Q) {
                    cad = lector.readLine().trim().split(" ");
                    if (cad.length < 5) {
                        int N = Integer.parseInt(cad[1]);
                        int coorX = Integer.parseInt(cad[2]);
                        int coorY = Integer.parseInt(cad[3]);
                        actualizar(N, coorX + 1, coorY + 1);
                    } else {
                        int coorX = Integer.parseInt(cad[1]);
                        int coorY = Integer.parseInt(cad[2]);
                        int coorZ = Integer.parseInt(cad[3]);
                        int coorW = Integer.parseInt(cad[4]);
                        System.out.print(respuesta(coorX + 1, coorY + 1, coorZ + 1, coorW + 1, P) + "\n");
                    }
                    k++;
                }
            }
            System.out.println();
            X = lector.nextInt();
            Y = lector.nextInt();
            P = lector.nextInt();
        }

    }
    
    static int menorBit(int i) {
        return (i & -i);
    }

    static int suma(int x, int y) {
        int sum = 0;
        for (; x > 0; x -= menorBit(x)) {
            for (int z = y; z > 0; z -= menorBit(z)) {
                sum += arbolFenwick[x][z];
            }
        }
        return sum;
    }

    static void actualizar(int val, int x, int y) {
        for (; x < arbolFenwick.length; x += menorBit(x)) {
            for (int z = y; z < arbolFenwick[0].length; z += menorBit(z)) {
                arbolFenwick[x][z] += val;
            }
        }
    }

    private static int respuesta(int x1, int y1, int x2, int y2, int P) {
        int aux = 0;
        if (x1 > x2) {
            aux = x1;
            x1 = x2;
            x2 = aux;
        }
        if (y1 > y2) {
            aux = y1;
            y1 = y2;
            y2 = aux;
        }
        int resp = suma(x2, y2) - suma(x1 - 1, y2) - suma(x2, y1 - 1) + suma(x1 - 1, y1 - 1);
        return resp * P;
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
            byte[] buf = new byte[64]; // line length
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
