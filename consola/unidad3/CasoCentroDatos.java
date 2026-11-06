/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 7.1 Caso practico 1 - Optimizacion de un centro de datos
 *  Programa  : CasoCentroDatos
 *  Objetivo  : Aplicar dos estrategias voraces: ordenar los backups en la
 *              cinta por longitud creciente (minimo tiempo total de acceso) y
 *              fusionar los logs tomando siempre los dos mas pequenos
 *              (minimo costo de intercalacion, con un Min-Heap en arreglo).
 *  Complejidad: O(n^2) la cinta (seleccion), O(m log m) la fusion
 *  Ejecutar  : java consola/unidad3/CasoCentroDatos.java
 * =============================================================================
 */

// Clase principal del programa
public class CasoCentroDatos {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Longitudes de los backups en GB
        int[] backups = {120, 45, 30, 200, 75, 60, 110, 90, 35, 50};
        // Tamanos de los logs en MB
        int[] logs = {15, 8, 23, 10, 7};
        // Titulo del informe
        System.out.println("=== RESULTADOS CENTRO DE DATOS ===");
        // Orden ascendente: la eleccion voraz
        ordenar(backups, true);
        // Encabezado del orden optimo
        System.out.print("Orden optimo de backups (GB): ");
        // Mostramos el orden
        mostrar(backups);
        // Tiempo total con el orden optimo
        int optimo = tiempoTotal(backups);
        // Lo mostramos
        System.out.println("Tiempo total de acceso: " + optimo);
        // Orden descendente: el peor orden posible, como referencia
        ordenar(backups, false);
        // Tiempo total con el peor orden
        int peor = tiempoTotal(backups);
        // Mostramos la comparacion y el porcentaje de mejora
        System.out.println("Tiempo con orden descendente: " + peor + " (" + (100 * (peor - optimo) / optimo) + "% peor)");
        // Costo minimo de fusion de los logs
        System.out.println("Costo minimo de fusion de logs (MB): " + costoFusion(logs));
    }

    // Seleccion directa: ascendente si asc es true, descendente si es false
    static void ordenar(int[] arr, boolean asc) {
        // Recorremos las posiciones
        for (int k = 0; k < arr.length - 1; k++) {
            // Mejor candidato para la posicion k
            int mejor = k;
            // Revisamos los restantes
            for (int j = k + 1; j < arr.length; j++) {
                // Menor si es ascendente, mayor si es descendente
                if (asc ? arr[j] < arr[mejor] : arr[j] > arr[mejor]) {
                    // Nuevo candidato
                    mejor = j;
                }
            }
            // Guardamos el de la posicion k
            int temp = arr[k];
            // Colocamos el mejor
            arr[k] = arr[mejor];
            // Completamos el intercambio
            arr[mejor] = temp;
        }
    }

    // T = suma de las longitudes acumuladas (tiempo de llegada a cada archivo)
    static int tiempoTotal(int[] cinta) {
        // Longitud acumulada
        int acumulado = 0;
        // Suma de tiempos de llegada
        int total = 0;
        // Recorremos la cinta
        for (int i = 0; i < cinta.length; i++) {
            // Llegada al archivo i
            acumulado += cinta[i];
            // La sumamos al total
            total += acumulado;
        }
        // Tiempo total
        return total;
    }

    // Fusion optima con un Min-Heap; imprime cada fusion y devuelve el costo
    static int costoFusion(int[] tamanos) {
        // Heap con capacidad para todos
        int[] heap = new int[tamanos.length];
        // Elementos del heap
        int n = 0;
        // Insertamos todos los tamanos
        for (int i = 0; i < tamanos.length; i++) {
            // INSERTAR en el Min-Heap
            n = insertar(heap, n, tamanos[i]);
        }
        // Costo acumulado
        int costo = 0;
        // Mientras quede mas de un archivo
        while (n > 1) {
            // El menor esta en la raiz
            int a = heap[0];
            // Lo extraemos
            n = extraerMinimo(heap, n);
            // El segundo menor queda en la raiz
            int b = heap[0];
            // Lo extraemos
            n = extraerMinimo(heap, n);
            // Sumamos el costo de fusionarlos
            costo += a + b;
            // Mostramos la fusion
            System.out.println("  Fusionar " + a + " y " + b + " -> " + (a + b) + ", acumulado " + costo);
            // El resultado vuelve al heap
            n = insertar(heap, n, a + b);
        }
        // Costo minimo
        return costo;
    }

    // INSERTAR en Min-Heap
    static int insertar(int[] heap, int n, int valor) {
        // Colocamos el valor al final
        heap[n] = valor;
        // Posicion del nuevo valor
        int i = n;
        // Flotamos mientras el padre sea mayor
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            // Indice del padre
            int padre = (i - 1) / 2;
            // Guardamos el padre
            int temp = heap[padre];
            // Subimos el valor
            heap[padre] = heap[i];
            // Bajamos el padre
            heap[i] = temp;
            // Continuamos desde el padre
            i = padre;
        }
        // Nuevo tamano
        return n + 1;
    }

    // Extrae la raiz y restaura el Min-Heap con HEAPIFY
    static int extraerMinimo(int[] heap, int n) {
        // El ultimo pasa a la raiz
        heap[0] = heap[n - 1];
        // Un elemento menos
        n = n - 1;
        // Nodo que se hunde
        int i = 0;
        // Control del ciclo
        boolean continuar = true;
        // Hundimos mientras un hijo sea menor
        while (continuar) {
            // Hijo izquierdo
            int izq = 2 * i + 1;
            // Hijo derecho
            int der = 2 * i + 2;
            // Suponemos que el menor es el nodo
            int menor = i;
            // Comparamos con el hijo izquierdo
            if (izq < n && heap[izq] < heap[menor]) {
                // El izquierdo es menor
                menor = izq;
            }
            // Comparamos con el hijo derecho
            if (der < n && heap[der] < heap[menor]) {
                // El derecho es menor
                menor = der;
            }
            // Si hay un hijo menor, intercambiamos
            if (menor != i) {
                // Guardamos el nodo
                int temp = heap[i];
                // Subimos el hijo
                heap[i] = heap[menor];
                // Bajamos el nodo
                heap[menor] = temp;
                // Seguimos bajando
                i = menor;
            } else {
                // Ya esta en su lugar
                continuar = false;
            }
        }
        // Nuevo tamano
        return n;
    }

    // Muestra un arreglo en una linea
    static void mostrar(int[] arr) {
        // Recorremos los elementos
        for (int i = 0; i < arr.length; i++) {
            // Imprimimos cada uno
            System.out.print(arr[i] + " ");
        }
        // Salto de linea
        System.out.println();
    }
}
