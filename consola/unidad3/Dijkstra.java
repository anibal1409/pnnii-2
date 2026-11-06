/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 6 Camino mas corto desde un origen unico (Dijkstra)
 *  Programa  : Dijkstra
 *  Objetivo  : Calcular la distancia minima desde un origen a todos los
 *              vertices de un grafo dirigido con pesos no negativos.
 *              Eleccion voraz: fijar el vertice no visitado de menor
 *              distancia y relajar sus aristas. prev[] permite reconstruir
 *              el camino. Variante con matriz de adyacencia (6.5).
 *  Complejidad: O(V^2)
 *  Ejecutar  : java consola/unidad3/Dijkstra.java
 * =============================================================================
 */

// Clase principal del programa
public class Dijkstra {

    // Marca de "no hay arista" en la matriz
    static final int SIN_ARISTA = -1;
    // Valor que representa infinito
    static final int INF = Integer.MAX_VALUE;

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Matriz del grafo dirigido de 5 vertices: grafo[u][v] = peso de u -> v
        int[][] grafo = {
            {SIN_ARISTA, 4, 1, SIN_ARISTA, SIN_ARISTA},
            {SIN_ARISTA, SIN_ARISTA, SIN_ARISTA, 1, SIN_ARISTA},
            {SIN_ARISTA, 2, SIN_ARISTA, 5, SIN_ARISTA},
            {SIN_ARISTA, SIN_ARISTA, SIN_ARISTA, SIN_ARISTA, 3},
            {SIN_ARISTA, SIN_ARISTA, SIN_ARISTA, SIN_ARISTA, SIN_ARISTA}
        };
        // Distancias minimas desde el origen
        int[] dist = new int[grafo.length];
        // Predecesor de cada vertice en su camino minimo
        int[] prev = new int[grafo.length];
        // Ejecutamos Dijkstra desde el vertice 0
        dijkstra(grafo, 0, dist, prev);
        // Encabezado de las distancias
        System.out.print("Distancias desde vertice 0: ");
        // Recorremos los vertices
        for (int v = 0; v < dist.length; v++) {
            // INF significa que el vertice no es alcanzable
            System.out.print((dist[v] == INF ? "INF" : String.valueOf(dist[v])) + " ");
        }
        // Salto de linea
        System.out.println();
        // Mostramos el camino hasta el vertice 4
        System.out.print("Camino a 4: ");
        // Reconstruimos el camino con prev
        imprimirCamino(prev, 4);
        // Salto de linea final
        System.out.println();
    }

    // Dijkstra con matriz: llena dist[] y prev[]
    static void dijkstra(int[][] grafo, int origen, int[] dist, int[] prev) {
        // Numero de vertices
        int n = grafo.length;
        // visitado[v] indica si la distancia de v ya es definitiva
        boolean[] visitado = new boolean[n];
        // Inicializamos todos los vertices
        for (int v = 0; v < n; v++) {
            // Distancia desconocida
            dist[v] = INF;
            // Sin predecesor
            prev[v] = -1;
        }
        // El origen esta a distancia 0 de si mismo
        dist[origen] = 0;
        // Cada vuelta fija la distancia de un vertice
        for (int vuelta = 0; vuelta < n; vuelta++) {
            // Vertice elegido en esta vuelta
            int u = -1;
            // Buscamos el no visitado con menor distancia (eleccion voraz)
            for (int v = 0; v < n; v++) {
                // Solo vertices no visitados y alcanzables
                if (!visitado[v] && dist[v] != INF && (u == -1 || dist[v] < dist[u])) {
                    // Nuevo mejor candidato
                    u = v;
                }
            }
            // Si no queda ninguno alcanzable, terminamos
            if (u == -1) {
                // Los restantes quedan en INF
                return;
            }
            // La distancia de u ya es definitiva
            visitado[u] = true;
            // Relajamos cada arista u -> v
            for (int v = 0; v < n; v++) {
                // Solo si la arista existe y v no es definitivo
                if (grafo[u][v] != SIN_ARISTA && !visitado[v]) {
                    // Distancia pasando por u
                    int nueva = dist[u] + grafo[u][v];
                    // Si mejora la distancia conocida de v
                    if (nueva < dist[v]) {
                        // Actualizamos la distancia
                        dist[v] = nueva;
                        // Y recordamos que se llega desde u
                        prev[v] = u;
                    }
                }
            }
        }
    }

    // Imprime el camino origen -> destino siguiendo prev hacia atras
    static void imprimirCamino(int[] prev, int destino) {
        // Si destino tiene predecesor, primero imprimimos el camino hasta el
        if (prev[destino] != -1) {
            // Llamada recursiva sobre el predecesor
            imprimirCamino(prev, prev[destino]);
        }
        // Despues imprimimos el propio destino
        System.out.print(destino + " ");
    }
}
