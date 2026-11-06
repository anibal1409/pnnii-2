/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 4 Modelos optimos de intercalacion (costo minimo de fusion)
 *  Programa  : IntercalacionOptima
 *  Objetivo  : Fusionar varios archivos ordenados en uno solo; fusionar dos
 *              archivos de tamanos a y b cuesta a + b. Eleccion voraz (igual
 *              que Huffman): fusionar siempre los dos mas pequenos. Los
 *              tamanos se guardan en un Min-Heap hecho con un arreglo
 *              (INSERTAR y HEAPIFY de la Unidad I).
 *  Complejidad: O(n log n): n - 1 fusiones, cada una con operaciones O(log n)
 *  Ejecutar  : java consola/unidad3/IntercalacionOptima.java
 * =============================================================================
 */

// Clase principal del programa
public class IntercalacionOptima {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Tamanos de los archivos a intercalar
        int[] archivos = {4, 3, 2, 6};
        // Mostramos los datos de entrada
        System.out.print("Tamanos iniciales: ");
        // Recorremos los tamanos
        for (int i = 0; i < archivos.length; i++) {
            // Imprimimos cada tamano
            System.out.print(archivos[i] + " ");
        }
        // Salto de linea
        System.out.println();
        // Calculamos el costo minimo
        int costo = costoMinimoFusion(archivos);
        // Mostramos el resultado
        System.out.println("Costo total minimo de fusion: " + costo);
    }

    // Fusiona siempre los dos archivos mas pequenos; devuelve el costo total
    static int costoMinimoFusion(int[] tamanos) {
        // Min-Heap con capacidad para todos los archivos
        int[] heap = new int[tamanos.length];
        // Numero de elementos del heap
        int n = 0;
        // Insertamos cada tamano en el heap
        for (int i = 0; i < tamanos.length; i++) {
            // INSERTAR devuelve el nuevo tamano del heap
            n = insertar(heap, n, tamanos[i]);
        }
        // Costo acumulado de todas las fusiones
        int costoTotal = 0;
        // Mientras quede mas de un archivo
        while (n > 1) {
            // El mas pequeno esta en la raiz
            int a = heap[0];
            // Lo extraemos
            n = extraerMinimo(heap, n);
            // El siguiente mas pequeno queda ahora en la raiz
            int b = heap[0];
            // Lo extraemos tambien
            n = extraerMinimo(heap, n);
            // Fusionarlos cuesta la suma de sus tamanos
            int costo = a + b;
            // Lo sumamos al total
            costoTotal += costo;
            // El archivo fusionado vuelve al heap
            n = insertar(heap, n, costo);
            // Mostramos el paso
            System.out.println("Fusionar " + a + " y " + b + " -> costo parcial " + costo + ", acumulado " + costoTotal);
        }
        // Costo minimo total
        return costoTotal;
    }

    // INSERTAR en Min-Heap: coloca al final y flota mientras el padre sea mayor
    static int insertar(int[] heap, int n, int valor) {
        // Colocamos el valor en la primera posicion libre
        heap[n] = valor;
        // Posicion actual del nuevo valor
        int i = n;
        // Mientras no sea la raiz y su padre sea mayor
        while (i > 0 && heap[(i - 1) / 2] > heap[i]) {
            // Indice del padre
            int padre = (i - 1) / 2;
            // Guardamos el padre
            int temp = heap[padre];
            // Subimos el valor
            heap[padre] = heap[i];
            // Bajamos el padre
            heap[i] = temp;
            // Seguimos desde la posicion del padre
            i = padre;
        }
        // El heap crece en uno
        return n + 1;
    }

    // Quita la raiz: el ultimo pasa a la raiz y se hunde con HEAPIFY
    static int extraerMinimo(int[] heap, int n) {
        // El ultimo elemento ocupa la raiz
        heap[0] = heap[n - 1];
        // El heap tiene un elemento menos
        n = n - 1;
        // Posicion del nodo que se hunde
        int i = 0;
        // Bandera de control del ciclo
        boolean continuar = true;
        // Hundimos mientras algun hijo sea menor
        while (continuar) {
            // Hijo izquierdo
            int izquierdo = 2 * i + 1;
            // Hijo derecho
            int derecho = 2 * i + 2;
            // Suponemos que el menor es el propio nodo
            int menor = i;
            // Si el hijo izquierdo existe y es menor
            if (izquierdo < n && heap[izquierdo] < heap[menor]) {
                // Pasa a ser el menor
                menor = izquierdo;
            }
            // Si el hijo derecho existe y es menor
            if (derecho < n && heap[derecho] < heap[menor]) {
                // Pasa a ser el menor
                menor = derecho;
            }
            // Si un hijo es menor, intercambiamos y seguimos bajando
            if (menor != i) {
                // Guardamos el nodo
                int temp = heap[i];
                // Subimos el hijo menor
                heap[i] = heap[menor];
                // Bajamos el nodo
                heap[menor] = temp;
                // Continuamos desde la nueva posicion
                i = menor;
            } else {
                // Ya esta en su lugar
                continuar = false;
            }
        }
        // Nuevo tamano del heap
        return n;
    }
}
