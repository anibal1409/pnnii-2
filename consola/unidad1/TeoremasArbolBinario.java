/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad I  : Estructuras de datos elementales
 *  Tema      : 4.1 y 4.2 Teoremas I y II para arboles binarios
 *  Programa  : TeoremasArbolBinario
 *  Objetivo  : Calcular el maximo de nodos por nivel (Teorema I: 2^i) y el
 *              maximo de nodos de un arbol de altura h (Teorema II: 2^(h+1)-1).
 *  Complejidad: O(h)
 *  Ejecutar  : java consola/unidad1/TeoremasArbolBinario.java
 * =============================================================================
 */

// Clase principal: todo el programa vive dentro de esta unica clase
public class TeoremasArbolBinario {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Altura del arbol que queremos analizar (la raiz esta en el nivel 0)
        int altura = 5;
        // Acumulador para comprobar el Teorema II sumando nivel por nivel
        int suma = 0;

        // Encabezado de la tabla del Teorema I
        System.out.println("Teorema I: maximo de nodos en el nivel i = 2^i");
        // Encabezado de las columnas
        System.out.println("Nivel  Nodos maximos  Acumulado");
        // Recorremos cada nivel desde la raiz (0) hasta la altura
        for (int nivel = 0; nivel <= altura; nivel++) {
            // Calculamos 2^nivel con el metodo auxiliar
            int nodosNivel = potenciaDeDos(nivel);
            // Sumamos los nodos del nivel al acumulado
            suma = suma + nodosNivel;
            // Mostramos la fila con formato de columnas alineadas
            System.out.printf("%5d  %13d  %9d%n", nivel, nodosNivel, suma);
        }

        // Aplicamos directamente la formula del Teorema II
        int maximoTotal = potenciaDeDos(altura + 1) - 1;
        // Mostramos el resultado de la formula
        System.out.println("Teorema II: maximo de nodos con altura " + altura
                + " = 2^(" + altura + "+1) - 1 = " + maximoTotal);
        // Verificamos que la suma por niveles coincide con la formula
        System.out.println("Comprobacion (suma por niveles): " + suma
                + (suma == maximoTotal ? "  -> coincide" : "  -> NO coincide"));
    }

    // Calcula 2^exponente multiplicando por 2 (evita Math.pow y sus decimales)
    static int potenciaDeDos(int exponente) {
        // Partimos de 2^0 = 1
        int resultado = 1;
        // Multiplicamos por 2 tantas veces como indique el exponente
        for (int k = 0; k < exponente; k++) {
            // Duplicamos el resultado en cada vuelta
            resultado = resultado * 2;
        }
        // Devolvemos la potencia calculada
        return resultado;
    }
}
