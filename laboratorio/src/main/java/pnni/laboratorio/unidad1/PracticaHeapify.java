package pnni.laboratorio.unidad1;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.unidad1.EscenaHeap.Rol;

/** Algoritmo HEAPIFY (hundir / sink down). */
public final class PracticaHeapify implements Practica {

    @Override
    public Unidad unidad() {
        return Unidad.I;
    }

    @Override
    public String titulo() {
        return "Algoritmo HEAPIFY";
    }

    @Override
    public String descripcion() {
        return "Hunde un nodo que viola la propiedad de heap intercambiándolo con su hijo mayor (o menor) hasta que "
                + "quede en su lugar. Precondición: sus dos subárboles ya son heaps.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(log n)");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.opciones("tipo", "Tipo de heap", "Max-Heap o Min-Heap", "Max-Heap", "Min-Heap"),
                Parametro.texto("arreglo", "Arreglo", "10, 80, 60, 70, 50, 30, 40", "Hasta 31 enteros"),
                Parametro.texto("indice", "Hundir desde i", "0", "Índice del nodo a hundir"));
    }

    @Override
    public String programaConsola() {
        return "HeapifyMaxHeap.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        boolean max = TrazadorHeap.esMax(entrada.texto("tipo"));
        int[] heap = entrada.enteros("arreglo", 1, 31, -999, 999);
        int n = heap.length;
        int i = entrada.entero("indice", 0, n - 1);
        String tipo = max ? "Max-Heap" : "Min-Heap";

        Traza t = new Traza(new Codigo(TrazadorHeap.adaptar(TrazadorHeap.CODIGO_HEAPIFY, max)));
        boolean precondicion = TrazadorHeap.esHeapDesde(heap, n, 2 * i + 1, max)
                && TrazadorHeap.esHeapDesde(heap, n, 2 * i + 2, max);
        t.imprimirLinea("Antes de HEAPIFY:   " + TrazadorHeap.texto(heap, n));
        TrazadorHeap.heapify(t, heap, i, n, max, c -> precondicion ? c
                : c.insignia("Precondición no cumplida: subárboles no son heap", false));
        t.imprimirLinea("Despues de HEAPIFY: " + TrazadorHeap.texto(heap, n));

        boolean valido = TrazadorHeap.esHeapDesde(heap, n, i, max);
        t.paso("hFin", valido ? "Subárbol convertido en " + tipo + " ✓" : "Resultado: aún no es un " + tipo,
                valido ? "El subárbol con raíz en el índice " + i + " cumple la propiedad de " + tipo + "."
                        : "HEAPIFY solo garantiza el resultado si los subárboles del nodo ya eran heaps. "
                        + "Para un arreglo arbitrario usa «Construir Heap», que aplica HEAPIFY de abajo hacia arriba.",
                EscenaHeap.de(heap, n).todos(valido ? Rol.LISTO : Rol.NORMAL)
                        .insignia(valido ? tipo + " válido ✓" : "No es " + tipo, valido).crear());
        return t;
    }
}
