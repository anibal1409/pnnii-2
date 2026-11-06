package pnni.laboratorio.unidad1;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.unidad1.EscenaHeap.Rol;

/** Construcción de un heap desde un arreglo: HEAPIFY desde el último nodo interno hasta la raíz. */
public final class PracticaConstruirHeap implements Practica {

    private static final String CODIGO_CONSTRUIR = """
            static void construirHeap(int[] heap, int tamano) {
                int ultimoInterno = (tamano / 2) - 1; @@ultimo
                for (int k = ultimoInterno; k >= 0; k--) { @@for
                    heapify(heap, k, tamano); @@llamada
                }
            }

            """;

    @Override
    public Unidad unidad() {
        return Unidad.I;
    }

    @Override
    public String titulo() {
        return "Construir un Heap";
    }

    @Override
    public String descripcion() {
        return "Convierte un arreglo desordenado en heap aplicando HEAPIFY a cada nodo interno, desde el último "
                + "((n/2) − 1) hasta la raíz. Las hojas ya son heaps de un elemento.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(n)");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.opciones("tipo", "Tipo de heap", "Max-Heap o Min-Heap", "Max-Heap", "Min-Heap"),
                Parametro.texto("arreglo", "Arreglo desordenado", "4, 10, 3, 5, 1, 2, 8", "Hasta 31 enteros"));
    }

    @Override
    public String programaConsola() {
        return "ConstruirMaxHeap.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        boolean max = TrazadorHeap.esMax(entrada.texto("tipo"));
        int[] heap = entrada.enteros("arreglo", 1, 31, -999, 999);
        int n = heap.length;
        String tipo = max ? "Max-Heap" : "Min-Heap";
        Traza t = new Traza(new Codigo(TrazadorHeap.adaptar(CODIGO_CONSTRUIR + TrazadorHeap.CODIGO_HEAPIFY, max)));
        t.imprimirLinea("Arreglo original:    " + TrazadorHeap.texto(heap, n));

        int ultimo = n / 2 - 1;
        t.var("tamano", n).var("ultimoInterno", ultimo);
        t.paso("ultimo", "Último nodo interno: (" + n + " / 2) − 1 = " + ultimo,
                ultimo < 0 ? "Con un solo elemento no hay nodos internos: el arreglo ya es un heap."
                        : "Los índices mayores que " + ultimo + " son hojas, que por sí solas ya son heaps. "
                        + "Solo hay que hundir los nodos 0.." + ultimo + ".",
                hojas(EscenaHeap.de(heap, n), ultimo, n).rol(ultimo, Rol.ACTUAL).puntero(ultimo, "ultimoInterno").crear());

        for (int k = ultimo; k >= 0; k--) {
            int kFinal = k;
            t.var("k", k);
            t.paso("for", "k = " + k + " ≥ 0 → aplicar HEAPIFY",
                    "Los subárboles de heap[" + k + "] ya son heaps porque se procesaron antes (o son hojas).",
                    hojas(EscenaHeap.de(heap, n), k, n).rol(k, Rol.ACTUAL).puntero(k, "k").crear());
            t.paso("llamada", "Llamar a heapify(heap, " + k + ", " + n + ")",
                    "Hundimos heap[" + k + "] = " + heap[k] + " dentro de su subárbol.",
                    hojas(EscenaHeap.de(heap, n), k, n).rol(k, Rol.ACTUAL).puntero(k, "k").crear());
            TrazadorHeap.heapify(t, heap, k, n, max, c -> c.nota("HEAPIFY en k = " + kFinal));
            t.quitar("i").quitar("izquierdo").quitar("derecho").quitar(max ? "mayor" : "menor")
                    .quitar("temp").quitar("continuar");
            t.imprimirLinea("HEAPIFY en indice " + k + ": " + TrazadorHeap.texto(heap, n));
        }
        t.var("k", -1);
        t.imprimirLinea(tipo + " construido: " + TrazadorHeap.texto(heap, n));
        t.paso("for", tipo + " construido ✓",
                "k = −1 < 0, el ciclo termina. Todos los nodos internos fueron hundidos: el arreglo es un " + tipo
                        + ". Aunque parece O(n log n), la mayoría de nodos está cerca de las hojas y el costo total es O(n).",
                EscenaHeap.de(heap, n).todos(Rol.LISTO).insignia(tipo + " válido ✓", true).crear());
        return t;
    }

    /** Marca en verde las posiciones posteriores a k: hojas o subárboles ya convertidos en heap. */
    private static EscenaHeap.Constructor hojas(EscenaHeap.Constructor c, int k, int n) {
        for (int j = Math.max(k + 1, 0); j < n; j++) {
            c.rol(j, Rol.LISTO);
        }
        return c;
    }
}
