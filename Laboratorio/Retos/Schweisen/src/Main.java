import java.util.Scanner;
import java.io.IOException;

public class Main {

    static int arbolFenwick[][];

    public static void main(String[] args) throws IOException {

        Scanner lector;
        int X = 1, Y = 1, P = 1, Q = 0;

        lector = new Scanner(System.in);

        X = lector.nextInt();
        Y = lector.nextInt();
        P = lector.nextInt();

        while (X != 0 && Y != 0 && P != 0) {
            Q = lector.nextInt();
            arbolFenwick = new int[X + 1][Y + 1];
            if (X <= 1000 && Y <= 1000 && P <= 10 && Q <= 10000) {
                int k = 0;
                while (k < Q) {
                    char id = lector.next().charAt(0);
                    if (id == 'A') {
                        int N = lector.nextInt();
                        int coorX = lector.nextInt();
                        int coorY = lector.nextInt();
                        actualizar(N, coorX + 1, coorY + 1);
                    } else if (id == 'P') {
                        int coorX = lector.nextInt();
                        int coorY = lector.nextInt();
                        int coorZ = lector.nextInt();
                        int coorW = lector.nextInt();
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

}
