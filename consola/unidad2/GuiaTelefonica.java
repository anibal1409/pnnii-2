/*
 * =============================================================================
 *  PROGRAMACION NO NUMERICA II (4703337)
 *  Unidad II : Divide y conquista
 *  Tema      : Caso practico 1 - Busqueda en una guia telefonica
 *  Programa  : GuiaTelefonica
 *  Objetivo  : Buscar contactos en una guia ordenada alfabeticamente con
 *              busqueda binaria recursiva sobre cadenas (compareTo).
 *  Complejidad: O(log n) comparaciones de cadenas
 *  Ejecutar  : java consola/unidad2/GuiaTelefonica.java
 * =============================================================================
 */

// Clase principal del programa
public class GuiaTelefonica {

    // Metodo principal: punto de entrada del programa
    public static void main(String[] args) {
        // Guia simulada, ordenada alfabeticamente por apellido
        String[] guia = {
            "Alvarez, Juan: 0414-1111111",
            "Blanco, Maria: 0414-2222222",
            "Castillo, Pedro: 0414-3333333",
            "Diaz, Ana: 0414-4444444",
            "Estevez, Luis: 0414-5555555",
            "Fernandez, Carla: 0414-6666666",
            "Garcia, Jose: 0414-7777777",
            "Hernandez, Lucia: 0414-8888888",
            "Iglesias, Mario: 0414-9999999",
            "Jimenez, Sofia: 0414-0000000"
        };
        // Contacto que si existe
        String objetivo = "Garcia, Jose: 0414-7777777";
        // Buscamos en toda la guia
        int indice = busquedaBinaria(guia, 0, guia.length - 1, objetivo);
        // Si se encontro, mostramos su posicion y el contacto
        if (indice != -1) {
            // Posicion del contacto
            System.out.println("Contacto encontrado en indice " + indice + ":");
            // Datos del contacto
            System.out.println(guia[indice]);
        } else {
            // No se encontro
            System.out.println("Contacto no encontrado.");
        }
        // Contacto que no existe: busqueda fallida
        String objetivo2 = "Perez, Juan: 0414-1234567";
        // Buscamos en toda la guia
        int indice2 = busquedaBinaria(guia, 0, guia.length - 1, objetivo2);
        // Mostramos el resultado
        if (indice2 != -1) {
            // Se encontro (no ocurre con estos datos)
            System.out.println("Contacto encontrado: " + guia[indice2]);
        } else {
            // Informamos que no esta
            System.out.println("Contacto '" + objetivo2 + "' no encontrado.");
        }
    }

    // Busqueda binaria recursiva de una cadena en [inicio, fin]
    static int busquedaBinaria(String[] guia, int inicio, int fin, String objetivo) {
        // Caso base: rango vacio
        if (inicio > fin) {
            // No encontrado
            return -1;
        }
        // Punto medio del rango
        int medio = (inicio + fin) / 2;
        // compareTo: 0 si son iguales, > 0 si guia[medio] va despues del objetivo
        int comparacion = guia[medio].compareTo(objetivo);
        // Si son iguales, encontramos el contacto
        if (comparacion == 0) {
            // Devolvemos su posicion
            return medio;
        }
        // Si el medio va despues, el objetivo esta a la izquierda
        if (comparacion > 0) {
            // Buscamos en [inicio, medio-1]
            return busquedaBinaria(guia, inicio, medio - 1, objetivo);
        }
        // Si no, esta a la derecha: [medio+1, fin]
        return busquedaBinaria(guia, medio + 1, fin, objetivo);
    }
}
