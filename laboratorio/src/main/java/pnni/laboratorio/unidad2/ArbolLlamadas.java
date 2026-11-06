package pnni.laboratorio.unidad2;

import java.util.ArrayList;
import java.util.List;

import pnni.laboratorio.ui.Arbol;
import pnni.laboratorio.ui.Tono;

/**
 * Registro mutable del árbol de llamadas de un algoritmo recursivo. Cada llamada
 * es un nodo; {@link #foto()} produce la copia inmutable que guarda cada escena.
 * Activa = en ejecución, en pila = esperando a una llamada hija, terminada = ya devolvió.
 */
final class ArbolLlamadas {

    private final List<String> textos = new ArrayList<>();
    private final List<String> detalles = new ArrayList<>();
    private final List<Integer> padres = new ArrayList<>();
    private final List<Tono> tonos = new ArrayList<>();
    private int activa = -1;

    /** Registra una llamada hija de la activa y la convierte en la activa. */
    int llamar(String texto) {
        if (activa >= 0) {
            tonos.set(activa, Tono.INFO);
        }
        textos.add(texto);
        detalles.add("");
        padres.add(activa);
        tonos.add(Tono.ACENTO);
        activa = textos.size() - 1;
        return activa;
    }

    /** La llamada activa devuelve su resultado y el control vuelve a su padre. */
    void devolver(String resultado) {
        detalles.set(activa, resultado);
        tonos.set(activa, Tono.EXITO);
        activa = padres.get(activa);
        if (activa >= 0) {
            tonos.set(activa, Tono.ACENTO);
        }
    }

    void detalle(String texto) {
        if (activa >= 0) {
            detalles.set(activa, texto);
        }
    }

    List<Arbol.Nodo> foto() {
        List<Arbol.Nodo> nodos = new ArrayList<>();
        for (int k = 0; k < textos.size(); k++) {
            nodos.add(new Arbol.Nodo(textos.get(k), detalles.get(k), padres.get(k), tonos.get(k)));
        }
        return nodos;
    }
}
