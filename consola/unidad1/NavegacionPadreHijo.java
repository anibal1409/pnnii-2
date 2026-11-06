/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 4.3 y 4.4 Representacion en arreglos y navegacion padre-hijo
 *  Programa  : NavegacionPadreHijo
 *  Objetivo  : Usar las formulas de indice base 0 para ubicar el padre y los
 *              hijos de cada nodo de un arbol guardado en un arreglo.
 *              padre = (i - 1) / 2   hijoIzq = 2i + 1   hijoDer = 2i + 2
 *  Complejidad: O(1) por nodo
 *  Ejecutar  : java consola/unidad1/NavegacionPadreHijo.java
 * =============================================================================
 */

// Clase principal del programa
public class NavegacionPadreHijo {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Max-Heap de ejemplo representado en un arreglo secuencial
        int[] heap = {90, 70, 80, 30, 50, 60, 40};
        // Mostramos el arreglo completo con sus indices
        System.out.println("Arbol en arreglo: 90 70 80 30 50 60 40");
        // Recorremos todos los nodos para describir sus relaciones
        for (int i = 0; i < heap.length; i++) {
            // Describimos el nodo i usando el metodo auxiliar
            describirNodo(heap, i);
        }
    }

    // Muestra el nodo i junto con su padre y sus hijos (si existen)
    static void describirNodo(int[] heap, int i) {
        // Formula del padre (la division entera trunca el resultado)
        int padre = (i - 1) / 2;
        // Formula del hijo izquierdo
        int hijoIzq = 2 * i + 1;
        // Formula del hijo derecho
        int hijoDer = 2 * i + 2;
        // Mostramos el nodo actual con su indice
        System.out.print("Nodo [" + i + "]=" + heap[i]);
        // La raiz (indice 0) es el unico nodo sin padre
        if (i == 0) {
            // Indicamos que es la raiz
            System.out.print(" | padre: ninguno (raiz)");
        } else {
            // Mostramos el padre y su indice
            System.out.print(" | padre: [" + padre + "]=" + heap[padre]);
        }
        // El hijo izquierdo existe solo si su indice esta dentro del arreglo
        if (hijoIzq < heap.length) {
            // Mostramos el hijo izquierdo
            System.out.print(" | izq: [" + hijoIzq + "]=" + heap[hijoIzq]);
        } else {
            // No hay hijo izquierdo
            System.out.print(" | izq: ninguno");
        }
        // El hijo derecho existe solo si su indice esta dentro del arreglo
        if (hijoDer < heap.length) {
            // Mostramos el hijo derecho
            System.out.print(" | der: [" + hijoDer + "]=" + heap[hijoDer]);
        } else {
            // No hay hijo derecho
            System.out.print(" | der: ninguno");
        }
        // Un nodo sin hijo izquierdo es una hoja
        System.out.println(hijoIzq >= heap.length ? "  (hoja)" : "");
    }
}
