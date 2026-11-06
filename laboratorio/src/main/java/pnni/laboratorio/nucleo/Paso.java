package pnni.laboratorio.nucleo;

import java.util.Map;

/** Un instante de la ejecución: qué líneas se ejecutan, qué ocurre, valores de variables, salida y escena. */
public record Paso(int[] lineas, String titulo, String explicacion,
                   Map<String, String> variables, String salida, Escena escena) {
}
