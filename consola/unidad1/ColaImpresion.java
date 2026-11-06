/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 6.2 Caso practico - Cola de impresion con prioridades
 *  Programa  : ColaImpresion
 *  Objetivo  : Gestionar una impresora compartida con un Max-Heap donde las
 *              prioridades son 1 (baja), 2 (normal) y 3 (urgente).
 *              Siempre se imprime primero el trabajo de mayor prioridad.
 *  Complejidad: INSERTAR O(log n), EXTRAER O(log n)
 *  Ejecutar  : java consola/unidad1/ColaImpresion.java
 * =============================================================================
 */

// Clase principal de la cola de impresion con prioridades
public class ColaImpresion {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo para el Max-Heap de prioridades
        int[] heap = new int[10];
        // Numero de trabajos en la cola
        int tamano = 0;
        // Prioridades de los trabajos en orden de llegada
        int[] trabajos = {2, 1, 3, 2, 3};
        // Encolamos cada trabajo
        for (int k = 0; k < trabajos.length; k++) {
            // Insertamos el trabajo en el Max-Heap
            tamano = insertar(heap, tamano, trabajos[k]);
        }
        // Mostramos la cola de impresion
        System.out.print("Cola de impresion (mayor = mas urgente): ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
        // El trabajo mas urgente siempre esta en la raiz
        System.out.println("Imprimiendo trabajo con prioridad: " + heap[0]);
        // Extraemos la raiz y reorganizamos
        tamano = extraerMaximo(heap, tamano);
        // Mostramos la cola despues de imprimir
        System.out.print("Cola despues de imprimir: ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
    }

    // INSERTAR en Max-Heap: agrega al final y flota mientras el padre sea menor
    static int insertar(int[] heap, int tamano, int valor) {
        // Colocamos el trabajo al final
        heap[tamano] = valor;
        // Indice del nuevo trabajo
        int i = tamano;
        // Sube mientras su prioridad sea mayor que la de su padre
        while (i > 0 && heap[(i - 1) / 2] < heap[i]) {
            // Indice del padre
            int padre = (i - 1) / 2;
            // Intercambio con variable temporal
            int temp = heap[padre];
            // El trabajo sube
            heap[padre] = heap[i];
            // El padre baja
            heap[i] = temp;
            // Continuamos desde la posicion del padre
            i = padre;
        }
        // Un trabajo mas en la cola
        return tamano + 1;
    }

    // EXTRAER el maximo: la raiz se reemplaza por el ultimo y se aplica HEAPIFY
    static int extraerMaximo(int[] heap, int tamano) {
        // Movemos el ultimo trabajo a la raiz
        heap[0] = heap[tamano - 1];
        // La cola tiene un trabajo menos
        tamano--;
        // Comenzamos a hundir desde la raiz
        int i = 0;
        // Bandera que controla el ciclo
        boolean continuar = true;
        // Hundimos mientras algun hijo sea mayor
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
                // Propiedad de Max-Heap restaurada
                continuar = false;
            }
        }
        // Devolvemos el nuevo tamano
        return tamano;
    }

    // Muestra los elementos validos del heap
    static void mostrar(int[] heap, int tamano) {
        // Recorremos la parte ocupada del arreglo
        for (int j = 0; j < tamano; j++) {
            // Imprimimos cada prioridad
            System.out.print(heap[j] + " ");
        }
        // Salto de linea final
        System.out.println();
    }
}
