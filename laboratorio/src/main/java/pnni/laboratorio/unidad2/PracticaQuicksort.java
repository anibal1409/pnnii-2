package pnni.laboratorio.unidad2;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Quicksort con partición de Lomuto; cuenta comparaciones para mostrar el peor caso. */
public final class PracticaQuicksort implements Practica {

    private static final String CODIGO = """
            static void quicksort(int[] arr, int bajo, int alto) { @@llamada
                if (bajo < alto) { @@si
                    int indicePivote = particionLomuto(arr, bajo, alto); @@particion
                    quicksort(arr, bajo, indicePivote - 1); @@izq
                    quicksort(arr, indicePivote + 1, alto); @@der
                }
            }

            static int particionLomuto(int[] arr, int bajo, int alto) {
                int pivote = arr[alto]; @@pivote
                int i = bajo - 1; @@i
                for (int j = bajo; j < alto; j++) { @@for
                    comparaciones++; @@cmp
                    if (arr[j] <= pivote) { @@cmp
                        i++; @@swap
                        int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp; @@swap
                    }
                }
                int temp = arr[i + 1]; arr[i + 1] = arr[alto]; arr[alto] = temp; @@final
                return i + 1; @@ret
            }

            // en main:
            quicksort(datos, 0, datos.length - 1); @@main
            """;

    private int comparaciones;
    private boolean[] fijo;

    @Override
    public Unidad unidad() {
        return Unidad.II;
    }

    @Override
    public String titulo() {
        return "Quicksort (Lomuto)";
    }

