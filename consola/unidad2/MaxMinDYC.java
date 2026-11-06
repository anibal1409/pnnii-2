/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 4 Maximos y minimos
 *  Programa  : MaxMinDYC
 *  Objetivo  : Hallar a la vez el maximo y el minimo de un arreglo dividiendo
 *              en mitades, resolviendo cada mitad y combinando:
 *              max = max(maxIzq, maxDer), min = min(minIzq, minDer).
 *              Tambien cuenta las comparaciones entre elementos: 3n/2 - 2
 *              cuando n es potencia de 2 (el metodo ingenuo usa 2n - 2).
 *  Complejidad: O(n)
 *  Ejecutar  : java consola/unidad2/MaxMinDYC.java
 * =============================================================================
 */

// Clase principal del programa
public class MaxMinDYC {

    // Contador global de comparaciones entre elementos del arreglo
    static int comparaciones = 0;

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo de ejemplo sin ordenar
        int[] datos = {8, 3, 15, 6, 2, 11, 7, 4};
        // Resolvemos el problema sobre todo el arreglo
        int[] resultado = maxMinDYC(datos, 0, datos.length - 1);
        // Encabezado del arreglo
        System.out.println("Arreglo: ");
        // Recorremos el arreglo
        for (int i = 0; i < datos.length; i++) {
            // Imprimimos cada elemento
            System.out.print(datos[i] + " ");
        }
        // Salto de linea
        System.out.println();
        // La posicion 0 del resultado es el maximo
        System.out.println("Maximo: " + resultado[0]);
        // La posicion 1 del resultado es el minimo
        System.out.println("Minimo: " + resultado[1]);
        // Comparaciones realizadas frente a la formula 3n/2 - 2
        System.out.println("Comparaciones: " + comparaciones + " (3n/2 - 2 = " + (3 * datos.length / 2 - 2) + ")");
    }

    // Devuelve {maximo, minimo} del rango [inicio, fin]
    static int[] maxMinDYC(int[] arr, int inicio, int fin) {
        // CASO BASE 1: un solo elemento, es maximo y minimo a la vez
        if (inicio == fin) {
            // No hace falta comparar
            return new int[]{arr[inicio], arr[inicio]};
        }
        // CASO BASE 2: dos elementos, una sola comparacion basta
        if (inicio + 1 == fin) {
            // Contamos esa comparacion
            comparaciones++;
            // Si el primero es mayor, el es el maximo
            if (arr[inicio] > arr[fin]) {
                // Devolvemos {max, min}
                return new int[]{arr[inicio], arr[fin]};
            } else {
                // Si no, el segundo es el maximo
                return new int[]{arr[fin], arr[inicio]};
            }
        }
        // DIVIDIR: punto medio del rango
        int medio = (inicio + fin) / 2;
        // CONQUISTAR: resolvemos la mitad izquierda
        int[] izq = maxMinDYC(arr, inicio, medio);
        // CONQUISTAR: resolvemos la mitad derecha
        int[] der = maxMinDYC(arr, medio + 1, fin);
        // COMBINAR: dos comparaciones, una para el maximo y otra para el minimo
        comparaciones += 2;
        // El maximo global es el mayor de los dos maximos
        int maxGlobal = Math.max(izq[0], der[0]);
        // El minimo global es el menor de los dos minimos
        int minGlobal = Math.min(izq[1], der[1]);
        // Devolvemos el resultado combinado
        return new int[]{maxGlobal, minGlobal};
    }
}
