package pnni.laboratorio.unidad1;

import java.util.function.UnaryOperator;

import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.unidad1.EscenaHeap.Constructor;
import pnni.laboratorio.unidad1.EscenaHeap.Rol;

/**
 * INSERTAR y HEAPIFY instrumentados para Max-Heap y Min-Heap. El código mostrado y la
 * ejecución trazada viven juntos para que nunca se desincronicen.
 */
final class TrazadorHeap {

    private TrazadorHeap() {
    }

    /** Hace que el código mostrado use la comparación y el nombre de variable del tipo de heap. */
    static String adaptar(String codigo, boolean max) {
        return codigo
                .replace("%CMP_SUBIR%", max ? "<" : ">")
                .replace("%CMP%", max ? ">" : "<")
                .replace("%EXT%", max ? "mayor" : "menor");
    }

    static final String CODIGO_INSERTAR = """
            static int insertar(int[] heap, int tamano, int valor) {
                heap[tamano] = valor;            // al final del arreglo @@colocar
                int i = tamano; @@i
                while (i > 0 && heap[(i - 1) / 2] %CMP_SUBIR% heap[i]) { @@while
                    int padre = (i - 1) / 2; @@padre
                    int temp = heap[padre]; @@swap
                    heap[padre] = heap[i]; @@swap
                    heap[i] = temp; @@swap
                    i = padre;                   // seguimos subiendo @@subir
                }
                return tamano + 1; @@retInsertar
            }
            """;

    static final String CODIGO_HEAPIFY = """
            static void heapify(int[] heap, int i, int tamano) {
                boolean continuar = true; @@hIni
                while (continuar) { @@hWhile
                    int izquierdo = 2 * i + 1; @@hHijos
                    int derecho = 2 * i + 2; @@hHijos
                    int %EXT% = i; @@hExt
                    if (izquierdo < tamano && heap[izquierdo] %CMP% heap[%EXT%]) { @@hCmpIzq
                        %EXT% = izquierdo; @@hSetIzq
                    }
                    if (derecho < tamano && heap[derecho] %CMP% heap[%EXT%]) { @@hCmpDer
                        %EXT% = derecho; @@hSetDer
                    }
                    if (%EXT% != i) { @@hCmpFin
                        int temp = heap[i]; @@hSwap
                        heap[i] = heap[%EXT%]; @@hSwap
                        heap[%EXT%] = temp; @@hSwap
                        i = %EXT%;                // seguimos hundiendo @@hBajar
                    } else {
                        continuar = false;       // propiedad restaurada @@hFin
                    }
                }
            }
            """;

