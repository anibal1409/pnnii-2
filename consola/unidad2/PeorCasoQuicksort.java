/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 5.1 Ejercicio resuelto 2 - Peor caso de Quicksort
 *  Programa  : PeorCasoQuicksort
 *  Objetivo  : Mostrar que con pivote = ultimo elemento y un arreglo ya
 *              ordenado, cada particion deja un lado vacio y se hacen
 *              (n-1) + (n-2) + ... + 1 = n(n-1)/2 comparaciones: O(n^2).
 *  Complejidad: O(n^2) en este caso
 *  Ejecutar  : java consola/unidad2/PeorCasoQuicksort.java
 * =============================================================================
 */

// Clase principal del programa
public class PeorCasoQuicksort {

    // Contador global de comparaciones con el pivote
    static int comparaciones = 0;

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo ya ordenado: el peor caso para Lomuto
        int[] datos = {1, 2, 3, 4, 5, 6, 7, 8};
        // Numero de elementos
        int n = datos.length;
        // Ordenamos todo el arreglo
        quicksort(datos, 0, n - 1);
        // Comparaciones contadas
        System.out.println("Comparaciones en peor caso: " + comparaciones);
        // Valor teorico n(n-1)/2
        System.out.println("n(n-1)/2 = " + (n * (n - 1) / 2));
    }

    // Ordena el rango [bajo, alto] del arreglo
    static void quicksort(int[] arr, int bajo, int alto) {
        // Solo seguimos si el tramo tiene al menos dos elementos
        if (bajo < alto) {
            // Particionamos y obtenemos la posicion del pivote
            int p = particion(arr, bajo, alto);
            // Mostramos como quedaron los dos lados
            System.out.println("Pivote " + arr[p] + ": izquierda " + (p - bajo) + " elementos, derecha " + (alto - p));
            // Ordenamos la parte izquierda
            quicksort(arr, bajo, p - 1);
            // Ordenamos la parte derecha
            quicksort(arr, p + 1, alto);
        }
    }

    // Particion de Lomuto con pivote arr[alto]
    static int particion(int[] arr, int bajo, int alto) {
        // El pivote es el ultimo elemento
        int pivote = arr[alto];
        // Final de la zona de elementos <= pivote
        int i = bajo - 1;
        // Recorremos el tramo sin el pivote
        for (int j = bajo; j < alto; j++) {
            // Contamos cada comparacion con el pivote
            comparaciones++;
            // Si el elemento es menor o igual al pivote
            if (arr[j] <= pivote) {
                // Ampliamos la zona de menores
                i++;
                // Guardamos arr[i]
                int temp = arr[i];
                // Movemos arr[j] a la zona de menores
                arr[i] = arr[j];
                // Completamos el intercambio
                arr[j] = temp;
            }
        }
        // Guardamos el elemento que ocupara el lugar del pivote
        int temp = arr[i + 1];
        // Colocamos el pivote en su posicion final
        arr[i + 1] = arr[alto];
        // Completamos el intercambio
        arr[alto] = temp;
        // Posicion final del pivote
        return i + 1;
    }
}
