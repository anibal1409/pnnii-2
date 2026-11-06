/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 3.4 Busqueda binaria (version iterativa)
 *  Programa  : BusquedaBinariaIterativa
 *  Objetivo  : La misma estrategia que la version recursiva, pero las
 *              llamadas se sustituyen por un ciclo que ajusta los limites
 *              inicio y fin del rango de busqueda.
 *  Complejidad: O(log n) en tiempo, O(1) en memoria
 *  Ejecutar  : java consola/unidad2/BusquedaBinariaIterativa.java
 * =============================================================================
 */

// Clase principal del programa
public class BusquedaBinariaIterativa {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo ordenado ascendentemente
        int[] datos = {3, 7, 12, 19, 25, 33, 42, 56, 71};
        // Valor que queremos buscar
        int objetivo = 19;
        // Llamada al metodo iterativo
        int indice = busquedaBinariaIter(datos, objetivo);
        // Mostramos el resultado (-1 si no se encontro)
        System.out.println("Resultado iterativo: indice " + indice);
    }

    // Busqueda binaria iterativa sobre todo el arreglo
    static int busquedaBinariaIter(int[] arr, int objetivo) {
        // Limite izquierdo del rango de busqueda
        int inicio = 0;
        // Limite derecho del rango de busqueda
        int fin = arr.length - 1;
        // Repetimos mientras el rango no este vacio
        while (inicio <= fin) {
            // Indice medio del rango actual
            int medio = (inicio + fin) / 2;
            // Si el elemento medio es el buscado, terminamos
            if (arr[medio] == objetivo) {
                // Devolvemos su posicion
                return medio;
            }
            // Si el objetivo es menor, descartamos la mitad derecha
            if (objetivo < arr[medio]) {
                // El nuevo limite derecho queda antes del medio
                fin = medio - 1;
            } else {
                // Si es mayor, descartamos la mitad izquierda
                inicio = medio + 1;
            }
        }
        // El rango quedo vacio: el objetivo no existe
        return -1;
    }
}
