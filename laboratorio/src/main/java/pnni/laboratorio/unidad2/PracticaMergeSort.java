package pnni.laboratorio.unidad2;

import java.util.Arrays;
import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Sortmerge (Merge Sort recursivo, top-down) con su árbol de llamadas. */
public final class PracticaMergeSort implements Practica {

    private static final String CODIGO = """
            static void mergeSort(int[] arr, int inicio, int fin) { @@llamada
                if (inicio >= fin) { @@base
                    return;                          // 0 o 1 elemento @@base
                }
                int medio = (inicio + fin) / 2; @@medio
                mergeSort(arr, inicio, medio); @@izq
                mergeSort(arr, medio + 1, fin); @@der
                merge(arr, inicio, medio, fin); @@merge
            }

            """ + Intercalar.CODIGO_MERGE + """

            // en main:
            mergeSort(datos, 0, datos.length - 1); @@main
            """;

    @Override
    public Unidad unidad() {
        return Unidad.II;
    }

    @Override
    public String titulo() {
        return "Sortmerge (recursivo)";
    }

    @Override
    public String descripcion() {
        return "Divide el arreglo por la mitad hasta tramos de un elemento y luego intercala (merge) las mitades "
                + "ordenadas tomando siempre el menor de los dos frentes. O(n log n) en todos los casos, estable, "
                + "con memoria adicional O(n).";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(n log n)", "Memoria O(n)", "Estable");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(Parametro.texto("arreglo", "Arreglo", "38, 27, 43, 3, 9, 82, 10", "Hasta 12 enteros"));
    }

    @Override
    public String programaConsola() {
        return "MergeSort.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] arr = entrada.enteros("arreglo", 1, 12, -999, 999);
        Traza t = new Traza(new Codigo(CODIGO));
        t.imprimirLinea("Arreglo original:");
        t.imprimirLinea(EscenaArreglo.texto(arr));
        ArbolLlamadas arbol = new ArbolLlamadas();
        mergeSort(t, arbol, arr, 0, arr.length - 1);
        t.quitar("inicio").quitar("fin").quitar("medio");
        t.imprimirLinea("Arreglo ordenado:");
        t.imprimirLinea(EscenaArreglo.texto(arr));
        t.paso("main", "Arreglo ordenado ✓", "Todas las llamadas terminaron: " + arbol.foto().size()
                        + " llamadas para " + arr.length + " elementos.",
                EscenaArreglo.nueva().arbol(arbol.foto()).linea(EscenaArreglo.fila("arr", arr).tonos(0, arr.length - 1, Tono.EXITO))
                        .insignia("Ordenado ✓", true).crear());
        return t;
    }

    private void mergeSort(Traza t, ArbolLlamadas arbol, int[] arr, int inicio, int fin) {
        arbol.llamar("[" + inicio + ".." + fin + "]");
        t.var("inicio", inicio).var("fin", fin).quitar("medio");
        t.paso("llamada", "Llamada sobre [" + inicio + ", " + fin + "]",
                "Ordenar " + Math.max(0, fin - inicio + 1) + " elemento(s): " + tramo(arr, inicio, fin) + ".",
                simple(arr, inicio, fin, -1, arbol));
        boolean base = inicio >= fin;
        t.paso("base", "¿inicio ≥ fin? " + (base ? "Sí → ya ordenado" : "No"),
                base ? "Un tramo de un solo elemento ya está ordenado (caso base)." : "Hay que dividir.",
                simple(arr, inicio, fin, -1, arbol));
        if (base) {
            arbol.devolver(tramo(arr, inicio, fin));
            return;
        }
        int medio = (inicio + fin) / 2;
        t.var("medio", medio);
        t.paso("medio", "DIVIDIR: medio = " + medio, "Mitades [" + inicio + ", " + medio + "] y [" + (medio + 1) + ", " + fin + "].",
                simple(arr, inicio, fin, medio, arbol));
        t.paso("izq", "CONQUISTAR: ordenar [" + inicio + ", " + medio + "]", "Llamada recursiva sobre la mitad izquierda.",
                simple(arr, inicio, medio, -1, arbol));
        mergeSort(t, arbol, arr, inicio, medio);
        t.var("inicio", inicio).var("fin", fin).var("medio", medio);
        t.paso("der", "CONQUISTAR: ordenar [" + (medio + 1) + ", " + fin + "]", "Llamada recursiva sobre la mitad derecha.",
                simple(arr, medio + 1, fin, -1, arbol));
        mergeSort(t, arbol, arr, medio + 1, fin);
        t.var("inicio", inicio).var("fin", fin).var("medio", medio);
        t.paso("merge", "COMBINAR: intercalar las dos mitades",
                tramo(arr, inicio, medio) + " y " + tramo(arr, medio + 1, fin) + " ya están ordenadas.",
                simple(arr, inicio, fin, medio, arbol));
        arbol.detalle("intercalando…");
        Intercalar.merge(t, arr, inicio, medio, fin, () -> EscenaArreglo.nueva().arbol(arbol.foto()));
        t.var("inicio", inicio).var("fin", fin).var("medio", medio);
        arbol.devolver(tramo(arr, inicio, fin));
    }

    static String tramo(int[] arr, int inicio, int fin) {
        return inicio > fin ? "[]" : Arrays.toString(Arrays.copyOfRange(arr, inicio, fin + 1));
    }

    private static EscenaArreglo simple(int[] arr, int inicio, int fin, int medio, ArbolLlamadas arbol) {
        EscenaArreglo.Fila f = EscenaArreglo.fila("arr", arr).rango(inicio, fin).puntero(inicio, "inicio").puntero(fin, "fin");
        if (medio >= 0) {
            f.tono(medio, Tono.ACENTO).puntero(medio, "medio");
        }
        return EscenaArreglo.nueva().arbol(arbol.foto()).linea(f)
                .leyenda(Tono.ACENTO, "medio").leyenda(Tono.ATENUADO, "fuera del tramo").crear();
    }
}
