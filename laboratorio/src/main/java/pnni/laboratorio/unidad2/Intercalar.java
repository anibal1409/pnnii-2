package pnni.laboratorio.unidad2;

import java.util.Arrays;

import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.ui.Tono;

/** Traza del método merge, común a Sortmerge (recursivo) y Sortmerge 2 (iterativo). */
final class Intercalar {

    static final String CODIGO_MERGE = """
            static void merge(int[] arr, int inicio, int medio, int fin) {
                int n1 = medio - inicio + 1, n2 = fin - medio; @@copiar
                int[] izq = new int[n1], der = new int[n2]; @@copiar
                for (int x = 0; x < n1; x++) izq[x] = arr[inicio + x]; @@copiar
                for (int x = 0; x < n2; x++) der[x] = arr[medio + 1 + x]; @@copiar
                int i = 0, j = 0, k = inicio; @@indices
                while (i < n1 && j < n2) { @@while
                    if (izq[i] <= der[j]) { @@cmp
                        arr[k] = izq[i]; i++; @@tomaIzq
                    } else {
                        arr[k] = der[j]; j++; @@tomaDer
                    }
                    k++; @@k
                }
                while (i < n1) { arr[k] = izq[i]; i++; k++; } @@restoIzq
                while (j < n2) { arr[k] = der[j]; j++; k++; } @@restoDer
            }
            """;

    /** Contexto visual que aporta cada práctica (árbol de llamadas o nota de la pasada). */
    interface Marco {
        EscenaArreglo.Constructor base();
    }

    private Intercalar() {
    }

    static void merge(Traza t, int[] arr, int inicio, int medio, int fin, Marco marco) {
        int n1 = medio - inicio + 1;
        int n2 = fin - medio;
        int[] izq = Arrays.copyOfRange(arr, inicio, medio + 1);
        int[] der = Arrays.copyOfRange(arr, medio + 1, fin + 1);
        t.var("n1", n1).var("n2", n2).var("izq", Arrays.toString(izq)).var("der", Arrays.toString(der));
        t.paso("copiar", "Copiar las mitades a izq y der",
                "izq = arr[" + inicio + ".." + medio + "] y der = arr[" + (medio + 1) + ".." + fin
                        + "]. Ambas ya están ordenadas; ahora se intercalan sobre arr.",
                escena(marco, arr, inicio, fin, inicio, izq, 0, der, 0, -1, -1));
        int i = 0;
        int j = 0;
        int k = inicio;
        t.var("i", i).var("j", j).var("k", k);
        t.paso("indices", "i = 0, j = 0, k = " + inicio, "i recorre izq, j recorre der y k es la posición de escritura en arr.",
                escena(marco, arr, inicio, fin, k, izq, i, der, j, -1, -1));
        while (true) {
            boolean ambas = i < n1 && j < n2;
            t.paso("while", "¿Quedan elementos en ambas mitades? " + (ambas ? "Sí" : "No"),
                    "i = " + i + " de " + n1 + ", j = " + j + " de " + n2 + ".",
                    escena(marco, arr, inicio, fin, k, izq, i, der, j, -1, -1));
            if (!ambas) {
                break;
            }
            boolean tomaIzq = izq[i] <= der[j];
            t.paso("cmp", "¿izq[" + i + "] = " + izq[i] + " ≤ der[" + j + "] = " + der[j] + "? " + (tomaIzq ? "Sí" : "No"),
                    tomaIzq ? "Va primero el de la izquierda (con ≤ los iguales conservan su orden: estabilidad)."
                            : "El de la derecha es menor: va primero.",
                    escena(marco, arr, inicio, fin, k, izq, i, der, j, i, j));
            if (tomaIzq) {
                arr[k] = izq[i];
                i++;
                t.var("i", i);
                t.paso("tomaIzq", "arr[" + k + "] = " + arr[k], "Se escribe el menor de los dos frentes y avanza i.",
                        escena(marco, arr, inicio, fin, k, izq, i, der, j, -1, -1));
            } else {
                arr[k] = der[j];
                j++;
                t.var("j", j);
                t.paso("tomaDer", "arr[" + k + "] = " + arr[k], "Se escribe el menor de los dos frentes y avanza j.",
                        escena(marco, arr, inicio, fin, k, izq, i, der, j, -1, -1));
            }
            k++;
            t.var("k", k);
        }
        if (i < n1) {
            while (i < n1) {
                arr[k] = izq[i];
                i++;
                k++;
            }
            t.var("i", i).var("k", k);
            t.paso("restoIzq", "Copiar los sobrantes de izq",
                    "der se agotó; lo que queda en izq ya está ordenado y es mayor que todo lo escrito.",
                    escena(marco, arr, inicio, fin, k, izq, i, der, j, -1, -1));
        }
        if (j < n2) {
            while (j < n2) {
                arr[k] = der[j];
                j++;
                k++;
            }
            t.var("j", j).var("k", k);
            t.paso("restoDer", "Copiar los sobrantes de der",
                    "izq se agotó; lo que queda en der ya está ordenado y es mayor que todo lo escrito.",
                    escena(marco, arr, inicio, fin, k, izq, i, der, j, -1, -1));
        }
        t.quitar("n1").quitar("n2").quitar("izq").quitar("der").quitar("i").quitar("j").quitar("k");
    }

    private static EscenaArreglo escena(Marco marco, int[] arr, int inicio, int fin, int k,
                                        int[] izq, int i, int[] der, int j, int cmpI, int cmpJ) {
        EscenaArreglo.Fila a = EscenaArreglo.fila("arr", arr).rango(inicio, fin).tonos(inicio, k - 1, Tono.EXITO);
        if (k <= fin) {
            a.tono(k, Tono.ACENTO).puntero(k, "k");
        }
        EscenaArreglo.Fila fi = EscenaArreglo.fila("izq", izq).tonos(0, i - 1, Tono.ATENUADO);
        EscenaArreglo.Fila fd = EscenaArreglo.fila("der", der).tonos(0, j - 1, Tono.ATENUADO);
        if (i < izq.length) {
            fi.tono(i, cmpI >= 0 ? Tono.AVISO : Tono.INFO).puntero(i, "i");
        }
        if (j < der.length) {
            fd.tono(j, cmpJ >= 0 ? Tono.AVISO : Tono.VIOLETA).puntero(j, "j");
        }
        return marco.base().linea(a).linea(fi, fd)
                .leyenda(Tono.ACENTO, "k (escritura)").leyenda(Tono.EXITO, "ya intercalado")
                .leyenda(Tono.AVISO, "comparando").leyenda(Tono.ATENUADO, "ya usado").crear();
    }
}
