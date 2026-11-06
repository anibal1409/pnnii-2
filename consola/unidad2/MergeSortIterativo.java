/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 6 Sortmerge 2 (Merge Sort iterativo, bottom-up)
 *  Programa  : MergeSortIterativo
 *  Objetivo  : Ordenar sin recursion: se intercalan bloques vecinos de tamano
 *              1, luego 2, 4, 8... hasta cubrir el arreglo. Si n no es
 *              potencia de 2, el ultimo bloque queda mas corto o sin pareja.
 *  Complejidad: O(n log n); memoria adicional O(n), sin pila de recursion
 *  Ejecutar  : java consola/unidad2/MergeSortIterativo.java
 * =============================================================================
 */

// Clase principal del programa
public class MergeSortIterativo {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo de ejemplo
        int[] datos = {6, 5, 3, 1, 8, 7, 2, 4};
        // Encabezado del arreglo original
        System.out.println("Arreglo original:");
        // Mostramos el arreglo antes de ordenar
        mostrar(datos);
        // Ordenamos de abajo hacia arriba
        mergeSortIterativo(datos);
        // Encabezado del arreglo ordenado
        System.out.println("Arreglo ordenado (bottom-up):");
        // Mostramos el resultado
        mostrar(datos);
    }

    // Merge Sort iterativo: bloques de tamano 1, 2, 4, ... hasta n
    static void mergeSortIterativo(int[] arr) {
        // Numero de elementos
        int n = arr.length;
        // Tamano de los bloques que se intercalan en esta pasada
        int tamano = 1;
        // Mientras un bloque no cubra todo el arreglo
        while (tamano < n) {
            // Recorremos el arreglo de dos bloques en dos bloques
            for (int inicio = 0; inicio < n; inicio += 2 * tamano) {
                // Ultimo indice del primer bloque (sin salirnos del arreglo)
                int medio = Math.min(inicio + tamano - 1, n - 1);
                // Ultimo indice del segundo bloque (sin salirnos del arreglo)
                int fin = Math.min(inicio + 2 * tamano - 1, n - 1);
                // Solo intercalamos si el segundo bloque tiene elementos
                if (medio < fin) {
                    // Intercalamos [inicio, medio] con [medio+1, fin]
                    merge(arr, inicio, medio, fin);
                }
            }
            // Mostramos el resultado de la pasada
            System.out.print("Pasada tamano=" + tamano + ": ");
            // Contenido actual del arreglo
            mostrar(arr);
            // La siguiente pasada usa bloques del doble de tamano
            tamano *= 2;
        }
    }

    // Intercala los tramos ordenados [inicio, medio] y [medio+1, fin]
    static void merge(int[] arr, int inicio, int medio, int fin) {
        // Tamano del primer bloque
        int n1 = medio - inicio + 1;
        // Tamano del segundo bloque
        int n2 = fin - medio;
        // Copia temporal del primer bloque
        int[] izq = new int[n1];
        // Copia temporal del segundo bloque
        int[] der = new int[n2];
        // Copiamos el primer bloque
        for (int i = 0; i < n1; i++) {
            // Elemento i del primer bloque
            izq[i] = arr[inicio + i];
        }
        // Copiamos el segundo bloque
        for (int j = 0; j < n2; j++) {
            // Elemento j del segundo bloque
            der[j] = arr[medio + 1 + j];
        }
        // Indice en izq
        int i = 0;
        // Indice en der
        int j = 0;
        // Posicion de escritura en arr
        int k = inicio;
        // Tomamos siempre el menor de los dos frentes
        while (i < n1 && j < n2) {
            // Si el de la izquierda es menor o igual, va primero
            if (izq[i] <= der[j]) {
                // Escribimos el de la izquierda
                arr[k] = izq[i];
                // Avanzamos en la izquierda
                i++;
            } else {
                // Escribimos el de la derecha
                arr[k] = der[j];
                // Avanzamos en la derecha
                j++;
            }
            // Avanzamos la escritura
            k++;
        }
        // Sobrantes del primer bloque
        while (i < n1) {
            // Copiamos el sobrante
            arr[k] = izq[i];
            // Siguiente sobrante
            i++;
            // Siguiente posicion
            k++;
        }
        // Sobrantes del segundo bloque
        while (j < n2) {
            // Copiamos el sobrante
            arr[k] = der[j];
            // Siguiente sobrante
            j++;
            // Siguiente posicion
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
