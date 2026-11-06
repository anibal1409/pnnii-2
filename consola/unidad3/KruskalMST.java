/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 5.4 Arbol de costo minimo - Algoritmo de Kruskal
 *  Programa  : KruskalMST
 *  Objetivo  : Recorrer las aristas de menor a mayor peso y aceptar cada una
 *              si no forma ciclo con las ya aceptadas. Los ciclos se detectan
 *              con union-busqueda (conjuntos disjuntos) en arreglos: padre[]
 *              con compresion de camino y rango[] para unir por rango.
 *  Complejidad: O(E log E) con un ordenamiento eficiente; aqui O(E^2) por la
 *              insercion directa
 *  Ejecutar  : java consola/unidad3/KruskalMST.java
 * =============================================================================
 */

// Clase principal del programa
public class KruskalMST {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Numero de vertices
        int n = 4;
        // Extremo u de cada arista
        int[] u = {0, 0, 1, 1, 2};
        // Extremo v de cada arista
        int[] v = {1, 2, 2, 3, 3};
        // Peso de cada arista
        int[] peso = {1, 3, 1, 4, 2};
        // Construimos el arbol
        int total = kruskal(n, u, v, peso);
        // Peso total del arbol
        System.out.println("Peso total MST (Kruskal): " + total);
    }

    // Kruskal: imprime cada arista aceptada y devuelve el peso total
    static int kruskal(int n, int[] u, int[] v, int[] peso) {
        // Numero de aristas
        int m = peso.length;
        // Ordenamos las aristas por peso ascendente (insercion, estable)
        for (int k = 1; k < m; k++) {
            // Arista que se inserta en la parte ya ordenada
            int pu = u[k];
            // Su otro extremo
            int pv = v[k];
            // Su peso
            int pp = peso[k];
            // Posicion de comparacion
            int j = k - 1;
            // Desplazamos a la derecha las aristas mas pesadas
            while (j >= 0 && peso[j] > pp) {
                // Movemos el extremo u
                u[j + 1] = u[j];
                // Movemos el extremo v
                v[j + 1] = v[j];
                // Movemos el peso
                peso[j + 1] = peso[j];
                // Seguimos hacia la izquierda
                j--;
            }
            // Colocamos la arista en su lugar
            u[j + 1] = pu;
            // Su otro extremo
            v[j + 1] = pv;
            // Su peso
            peso[j + 1] = pp;
        }
        // padre[x] = representante provisional del conjunto de x
        int[] padre = new int[n];
        // rango[x] = cota de la altura del arbol de x
        int[] rango = new int[n];
        // Al inicio cada vertice es su propio conjunto
        for (int x = 0; x < n; x++) {
            // Representante de si mismo
            padre[x] = x;
        }
        // Peso acumulado del arbol
        int total = 0;
        // Aristas aceptadas
        int aceptadas = 0;
        // Recorremos las aristas de menor a mayor peso
        for (int k = 0; k < m && aceptadas < n - 1; k++) {
            // Representante del conjunto de u
            int ru = buscar(padre, u[k]);
            // Representante del conjunto de v
            int rv = buscar(padre, v[k]);
            // Si estan en conjuntos distintos, la arista no forma ciclo
            if (ru != rv) {
                // Unimos por rango: el arbol mas bajo cuelga del mas alto
                if (rango[ru] < rango[rv]) {
                    // ru cuelga de rv
                    padre[ru] = rv;
                } else if (rango[ru] > rango[rv]) {
                    // rv cuelga de ru
                    padre[rv] = ru;
                } else {
                    // Igual rango: rv cuelga de ru
                    padre[rv] = ru;
                    // Y ru crece en uno
                    rango[ru]++;
                }
                // Sumamos su peso
                total += peso[k];
                // Una arista mas en el arbol
                aceptadas++;
                // Mostramos la arista aceptada
                System.out.println("Acepta " + u[k] + "-" + v[k] + " (peso " + peso[k] + ")");
            } else {
                // Formaria un ciclo: se descarta
                System.out.println("Descarta " + u[k] + "-" + v[k] + " (peso " + peso[k] + "): forma ciclo");
            }
        }
        // Peso total del arbol de costo minimo
        return total;
    }

    // BUSCAR con compresion de camino: devuelve el representante de x
    static int buscar(int[] padre, int x) {
        // Si x no es su propio padre, buscamos la raiz de su arbol
        if (padre[x] != x) {
            // Y lo colgamos directamente de ella
            padre[x] = buscar(padre, padre[x]);
        }
        // Representante del conjunto
        return padre[x];
    }
}
