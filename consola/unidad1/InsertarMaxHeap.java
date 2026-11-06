/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 5.1 Algoritmo INSERTAR en un Heap
 *  Programa  : InsertarMaxHeap
 *  Objetivo  : Insertar valores uno a uno en un Max-Heap: se coloca el valor al
 *              final del arreglo y se "flota" (bubble up) mientras su padre
 *              sea menor.
 *  Complejidad: O(log n) por insercion
 *  Ejecutar  : java consola/unidad1/InsertarMaxHeap.java
 * =============================================================================
 */

// Clase principal del programa
public class InsertarMaxHeap {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo con capacidad para 10 elementos
        int[] heap = new int[10];
        // Numero de elementos que contiene el heap actualmente
        int tamano = 0;
        // Valores que vamos a insertar en orden de llegada
        int[] valores = {50, 30, 70, 90};
        // Insertamos cada valor y mostramos el heap despues de cada insercion
        for (int k = 0; k < valores.length; k++) {
            // INSERTAR devuelve el nuevo tamano del heap
            tamano = insertar(heap, tamano, valores[k]);
            // Mostramos el estado del heap
            System.out.print("Insertado " + valores[k] + " -> ");
            // Imprimimos el contenido actual
            mostrar(heap, tamano);
        }
    }

    // INSERTAR en Max-Heap: agrega al final y flota hacia arriba
    static int insertar(int[] heap, int tamano, int valor) {
        // Paso 1: colocamos el nuevo valor en la primera posicion libre
        heap[tamano] = valor;
        // Paso 2: el indice del nuevo elemento es el tamano anterior
        int i = tamano;
        // Mientras no sea la raiz y su padre sea menor, debe subir
        while (i > 0 && heap[(i - 1) / 2] < heap[i]) {
            // Calculamos el indice del padre
            int padre = (i - 1) / 2;
            // Guardamos el valor del padre en una variable temporal
            int temp = heap[padre];
            // Subimos el hijo a la posicion del padre
            heap[padre] = heap[i];
            // Bajamos el padre a la posicion del hijo
            heap[i] = temp;
            // Seguimos subiendo desde la posicion del padre
            i = padre;
        }
        // El heap crecio en un elemento
        return tamano + 1;
    }

    // Muestra los primeros "tamano" elementos del arreglo en una linea
    static void mostrar(int[] heap, int tamano) {
        // Recorremos solo la parte ocupada del arreglo
        for (int j = 0; j < tamano; j++) {
            // Imprimimos cada elemento separado por un espacio
            System.out.print(heap[j] + " ");
        }
        // Salto de linea al terminar
        System.out.println();
    }
}
