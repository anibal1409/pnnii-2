/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : 7 Quicksort con particion de Lomuto
 *  Programa  : Quicksort
 *  Objetivo  : Ordenar eligiendo como pivote el ultimo elemento del tramo,
 *              dejando a su izquierda los menores o iguales y a su derecha
 *              los mayores (particion de Lomuto), y ordenando recursivamente
 *              cada lado. Cuenta las comparaciones con el pivote.
 *  Complejidad: O(n log n) promedio, O(n^2) peor caso (arreglo ya ordenado)
 *  Ejecutar  : java consola/unidad2/Quicksort.java
 * =============================================================================
 */

// Clase principal del programa
public class Quicksort {

    // Contador global de comparaciones con el pivote
    static int comparaciones = 0;

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Arreglo de ejemplo
        int[] datos = {3, 8, 2, 5, 1, 4, 7, 6};
        // Encabezado del arreglo original
        System.out.println("Arreglo original:");
        // Mostramos el arreglo antes de ordenar
        mostrar(datos);
        // Ordenamos todo el arreglo: rango [0, n-1]
        quicksort(datos, 0, datos.length - 1);
        // Encabezado del arreglo ordenado
        System.out.println("Arreglo ordenado con Quicksort:");
        // Mostramos el resultado
        mostrar(datos);
        // Total de comparaciones realizadas
        System.out.println("Comparaciones: " + comparaciones);
    }

    // Ordena el rango [bajo, alto] del arreglo
    static void quicksort(int[] arr, int bajo, int alto) {
        // Solo seguimos si el tramo tiene al menos dos elementos
        if (bajo < alto) {
            // Particionamos y obtenemos la posicion final del pivote
            int indicePivote = particionLomuto(arr, bajo, alto);
            // Ordenamos la parte izquierda (menores o iguales al pivote)
            quicksort(arr, bajo, indicePivote - 1);
            // Ordenamos la parte derecha (mayores que el pivote)
            quicksort(arr, indicePivote + 1, alto);
        }
    }

    // Particion de Lomuto: el pivote es arr[alto]; devuelve su posicion final
    static int particionLomuto(int[] arr, int bajo, int alto) {
        // Tomamos como pivote el ultimo elemento
        int pivote = arr[alto];
        // i marca el final de la zona de elementos <= pivote
        int i = bajo - 1;
        // Recorremos todos los elementos excepto el pivote
        for (int j = bajo; j < alto; j++) {
            // Contamos la comparacion con el pivote
            comparaciones++;
            // Si el elemento actual es menor o igual al pivote
            if (arr[j] <= pivote) {
                // Ampliamos la zona de menores
                i++;
                // Guardamos arr[i] para el intercambio
                int temp = arr[i];
                // Llevamos arr[j] a la zona de menores
                arr[i] = arr[j];
                // Y el antiguo arr[i] a la posicion j
                arr[j] = temp;
            }
        }
        // Guardamos el primer elemento mayor que el pivote
        int temp = arr[i + 1];
        // Colocamos el pivote en su posicion definitiva
        arr[i + 1] = arr[alto];
        // El elemento desplazado ocupa el lugar del pivote
        arr[alto] = temp;
        // Devolvemos la posicion final del pivote
        return i + 1;
    }

    // Muestra el arreglo en una linea
    static void mostrar(int[] arr) {
        // Recorremos todos los elementos
        for (int i = 0; i < arr.length; i++) {
            // Imprimimos cada uno seguido de un espacio
            System.out.print(arr[i] + " ");
        }
        // Salto de linea final
        System.out.println();
    }
}
