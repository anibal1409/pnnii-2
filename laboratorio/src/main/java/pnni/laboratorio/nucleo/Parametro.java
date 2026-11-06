package pnni.laboratorio.nucleo;

import java.util.List;

/** Dato de entrada editable por el estudiante. Si {@code opciones} no está vacío se muestra como lista. */
public record Parametro(String clave, String etiqueta, String porDefecto, String ayuda, List<String> opciones) {

    public static Parametro texto(String clave, String etiqueta, String porDefecto, String ayuda) {
        return new Parametro(clave, etiqueta, porDefecto, ayuda, List.of());
    }

    public static Parametro opciones(String clave, String etiqueta, String ayuda, String... opciones) {
        return new Parametro(clave, etiqueta, opciones[0], ayuda, List.of(opciones));
    }

    public boolean esLista() {
        return !opciones.isEmpty();
    }
}
