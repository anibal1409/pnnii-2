package pnni.laboratorio.unidad2;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Sortmerge 2 (Merge Sort iterativo, bottom-up): bloques de tamaño 1, 2, 4… */
public final class PracticaMergeSortIterativo implements Practica {

    private static final String CODIGO = """
            static void mergeSortIterativo(int[] arr) {
                int n = arr.length;
                int tamano = 1; @@ini
                while (tamano < n) { @@while
                    for (int inicio = 0; inicio < n; inicio += 2 * tamano) { @@for
                        int medio = Math.min(inicio + tamano - 1, n - 1); @@limites
                        int fin = Math.min(inicio + 2 * tamano - 1, n - 1); @@limites
                        if (medio < fin) { @@si
                            merge(arr, inicio, medio, fin); @@merge
                        }
                    }
                    tamano *= 2; @@doble
                }
            }

            """ + Intercalar.CODIGO_MERGE;

    @Override
    public Unidad unidad() {
        return Unidad.II;
    }

    @Override
    public String titulo() {
        return "Sortmerge 2 (iterativo)";
    }

    @Override
    public String descripcion() {
        return "Variante sin recursión (bottom-up): en cada pasada intercala bloques vecinos de tamaño 1, 2, 4… "
                + "hasta cubrir el arreglo. Si n no es potencia de 2, el último bloque queda más corto o sin pareja.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(n log n)", "Sin pila de recursión");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(Parametro.texto("arreglo", "Arreglo", "6, 5, 3, 1, 8, 7, 2, 4", "Hasta 12 enteros"));
    }

    @Override
    public String programaConsola() {
        return "MergeSortIterativo.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] arr = entrada.enteros("arreglo", 1, 12, -999, 999);
        int n = arr.length;
        Traza t = new Traza(new Codigo(CODIGO));
        t.imprimirLinea("Arreglo original:");
        t.imprimirLinea(EscenaArreglo.texto(arr));
        int tamano = 1;
        t.var("n", n).var("tamano", tamano);
        t.paso("ini", "tamano = 1", "Se empieza considerando cada elemento como un bloque ordenado de tamaño 1.",
                bloques(arr, tamano, -1, -1, -1, "Pasada tamaño = 1"));
        while (true) {
            boolean sigue = tamano < n;
            t.paso("while", "¿tamano < n? " + tamano + " < " + n + " → " + (sigue ? "Sí" : "No"),
                    sigue ? "Hay más de un bloque: se hace una pasada intercalando bloques de " + tamano + "."
                            : "Un bloque cubre todo el arreglo: está ordenado.",
                    bloques(arr, tamano, -1, -1, -1, "Pasada tamaño = " + tamano));
            if (!sigue) {
                break;
            }
            for (int inicio = 0; inicio < n; inicio += 2 * tamano) {
                int medio = Math.min(inicio + tamano - 1, n - 1);
                int fin = Math.min(inicio + 2 * tamano - 1, n - 1);
                t.var("inicio", inicio).var("medio", medio).var("fin", fin);
                String nota = "Pasada tamaño = " + tamano;
                t.paso("limites", "Bloques [" + inicio + ".." + medio + "] y [" + (medio + 1) + ".." + fin + "]",
                        "medio = min(" + (inicio + tamano - 1) + ", " + (n - 1) + ") = " + medio + ", fin = min("
                                + (inicio + 2 * tamano - 1) + ", " + (n - 1) + ") = " + fin + ".",
                        bloques(arr, tamano, inicio, medio, fin, nota));
                boolean hay = medio < fin;
                t.paso("si", "¿medio < fin? " + (hay ? "Sí" : "No → bloque sin pareja"),
                        hay ? "El segundo bloque tiene elementos: se intercalan."
                                : "El bloque [" + inicio + ".." + medio + "] no tiene pareja en esta pasada; se queda igual.",
                        bloques(arr, tamano, inicio, medio, fin, nota));
                if (hay) {
                    t.paso("merge", "Intercalar [" + inicio + ".." + medio + "] con [" + (medio + 1) + ".." + fin + "]",
                            PracticaMergeSort.tramo(arr, inicio, medio) + " + " + PracticaMergeSort.tramo(arr, medio + 1, fin) + ".",
                            bloques(arr, tamano, inicio, medio, fin, nota));
                    Intercalar.merge(t, arr, inicio, medio, fin, () -> EscenaArreglo.nueva().nota(nota));
                    t.var("inicio", inicio).var("medio", medio).var("fin", fin);
                }
            }
            t.imprimirLinea("Pasada tamano=" + tamano + ": " + EscenaArreglo.texto(arr));
            t.quitar("inicio").quitar("medio").quitar("fin");
            tamano *= 2;
            t.var("tamano", tamano);
            t.paso("doble", "Fin de la pasada: tamano = " + tamano,
                    "Ahora hay bloques ordenados de hasta " + tamano + " elementos: " + EscenaArreglo.texto(arr).trim() + ".",
                    bloques(arr, tamano, -1, -1, -1, "Pasada tamaño = " + tamano));
        }
        t.imprimirLinea("Arreglo ordenado (bottom-up):");
        t.imprimirLinea(EscenaArreglo.texto(arr));
        t.paso("while", "Arreglo ordenado ✓", "Se necesitaron ⌈log₂ " + n + "⌉ pasadas.",
                EscenaArreglo.nueva().linea(EscenaArreglo.fila("arr", arr).tonos(0, n - 1, Tono.EXITO))
                        .insignia("Ordenado ✓", true).crear());
        return t;
    }

    /** Colorea los bloques alternando info/violeta; si hay un par en curso, lo resalta. */
    private static EscenaArreglo bloques(int[] arr, int tamano, int inicio, int medio, int fin, String nota) {
        EscenaArreglo.Fila f = EscenaArreglo.fila("arr", arr);
        if (inicio < 0) {
            for (int b = 0; b * tamano < arr.length; b++) {
                f.tonos(b * tamano, Math.min(arr.length, (b + 1) * tamano) - 1, b % 2 == 0 ? Tono.INFO : Tono.VIOLETA);
            }
        } else {
            f.rango(inicio, fin).tonos(inicio, medio, Tono.INFO).tonos(medio + 1, fin, Tono.VIOLETA)
                    .puntero(inicio, "inicio").puntero(medio, "medio").puntero(fin, "fin");
        }
        return EscenaArreglo.nueva().nota(nota).linea(f)
                .leyenda(Tono.INFO, "bloque").leyenda(Tono.VIOLETA, "bloque vecino").crear();
    }
}
