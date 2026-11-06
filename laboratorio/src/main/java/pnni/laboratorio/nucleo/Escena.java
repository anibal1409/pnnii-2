package pnni.laboratorio.nucleo;

import java.awt.Graphics2D;

import pnni.laboratorio.ui.Paleta;

/** Fotografía visual de un paso. Cada unidad define las suyas (heap, grafo, tablero...). */
public interface Escena {

    /**
     * @param progreso de 0 a 1 desde que comenzó el paso; permite animar transiciones
     *                 (p. ej. el desplazamiento de un intercambio)
     */
    void pintar(Graphics2D g, int ancho, int alto, double progreso, Paleta paleta);
}
