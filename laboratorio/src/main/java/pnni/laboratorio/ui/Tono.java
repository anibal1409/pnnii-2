package pnni.laboratorio.ui;

import java.awt.Color;
import java.util.function.Function;

/**
 * Rol visual de un elemento (celda, nodo, arista). El color sale siempre de la
 * paleta activa para que el cambio de tema sea inmediato.
 */
public enum Tono {
    NORMAL(p -> p.borde()),
    ACENTO(Paleta::acento),
    INFO(Paleta::info),
    VIOLETA(Paleta::violeta),
    AVISO(Paleta::aviso),
    PELIGRO(Paleta::peligro),
    EXITO(Paleta::exito),
    ATENUADO(Paleta::textoSuave);

    private final Function<Paleta, Color> color;

    Tono(Function<Paleta, Color> color) {
        this.color = color;
    }

    public Color color(Paleta p) {
        return color.apply(p);
    }

    /** Los tonos sólidos (actual e intercambio) se rellenan; los demás se dibujan como contorno suave. */
    public boolean solido() {
        return this == ACENTO || this == PELIGRO;
    }
}
