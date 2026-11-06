/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : Caso practico 2 - Sistema de pedidos de comercio electronico
 *  Programa  : SistemaPedidos
 *  Objetivo  : Integrar los tres algoritmos de la unidad: maximo y minimo
 *              por divide y conquista, ordenamiento con Quicksort (Lomuto) y
 *              busqueda binaria iterativa sobre los montos ordenados.
 *  Complejidad: O(n) + O(n log n) promedio + O(log n)
 *  Ejecutar  : java consola/unidad2/SistemaPedidos.java
 * =============================================================================
 */

// Clase principal del programa
public class SistemaPedidos {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Montos de los pedidos del dia
        int[] montos = {250, 120, 890, 45, 670, 310, 150, 980, 60, 430};
        // Maximo y minimo por divide y conquista
        int[] resultado = maxMinDYC(montos, 0, montos.length - 1);
        // Mostramos el maximo
        System.out.println("Monto maximo: " + resultado[0]);
        // Mostramos el minimo
        System.out.println("Monto minimo: " + resultado[1]);
        // Ordenamos los montos con Quicksort
        quicksort(montos, 0, montos.length - 1);
        // Encabezado de los montos ordenados
        System.out.println("Montos ordenados:");
        // Recorremos el arreglo ordenado
        for (int i = 0; i < montos.length; i++) {
            // Imprimimos cada monto
            System.out.print(montos[i] + " ");
        }
        // Salto de linea
        System.out.println();
        // Monto que queremos localizar
        int objetivo = 670;
        // Busqueda binaria sobre el arreglo ya ordenado
        int indice = busquedaBinaria(montos, objetivo);
        // Mostramos el resultado
        if (indice != -1) {
            // Encontrado
            System.out.println("Monto " + objetivo + " encontrado en indice " + indice);
        } else {
            // No encontrado
            System.out.println("Monto " + objetivo + " no encontrado.");
        }
    }

    // Devuelve {maximo, minimo} del rango [inicio, fin]
    static int[] maxMinDYC(int[] montos, int inicio, int fin) {
        // Caso base: un elemento
        if (inicio == fin) {
            // Es maximo y minimo
            return new int[]{montos[inicio], montos[inicio]};
        }
        // Caso base: dos elementos
        if (inicio + 1 == fin) {
            // Una comparacion decide cual es mayor
            if (montos[inicio] > montos[fin]) {
                // El primero es el maximo
                return new int[]{montos[inicio], montos[fin]};
            } else {
                // El segundo es el maximo
                return new int[]{montos[fin], montos[inicio]};
            }
        }
        // Dividir: punto medio
        int medio = (inicio + fin) / 2;
        // Conquistar: mitad izquierda
        int[] izq = maxMinDYC(montos, inicio, medio);
        // Conquistar: mitad derecha
        int[] der = maxMinDYC(montos, medio + 1, fin);
        // Combinar: mayor de los maximos
        int max = Math.max(izq[0], der[0]);
        // Combinar: menor de los minimos
        int min = Math.min(izq[1], der[1]);
        // Resultado combinado
        return new int[]{max, min};
    }

    // Quicksort sobre [bajo, alto]
    static void quicksort(int[] arr, int bajo, int alto) {
        // Solo si hay al menos dos elementos
        if (bajo < alto) {
            // Posicion final del pivote
            int p = particion(arr, bajo, alto);
            // Ordenamos la izquierda
            quicksort(arr, bajo, p - 1);
            // Ordenamos la derecha
            quicksort(arr, p + 1, alto);
        }
    }

    // Particion de Lomuto con pivote arr[alto]
    static int particion(int[] arr, int bajo, int alto) {
        // El pivote es el ultimo elemento
        int pivote = arr[alto];
        // Final de la zona de menores o iguales
        int i = bajo - 1;
        // Recorremos el tramo sin el pivote
        for (int j = bajo; j < alto; j++) {
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
        // Guardamos el elemento que cedera su lugar
        int temp = arr[i + 1];
        // Colocamos el pivote
        arr[i + 1] = arr[alto];
        // Completamos el intercambio
        arr[alto] = temp;
        // Posicion final del pivote
        return i + 1;
    }

    // Busqueda binaria iterativa
    static int busquedaBinaria(int[] arr, int objetivo) {
        // Limite izquierdo
        int inicio = 0;
        // Limite derecho
        int fin = arr.length - 1;
        // Mientras el rango no este vacio
        while (inicio <= fin) {
            // Punto medio
            int medio = (inicio + fin) / 2;
            // Comparamos con el objetivo
            if (arr[medio] == objetivo) {
                // Encontrado
                return medio;
            } else if (objetivo < arr[medio]) {
                // Esta a la izquierda
                fin = medio - 1;
            } else {
                // Esta a la derecha
                inicio = medio + 1;
            }
        }
        // No encontrado
        return -1;
    }
}
