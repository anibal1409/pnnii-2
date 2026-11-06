package pnni.laboratorio.unidad2;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Búsqueda binaria en sus dos versiones: recursiva (con árbol de llamadas) e iterativa. */
public final class PracticaBusquedaBinaria implements Practica {

    private static final String CODIGO_RECURSIVO = """
            static int busquedaBinariaRec(int[] arr, int inicio, int fin, int objetivo) { @@llamada
                if (inicio > fin) { @@vacio
                    return -1;                       // no encontrado @@noEsta
                }
                int medio = (inicio + fin) / 2; @@medio
                if (arr[medio] == objetivo) { @@igual
                    return medio; @@encontrado
                }
                if (objetivo < arr[medio]) { @@menor
                    return busquedaBinariaRec(arr, inicio, medio - 1, objetivo); @@izq
                }
                return busquedaBinariaRec(arr, medio + 1, fin, objetivo); @@der
            }

            // en main:
            int indice = busquedaBinariaRec(datos, 0, datos.length - 1, objetivo); @@main
            """;

    private static final String CODIGO_ITERATIVO = """
            static int busquedaBinariaIter(int[] arr, int objetivo) {
                int inicio = 0; @@ini
                int fin = arr.length - 1; @@ini
                while (inicio <= fin) { @@while
                    int medio = (inicio + fin) / 2; @@medio
                    if (arr[medio] == objetivo) { @@igual
                        return medio; @@encontrado
                    }
                    if (objetivo < arr[medio]) { @@menor
                        fin = medio - 1; @@izq
                    } else {
                        inicio = medio + 1; @@der
                    }
                }
                return -1;                           // no encontrado @@noEsta
            }

            // en main:
            int indice = busquedaBinariaIter(datos, objetivo); @@main
            """;

    private final boolean recursiva;

    private PracticaBusquedaBinaria(boolean recursiva) {
        this.recursiva = recursiva;
    }

    public static PracticaBusquedaBinaria recursiva() {
        return new PracticaBusquedaBinaria(true);
    }

    public static PracticaBusquedaBinaria iterativa() {
        return new PracticaBusquedaBinaria(false);
    }

    @Override
    public Unidad unidad() {
        return Unidad.II;
    }

    @Override
    public String titulo() {
        return recursiva ? "Búsqueda binaria recursiva" : "Búsqueda binaria iterativa";
    }

