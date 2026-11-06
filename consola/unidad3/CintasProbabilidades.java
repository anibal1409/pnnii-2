/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 8.1 Ejercicio resuelto 1 - Cintas con probabilidades de acceso
 *  Programa  : CintasProbabilidades
 *  Objetivo  : Si cada archivo tiene una probabilidad p de ser pedido, el
 *              tiempo esperado es la suma de p(k) * T(k). Eleccion voraz
 *              (regla de Smith): grabar primero el archivo con mayor p / L.
 *              Las probabilidades se guardan en centesimas (enteros) para que
 *              las comparaciones y las sumas sean exactas.
 *  Complejidad: O(n^2) con seleccion directa
 *  Ejecutar  : java consola/unidad3/CintasProbabilidades.java
 * =============================================================================
 */

// Clase principal del programa
public class CintasProbabilidades {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Nombres de los archivos
        char[] nombres = {'A', 'B', 'C', 'D'};
        // Longitudes de los archivos
        int[] longitudes = {10, 2, 8, 5};
        // Probabilidades en centesimas: 0.1, 0.5, 0.3, 0.1
        int[] prob = {10, 50, 30, 10};
        // Orden arbitrario de referencia: A, B, C, D
        int[] orden = {0, 1, 2, 3};
        // Mostramos el costo del orden arbitrario
        System.out.print("Orden A B C D: ");
        // Calculamos e imprimimos su tiempo esperado
        imprimirEsperado(orden, longitudes, prob, nombres);
        // Aplicamos la eleccion voraz por p / L descendente
        ordenarPorRazon(orden, longitudes, prob);
        // Mostramos el orden optimo
        System.out.print("Orden optimo:  ");
        // Calculamos e imprimimos su tiempo esperado
        imprimirEsperado(orden, longitudes, prob, nombres);
    }

    // Seleccion voraz: en cada posicion, el archivo restante con mayor p / L
    static void ordenarPorRazon(int[] orden, int[] longitudes, int[] prob) {
        // Recorremos las posiciones de la cinta
        for (int k = 0; k < orden.length - 1; k++) {
            // Mejor candidato hasta ahora
            int mejor = k;
            // Revisamos los archivos que faltan
            for (int j = k + 1; j < orden.length; j++) {
                // p_j / L_j > p_m / L_m  equivale a  p_j * L_m > p_m * L_j (sin divisiones)
                if (prob[orden[j]] * longitudes[orden[mejor]] > prob[orden[mejor]] * longitudes[orden[j]]) {
                    // Nuevo mejor candidato
                    mejor = j;
                }
            }
            // Guardamos el de la posicion k
            int temp = orden[k];
            // Colocamos el mejor en la posicion k
            orden[k] = orden[mejor];
            // Completamos el intercambio
            orden[mejor] = temp;
        }
    }

    // Imprime el orden y su tiempo esperado = suma de p(k) * T(k)
    static void imprimirEsperado(int[] orden, int[] longitudes, int[] prob, char[] nombres) {
        // Longitud acumulada (tiempo de llegada)
        int acumulado = 0;
        // Tiempo esperado en centesimas
        int esperado = 0;
        // Recorremos la cinta en el orden dado
        for (int k = 0; k < orden.length; k++) {
            // Archivo en la posicion k
            int a = orden[k];
            // Llegar a el cuesta todo lo anterior mas su longitud
            acumulado += longitudes[a];
            // Su aporte es probabilidad por tiempo de llegada
            esperado += prob[a] * acumulado;
            // Mostramos el nombre del archivo
            System.out.print(nombres[a] + " ");
        }
        // Convertimos de centesimas a unidades con dos decimales
        System.out.println("-> tiempo esperado = " + String.format("%.2f", esperado / 100.0));
    }
}
