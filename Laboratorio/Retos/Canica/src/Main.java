import java.io.IOException;
import java.util.Scanner;

/**
 * Clase para correr el programa.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        int[] bolsa;
        int casos = 1, canicas = 0, preguntas = 0, busqueda = 0, numero = 0;
        Scanner lector = new Scanner(System.in);

        while (casos < 65) {

            canicas = lector.nextInt();
            preguntas = lector.nextInt();
            bolsa = new int[canicas];
            try {
                if (canicas != 0 || preguntas != 0) {

                    for (int i = 0; i < canicas; i++) {
                        bolsa[i] = lector.nextInt();
                    }
                    quickSort(bolsa, 0, canicas - 1);
                    System.out.println("CASE# " + casos + ":");
                    int j = 0;
                    while (j < preguntas) {
                        numero = lector.nextInt();
                        busqueda = busquedaBinaria(bolsa, numero);
                        if (busqueda != -1) {
                            busqueda += 1;
                            System.out.println(numero + " found at " + busqueda);

                        } else {
                            System.out.println(numero + " not found");
                        }
                        j++;
                    }

                } else {
                    break;
                }
                casos++;
            } catch (StackOverflowError exception) {
                System.out.println("Ha ocurrido un error, vuelve a intentarlo");
                break;
            }
        }
    }

    /**
     * Método para devolver la i-ésima posición del elemento a encontrar
     *
     * @param n -- Posición donde se encuentra el elemento a buscar
     * @return
     */
    public static int busquedaBinaria(int[] bolsa, int busqueda) {
        int numero = -1;
        int ini = 0;
        int fin = bolsa.length - 1;

        while (ini <= fin) {

            int medio = (ini + fin) / 2;

            if (bolsa[medio] == busqueda) {

                numero = medio;

            }

            if (bolsa[medio] < busqueda) {

                ini = medio + 1;

            } else {

                fin = medio - 1;
            }
        }
        return numero;
    }

    /**
     * Ordena el arreglo pasado como parametro usando quickSort.
     * 
     * @param <T>
     * @param bolsa -- El arreglo a ordenar.
     * @param ini   -- La posición del primer elemento del arreglo.
     * @param fin   -- La posición del ultimo elemento del arreglo.
     */
    public static void quickSort(int[] bolsa, int ini, int fin) {

        int pivote = bolsa[ini];
        int i = ini;
        int j = fin;
        int aux;

        while (i < j) {
            while (bolsa[i] <= pivote && i < j)
                i++;

            while (bolsa[j] > pivote)
                j--;

            if (i < j) {
                aux = bolsa[i];
                bolsa[i] = bolsa[j];
                bolsa[j] = aux;
            }
        }

        bolsa[ini] = bolsa[j];
        bolsa[j] = pivote;

        if (ini < j - 1)
            quickSort(bolsa, ini, j - 1);

        if (j + 1 < fin)
            quickSort(bolsa, j + 1, fin);

    }
}