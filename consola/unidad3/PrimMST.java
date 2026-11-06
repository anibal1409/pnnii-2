/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 5.3 Arbol de costo minimo - Algoritmo de Prim
 *  Programa  : PrimMST
 *  Objetivo  : Conectar todos los vertices de un grafo no dirigido con el
 *              menor peso total. Eleccion voraz: agregar siempre el vertice
 *              fuera del arbol unido a el por la arista mas barata.
 *              Se usa la variante con matriz de adyacencia (5.6), que solo
 *              necesita arreglos: costo[v] guarda la arista mas barata que
 *              une v con el arbol y padre[v] su otro extremo.
 *  Complejidad: O(V^2)
 *  Ejecutar  : java consola/unidad3/PrimMST.java
 * =============================================================================
 */

// Clase principal del programa
public class PrimMST {

    // Marca de "no hay arista" en la matriz
    static final int SIN_ARISTA = -1;
    // Valor que representa infinito
    static final int INF = Integer.MAX_VALUE;

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Matriz de adyacencia del grafo de 4 vertices (simetrica)
        int[][] grafo = {
            {SIN_ARISTA, 1, 3, SIN_ARISTA},
            {1, SIN_ARISTA, 1, 4},
            {3, 1, SIN_ARISTA, 2},
            {SIN_ARISTA, 4, 2, SIN_ARISTA}
        };
        // Construimos el arbol desde el vertice 0
        int peso = prim(grafo, 0);
        // Peso total del arbol
        System.out.println("Peso total MST (Prim): " + peso);
    }

    // Prim con matriz: imprime cada arista elegida y devuelve el peso total
    static int prim(int[][] grafo, int inicio) {
        // Numero de vertices
        int n = grafo.length;
        // enArbol[v] indica si v ya pertenece al arbol
        boolean[] enArbol = new boolean[n];
        // costo[v] = peso de la arista mas barata entre v y el arbol
        int[] costo = new int[n];
        // padre[v] = vertice del arbol en el otro extremo de esa arista
        int[] padre = new int[n];
        // Inicializamos todos los vertices
        for (int v = 0; v < n; v++) {
            // Aun no se conoce ninguna arista hacia el arbol
            costo[v] = INF;
            // Sin padre
            padre[v] = -1;
        }
        // El vertice inicial entra con costo 0
        costo[inicio] = 0;
        // Peso acumulado del arbol
        int pesoTotal = 0;
        // Cada vuelta agrega un vertice al arbol
        for (int vuelta = 0; vuelta < n; vuelta++) {
            // Vertice elegido en esta vuelta
            int u = -1;
            // Buscamos el vertice fuera del arbol con menor costo (eleccion voraz)
            for (int v = 0; v < n; v++) {
                // Solo vertices fuera del arbol y alcanzables
                if (!enArbol[v] && costo[v] != INF && (u == -1 || costo[v] < costo[u])) {
                    // Nuevo mejor candidato
                    u = v;
                }
            }
            // Si no hay candidato el grafo no es conexo
            if (u == -1) {
                // Avisamos y terminamos
                System.out.println("El grafo no es conexo");
                // Devolvemos lo acumulado
                return pesoTotal;
            }
            // Agregamos u al arbol (decision irreversible)
            enArbol[u] = true;
            // Si no es el inicial, su arista entra al arbol
            if (padre[u] != -1) {
                // Sumamos el peso de la arista
                pesoTotal += costo[u];
                // Mostramos la arista elegida
                System.out.println("Arista " + padre[u] + "-" + u + " (peso " + costo[u] + ")");
            }
            // Actualizamos el costo de los vecinos de u que siguen fuera
            for (int v = 0; v < n; v++) {
                // Si hay arista u-v, v esta fuera y la arista es mas barata
                if (grafo[u][v] != SIN_ARISTA && !enArbol[v] && grafo[u][v] < costo[v]) {
                    // Nueva arista mas barata hacia v
                    costo[v] = grafo[u][v];
                    // Que llega desde u
                    padre[v] = u;
                }
            }
        }
        // Peso total del arbol de costo minimo
        return pesoTotal;
    }
}
