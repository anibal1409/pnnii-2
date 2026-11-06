package pnni.laboratorio.nucleo;

import java.util.List;

import pnni.laboratorio.unidad1.PracticaColaPrioridad;
import pnni.laboratorio.unidad1.PracticaConstruirHeap;
import pnni.laboratorio.unidad1.PracticaHeapify;
import pnni.laboratorio.unidad1.PracticaInsertar;
import pnni.laboratorio.unidad1.PracticaNavegacion;
import pnni.laboratorio.unidad1.PracticaTeoremas;

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
                PracticaColaPrioridad.impresora());
    }
}
