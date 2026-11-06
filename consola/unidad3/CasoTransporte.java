/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 7.2 Caso practico 2 - Planificacion de redes de transporte
 *  Programa  : CasoTransporte
 *  Objetivo  : Sobre el mismo mapa de 7 barrios (grafo no dirigido, km):
 *              Prim obtiene la red de ciclovias de longitud minima y Dijkstra
 *              las distancias minimas desde la estacion central (barrio 0).
 *              Ambos con matriz de adyacencia.
 *  Complejidad: O(V^2) cada algoritmo
 *  Ejecutar  : java consola/unidad3/CasoTransporte.java
 * =============================================================================
 */

// Clase principal del programa
public class CasoTransporte {

    // Marca de "no hay arista"
    static final int SIN_ARISTA = -1;
    // Valor que representa infinito
    static final int INF = Integer.MAX_VALUE;

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Numero de barrios
        int n = 7;
        // Matriz de distancias entre barrios
        int[][] grafo = new int[n][n];
        // Al inicio no hay ninguna conexion
        for (int i = 0; i < n; i++) {
            // Recorremos la fila i
            for (int j = 0; j < n; j++) {
                // Sin arista entre i y j
                grafo[i][j] = SIN_ARISTA;
            }
        }
        // Conexiones del material: barrio, barrio, km
        int[][] calles = {{0, 1, 4}, {0, 2, 3}, {1, 3, 5}, {1, 4, 6}, {2, 4, 5}, {3, 5, 2}, {4, 6, 4}, {5, 6, 3}};
        // Cargamos cada calle en ambos sentidos
        for (int k = 0; k < calles.length; k++) {
            // Sentido ida
            grafo[calles[k][0]][calles[k][1]] = calles[k][2];
            // Sentido vuelta (grafo no dirigido)
            grafo[calles[k][1]][calles[k][0]] = calles[k][2];
        }
        // Titulo de la primera parte
        System.out.println("=== RED DE CICLOVIAS (MST) ===");
        // Arbol de costo minimo con Prim
        int peso = prim(grafo);
        // Longitud total de la red
        System.out.println("Peso total del arbol: " + peso + " km");
        // Titulo de la segunda parte
        System.out.println("=== DISTANCIAS MINIMAS DESDE BARRIO 0 ===");
        // Distancias desde la estacion central
        int[] dist = dijkstra(grafo, 0);
        // Recorremos los barrios
        for (int v = 0; v < n; v++) {
            // Distancia minima a cada barrio
            System.out.println("Barrio " + v + ": " + dist[v] + " km");
        }
    }

    // Prim desde el vertice 0; imprime las conexiones y devuelve el peso total
    static int prim(int[][] grafo) {
        // Numero de vertices
        int n = grafo.length;
        // Pertenencia al arbol
        boolean[] enArbol = new boolean[n];
        // Arista mas barata hacia el arbol
        int[] costo = new int[n];
        // Extremo en el arbol de esa arista
        int[] padre = new int[n];
        // Inicializamos
        for (int v = 0; v < n; v++) {
            // Costo desconocido
            costo[v] = INF;
            // Sin padre
            padre[v] = -1;
        }
        // El vertice 0 inicia el arbol
        costo[0] = 0;
        // Peso acumulado
        int total = 0;
        // Una vuelta por vertice
        for (int vuelta = 0; vuelta < n; vuelta++) {
            // Vertice elegido
            int u = -1;
            // El de menor costo fuera del arbol
            for (int v = 0; v < n; v++) {
                // Candidato valido y mejor que el actual
                if (!enArbol[v] && costo[v] != INF && (u == -1 || costo[v] < costo[u])) {
                    // Nuevo mejor
                    u = v;
                }
            }
            // Agregamos u al arbol
            enArbol[u] = true;
            // Si tiene padre, su arista forma parte de la red
            if (padre[u] != -1) {
                // Sumamos su longitud
                total += costo[u];
                // Mostramos la conexion
                System.out.println("Conexion " + padre[u] + "-" + u + ": " + costo[u] + " km");
            }
            // Actualizamos los vecinos de u
            for (int v = 0; v < n; v++) {
                // Arista mas barata hacia un vertice externo
                if (grafo[u][v] != SIN_ARISTA && !enArbol[v] && grafo[u][v] < costo[v]) {
                    // Nuevo costo
                    costo[v] = grafo[u][v];
                    // Nuevo padre
                    padre[v] = u;
                }
            }
        }
        // Longitud total
        return total;
    }

    // Dijkstra desde el origen; devuelve las distancias minimas
    static int[] dijkstra(int[][] grafo, int origen) {
        // Numero de vertices
        int n = grafo.length;
        // Distancias minimas
        int[] dist = new int[n];
        // Distancias ya definitivas
        boolean[] visitado = new boolean[n];
        // Todas desconocidas al inicio
        for (int v = 0; v < n; v++) {
            // Infinito
            dist[v] = INF;
        }
        // El origen esta a 0
        dist[origen] = 0;
        // Una vuelta por vertice
        for (int vuelta = 0; vuelta < n; vuelta++) {
            // Vertice elegido
            int u = -1;
            // El no visitado mas cercano
            for (int v = 0; v < n; v++) {
                // Candidato valido y mejor que el actual
                if (!visitado[v] && dist[v] != INF && (u == -1 || dist[v] < dist[u])) {
                    // Nuevo mejor
                    u = v;
                }
            }
            // Si no queda ninguno alcanzable, terminamos
            if (u == -1) {
                // Salimos del ciclo
                break;
            }
            // Su distancia queda fija
            visitado[u] = true;
            // Relajamos sus aristas
            for (int v = 0; v < n; v++) {
                // Arista existente hacia un vertice no fijado que mejora
                if (grafo[u][v] != SIN_ARISTA && !visitado[v] && dist[u] + grafo[u][v] < dist[v]) {
                    // Nueva distancia
                    dist[v] = dist[u] + grafo[u][v];
                }
            }
        }
        // Distancias minimas
        return dist;
    }
}
