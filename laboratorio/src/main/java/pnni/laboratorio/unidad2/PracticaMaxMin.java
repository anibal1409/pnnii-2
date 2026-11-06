package pnni.laboratorio.unidad2;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Máximo y mínimo simultáneos por divide y conquista (3n/2 − 2 comparaciones). */
public final class PracticaMaxMin implements Practica {

    private static final String CODIGO = """
            static int[] maxMinDYC(int[] arr, int inicio, int fin) { @@llamada
                if (inicio == fin) { @@base1
                    return new int[]{arr[inicio], arr[inicio]}; @@ret1
                }
                if (inicio + 1 == fin) { @@base2
                    comparaciones++; @@cmp2
                    if (arr[inicio] > arr[fin]) { @@cmp2
                        return new int[]{arr[inicio], arr[fin]}; @@ret2
                    } else {
                        return new int[]{arr[fin], arr[inicio]}; @@ret2
                    }
                }
                int medio = (inicio + fin) / 2; @@medio
                int[] izq = maxMinDYC(arr, inicio, medio); @@izq
                int[] der = maxMinDYC(arr, medio + 1, fin); @@der
                comparaciones += 2; @@combinar
                int maxGlobal = Math.max(izq[0], der[0]); @@combinar
                int minGlobal = Math.min(izq[1], der[1]); @@combinar
                return new int[]{maxGlobal, minGlobal}; @@ret
            }

            // en main:
            int[] resultado = maxMinDYC(datos, 0, datos.length - 1); @@main
            """;

    private int comparaciones;

    @Override
    public Unidad unidad() {
        return Unidad.II;
    }

    @Override
    public String titulo() {
        return "Máximos y mínimos";
    }

