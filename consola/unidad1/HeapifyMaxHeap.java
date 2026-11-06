/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 5.2 Algoritmo HEAPIFY (hundir / sink down)
 *  Programa  : HeapifyMaxHeap
 *  Objetivo  : Restaurar la propiedad de Max-Heap cuando la raiz la viola.
 *              Precondicion: los subarboles izquierdo y derecho del nodo ya
 *              son Max-Heaps; solo el nodo i esta "fuera de lugar".
 *  Complejidad: O(log n)
 *  Ejecutar  : java consola/unidad1/HeapifyMaxHeap.java
 * =============================================================================
 */

// Clase principal del programa
public class HeapifyMaxHeap {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo cuya raiz (10) viola la propiedad; sus subarboles si son heaps
        int[] heap = {10, 80, 60, 70, 50, 30, 40};
        // Mostramos el arreglo original
        System.out.print("Antes de HEAPIFY:   ");
        // Imprimimos su contenido
        mostrar(heap, heap.length);
        // Aplicamos HEAPIFY desde la raiz (indice 0)
        heapify(heap, 0, heap.length);
        // Mostramos el resultado
        System.out.print("Despues de HEAPIFY: ");
        // Imprimimos su contenido
        mostrar(heap, heap.length);
    }

    // HEAPIFY iterativo para Max-Heap: hunde el nodo i hasta su lugar
    static void heapify(int[] heap, int i, int tamano) {
        // Bandera que controla el ciclo de hundimiento
        boolean continuar = true;
        // Repetimos mientras el nodo siga siendo menor que algun hijo
        while (continuar) {
            // Indice del hijo izquierdo
            int izquierdo = 2 * i + 1;
            // Indice del hijo derecho
            int derecho = 2 * i + 2;
            // Suponemos que el mayor es el propio nodo
            int mayor = i;
            // Si el hijo izquierdo existe y es mayor, pasa a ser el mayor
            if (izquierdo < tamano && heap[izquierdo] > heap[mayor]) {
                // Actualizamos el indice del mayor
                mayor = izquierdo;
            }
            // Si el hijo derecho existe y es mayor, pasa a ser el mayor
            if (derecho < tamano && heap[derecho] > heap[mayor]) {
                // Actualizamos el indice del mayor
                mayor = derecho;
            }
            // Si un hijo es mayor que el nodo, intercambiamos y seguimos bajando
            if (mayor != i) {
                // Guardamos el valor del nodo
                int temp = heap[i];
                // Subimos el hijo mayor
                heap[i] = heap[mayor];
                // Bajamos el nodo
                heap[mayor] = temp;
                // Continuamos desde la nueva posicion del nodo
                i = mayor;
            } else {
                // El nodo ya es mayor que sus hijos: terminamos
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
