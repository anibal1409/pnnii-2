package pnni.laboratorio.unidad3;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Almacenamiento óptimo en cintas: grabar siempre el archivo más corto que falta. */
public final class PracticaCintas implements Practica {

    private static final String CODIGO = """
            static void ordenarVoraz(int[] cinta) {
                for (int k = 0; k < cinta.length - 1; k++) { @@k
                    int menor = k; @@k
                    for (int j = k + 1; j < cinta.length; j++) @@cmp
                        if (cinta[j] < cinta[menor]) menor = j;      // el más corto restante @@cmp
                    int temp = cinta[k]; cinta[k] = cinta[menor]; cinta[menor] = temp; @@grabar
                }
            }

            static int tiempoTotal(int[] cinta) {
                int acumulado = 0, total = 0; @@t0
                for (int i = 0; i < cinta.length; i++) {
                    acumulado += cinta[i];                           // llegada al archivo i @@tsuma
                    total += acumulado; @@tsuma
                }
                return total; @@tret
            }
            """;

    @Override
    public Unidad unidad() {
        return Unidad.III;
    }

    @Override
    public String titulo() {
        return "Almacenamiento en cintas";
    }

    @Override
    public String descripcion() {
        return "Para leer un archivo de la cinta hay que pasar por todos los anteriores. Para minimizar la suma de los "
                + "tiempos de llegada, la elección voraz graba en cada posición el archivo más corto de los que faltan: "
                + "T = n·L₁ + (n−1)·L₂ + … + 1·Lₙ.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(n log n) ordenando", "O(n²) con selección");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(Parametro.texto("longitudes", "Longitudes", "15, 3, 8, 20, 5", "Hasta 10 enteros positivos"));
    }

    @Override
    public String programaConsola() {
        return "AlmacenamientoCintas.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        int[] longitudes = entrada.enteros("longitudes", 1, 10, 1, 999);
        int n = longitudes.length;
        Traza t = new Traza(new Codigo(CODIGO));
        int original = tiempo(longitudes, n);
        t.imprimirLinea("Longitudes originales: " + texto(longitudes));
        t.imprimirLinea("Tiempo total en el orden original: " + original);
        String refOriginal = "En el orden de llegada: T = " + original;
        t.paso("k", "Orden de llegada: T = " + original,
                "Si se grabaran tal como llegaron, la suma de los tiempos de llegada sería " + suma(longitudes, n) + ".",
                EscenaCinta.de(longitudes).llegadas(n, suma(longitudes, n)).crear());

        int[] cinta = longitudes.clone();
        for (int k = 0; k < n - 1; k++) {
            int menor = k;
            t.var("k", k).var("menor", menor).quitar("j");
            t.paso("k", "Posición " + k + ": buscar el más corto entre los restantes",
                    "Las posiciones 0.." + (k - 1) + " ya están grabadas; se empieza suponiendo que el más corto es cinta[" + k + "] = "
                            + cinta[k] + ".",
                    vista(cinta, k, menor, -1).referencia(refOriginal).crear());
            for (int j = k + 1; j < n; j++) {
                boolean mas = cinta[j] < cinta[menor];
                t.var("j", j);
                t.paso("cmp", "¿cinta[" + j + "] = " + cinta[j] + " < cinta[" + menor + "] = " + cinta[menor] + "? " + (mas ? "Sí" : "No"),
                        mas ? cinta[j] + " es más corto: pasa a ser el candidato." : "El candidato sigue siendo " + cinta[menor] + ".",
                        vista(cinta, k, menor, j).referencia(refOriginal).crear());
                if (mas) {
                    menor = j;
                    t.var("menor", menor);
                }
            }
            int temp = cinta[k];
            cinta[k] = cinta[menor];
            cinta[menor] = temp;
            t.quitar("j");
            t.paso("grabar", "Grabar " + cinta[k] + " en la posición " + k,
                    "Decisión voraz e irreversible: el archivo más corto que faltaba (" + cinta[k] + ") va en la posición " + k
                            + ", porque contará en el tiempo de llegada de todos los que vengan detrás.",
                    vista(cinta, k + 1, -1, -1).tono(k, Tono.ACENTO).referencia(refOriginal).crear());
        }
        t.quitar("k").quitar("menor");
        t.imprimirLinea("Orden optimo (menor a mayor): " + texto(cinta));
        t.var("acumulado", 0).var("total", 0);
        t.paso("t0", "Calcular el tiempo total del orden óptimo", "acumulado = 0, total = 0.",
                EscenaCinta.de(cinta).tonos(0, n - 1, Tono.EXITO).referencia(refOriginal).crear());
        int acumulado = 0;
        int total = 0;
        for (int i = 0; i < n; i++) {
            acumulado += cinta[i];
            total += acumulado;
            t.var("i", i).var("acumulado", acumulado).var("total", total);
            t.imprimirLinea("  Archivo " + cinta[i] + ": acceso = " + acumulado + ", aporta " + (n - i) + " x " + cinta[i]
                    + " = " + ((n - i) * cinta[i]));
            t.paso("tsuma", "Archivo " + cinta[i] + ": llega en " + acumulado,
                    "Para leerlo se recorren " + (i == 0 ? "solo él" : "los " + i + " anteriores y él") + ". En el total, "
                            + cinta[i] + " se cuenta " + (n - i) + " veces (él y los que le siguen).",
                    EscenaCinta.de(cinta).tonos(0, n - 1, Tono.EXITO).tono(i, Tono.ACENTO).puntero(i, "i")
                            .llegadas(i + 1, suma(cinta, i + 1)).referencia(refOriginal).crear());
        }
        t.quitar("i");
        t.imprimirLinea("Tiempo total minimo: " + total);
        t.paso("tret", "Tiempo total mínimo: " + total + " ✓",
                "El orden creciente da " + total + " frente a " + original + " del orden de llegada."
                        + (original > total ? " Ahorro: " + (original - total) + "." : ""),
                EscenaCinta.de(cinta).tonos(0, n - 1, Tono.EXITO).llegadas(n, suma(cinta, n)).referencia(refOriginal)
                        .insignia("T mínimo = " + total, true).crear());
        return t;
    }

    private static EscenaCinta.Constructor vista(int[] cinta, int grabados, int menor, int j) {
        EscenaCinta.Constructor c = EscenaCinta.de(cinta).tonos(0, grabados - 1, Tono.EXITO);
        if (menor >= 0) {
            c.tono(menor, Tono.ACENTO).puntero(menor, "menor");
        }
        if (j >= 0) {
            c.tono(j, Tono.AVISO).puntero(j, "j");
        }
        if (grabados < cinta.length) {
            c.puntero(grabados, "k");
        }
        return c;
    }

    private static int tiempo(int[] cinta, int cantidad) {
        int acumulado = 0;
        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            acumulado += cinta[i];
            total += acumulado;
        }
        return total;
    }

    /** «3 + 8 + 16 = 27»: los tiempos de llegada de los primeros archivos y su suma. */
    private static String suma(int[] cinta, int cantidad) {
        StringBuilder sb = new StringBuilder();
        int acumulado = 0;
        for (int i = 0; i < cantidad; i++) {
            acumulado += cinta[i];
            sb.append(i == 0 ? "" : " + ").append(acumulado);
        }
        return sb.append(" = ").append(tiempo(cinta, cantidad)).toString();
    }

    private static String texto(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int v : arr) {
            sb.append(v).append(' ');
        }
        return sb.toString();
    }
}
