/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 5.1 Ejercicio resuelto 1 - Busqueda binaria en orden descendente
 *  Programa  : BusquedaBinariaDesc
 *  Objetivo  : Adaptar la busqueda binaria a un arreglo ordenado de mayor a
 *              menor: si el objetivo es mayor que el medio, esta a la izquierda.
 *  Complejidad: O(log n)
 *  Ejecutar  : java consola/unidad2/BusquedaBinariaDesc.java
 * =============================================================================
 */

// Clase principal del programa
public class BusquedaBinariaDesc {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo ordenado descendentemente
        int[] datos = {20, 15, 10, 5, 0};
        // Valor que queremos buscar
        int objetivo = 10;
        // Buscamos el objetivo
        int indice = busquedaBinariaDesc(datos, objetivo);
        // Mostramos el resultado (-1 si no se encontro)
        System.out.println("Indice de " + objetivo + ": " + indice);
    }

    // Busqueda binaria para un arreglo en orden descendente
    static int busquedaBinariaDesc(int[] arr, int objetivo) {
        // Limite izquierdo del rango
        int inicio = 0;
        // Limite derecho del rango
        int fin = arr.length - 1;
        // Repetimos mientras el rango no este vacio
        while (inicio <= fin) {
            // Indice medio del rango actual
            int medio = (inicio + fin) / 2;
            // Si coincide, terminamos
            if (arr[medio] == objetivo) {
                // Devolvemos su posicion
                return medio;
            } else if (objetivo > arr[medio]) {
                // En orden descendente los mayores estan a la izquierda
                fin = medio - 1;
            } else {
                // Y los menores estan a la derecha
                inicio = medio + 1;
            }
        }
        // El objetivo no existe en el arreglo
        return -1;
    }
}