    @Override
    public String descripcion() {
        return recursiva
                ? "Compara el objetivo con el elemento central y se llama a sí misma sobre la mitad donde puede estar. "
                + "Cada llamada descarta la mitad del rango: a lo sumo ⌊log₂ n⌋ + 1 comparaciones."
                : "La misma estrategia sin recursión: un ciclo ajusta los límites inicio y fin hasta encontrar el "
                + "objetivo o dejar el rango vacío. Usa memoria O(1).";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(log n)", "Mejor caso O(1)");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.texto("arreglo", "Arreglo ordenado", "3, 7, 12, 19, 25, 33, 42, 56, 71",
                        "Hasta 16 enteros en orden ascendente"),
                Parametro.texto("objetivo", "Objetivo", recursiva ? "25" : "19", "Valor a buscar"));
    }

    @Override
    public String programaConsola() {
        return recursiva ? "BusquedaBinaria.java" : "BusquedaBinariaIterativa.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] arr = entrada.enteros("arreglo", 1, 16, -999, 999);
        for (int k = 1; k < arr.length; k++) {
            if (arr[k] < arr[k - 1]) {
                throw entrada.error("arreglo", "debe estar ordenado de menor a mayor (" + arr[k - 1] + " va antes que "
                        + arr[k] + ")");
            }
        }
        int objetivo = entrada.entero("objetivo", -999, 999);
        Traza t = new Traza(new Codigo(recursiva ? CODIGO_RECURSIVO : CODIGO_ITERATIVO));
        t.var("objetivo", objetivo);
        int indice;
        if (recursiva) {
            ArbolLlamadas arbol = new ArbolLlamadas();
            indice = recursiva(t, arbol, arr, 0, arr.length - 1, objetivo);
            t.quitar("inicio").quitar("fin").quitar("medio").var("indice", indice);
            t.imprimirLinea(indice != -1
                    ? "El elemento " + objetivo + " se encuentra en el indice " + indice + "."
                    : "El elemento " + objetivo + " no esta en el arreglo.");
            t.paso("main", indice != -1 ? "Resultado: índice " + indice + " ✓" : "Resultado: −1 (no está)",
                    "La llamada inicial devolvió " + indice + ". Se hicieron " + arbol.foto().size()
                            + " llamadas para " + arr.length + " elementos.",
                    escena(arr, -1, -1, -1, indice, arbol).insignia(indice != -1 ? "Encontrado ✓" : "No encontrado",
                            indice != -1).crear());
        } else {
            indice = iterativa(t, arr, objetivo);
            t.var("indice", indice);
            t.imprimirLinea("Resultado iterativo: indice " + indice);
            t.paso("main", indice != -1 ? "Resultado: índice " + indice + " ✓" : "Resultado: −1 (no está)",
                    "El método devolvió " + indice + ".",
                    escena(arr, -1, -1, -1, indice, null).insignia(indice != -1 ? "Encontrado ✓" : "No encontrado",
                            indice != -1).crear());
        }
        return t;
    }

    private int recursiva(Traza t, ArbolLlamadas arbol, int[] arr, int inicio, int fin, int objetivo) {
        arbol.llamar("[" + inicio + ".." + fin + "]");
        t.var("inicio", inicio).var("fin", fin).quitar("medio");
        t.paso("llamada", "Llamada con el rango [" + inicio + ", " + fin + "]",
                inicio > fin ? "El rango está vacío: inicio = " + inicio + " es mayor que fin = " + fin + "."
                        : "Se busca " + objetivo + " entre los índices " + inicio + " y " + fin + " ("
                        + (fin - inicio + 1) + " elementos).",
                escena(arr, inicio, fin, -1, -1, arbol).crear());
        boolean vacio = inicio > fin;
        t.paso("vacio", "¿inicio > fin? " + (vacio ? "Sí" : "No"),
                inicio + " > " + fin + " → " + vacio + (vacio ? ". Caso base: no quedan elementos donde buscar."
                        : ". Todavía hay elementos en el rango."),
                escena(arr, inicio, fin, -1, -1, arbol).crear());
        if (vacio) {
            t.paso("noEsta", "Devolver −1", "El objetivo " + objetivo + " no está en el arreglo.",
                    escena(arr, inicio, fin, -1, -1, arbol).crear());
            arbol.devolver("→ −1");
            return -1;
        }
        int medio = (inicio + fin) / 2;
        t.var("medio", medio);
        t.paso("medio", "medio = (" + inicio + " + " + fin + ") / 2 = " + medio,
                "DIVIDIR: la división entera trunca, así que medio = " + medio + ". arr[" + medio + "] = " + arr[medio] + ".",
                escena(arr, inicio, fin, medio, -1, arbol).crear());
        boolean igual = arr[medio] == objetivo;
        t.paso("igual", "¿arr[" + medio + "] == " + objetivo + "? " + (igual ? "Sí" : "No"),
                "arr[" + medio + "] = " + arr[medio] + (igual ? " es el objetivo." : " ≠ " + objetivo + "."),
                escena(arr, inicio, fin, medio, igual ? medio : -1, arbol).crear());
        if (igual) {
            t.paso("encontrado", "Devolver " + medio, "Caso base de éxito: el objetivo está en el índice " + medio + ".",
                    escena(arr, inicio, fin, medio, medio, arbol).crear());
            arbol.devolver("→ " + medio);
            return medio;
        }
        boolean menor = objetivo < arr[medio];
        t.paso("menor", "¿" + objetivo + " < " + arr[medio] + "? " + (menor ? "Sí → izquierda" : "No → derecha"),
                menor ? "Como el arreglo está ordenado, el objetivo solo puede estar antes de medio: se descarta "
                        + "[" + medio + ", " + fin + "]."
                        : "El objetivo es mayor, solo puede estar después de medio: se descarta [" + inicio + ", " + medio + "].",
                escena(arr, inicio, fin, medio, -1, arbol).crear());
        int resultado;
        if (menor) {
            t.paso("izq", "CONQUISTAR: buscar en [" + inicio + ", " + (medio - 1) + "]",
                    "Llamada recursiva con fin = medio − 1 = " + (medio - 1) + ".",
                    escena(arr, inicio, medio - 1, -1, -1, arbol).crear());
            resultado = recursiva(t, arbol, arr, inicio, medio - 1, objetivo);
        } else {
            t.paso("der", "CONQUISTAR: buscar en [" + (medio + 1) + ", " + fin + "]",
                    "Llamada recursiva con inicio = medio + 1 = " + (medio + 1) + ".",
                    escena(arr, medio + 1, fin, -1, -1, arbol).crear());
            resultado = recursiva(t, arbol, arr, medio + 1, fin, objetivo);
        }
        t.var("inicio", inicio).var("fin", fin).var("medio", medio);
        arbol.devolver("→ " + resultado);
        return resultado;
    }

    private int iterativa(Traza t, int[] arr, int objetivo) {
        int inicio = 0;
        int fin = arr.length - 1;
        t.var("inicio", inicio).var("fin", fin);
        t.paso("ini", "Rango inicial [0, " + fin + "]", "Al principio el objetivo puede estar en cualquier posición.",
                escena(arr, inicio, fin, -1, -1, null).crear());
        int vuelta = 0;
        while (true) {
            boolean sigue = inicio <= fin;
            t.paso("while", "¿inicio ≤ fin? " + inicio + " ≤ " + fin + " → " + (sigue ? "Sí" : "No"),
                    sigue ? "Vuelta " + (++vuelta) + ": quedan " + (fin - inicio + 1) + " candidatos."
                            : "El rango quedó vacío: el objetivo no está.",
                    escena(arr, inicio, fin, -1, -1, null).crear());
            if (!sigue) {
                break;
            }
            int medio = (inicio + fin) / 2;
            t.var("medio", medio);
            t.paso("medio", "medio = (" + inicio + " + " + fin + ") / 2 = " + medio,
                    "arr[" + medio + "] = " + arr[medio] + ".", escena(arr, inicio, fin, medio, -1, null).crear());
            boolean igual = arr[medio] == objetivo;
            t.paso("igual", "¿arr[" + medio + "] == " + objetivo + "? " + (igual ? "Sí" : "No"),
                    "arr[" + medio + "] = " + arr[medio] + (igual ? " es el objetivo." : " ≠ " + objetivo + "."),
                    escena(arr, inicio, fin, medio, igual ? medio : -1, null).crear());
            if (igual) {
                t.paso("encontrado", "Devolver " + medio, "El objetivo está en el índice " + medio + ".",
                        escena(arr, inicio, fin, medio, medio, null).crear());
                return medio;
            }
            boolean menor = objetivo < arr[medio];
            t.paso("menor", "¿" + objetivo + " < " + arr[medio] + "? " + (menor ? "Sí" : "No"),
                    menor ? "Se descarta la mitad derecha." : "Se descarta la mitad izquierda.",
                    escena(arr, inicio, fin, medio, -1, null).crear());
            if (menor) {
                fin = medio - 1;
                t.var("fin", fin);
                t.paso("izq", "fin = medio − 1 = " + fin, "El nuevo rango es [" + inicio + ", " + fin + "].",
                        escena(arr, inicio, fin, -1, -1, null).crear());
            } else {
                inicio = medio + 1;
                t.var("inicio", inicio);
                t.paso("der", "inicio = medio + 1 = " + inicio, "El nuevo rango es [" + inicio + ", " + fin + "].",
                        escena(arr, inicio, fin, -1, -1, null).crear());
            }
        }
        t.paso("noEsta", "Devolver −1", "Convención para «no encontrado».", escena(arr, inicio, fin, -1, -1, null).crear());
        return -1;
    }

    private static EscenaArreglo.Constructor escena(int[] arr, int inicio, int fin, int medio, int encontrado,
                                                    ArbolLlamadas arbol) {
        EscenaArreglo.Fila f = EscenaArreglo.fila("arr", arr);
        if (inicio >= 0 || fin >= 0) {
            f.rango(inicio, fin);
            if (inicio <= fin) {
                f.puntero(inicio, "inicio").puntero(fin, "fin");
            }
        }
        if (medio >= 0) {
            f.tono(medio, Tono.ACENTO).puntero(medio, "medio");
        }
        if (encontrado >= 0) {
            f.tono(encontrado, Tono.EXITO);
        }
        EscenaArreglo.Constructor c = EscenaArreglo.nueva().linea(f)
                .leyenda(Tono.ACENTO, "medio").leyenda(Tono.ATENUADO, "descartado").leyenda(Tono.EXITO, "encontrado");
        if (arbol != null) {
            c.arbol(arbol.foto());
        }
        return c;
    }
}
