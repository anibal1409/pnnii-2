package pnni.laboratorio.nucleo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registro de la ejecución de un algoritmo instrumentado. El algoritmo actualiza
 * variables y salida, y llama a {@link #paso} en cada punto que el estudiante debe ver.
 */
public final class Traza {

    private static final int MAX_PASOS = 5_000;

    private final Codigo codigo;
    private final Map<String, String> variables = new LinkedHashMap<>();
    private final StringBuilder salida = new StringBuilder();
    private final List<Paso> pasos = new ArrayList<>();

    public Traza(Codigo codigo) {
        this.codigo = codigo;
    }

    public Traza var(String nombre, Object valor) {
        variables.put(nombre, String.valueOf(valor));
        return this;
    }

    public Traza quitar(String nombre) {
        variables.remove(nombre);
        return this;
    }

    public Traza imprimir(String texto) {
        salida.append(texto);
        return this;
    }

    public Traza imprimirLinea(String texto) {
        salida.append(texto).append('\n');
        return this;
    }

    public void paso(String marca, String titulo, String explicacion, Escena escena) {
        if (pasos.size() >= MAX_PASOS) {
            throw new IllegalArgumentException("La entrada genera demasiados pasos; usa datos más pequeños.");
        }
        pasos.add(new Paso(codigo.lineasDe(marca), titulo, explicacion,
                Collections.unmodifiableMap(new LinkedHashMap<>(variables)), salida.toString(), escena));
    }

    public Codigo codigo() {
        return codigo;
    }

    public List<Paso> pasos() {
        return Collections.unmodifiableList(pasos);
    }
}
