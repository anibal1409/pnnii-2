package pnni.laboratorio.unidad1;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;

/** Algoritmo INSERTAR: colocar al final y flotar (bubble up). */
public final class PracticaInsertar implements Practica {

    private static final int CAPACIDAD = 31;

    @Override
    public Unidad unidad() {
        return Unidad.I;
    }

    @Override
    public String titulo() {
        return "Algoritmo INSERTAR";
    }

    @Override
    public String descripcion() {
        return "Cada valor nuevo se coloca al final del arreglo y flota hacia arriba, intercambiándose con su padre "
                + "mientras viole la propiedad de heap. Puedes partir de un heap vacío o de uno existente.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(log n) por inserción");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.opciones("tipo", "Tipo de heap", "Max-Heap: padre ≥ hijos · Min-Heap: padre ≤ hijos",
                        "Max-Heap", "Min-Heap"),
                Parametro.texto("inicial", "Heap inicial (opcional)", "", "Vacío, o un heap válido p. ej. 5, 10, 15"),
                Parametro.texto("valores", "Valores a insertar", "50, 30, 70, 90", "Enteros separados por coma"));
    }

    @Override
    public String programaConsola() {
        return "InsertarMaxHeap.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        boolean max = TrazadorHeap.esMax(entrada.texto("tipo"));
        int[] inicial = entrada.enteros("inicial", 0, CAPACIDAD - 1, -999, 999);
        int[] valores = entrada.enteros("valores", 1, 15, -999, 999);
        if (inicial.length + valores.length > CAPACIDAD) {
            throw entrada.error("valores", "excede la capacidad: en total caben " + CAPACIDAD + " elementos");
        }
        if (!TrazadorHeap.esHeap(inicial, inicial.length, max)) {
            throw entrada.error("inicial", "no es un " + (max ? "Max-Heap" : "Min-Heap")
                    + " válido; deja el campo vacío o corrige el orden");
        }
        int[] heap = new int[CAPACIDAD];
        System.arraycopy(inicial, 0, heap, 0, inicial.length);
        int tamano = inicial.length;

        Traza t = new Traza(new Codigo(TrazadorHeap.adaptar(TrazadorHeap.CODIGO_INSERTAR, max)));
        for (int valor : valores) {
            tamano = TrazadorHeap.insertar(t, heap, tamano, valor, max, c -> c);
            t.imprimirLinea("Insertado " + valor + " -> " + TrazadorHeap.texto(heap, tamano));
        }
        String tipo = max ? "Max-Heap" : "Min-Heap";
        t.paso("retInsertar", "Resultado: " + TrazadorHeap.texto(heap, tamano).trim(),
                "Se insertaron " + valores.length + " valores. Cada inserción subió a lo sumo la altura del árbol ("
                        + EscenaHeap.nivel(tamano - 1) + " niveles), por eso INSERTAR es O(log n).",
                EscenaHeap.de(heap, tamano).todos(EscenaHeap.Rol.LISTO).insignia(tipo + " válido ✓", true).crear());
        return t;
    }
}
