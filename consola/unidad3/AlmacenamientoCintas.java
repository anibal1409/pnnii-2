/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 2 Almacenamiento optimo en cintas magneticas
 *  Programa  : AlmacenamientoCintas
 *  Objetivo  : Ordenar los archivos en la cinta para minimizar el tiempo
 *              total de acceso T = suma de los tiempos de llegada a cada
 *              archivo. Eleccion voraz: en cada posicion se graba el archivo
 *              mas corto de los que faltan (orden por longitud creciente).
 *              T = n*L1 + (n-1)*L2 + ... + 1*Ln
 *  Complejidad: O(n^2) con seleccion directa (O(n log n) con un ordenamiento
 *              eficiente de la Unidad II)
 *  Ejecutar  : java consola/unidad3/AlmacenamientoCintas.java
 * =============================================================================
 */

// Clase principal del programa
public class AlmacenamientoCintas {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Longitudes de los archivos en el orden en que llegaron
        int[] longitudes = {15, 3, 8, 20, 5};
        // Mostramos los datos de entrada
        System.out.print("Longitudes originales: ");
        // Imprimimos el arreglo original
        mostrar(longitudes);
        // Costo si se grabaran en el orden de llegada
        System.out.println("Tiempo total en el orden original: " + tiempoTotal(longitudes));
        // Copia para no modificar los datos originales
        int[] cinta = new int[longitudes.length];
        // Copiamos elemento a elemento
        for (int i = 0; i < longitudes.length; i++) {
            // Elemento i de la copia
            cinta[i] = longitudes[i];
        }
        // Aplicamos la estrategia voraz
        ordenarVoraz(cinta);
        // Mostramos el orden optimo
        System.out.print("Orden optimo (menor a mayor): ");
        // Imprimimos la cinta ordenada
        mostrar(cinta);
        // Detalle del tiempo de llegada a cada archivo
        detalle(cinta);
        // Costo minimo
        System.out.println("Tiempo total minimo: " + tiempoTotal(cinta));
    }

    // Eleccion voraz: en cada posicion k se coloca el menor de los restantes
    static void ordenarVoraz(int[] cinta) {
        // Recorremos las posiciones de la cinta de izquierda a derecha
        for (int k = 0; k < cinta.length - 1; k++) {
            // Suponemos que el menor de los restantes esta en k
            int menor = k;
            // Buscamos el archivo mas corto entre k y el final
            for (int j = k + 1; j < cinta.length; j++) {
                // Si encontramos uno mas corto, lo recordamos
                if (cinta[j] < cinta[menor]) {
                    // Nuevo candidato
                    menor = j;
                }
            }
            // Guardamos el valor de la posicion k
            int temp = cinta[k];
            // Grabamos el mas corto en la posicion k (decision irreversible)
            cinta[k] = cinta[menor];
            // El desplazado ocupa el hueco
            cinta[menor] = temp;
        }
    }

    // T = suma de los tiempos de llegada (longitudes acumuladas) de cada archivo
    static int tiempoTotal(int[] cinta) {
        // Longitud acumulada hasta el archivo actual
        int acumulado = 0;
        // Suma de todos los tiempos de llegada
        int total = 0;
        // Recorremos la cinta en orden
        for (int i = 0; i < cinta.length; i++) {
            // Para llegar al archivo i hay que pasar por todos los anteriores y leerlo
            acumulado += cinta[i];
            // Sumamos su tiempo de acceso
            total += acumulado;
        }
        // Devolvemos el tiempo total
        return total;
    }

    // Muestra el tiempo de acceso de cada archivo y su factor (n - i)
    static void detalle(int[] cinta) {
        // Numero de archivos
        int n = cinta.length;
        // Longitud acumulada
        int acumulado = 0;
        // Recorremos la cinta
        for (int i = 0; i < n; i++) {
            // Tiempo de llegada al archivo i
            acumulado += cinta[i];
            // El archivo i se cuenta (n - i) veces en el total
            System.out.println("  Archivo " + cinta[i] + ": acceso = " + acumulado + ", aporta " + (n - i) + " x " + cinta[i] + " = " + ((n - i) * cinta[i]));
        }
    }

    // Muestra un arreglo en una linea
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
