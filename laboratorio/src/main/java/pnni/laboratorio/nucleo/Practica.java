package pnni.laboratorio.nucleo;

import java.util.List;

/**
 * Una práctica del laboratorio: un algoritmo que se ejecuta con datos del usuario
 * y produce una traza paso a paso (línea de código + explicación + escena).
 */
public interface Practica {

    Unidad unidad();

    String titulo();

    /** Párrafo corto que explica qué demuestra la práctica. */
    String descripcion();

    /** Etiquetas de complejidad, p. ej. "INSERTAR O(log n)". */
    List<String> complejidad();

    List<Parametro> parametros();

    /** Archivo equivalente en consola/unidadN/ (se muestra en la pestaña "Programa de consola"). */
    String programaConsola();

    /**
     * Ejecuta el algoritmo instrumentado.
     *
     * @throws IllegalArgumentException con un mensaje en español si la entrada no es válida
     */
    Traza ejecutar(Entrada entrada);
}
