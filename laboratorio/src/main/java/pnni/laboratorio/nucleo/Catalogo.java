package pnni.laboratorio.nucleo;

import java.util.List;

import pnni.laboratorio.unidad1.PracticaColaPrioridad;
import pnni.laboratorio.unidad1.PracticaConstruirHeap;
import pnni.laboratorio.unidad1.PracticaHeapify;
import pnni.laboratorio.unidad1.PracticaInsertar;
import pnni.laboratorio.unidad1.PracticaNavegacion;
import pnni.laboratorio.unidad1.PracticaTeoremas;
import pnni.laboratorio.unidad2.PracticaBusquedaBinaria;
import pnni.laboratorio.unidad2.PracticaMaxMin;
import pnni.laboratorio.unidad2.PracticaMergeSort;
import pnni.laboratorio.unidad2.PracticaMergeSortIterativo;
import pnni.laboratorio.unidad2.PracticaQuicksort;
import pnni.laboratorio.unidad3.PracticaCintas;
import pnni.laboratorio.unidad3.PracticaDijkstra;
import pnni.laboratorio.unidad3.PracticaIntercalacion;
import pnni.laboratorio.unidad3.PracticaKruskal;
import pnni.laboratorio.unidad3.PracticaMochila;
import pnni.laboratorio.unidad3.PracticaPrim;

/** Registro de todas las prácticas. Agregar aquí cada práctica nueva, en el orden del programa. */
public final class Catalogo {

    private Catalogo() {
    }

    public static List<Practica> practicas() {
        return List.of(
                new PracticaTeoremas(),
                new PracticaNavegacion(),
                new PracticaInsertar(),
                new PracticaHeapify(),
                new PracticaConstruirHeap(),
                PracticaColaPrioridad.hospital(),
                PracticaColaPrioridad.impresora(),
                PracticaBusquedaBinaria.recursiva(),
                PracticaBusquedaBinaria.iterativa(),
                new PracticaMaxMin(),
                new PracticaMergeSort(),
                new PracticaMergeSortIterativo(),
                new PracticaQuicksort(),
                new PracticaCintas(),
                new PracticaMochila(),
                new PracticaIntercalacion(),
                new PracticaPrim(),
                new PracticaKruskal(),
                new PracticaDijkstra());
    }
}
