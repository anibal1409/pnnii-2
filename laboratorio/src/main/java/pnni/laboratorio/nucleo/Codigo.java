package pnni.laboratorio.nucleo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Código Java que se muestra en el laboratorio. Las líneas pueden terminar en
 * {@code @@marca}; la marca se elimina al mostrar y sirve para que la traza
 * resalte esas líneas sin depender de números fijos. Varias líneas pueden
 * compartir marca (p. ej. las tres líneas de un intercambio).
 */
public final class Codigo {

    private static final String SEPARADOR = " @@";

    private final List<String> lineas = new ArrayList<>();
    private final Map<String, List<Integer>> marcas = new LinkedHashMap<>();

    public Codigo(String fuente) {
        for (String linea : fuente.stripTrailing().split("\n", -1)) {
            int corte = linea.lastIndexOf(SEPARADOR);
            if (corte >= 0) {
                String marca = linea.substring(corte + SEPARADOR.length()).trim();
                marcas.computeIfAbsent(marca, m -> new ArrayList<>()).add(lineas.size());
                linea = linea.substring(0, corte).stripTrailing();
            }
            lineas.add(linea);
        }
    }

    public List<String> lineas() {
        return Collections.unmodifiableList(lineas);
    }

    /** Índices (base 0) de las líneas con esa marca. */
    public int[] lineasDe(String marca) {
        List<Integer> encontradas = marcas.get(marca);
        if (encontradas == null) {
            throw new IllegalStateException("Marca de código inexistente: " + marca);
        }
        return encontradas.stream().mapToInt(Integer::intValue).toArray();
    }
}
