/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 7.1 Ejercicio resuelto 2 - Insertar en un Min-Heap
 *  Programa  : InsertarMinHeap
 *  Objetivo  : Dado el Min-Heap [5, 10, 15, 20, 25], insertar el valor 3.
 *              En un Min-Heap el elemento sube mientras su padre sea MAYOR.
 *  Complejidad: O(log n)
 *  Ejecutar  : java consola/unidad1/InsertarMinHeap.java
 * =============================================================================
 */

// Clase principal del programa
public class InsertarMinHeap {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Min-Heap inicial; la ultima casilla queda libre para el nuevo valor
        int[] heap = {5, 10, 15, 20, 25, 0};
        // Cantidad de elementos validos del heap
        int tamano = 5;
        // Mostramos el heap original
        System.out.print("Min-Heap inicial: ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
        // Insertamos el 3 y actualizamos el tamano
        tamano = insertar(heap, tamano, 3);
        // Mostramos el resultado
        System.out.print("Min-Heap despues de insertar 3: ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
    }

    // INSERTAR en Min-Heap: agrega al final y flota hacia arriba
    static int insertar(int[] heap, int tamano, int valor) {
        // Colocamos el valor al final del arreglo
        heap[tamano] = valor;
        // Indice del nuevo elemento
        int i = tamano;
        // Mientras no sea la raiz y su padre sea mayor, debe subir
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            // Indice del padre
            int padre = (i - 1) / 2;
            // Intercambio con variable temporal
            int temp = heap[padre];
            // El hijo sube
            heap[padre] = heap[i];
            // El padre baja
            heap[i] = temp;
            // Continuamos desde la posicion del padre
            i = padre;
        }
        // Devolvemos el nuevo tamano
        return tamano + 1;
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
