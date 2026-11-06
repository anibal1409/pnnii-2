/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad III: Metodo de adquisicion (estrategia voraz)
 *  Tema      : 3 Problema de la bolsa de papel (mochila fraccionaria)
 *  Programa  : MochilaFraccionaria
 *  Objetivo  : Maximizar el valor que cabe en una bolsa de capacidad C cuando
 *              los objetos se pueden partir. Eleccion voraz: tomar primero el
 *              objeto con mayor valor por unidad de peso (v / w), completo si
 *              cabe, o la fraccion que llene la bolsa.
 *  Complejidad: O(n^2) con seleccion directa (O(n log n) ordenando)
 *  Ejecutar  : java consola/unidad3/MochilaFraccionaria.java
 * =============================================================================
 */

// Clase principal del programa
public class MochilaFraccionaria {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Valores de los objetos
        int[] valores = {60, 100, 120};
        // Pesos de los objetos
        int[] pesos = {10, 20, 30};
        // Capacidad de la bolsa
        int capacidad = 50;
        // Fraccion que se toma de cada objeto (entre 0 y 1)
        double[] fracciones = new double[valores.length];
        // Resolvemos con la estrategia voraz
        double valor = mochila(valores, pesos, capacidad, fracciones);
        // Mostramos el valor maximo
        System.out.println("Valor maximo obtenido: " + String.format("%.2f", valor));
        // Mostramos la fraccion de cada objeto
        mostrarFracciones(fracciones);
    }

    // Llena la bolsa por razon valor/peso descendente; devuelve el valor total
    static double mochila(int[] valores, int[] pesos, int capacidad, double[] fracciones) {
        // Numero de objetos
        int n = valores.length;
        // orden[k] = indice del objeto que se considera en el turno k
        int[] orden = new int[n];
        // Al inicio, el orden de llegada
        for (int i = 0; i < n; i++) {
            // Objeto i en la posicion i
            orden[i] = i;
        }
        // Ordenamos por razon valor/peso de mayor a menor (seleccion)
        for (int k = 0; k < n - 1; k++) {
            // Mejor candidato del turno k
            int mejor = k;
            // Revisamos los restantes
            for (int j = k + 1; j < n; j++) {
                // v_j / w_j > v_m / w_m  equivale a  v_j * w_m > v_m * w_j
                if (valores[orden[j]] * pesos[orden[mejor]] > valores[orden[mejor]] * pesos[orden[j]]) {
                    // Nuevo mejor candidato
                    mejor = j;
                }
            }
            // Guardamos el de la posicion k
            int temp = orden[k];
            // Colocamos el mejor en el turno k
            orden[k] = orden[mejor];
            // Completamos el intercambio
            orden[mejor] = temp;
        }
        // Capacidad que queda libre en la bolsa
        int restante = capacidad;
        // Valor acumulado dentro de la bolsa
        double total = 0;
        // Recorremos los objetos en orden de razon descendente
        for (int k = 0; k < n && restante > 0; k++) {
            // Objeto del turno k
            int i = orden[k];
            // Si el objeto cabe completo
            if (pesos[i] <= restante) {
                // Lo tomamos entero
                fracciones[i] = 1.0;
                // Sumamos todo su valor
                total += valores[i];
                // Descontamos su peso
                restante -= pesos[i];
                // Mostramos la decision
                System.out.println("Tomar objeto " + i + " completo (razon " + String.format("%.2f", (double) valores[i] / pesos[i]) + "), queda " + restante);
            } else {
                // Solo cabe una parte: la que llena la bolsa
                fracciones[i] = (double) restante / pesos[i];
                // Valor proporcional (se multiplica antes de dividir)
                total += (double) valores[i] * restante / pesos[i];
                // Mostramos la decision
                System.out.println("Tomar " + restante + "/" + pesos[i] + " del objeto " + i + " (razon " + String.format("%.2f", (double) valores[i] / pesos[i]) + "), bolsa llena");
                // La bolsa queda llena
                restante = 0;
            }
        }
        // Valor maximo alcanzado
        return total;
    }

    // Muestra las fracciones en el orden original de los objetos
    static void mostrarFracciones(double[] fracciones) {
        // Encabezado
        System.out.print("Fracciones tomadas: ");
        // Recorremos los objetos
        for (int i = 0; i < fracciones.length; i++) {
            // Fraccion del objeto i con tres decimales
            System.out.print(String.format("%.3f", fracciones[i]) + " ");
        }
        // Salto de linea final
        System.out.println();
    }
}
