package pnni.laboratorio.unidad1;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.unidad1.EscenaHeap.Rol;

/** Representación secuencial: fórmulas padre/hijos con índice base 0. */
public final class PracticaNavegacion implements Practica {

    private static final String CODIGO = """
            static void describirNodo(int[] heap, int i) {
                int padre = (i - 1) / 2; @@padre
                int hijoIzq = 2 * i + 1; @@izq
                int hijoDer = 2 * i + 2; @@der
                if (i == 0) {                    // (0 - 1) / 2 da 0 en Java @@esRaiz
                    System.out.print("padre: ninguno (raiz)"); @@sinPadre
                } else {
                    System.out.print("padre: " + heap[padre]); @@conPadre
                }
                if (hijoIzq < heap.length) { @@existeIzq
                    System.out.print(" | izq: " + heap[hijoIzq]); @@conIzq
                } else {
                    System.out.print(" | izq: ninguno"); @@sinIzq
                }
                if (hijoDer < heap.length) { @@existeDer
                    System.out.print(" | der: " + heap[hijoDer]); @@conDer
                } else {
                    System.out.print(" | der: ninguno"); @@sinDer
                }
            }
            """;

    @Override
    public Unidad unidad() {
        return Unidad.I;
    }

    @Override
    public String titulo() {
        return "Navegación padre-hijo";
    }

    @Override
    public String descripcion() {
        return "Un árbol casi completo se guarda en un arreglo sin punteros: el padre de i está en (i−1)/2 "
                + "y sus hijos en 2i+1 y 2i+2. Elige un índice y observa cómo se calculan.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(1) por nodo");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.texto("arreglo", "Árbol en arreglo", "90, 70, 80, 30, 50, 60, 40", "Hasta 31 enteros separados por coma"),
                Parametro.texto("indice", "Índice i", "2", "Posición a explorar (base 0)"));
    }

    @Override
    public String programaConsola() {
        return "NavegacionPadreHijo.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] heap = entrada.enteros("arreglo", 1, 31, -999, 999);
        int i = entrada.entero("indice", 0, heap.length - 1);
        int n = heap.length;
        Traza t = new Traza(new Codigo(CODIGO));
        t.var("heap.length", n).var("i", i);

        int padre = (i - 1) / 2;
        t.var("padre", padre);
        t.paso("padre", "Índice del padre: (i − 1) / 2",
                "padre = (" + i + " − 1) / 2 = " + padre + "."
                        + (i == 0 ? " Ojo: en Java la división entera trunca hacia cero, así que (0 − 1) / 2 da 0 y no −1."
                        + " Por eso más abajo se pregunta explícitamente si i == 0." : ""),
                EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).rol(i == 0 ? -1 : padre, Rol.PADRE)
                        .puntero(i, "i").puntero(i == 0 ? -1 : padre, "padre").crear());
        int izq = 2 * i + 1;
        t.var("hijoIzq", izq);
        t.paso("izq", "Índice del hijo izquierdo: 2i + 1",
                "hijoIzq = 2·" + i + " + 1 = " + izq + (izq < n ? "." : ", que está fuera del arreglo (≥ " + n + ")."),
                EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).rol(izq, Rol.HIJO).puntero(i, "i").puntero(izq, "hijoIzq").crear());
        int der = 2 * i + 2;
        t.var("hijoDer", der);
        t.paso("der", "Índice del hijo derecho: 2i + 2",
                "hijoDer = 2·" + i + " + 2 = " + der + (der < n ? "." : ", que está fuera del arreglo (≥ " + n + ")."),
                EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).rol(izq, Rol.HIJO).rol(der, Rol.HIJO)
                        .puntero(i, "i").puntero(izq, "hijoIzq").puntero(der, "hijoDer").crear());

        t.imprimir("Nodo [" + i + "]=" + heap[i] + " | ");
        t.paso("esRaiz", i == 0 ? "¿Es la raíz? Sí" : "¿Es la raíz? No",
                i == 0 ? "i == 0: el nodo es la raíz y no tiene padre."
                        : "i = " + i + " ≠ 0, así que el nodo tiene padre en el índice " + padre + ".",
                EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).puntero(i, "i").crear());
        if (i == 0) {
            t.imprimir("padre: ninguno (raiz)");
            t.paso("sinPadre", "Sin padre", "Se informa que la raíz no tiene padre.",
                    EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).puntero(i, "i").crear());
        } else {
            t.imprimir("padre: " + heap[padre]);
            t.paso("conPadre", "Padre: heap[" + padre + "] = " + heap[padre],
                    "El valor del padre es " + heap[padre] + ".",
                    EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).rol(padre, Rol.PADRE).puntero(i, "i").puntero(padre, "padre").crear());
        }
        hijo(t, heap, i, izq, "Izq", "izquierdo");
        hijo(t, heap, i, der, "Der", "derecho");
        t.imprimirLinea(izq >= n ? "  (hoja)" : "");
        t.paso(der < n ? "conDer" : "sinDer",
                izq >= n ? "Resultado: el nodo es una hoja" : "Resultado completo",
                izq >= n ? "Como no tiene hijo izquierdo, tampoco puede tener derecho: es una hoja (grado 0)."
                        : "El nodo " + heap[i] + " tiene grado " + ((izq < n ? 1 : 0) + (der < n ? 1 : 0)) + ".",
                EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).rol(i == 0 ? -1 : padre, Rol.PADRE)
                        .rol(izq, Rol.HIJO).rol(der, Rol.HIJO).puntero(i, "i").crear());
        return t;
    }

    private static void hijo(Traza t, int[] heap, int i, int hijo, String sufijo, String nombre) {
        int n = heap.length;
        boolean existe = hijo < n;
        t.paso("existe" + sufijo, "¿Existe el hijo " + nombre + "? " + (existe ? "Sí" : "No"),
                hijo + (existe ? " < " : " ≥ ") + "heap.length = " + n + (existe ? ": el índice es válido."
                        : ": el índice cae fuera del arreglo, no hay hijo " + nombre + "."),
                EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).rol(hijo, Rol.HIJO).puntero(i, "i")
                        .puntero(hijo, "hijo" + sufijo).crear());
        if (existe) {
            t.imprimir(" | " + sufijo.toLowerCase() + ": " + heap[hijo]);
            t.paso("con" + sufijo, "Hijo " + nombre + ": heap[" + hijo + "] = " + heap[hijo],
                    "Se muestra el valor " + heap[hijo] + ".",
                    EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).rol(hijo, Rol.HIJO).puntero(i, "i")
                            .puntero(hijo, "hijo" + sufijo).crear());
        } else {
            t.imprimir(" | " + sufijo.toLowerCase() + ": ninguno");
            t.paso("sin" + sufijo, "Sin hijo " + nombre, "Se informa que no existe.",
                    EscenaHeap.de(heap, n).rol(i, Rol.ACTUAL).puntero(i, "i").crear());
        }
    }
}