    /** Traza INSERTAR; devuelve el nuevo tamaño. {@code contexto} añade roles/notas propias de la práctica. */
    static int insertar(Traza t, int[] heap, int tamano, int valor, boolean max, UnaryOperator<Constructor> contexto) {
        String tipo = max ? "Max-Heap" : "Min-Heap";
        String sube = max ? "menor" : "mayor";
        heap[tamano] = valor;
        t.var("valor", valor).var("tamano", tamano).quitar("i").quitar("padre");
        t.paso("colocar", "Colocar " + valor + " al final",
                "Para mantener la forma de árbol casi completo, el nuevo valor ocupa la primera posición libre: "
                        + "heap[" + tamano + "] = " + valor + ". Ahora puede que su padre sea " + sube
                        + " que él y se viole la propiedad de " + tipo + ".",
                contexto.apply(EscenaHeap.de(heap, tamano + 1).rol(tamano, Rol.ACTUAL).puntero(tamano, "nuevo")).crear());

        int i = tamano;
        t.var("i", i);
        t.paso("i", "Recordar la posición del nuevo elemento",
                "i = " + i + ". La variable i sigue al elemento mientras flota hacia arriba.",
                contexto.apply(EscenaHeap.de(heap, tamano + 1).rol(i, Rol.ACTUAL).puntero(i, "i")).crear());

        while (true) {
            if (i == 0) {
                t.paso("while", "¿Seguir subiendo? No: es la raíz",
                        "i = 0, así que el elemento llegó a la raíz y no tiene padre. El ciclo termina.",
                        contexto.apply(EscenaHeap.de(heap, tamano + 1).rol(0, Rol.ACTUAL).puntero(0, "i")).crear());
                break;
            }
            int padre = (i - 1) / 2;
            boolean subir = max ? heap[padre] < heap[i] : heap[padre] > heap[i];
            t.paso("while", subir ? "¿Seguir subiendo? Sí" : "¿Seguir subiendo? No",
                    "Padre de i = (" + i + " − 1) / 2 = " + padre + ". Comparamos heap[" + padre + "] = " + heap[padre]
                            + (max ? " < " : " > ") + "heap[" + i + "] = " + heap[i] + " → " + (subir ? "verdadero" : "falso")
                            + ". " + (subir ? "El padre es " + sube + ", hay que intercambiarlos."
                            : "La propiedad de " + tipo + " ya se cumple; el elemento se queda aquí."),
                    contexto.apply(EscenaHeap.de(heap, tamano + 1).rol(i, Rol.ACTUAL).rol(padre, Rol.PADRE)
                            .puntero(i, "i").puntero(padre, "padre")).crear());
            if (!subir) {
                break;
            }
            t.var("padre", padre);
            t.paso("padre", "Calcular el índice del padre",
                    "padre = (i − 1) / 2 = (" + i + " − 1) / 2 = " + padre + " (la división entera trunca).",
                    contexto.apply(EscenaHeap.de(heap, tamano + 1).rol(i, Rol.ACTUAL).rol(padre, Rol.PADRE)
                            .puntero(i, "i").puntero(padre, "padre")).crear());
            int temp = heap[padre];
            heap[padre] = heap[i];
            heap[i] = temp;
            t.var("temp", temp);
            t.paso("swap", "Intercambiar hijo y padre",
                    "Con una variable temporal intercambiamos heap[" + padre + "] y heap[" + i + "]: el "
                            + heap[padre] + " sube y el " + heap[i] + " baja.",
                    contexto.apply(EscenaHeap.de(heap, tamano + 1).intercambio(padre, i)
                            .puntero(i, "i").puntero(padre, "padre")).crear());
            i = padre;
            t.var("i", i);
            t.paso("subir", "Subir un nivel",
                    "i = padre = " + i + ". Volvemos a evaluar la condición del while desde la nueva posición.",
                    contexto.apply(EscenaHeap.de(heap, tamano + 1).rol(i, Rol.ACTUAL).puntero(i, "i")).crear());
        }
        int nuevo = tamano + 1;
        t.var("tamano", nuevo);
        t.paso("retInsertar", "Inserción terminada",
                "INSERTAR devuelve tamano + 1 = " + nuevo + ". El arreglo vuelve a ser un " + tipo + ".",
                contexto.apply(EscenaHeap.de(heap, nuevo).todos(Rol.LISTO).insignia(tipo + " válido ✓", true)).crear());
        return nuevo;
    }

