/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 6.1 Caso practico - Sistema de prioridades en un hospital
 *  Programa  : HospitalPrioridades
 *  Objetivo  : Gestionar la emergencia con un Min-Heap de codigos de gravedad
 *              (1 = critico ... 5 = no urgente). Siempre se atiende primero al
 *              paciente con el codigo mas bajo.
 *  Complejidad: INSERTAR O(log n), EXTRAER O(log n)
 *  Ejecutar  : java consola/unidad1/HospitalPrioridades.java
 * =============================================================================
 */

// Clase principal del sistema de prioridades hospitalarias
public class HospitalPrioridades {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo que representa el Min-Heap de codigos de gravedad
        int[] heap = new int[20];
        // Numero de pacientes en espera
        int tamano = 0;
        // Codigos de los pacientes en orden de llegada
        int[] llegadas = {3, 5, 2, 1, 4, 2};
        // Registramos a cada paciente que llega
        for (int k = 0; k < llegadas.length; k++) {
            // Validamos que el codigo este entre 1 y 5
            if (llegadas[k] < 1 || llegadas[k] > 5) {
                // Informamos el codigo invalido y lo ignoramos
                System.out.println("Codigo invalido ignorado: " + llegadas[k]);
            } else {
                // Insertamos al paciente en el Min-Heap
                tamano = insertar(heap, tamano, llegadas[k]);
            }
        }
        // Mostramos la cola de espera
        System.out.print("Min-Heap de gravedad (menor = mas urgente): ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
        // El paciente mas critico siempre esta en la raiz
        System.out.println("Atendiendo al paciente mas critico: " + heap[0]);
        // Extraemos la raiz y reorganizamos
        tamano = extraerMinimo(heap, tamano);
        // Mostramos la cola despues de atender
        System.out.print("Min-Heap despues de atender: ");
        // Imprimimos su contenido
        mostrar(heap, tamano);
    }

    // INSERTAR en Min-Heap: agrega al final y flota mientras el padre sea mayor
    static int insertar(int[] heap, int tamano, int valor) {
        // Colocamos al paciente al final
        heap[tamano] = valor;
        // Indice del nuevo paciente
        int i = tamano;
        // Sube mientras su codigo sea menor que el de su padre
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            // Indice del padre
            int padre = (i - 1) / 2;
            // Intercambio con variable temporal
            int temp = heap[padre];
            // El paciente sube
            heap[padre] = heap[i];
            // El padre baja
            heap[i] = temp;
            // Continuamos desde la posicion del padre
            i = padre;
        }
        // Un paciente mas en espera
        return tamano + 1;
    }

    // EXTRAER el minimo: la raiz se reemplaza por el ultimo y se aplica HEAPIFY
    static int extraerMinimo(int[] heap, int tamano) {
        // Movemos el ultimo paciente a la raiz
        heap[0] = heap[tamano - 1];
        // La cola tiene un paciente menos
        tamano--;
        // Comenzamos a hundir desde la raiz
        int i = 0;
        // Bandera que controla el ciclo
        boolean continuar = true;
        // Hundimos mientras algun hijo sea menor
        while (continuar) {
            // Indice del hijo izquierdo
            int izquierdo = 2 * i + 1;
            // Indice del hijo derecho
            int derecho = 2 * i + 2;
            // Suponemos que el menor es el nodo actual
            int menor = i;
            // Comparamos con el hijo izquierdo
            if (izquierdo < tamano && heap[izquierdo] < heap[menor]) {
                // El izquierdo es menor
                menor = izquierdo;
            }
            // Comparamos con el hijo derecho
            if (derecho < tamano && heap[derecho] < heap[menor]) {
                // El derecho es menor
                menor = derecho;
            }
            // Si algun hijo es menor, intercambiamos
            if (menor != i) {
                // Guardamos el valor del nodo
                int temp = heap[i];
                // Subimos el hijo menor
                heap[i] = heap[menor];
                // Bajamos el nodo
                heap[menor] = temp;
                // Continuamos hundiendo
                i = menor;
            } else {
                // Propiedad de Min-Heap restaurada
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
            // Imprimimos cada codigo
            System.out.print(heap[j] + " ");
        }
        // Salto de linea final
        System.out.println();
    }
}
