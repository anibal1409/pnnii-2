/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 5 Sortmerge (Merge Sort recursivo, top-down)
 *  Programa  : MergeSort
 *  Objetivo  : Ordenar dividiendo el arreglo en dos mitades, ordenando cada
 *              mitad recursivamente e intercalando (merge) las dos mitades
 *              ordenadas en un solo tramo ordenado.
 *  Complejidad: O(n log n) en todos los casos; memoria adicional O(n)
 *  Ejecutar  : java consola/unidad2/MergeSort.java
 * =============================================================================
 */

// Clase principal del programa
public class MergeSort {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo de ejemplo
        int[] datos = {38, 27, 43, 3, 9, 82, 10};
        // Encabezado del arreglo original
        System.out.println("Arreglo original:");
        // Mostramos el arreglo antes de ordenar
        mostrar(datos);
        // Ordenamos todo el arreglo: rango [0, n-1]
        mergeSort(datos, 0, datos.length - 1);
        // Encabezado del arreglo ordenado
        System.out.println("Arreglo ordenado:");
        // Mostramos el resultado
        mostrar(datos);
    }

    // Ordena el rango [inicio, fin] del arreglo
    static void mergeSort(int[] arr, int inicio, int fin) {
        // Caso base: 0 o 1 elemento ya estan ordenados
        if (inicio >= fin) {
            // No hay nada que hacer
            return;
        }
        // DIVIDIR: punto medio del rango
        int medio = (inicio + fin) / 2;
        // CONQUISTAR: ordenamos la mitad izquierda
        mergeSort(arr, inicio, medio);
        // CONQUISTAR: ordenamos la mitad derecha
        mergeSort(arr, medio + 1, fin);
        // COMBINAR: intercalamos las dos mitades ordenadas
        merge(arr, inicio, medio, fin);
    }

    // Intercala los tramos ordenados [inicio, medio] y [medio+1, fin]
    static void merge(int[] arr, int inicio, int medio, int fin) {
        // Tamano de la mitad izquierda
        int n1 = medio - inicio + 1;
        // Tamano de la mitad derecha
        int n2 = fin - medio;
        // Arreglo temporal para la mitad izquierda
        int[] izq = new int[n1];
        // Arreglo temporal para la mitad derecha
        int[] der = new int[n2];
        // Copiamos la mitad izquierda
        for (int i = 0; i < n1; i++) {
            // Elemento i de la mitad izquierda
            izq[i] = arr[inicio + i];
        }
        // Copiamos la mitad derecha
        for (int j = 0; j < n2; j++) {
            // Elemento j de la mitad derecha
            der[j] = arr[medio + 1 + j];
        }
        // Indice de recorrido de izq
        int i = 0;
        // Indice de recorrido de der
        int j = 0;
        // Posicion de escritura en el arreglo original
        int k = inicio;
        // Mientras ambas mitades tengan elementos, tomamos el menor
        while (i < n1 && j < n2) {
            // Con <= se conservan los iguales en su orden (estabilidad)
            if (izq[i] <= der[j]) {
                // El menor esta en la izquierda
                arr[k] = izq[i];
                // Avanzamos en la izquierda
                i++;
            } else {
                // El menor esta en la derecha
                arr[k] = der[j];
                // Avanzamos en la derecha
                j++;
            }
            // Avanzamos la posicion de escritura
            k++;
        }
        // Copiamos lo que quede de la izquierda
        while (i < n1) {
            // Elemento sobrante de izq
            arr[k] = izq[i];
            // Siguiente de izq
            i++;
            // Siguiente posicion de escritura
            k++;
        }
        // Copiamos lo que quede de la derecha
        while (j < n2) {
            // Elemento sobrante de der
            arr[k] = der[j];
            // Siguiente de der
            j++;
            // Siguiente posicion de escritura
            k++;
        }
    }

    // Muestra el arreglo en una linea
    static void mostrar(int[] arr) {
        // Recorremos todos los elementos
        for (int i = 0; i < arr.length; i++) {
            // Imprimimos cada uno seguido de un espacio
            System.out.print(arr[i] + " ");
        }
        // Salto de linea final
        System.out.println();
    }
}
