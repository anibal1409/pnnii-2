/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 5.3 Construccion de un Heap desde un arreglo
 *  Programa  : ConstruirMaxHeap
 *  Objetivo  : Convertir un arreglo desordenado en Max-Heap aplicando HEAPIFY
 *              desde el ultimo nodo interno ((n / 2) - 1) hasta la raiz.
 *  Complejidad: O(n)
 *  Ejecutar  : java consola/unidad1/ConstruirMaxHeap.java
 * =============================================================================
 */

// Clase principal del programa
public class ConstruirMaxHeap {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo desordenado de entrada
        int[] heap = {4, 10, 3, 5, 1, 2, 8};
        // Cantidad de elementos
        int tamano = heap.length;
        // Indice del ultimo nodo que tiene al menos un hijo
        int ultimoInterno = (tamano / 2) - 1;
        // Mostramos el arreglo original
        System.out.print("Arreglo original:    ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
        // Recorremos los nodos internos desde abajo hacia la raiz
        for (int i = ultimoInterno; i >= 0; i--) {
            // Hundimos el nodo i; sus subarboles ya son heaps
            heapify(heap, i, tamano);
            // Mostramos el progreso para seguir el algoritmo
            System.out.print("HEAPIFY en indice " + i + ": ");
            // Imprimimos el estado parcial
            mostrar(heap, tamano);
        }
        // Mostramos el heap final
        System.out.print("Max-Heap construido: ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
    }

    // HEAPIFY iterativo para Max-Heap
    static void heapify(int[] heap, int i, int tamano) {
        // Bandera que controla el ciclo
        boolean continuar = true;
        // Repetimos mientras haya que hundir el nodo
        while (continuar) {
            // Indice del hijo izquierdo
            int izquierdo = 2 * i + 1;
            // Indice del hijo derecho
            int derecho = 2 * i + 2;
            // Suponemos que el mayor es el nodo actual
            int mayor = i;
            // Comparamos con el hijo izquierdo
            if (izquierdo < tamano && heap[izquierdo] > heap[mayor]) {
                // El izquierdo es mayor
                mayor = izquierdo;
            }
            // Comparamos con el hijo derecho
            if (derecho < tamano && heap[derecho] > heap[mayor]) {
                // El derecho es mayor
                mayor = derecho;
            }
            // Si algun hijo es mayor, intercambiamos
            if (mayor != i) {
                // Guardamos el valor del nodo
                int temp = heap[i];
                // Subimos el hijo mayor
                heap[i] = heap[mayor];
                // Bajamos el nodo
                heap[mayor] = temp;
                // Continuamos hundiendo
                i = mayor;
            } else {
                // Propiedad restaurada
                continuar = false;
            }
        }
    }

    // Muestra los elementos validos del heap
    static void mostrar(int[] heap, int tamano) {
        // Recorremos la parte ocupada del arreglo
        for (int j = 0; j < tamano; j++) {
            // Imprimimos cada elemento
            System.out.print(heap[j] + " ");
        }
        // Salto de linea final
        System.out.println();
    }
}