    @Override
    public String descripcion() {
        return "Divide el arreglo en mitades, obtiene el máximo y el mínimo de cada una y los combina con dos "
                + "comparaciones. Los tramos de dos elementos se resuelven con una sola comparación: en total "
                + "3n/2 − 2 en lugar de las 2n − 2 del recorrido ingenuo.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(n)", "3n/2 − 2 comparaciones");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(Parametro.texto("arreglo", "Arreglo", "8, 3, 15, 6, 2, 11, 7, 4", "Hasta 12 enteros"));
    }

    @Override
    public String programaConsola() {
        return "MaxMinDYC.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] arr = entrada.enteros("arreglo", 1, 12, -999, 999);
        Traza t = new Traza(new Codigo(CODIGO));
        ArbolLlamadas arbol = new ArbolLlamadas();
        comparaciones = 0;
        t.var("comparaciones", 0);
        int[] r = maxMin(t, arbol, arr, 0, arr.length - 1);
        t.quitar("inicio").quitar("fin").quitar("medio").var("resultado", "{" + r[0] + ", " + r[1] + "}");
        t.imprimirLinea("Arreglo: ");
        t.imprimirLinea(EscenaArreglo.texto(arr));
        t.imprimirLinea("Maximo: " + r[0]);
        t.imprimirLinea("Minimo: " + r[1]);
        int formula = 3 * arr.length / 2 - 2;
        t.imprimirLinea("Comparaciones: " + comparaciones + " (3n/2 - 2 = " + formula + ")");
        EscenaArreglo.Fila f = EscenaArreglo.fila("arr", arr);
        for (int k = 0; k < arr.length; k++) {
            if (arr[k] == r[0]) {
                f.tono(k, Tono.EXITO).puntero(k, "max");
            } else if (arr[k] == r[1]) {
                f.tono(k, Tono.INFO).puntero(k, "min");
            }
        }
        t.paso("main", "Máximo " + r[0] + ", mínimo " + r[1] + " ✓",
                "Se usaron " + comparaciones + " comparaciones entre elementos; el método ingenuo usaría 2n − 2 = "
                        + (2 * arr.length - 2) + "." + ((arr.length & (arr.length - 1)) == 0
                        ? " Como n es potencia de 2, coincide con 3n/2 − 2 = " + formula + "."
                        : " La fórmula 3n/2 − 2 es exacta cuando n es potencia de 2."),
                EscenaArreglo.nueva().linea(f).arbol(arbol.foto()).insignia("max = " + r[0] + "  ·  min = " + r[1], true)
                        .leyenda(Tono.EXITO, "máximo").leyenda(Tono.INFO, "mínimo").crear());
        return t;
    }

    private int[] maxMin(Traza t, ArbolLlamadas arbol, int[] arr, int inicio, int fin) {
        arbol.llamar("[" + inicio + ".." + fin + "]");
        t.var("inicio", inicio).var("fin", fin).quitar("medio");
        t.paso("llamada", "Llamada sobre [" + inicio + ", " + fin + "]",
                "Se busca el máximo y el mínimo de " + (fin - inicio + 1) + " elemento(s).",
                escena(arr, inicio, fin, arbol).crear());
        boolean uno = inicio == fin;
        t.paso("base1", "¿Un solo elemento? " + (uno ? "Sí" : "No"), inicio + " == " + fin + " → " + uno + ".",
                escena(arr, inicio, fin, arbol).crear());
        if (uno) {
            t.paso("ret1", "Devolver {" + arr[inicio] + ", " + arr[inicio] + "}",
                    "Un único elemento es a la vez máximo y mínimo: no hace falta comparar.",
                    escena(arr, inicio, fin, arbol).crear());
            arbol.devolver("max " + arr[inicio] + " · min " + arr[inicio]);
            return new int[]{arr[inicio], arr[inicio]};
        }
        boolean dos = inicio + 1 == fin;
        t.paso("base2", "¿Dos elementos? " + (dos ? "Sí" : "No"), (inicio + 1) + " == " + fin + " → " + dos + ".",
                escena(arr, inicio, fin, arbol).crear());
        if (dos) {
            comparaciones++;
            t.var("comparaciones", comparaciones);
            boolean mayor = arr[inicio] > arr[fin];
            t.paso("cmp2", "¿" + arr[inicio] + " > " + arr[fin] + "? " + (mayor ? "Sí" : "No"),
                    "Una sola comparación decide cuál es el máximo y cuál el mínimo (comparaciones = " + comparaciones + ").",
                    escena(arr, inicio, fin, arbol).compara(inicio, fin).crear());
            int max = mayor ? arr[inicio] : arr[fin];
            int min = mayor ? arr[fin] : arr[inicio];
            t.paso("ret2", "Devolver {" + max + ", " + min + "}", "Máximo " + max + " y mínimo " + min + " del par.",
                    escena(arr, inicio, fin, arbol).compara(inicio, fin).crear());
            arbol.devolver("max " + max + " · min " + min);
            return new int[]{max, min};
        }
        int medio = (inicio + fin) / 2;
        t.var("medio", medio);
        t.paso("medio", "DIVIDIR: medio = " + medio,
                "Mitades [" + inicio + ", " + medio + "] y [" + (medio + 1) + ", " + fin + "].",
                escena(arr, inicio, fin, arbol).medio(medio).crear());
        t.paso("izq", "CONQUISTAR la mitad izquierda", "Llamada recursiva sobre [" + inicio + ", " + medio + "].",
                escena(arr, inicio, medio, arbol).crear());
        int[] izq = maxMin(t, arbol, arr, inicio, medio);
        t.var("inicio", inicio).var("fin", fin).var("medio", medio).var("izq", "{" + izq[0] + ", " + izq[1] + "}");
        t.paso("der", "CONQUISTAR la mitad derecha", "Llamada recursiva sobre [" + (medio + 1) + ", " + fin + "].",
                escena(arr, medio + 1, fin, arbol).crear());
        int[] der = maxMin(t, arbol, arr, medio + 1, fin);
        t.var("inicio", inicio).var("fin", fin).var("medio", medio).var("der", "{" + der[0] + ", " + der[1] + "}");
        comparaciones += 2;
        int max = Math.max(izq[0], der[0]);
        int min = Math.min(izq[1], der[1]);
        t.var("comparaciones", comparaciones).var("maxGlobal", max).var("minGlobal", min);
        t.paso("combinar", "COMBINAR: max(" + izq[0] + ", " + der[0] + ") = " + max + ", min(" + izq[1] + ", " + der[1]
                        + ") = " + min,
                "Dos comparaciones más (comparaciones = " + comparaciones + "): una entre los máximos y otra entre los mínimos.",
                escena(arr, inicio, fin, arbol).crear());
        t.paso("ret", "Devolver {" + max + ", " + min + "}", "Resultado del tramo [" + inicio + ", " + fin + "].",
                escena(arr, inicio, fin, arbol).crear());
        t.quitar("izq").quitar("der").quitar("maxGlobal").quitar("minGlobal");
        arbol.devolver("max " + max + " · min " + min);
        return new int[]{max, min};
    }

    /** Constructor auxiliar para no repetir la configuración de la fila. */
    private static final class Vista {
        private final EscenaArreglo.Fila fila;
        private final ArbolLlamadas arbol;

        Vista(EscenaArreglo.Fila fila, ArbolLlamadas arbol) {
            this.fila = fila;
            this.arbol = arbol;
        }

        Vista compara(int a, int b) {
            fila.tono(a, Tono.AVISO).tono(b, Tono.AVISO);
            return this;
        }

        Vista medio(int m) {
            fila.tono(m, Tono.ACENTO).puntero(m, "medio");
            return this;
        }

        EscenaArreglo crear() {
            return EscenaArreglo.nueva().linea(fila).arbol(arbol.foto())
                    .leyenda(Tono.ACENTO, "medio").leyenda(Tono.AVISO, "comparando")
                    .leyenda(Tono.ATENUADO, "fuera del tramo").crear();
        }
    }

    private static Vista escena(int[] arr, int inicio, int fin, ArbolLlamadas arbol) {
        EscenaArreglo.Fila f = EscenaArreglo.fila("arr", arr).rango(inicio, fin).puntero(inicio, "inicio").puntero(fin, "fin");
        return new Vista(f, arbol);
    }
}