    @Override
    public String descripcion() {
        return "Toma el último elemento como pivote, deja a su izquierda los menores o iguales y a su derecha los "
                + "mayores, y ordena cada lado recursivamente. Prueba un arreglo ya ordenado para ver el peor caso: "
                + "n(n−1)/2 comparaciones.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("Promedio O(n log n)", "Peor caso O(n²)");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(Parametro.texto("arreglo", "Arreglo", "3, 8, 2, 5, 1, 4, 7, 6",
                "Hasta 12 enteros. Peor caso: 1, 2, 3, 4, 5, 6, 7, 8"));
    }

    @Override
    public String programaConsola() {
        return "Quicksort.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] arr = entrada.enteros("arreglo", 1, 12, -999, 999);
        int n = arr.length;
        Traza t = new Traza(new Codigo(CODIGO));
        comparaciones = 0;
        fijo = new boolean[n];
        t.var("comparaciones", 0);
        t.imprimirLinea("Arreglo original:");
        t.imprimirLinea(EscenaArreglo.texto(arr));
        ArbolLlamadas arbol = new ArbolLlamadas();
        quicksort(t, arbol, arr, 0, n - 1);
        t.quitar("bajo").quitar("alto").quitar("indicePivote");
        t.imprimirLinea("Arreglo ordenado con Quicksort:");
        t.imprimirLinea(EscenaArreglo.texto(arr));
        t.imprimirLinea("Comparaciones: " + comparaciones);
        int peor = n * (n - 1) / 2;
        t.paso("main", "Arreglo ordenado ✓ (" + comparaciones + " comparaciones)",
                comparaciones == peor && n > 2
                        ? "Se alcanzó el peor caso n(n−1)/2 = " + peor + ": cada partición dejó un lado vacío."
                        : "El peor caso para n = " + n + " serían n(n−1)/2 = " + peor + " comparaciones.",
                EscenaArreglo.nueva().arbol(arbol.foto()).linea(EscenaArreglo.fila("arr", arr).tonos(0, n - 1, Tono.EXITO))
                        .insignia("Ordenado ✓", true).crear());
        return t;
    }

    private void quicksort(Traza t, ArbolLlamadas arbol, int[] arr, int bajo, int alto) {
        arbol.llamar("[" + bajo + ".." + alto + "]");
        t.var("bajo", bajo).var("alto", alto).quitar("indicePivote");
        boolean sigue = bajo < alto;
        t.paso("llamada", "Llamada sobre [" + bajo + ", " + alto + "]",
                Math.max(0, alto - bajo + 1) + " elemento(s) en el tramo.", escena(arr, bajo, alto, -2, -1, arbol).crear());
        t.paso("si", "¿bajo < alto? " + bajo + " < " + alto + " → " + (sigue ? "Sí" : "No"),
                sigue ? "Hay al menos dos elementos: se particiona."
                        : "Con 0 o 1 elemento el tramo ya está ordenado.",
                escena(arr, bajo, alto, -2, -1, arbol).crear());
        if (!sigue) {
            if (bajo == alto) {
                fijo[bajo] = true;
            }
            arbol.devolver(bajo == alto ? "ya en su lugar" : "vacío");
            return;
        }
        int p = particion(t, arbol, arr, bajo, alto);
        fijo[p] = true;
        t.var("indicePivote", p);
        arbol.detalle("pivote " + arr[p] + " → [" + p + "]");
        t.paso("particion", "El pivote " + arr[p] + " quedó fijo en el índice " + p,
                "Izquierda: " + (p - bajo) + " elemento(s) ≤ " + arr[p] + ". Derecha: " + (alto - p) + " elemento(s) mayores.",
                escena(arr, bajo, alto, -2, -1, arbol).crear());
        t.paso("izq", "Ordenar la izquierda [" + bajo + ", " + (p - 1) + "]", "Llamada recursiva.",
                escena(arr, bajo, p - 1, -2, -1, arbol).crear());
        quicksort(t, arbol, arr, bajo, p - 1);
        t.var("bajo", bajo).var("alto", alto).var("indicePivote", p);
        t.paso("der", "Ordenar la derecha [" + (p + 1) + ", " + alto + "]", "Llamada recursiva.",
                escena(arr, p + 1, alto, -2, -1, arbol).crear());
        quicksort(t, arbol, arr, p + 1, alto);
        t.var("bajo", bajo).var("alto", alto).var("indicePivote", p);
        arbol.devolver("pivote " + arr[p] + " → [" + p + "]");
    }

    private int particion(Traza t, ArbolLlamadas arbol, int[] arr, int bajo, int alto) {
        int pivote = arr[alto];
        t.var("pivote", pivote);
        t.paso("pivote", "pivote = arr[" + alto + "] = " + pivote, "Lomuto usa como pivote el último elemento del tramo.",
                escena(arr, bajo, alto, bajo - 1, -1, arbol).crear());
        int i = bajo - 1;
        t.var("i", i);
        t.paso("i", "i = bajo − 1 = " + i, "La zona de elementos ≤ pivote empieza vacía (arr[bajo..i]).",
                escena(arr, bajo, alto, i, -1, arbol).crear());
        for (int j = bajo; j < alto; j++) {
            t.var("j", j);
            comparaciones++;
            t.var("comparaciones", comparaciones);
            boolean menor = arr[j] <= pivote;
            t.paso("cmp", "¿arr[" + j + "] = " + arr[j] + " ≤ " + pivote + "? " + (menor ? "Sí" : "No"),
                    (menor ? "Pertenece a la zona de menores." : "Se queda a la derecha de la zona.")
                            + " Comparaciones = " + comparaciones + ".",
                    escena(arr, bajo, alto, i, j, arbol).crear());
            if (menor) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                t.var("i", i);
                t.paso("swap", i == j ? "i = " + i + " (intercambio consigo mismo)" : "i = " + i + ", intercambiar arr[" + i + "] y arr[" + j + "]",
                        i == j ? "arr[" + i + "] ya está en la zona de menores." : "Se lleva " + arr[i] + " a la zona de menores.",
                        escena(arr, bajo, alto, i, j, arbol).intercambio(i, j).crear());
            }
        }
        t.quitar("j");
        int temp = arr[i + 1];
        arr[i + 1] = arr[alto];
        arr[alto] = temp;
        t.paso("final", "Colocar el pivote: intercambiar arr[" + (i + 1) + "] y arr[" + alto + "]",
                "El pivote " + pivote + " pasa a la posición " + (i + 1) + ", su lugar definitivo.",
                escena(arr, bajo, alto, i, -1, arbol).intercambio(i + 1, alto).crear());
        t.paso("ret", "Devolver " + (i + 1), "Posición final del pivote.",
                escena(arr, bajo, alto, i, -1, arbol).fijar(i + 1).crear());
        t.quitar("i").quitar("pivote");
        return i + 1;
    }

    /** Envoltorio de la escena con la zona de menores, el pivote y j. */
    private final class Vista {
        private final EscenaArreglo.Fila fila;
        private final ArbolLlamadas arbol;

        Vista(EscenaArreglo.Fila fila, ArbolLlamadas arbol) {
            this.fila = fila;
            this.arbol = arbol;
        }

        Vista intercambio(int a, int b) {
            if (a != b) {
                fila.intercambio(a, b);
            }
            return this;
        }

        Vista fijar(int k) {
            fila.tono(k, Tono.EXITO);
            return this;
        }

        EscenaArreglo crear() {
            return EscenaArreglo.nueva().arbol(arbol.foto()).linea(fila)
                    .leyenda(Tono.VIOLETA, "pivote").leyenda(Tono.INFO, "≤ pivote").leyenda(Tono.AVISO, "j")
                    .leyenda(Tono.PELIGRO, "intercambio").leyenda(Tono.EXITO, "en su lugar").crear();
        }
    }

    /** i = −2 indica que no hay partición en curso. */
    private Vista escena(int[] arr, int bajo, int alto, int i, int j, ArbolLlamadas arbol) {
        EscenaArreglo.Fila f = EscenaArreglo.fila("arr", arr).rango(bajo, alto);
        for (int k = 0; k < arr.length; k++) {
            if (fijo[k]) {
                f.tono(k, Tono.EXITO);
            }
        }
        if (bajo <= alto) {
            f.puntero(bajo, "bajo").puntero(alto, "alto");
        }
        if (i > -2) {
            f.tonos(bajo, i, Tono.INFO).tono(alto, Tono.VIOLETA);
            if (i >= bajo) {
                f.puntero(i, "i");
            }
            if (j >= 0) {
                f.tono(j, Tono.AVISO).puntero(j, "j");
            }
        }
        return new Vista(f, arbol);
    }
}