    /** Traza HEAPIFY desde el índice {@code i}. */
    static void heapify(Traza t, int[] heap, int i, int tamano, boolean max, UnaryOperator<Constructor> contexto) {
        String ext = max ? "mayor" : "menor";
        String cmp = max ? " > " : " < ";
        t.var("i", i).var("tamano", tamano).var("continuar", true)
                .quitar("izquierdo").quitar("derecho").quitar(ext).quitar("temp");
        t.paso("hIni", "Comenzar HEAPIFY en el índice " + i,
                "HEAPIFY hunde heap[" + i + "] = " + heap[i] + " hasta que sea " + ext + " o igual que sus hijos.",
                contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).puntero(i, "i")).crear());
        boolean continuar = true;
        while (continuar) {
            t.paso("hWhile", "continuar es verdadero → nueva vuelta",
                    "Revisamos el nodo heap[" + i + "] = " + heap[i] + " frente a sus hijos.",
                    contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).puntero(i, "i")).crear());
            int izq = 2 * i + 1;
            int der = 2 * i + 2;
            t.var("izquierdo", izq).var("derecho", der);
            t.paso("hHijos", "Ubicar los hijos",
                    "izquierdo = 2·" + i + " + 1 = " + izq + (izq < tamano ? " (existe)" : " (no existe: " + izq + " ≥ tamano)")
                            + "; derecho = 2·" + i + " + 2 = " + der + (der < tamano ? " (existe)." : " (no existe)."),
                    contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).rol(izq, Rol.HIJO).rol(der, Rol.HIJO)
                            .puntero(i, "i").puntero(izq, "izq").puntero(der, "der")).crear());
            int e = i;
            t.var(ext, e);
            t.paso("hExt", "Suponer que el " + ext + " es el propio nodo",
                    ext + " = i = " + i + ". Si ningún hijo lo supera, el nodo ya está en su lugar.",
                    contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).puntero(i, "i, " + ext)).crear());

            int[] hijos = {izq, der};
            String[] marcas = {"hCmpIzq", "hCmpDer"};
            String[] asignaciones = {"hSetIzq", "hSetDer"};
            String[] nombres = {"izquierdo", "derecho"};
            for (int h = 0; h < 2; h++) {
                int hijo = hijos[h];
                if (hijo >= tamano) {
                    t.paso(marcas[h], "¿Hijo " + nombres[h] + " " + ext + "? No existe",
                            nombres[h] + " = " + hijo + " no es menor que tamano = " + tamano + ": la condición es falsa.",
                            contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).rol(e, Rol.COMPARA)
                                    .puntero(i, "i").puntero(e, ext)).crear());
                    continue;
                }
                boolean gana = max ? heap[hijo] > heap[e] : heap[hijo] < heap[e];
                t.paso(marcas[h], "¿Hijo " + nombres[h] + " " + ext + "? " + (gana ? "Sí" : "No"),
                        "heap[" + hijo + "] = " + heap[hijo] + cmp + "heap[" + e + "] = " + heap[e] + " → "
                                + (gana ? "verdadero." : "falso."),
                        contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).rol(hijo, Rol.COMPARA)
                                .rol(e, e == i ? Rol.ACTUAL : Rol.COMPARA)
                                .puntero(i, "i").puntero(e, ext).puntero(hijo, nombres[h].substring(0, 3))).crear());
                if (gana) {
                    e = hijo;
                    t.var(ext, e);
                    t.paso(asignaciones[h], ext + " = " + nombres[h],
                            "El candidato a subir ahora es heap[" + e + "] = " + heap[e] + ".",
                            contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).rol(e, Rol.HIJO)
                                    .puntero(i, "i").puntero(e, ext)).crear());
                }
            }

            if (e != i) {
                t.paso("hCmpFin", ext + " ≠ i → hay que hundir",
                        "El hijo heap[" + e + "] = " + heap[e] + " es " + ext + " que heap[" + i + "] = " + heap[i]
                                + ", así que se viola la propiedad.",
                        contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).rol(e, Rol.HIJO)
                                .puntero(i, "i").puntero(e, ext)).crear());
                int temp = heap[i];
                heap[i] = heap[e];
                heap[e] = temp;
                t.var("temp", temp);
                t.paso("hSwap", "Intercambiar con el hijo " + ext,
                        "heap[" + i + "] ↔ heap[" + e + "]: el " + heap[i] + " sube y el " + temp + " baja un nivel.",
                        contexto.apply(EscenaHeap.de(heap, tamano).intercambio(i, e)
                                .puntero(i, "i").puntero(e, ext)).crear());
                i = e;
                t.var("i", i);
                t.paso("hBajar", "Bajar al índice " + i,
                        "i = " + ext + " = " + i + ". Repetimos el proceso desde la nueva posición del valor hundido.",
                        contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.ACTUAL).puntero(i, "i")).crear());
            } else {
                t.paso("hCmpFin", ext + " = i → el nodo está en su lugar",
                        "Ningún hijo es " + ext + " que heap[" + i + "] = " + heap[i] + ".",
                        contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.LISTO).puntero(i, "i")).crear());
                continuar = false;
                t.var("continuar", false);
                t.paso("hFin", "continuar = false",
                        "La propiedad se restauró en este subárbol; el while termina y HEAPIFY retorna.",
                        contexto.apply(EscenaHeap.de(heap, tamano).rol(i, Rol.LISTO).puntero(i, "i")).crear());
            }
        }
    }

    static boolean esHeap(int[] a, int n, boolean max) {
        return esHeapDesde(a, n, 0, max);
    }

    /** ¿El subárbol con raíz en {@code raiz} cumple la propiedad de heap? */
    static boolean esHeapDesde(int[] a, int n, int raiz, boolean max) {
        if (raiz >= n) {
            return true;
        }
        for (int hijo = 2 * raiz + 1; hijo <= 2 * raiz + 2 && hijo < n; hijo++) {
            boolean viola = max ? a[hijo] > a[raiz] : a[hijo] < a[raiz];
            if (viola || !esHeapDesde(a, n, hijo, max)) {
                return false;
            }
        }
        return true;
    }

    static String texto(int[] a, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) {
            sb.append(a[k]).append(' ');
        }
        return sb.toString();
    }

    static boolean esMax(String tipo) {
        return tipo.startsWith("Max");
    }
}
