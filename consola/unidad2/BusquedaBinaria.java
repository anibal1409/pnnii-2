/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 3.3 Busqueda binaria (version recursiva)
 *  Programa  : BusquedaBinaria
 *  Objetivo  : Encontrar un valor en un arreglo ordenado ascendentemente
 *              comparando con el elemento central y descartando en cada
 *              llamada la mitad donde no puede estar.
 *  Complejidad: O(log n) (mejor caso O(1))
 *  Ejecutar  : java consola/unidad2/BusquedaBinaria.java
 * =============================================================================
 */

// Clase principal del programa
public class BusquedaBinaria {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo ordenado ascendentemente (requisito indispensable)
        int[] datos = {3, 7, 12, 19, 25, 33, 42, 56, 71};
        // Valor que queremos buscar
        int objetivo = 25;
        // Llamada inicial con todo el arreglo: rango [0, n-1]
        int indice = busquedaBinariaRec(datos, 0, datos.length - 1, objetivo);
        // Si el indice es distinto de -1 el valor existe
        if (indice != -1) {
            // Informamos la posicion encontrada
            System.out.println("El elemento " + objetivo + " se encuentra en el indice " + indice + ".");
        } else {
            // El valor no esta en el arreglo
            System.out.println("El elemento " + objetivo + " no esta en el arreglo.");
        }
    }

    // Busqueda binaria recursiva en el rango [inicio, fin] del arreglo
    static int busquedaBinariaRec(int[] arr, int inicio, int fin, int objetivo) {
        // Caso base de fracaso: el rango esta vacio, el objetivo no existe
        if (inicio > fin) {
            // -1 es la convencion para "no encontrado"
            return -1;
        }
        // DIVIDIR: indice medio del rango (la division entera trunca)
        int medio = (inicio + fin) / 2;
        // Caso base de exito: el elemento medio es el buscado
        if (arr[medio] == objetivo) {
            // Devolvemos su posicion
            return medio;
        }
        // Si el objetivo es menor, solo puede estar en la mitad izquierda
        if (objetivo < arr[medio]) {
            // CONQUISTAR: buscamos en [inicio, medio-1]
            return busquedaBinariaRec(arr, inicio, medio - 1, objetivo);
        }
        // Si es mayor, solo puede estar en la mitad derecha [medio+1, fin]
        return busquedaBinariaRec(arr, medio + 1, fin, objetivo);
    }
}
