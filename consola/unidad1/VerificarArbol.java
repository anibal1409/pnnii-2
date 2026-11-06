/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 7.1 Ejercicio resuelto 1 - Altura y numero de nodos
 *  Programa  : VerificarArbol
 *  Objetivo  : Para el arbol A(B(D,E), C(-,F)) guardado en un arreglo, calcular
 *              su altura, los nodos del nivel 2 y el maximo de nodos que
 *              tendria si fuera lleno (Teorema II).
 *              El valor -1 representa una posicion vacia.
 *  Complejidad: O(n)
 *  Ejecutar  : java consola/unidad1/VerificarArbol.java
 * =============================================================================
 */

// Clase principal del programa
public class VerificarArbol {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // A=1, B=2, C=3, D=4, E=5, F=6; -1 marca un hueco (C no tiene hijo izq)
        int[] arbol = {1, 2, 3, 4, 5, -1, 6};
        // Calculamos la altura recorriendo el arreglo
        int altura = calcularAltura(arbol);
        // Contamos los nodos reales del nivel 2
        int nodosNivel2 = contarNodosEnNivel(arbol, 2);
        // Aplicamos el Teorema II: 2^(h+1) - 1
        int maxNodos = (1 << (altura + 1)) - 1;
        // Mostramos la altura
        System.out.println("Altura del arbol: " + altura);
        // Mostramos los nodos del nivel 2
        System.out.println("Nodos en nivel 2: " + nodosNivel2);
        // Mostramos el maximo teorico
        System.out.println("Maximo de nodos si fuera lleno: " + maxNodos);
    }

    // Devuelve el nivel del nodo real mas profundo (raiz = nivel 0)
    static int calcularAltura(int[] arbol) {
        // Altura encontrada hasta ahora (-1 si el arbol esta vacio)
        int altura = -1;
        // Recorremos todas las posiciones del arreglo
        for (int i = 0; i < arbol.length; i++) {
            // Solo cuentan las posiciones ocupadas
            if (arbol[i] != -1) {
                // El nivel del indice i es la cantidad de veces que subimos al padre
                int nivel = nivelDeIndice(i);
                // Nos quedamos con el nivel mas profundo
                if (nivel > altura) {
                    // Actualizamos la altura
                    altura = nivel;
                }
            }
        }
        // Devolvemos la altura del arbol
        return altura;
    }

    // Cuenta las posiciones ocupadas de un nivel: indices [2^n - 1, 2^(n+1) - 2]
    static int contarNodosEnNivel(int[] arbol, int nivel) {
        // Primer indice del nivel
        int inicio = (1 << nivel) - 1;
        // Ultimo indice del nivel
        int fin = (1 << (nivel + 1)) - 2;
        // Contador de nodos encontrados
        int contador = 0;
        // Recorremos el rango del nivel sin salirnos del arreglo
        for (int i = inicio; i <= fin && i < arbol.length; i++) {
            // Contamos solo las posiciones ocupadas
            if (arbol[i] != -1) {
                // Un nodo mas en el nivel
                contador++;
            }
        }
        // Devolvemos la cantidad de nodos del nivel
        return contador;
    }

    // Calcula el nivel de un indice subiendo al padre hasta llegar a la raiz
    static int nivelDeIndice(int i) {
        // Niveles recorridos
        int nivel = 0;
        // Mientras no lleguemos a la raiz
        while (i > 0) {
            // Subimos al padre
            i = (i - 1) / 2;
            // Sumamos un nivel
            nivel++;
        }
        // Devolvemos el nivel del indice original
        return nivel;
    }
}
