package pnni.laboratorio.unidad3;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Árbol de costo mínimo con Prim (variante con matriz de adyacencia, O(V²)). */
public final class PracticaPrim implements Practica {

    private static final int INF = Integer.MAX_VALUE;

    private static final String CODIGO = """
            static int prim(int[][] grafo, int inicio) {
                for (int v = 0; v < n; v++) { costo[v] = INF; padre[v] = -1; } @@init
                costo[inicio] = 0; @@init
                for (int vuelta = 0; vuelta < n; vuelta++) { @@vuelta
                    int u = -1; @@elegir
                    for (int v = 0; v < n; v++)                 // el más barato fuera @@elegir
                        if (!enArbol[v] && costo[v] != INF && (u == -1 || costo[v] < costo[u])) u = v; @@elegir
                    if (u == -1) return pesoTotal;              // no es conexo @@noConexo
                    enArbol[u] = true; @@agregar
                    if (padre[u] != -1) pesoTotal += costo[u]; @@agregar
                    for (int v = 0; v < n; v++) @@vecinos
                        if (grafo[u][v] != SIN_ARISTA && !enArbol[v] && grafo[u][v] < costo[v]) { @@cmp
                            costo[v] = grafo[u][v]; @@actualizar
                            padre[v] = u; @@actualizar
                        }
                }
                return pesoTotal; @@fin
            }
            """;

    @Override
    public Unidad unidad() {
        return Unidad.III;
    }

    @Override
    public String titulo() {
        return "Árbol de costo mínimo: Prim";
    }

    @Override
    public String descripcion() {
        return "Hace crecer un árbol desde un vértice: en cada vuelta agrega el vértice de fuera unido al árbol por la "
                + "arista más barata (elección voraz) y actualiza el costo de sus vecinos. Prueba los 7 barrios del "
                + "caso 7.2: 0-1:4, 0-2:3, 1-3:5, 1-4:6, 2-4:5, 3-5:2, 4-6:4, 5-6:3.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(V²) con matriz", "O((V+E) log V) con heap");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.texto("n", "Vértices", "4", "Entre 2 y 9 (se numeran desde 0)"),
                Parametro.texto("aristas", "Aristas (u-v:peso)", "0-1:1, 0-2:3, 1-2:1, 1-3:4, 2-3:2",
                        "No dirigidas, separadas por coma"),
                Parametro.texto("inicio", "Vértice inicial", "0", "Desde dónde crece el árbol"));
    }

    @Override
    public String programaConsola() {
        return "PrimMST.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        Grafo grafo = Grafo.leer(entrada, "n", "aristas", false);
        int n = grafo.n();
        int inicio = entrada.entero("inicio", 0, n - 1);
        int[][] mat = grafo.matriz();
        boolean[] enArbol = new boolean[n];
        int[] costo = new int[n];
        int[] padre = new int[n];
        Traza t = new Traza(new Codigo(CODIGO));
        for (int v = 0; v < n; v++) {
            costo[v] = INF;
            padre[v] = -1;
        }
        costo[inicio] = 0;
        t.var("inicio", inicio).var("pesoTotal", 0);
        t.paso("init", "Inicializar: costo[" + inicio + "] = 0, el resto ∞",
                "Ningún vértice está en el árbol. El inicial entra primero porque su costo es 0.",
                escena(grafo, enArbol, costo, padre, -1, -1).crear());
        int pesoTotal = 0;
        for (int vuelta = 0; vuelta < n; vuelta++) {
            t.var("vuelta", vuelta).quitar("v");
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!enArbol[v] && costo[v] != INF && (u == -1 || costo[v] < costo[u])) {
                    u = v;
                }
            }
            t.var("u", u);
            if (u == -1) {
                t.imprimirLinea("El grafo no es conexo");
                t.paso("noConexo", "Ningún vértice alcanzable fuera del árbol",
                        "Quedan vértices con costo ∞: no hay aristas que los unan al árbol, el grafo no es conexo.",
                        escena(grafo, enArbol, costo, padre, -1, -1).insignia("No es conexo", false).crear());
                return t;
            }
            t.paso("elegir", "Elección voraz: u = " + u + (padre[u] == -1 ? "" : " (costo " + costo[u] + ")"),
                    padre[u] == -1 ? "El vértice inicial no tiene arista: solo inicia el árbol."
                            : "De los vértices fuera del árbol, " + u + " tiene la arista más barata hacia él: "
                            + padre[u] + "-" + u + " con peso " + costo[u] + ".",
                    escena(grafo, enArbol, costo, padre, u, -1).crear());
            enArbol[u] = true;
            if (padre[u] != -1) {
                pesoTotal += costo[u];
                t.imprimirLinea("Arista " + padre[u] + "-" + u + " (peso " + costo[u] + ")");
            }
            t.var("pesoTotal", pesoTotal);
            t.paso("agregar", padre[u] == -1 ? "Agregar " + u + " al árbol" : "Agregar " + padre[u] + "-" + u + " al árbol",
                    "Decisión irreversible: " + u + " entra al árbol. Peso acumulado = " + pesoTotal + ".",
                    escena(grafo, enArbol, costo, padre, u, -1).crear());
            for (int v = 0; v < n; v++) {
                if (mat[u][v] == Grafo.SIN_ARISTA || enArbol[v]) {
                    continue;
                }
                t.var("v", v);
                boolean mejora = mat[u][v] < costo[v];
                t.paso("cmp", "¿" + u + "-" + v + " (" + mat[u][v] + ") < costo[" + v + "] (" + texto(costo[v]) + ")? "
                                + (mejora ? "Sí" : "No"),
                        mejora ? "La arista " + u + "-" + v + " es la forma más barata conocida de unir " + v + " al árbol."
                                : v + " ya tiene una arista más barata hacia el árbol.",
                        escena(grafo, enArbol, costo, padre, u, grafo.indice(u, v)).crear());
                if (mejora) {
                    costo[v] = mat[u][v];
                    padre[v] = u;
                    t.paso("actualizar", "costo[" + v + "] = " + costo[v] + ", padre[" + v + "] = " + u,
                            "Nueva arista candidata para " + v + ".",
                            escena(grafo, enArbol, costo, padre, u, grafo.indice(u, v)).crear());
                }
            }
        }
        t.quitar("v").quitar("u").quitar("vuelta");
        t.imprimirLinea("Peso total MST (Prim): " + pesoTotal);
        t.paso("fin", "Árbol de costo mínimo: peso " + pesoTotal + " ✓",
                "Todos los vértices están conectados con " + (n - 1) + " aristas y sin ciclos.",
                escena(grafo, enArbol, costo, padre, -1, -1).insignia("Peso total = " + pesoTotal, true).crear());
        return t;
    }

    private static String texto(int c) {
        return c == INF ? "∞" : String.valueOf(c);
    }

    private static EscenaGrafo.Constructor escena(Grafo grafo, boolean[] enArbol, int[] costo, int[] padre,
                                                  int u, int actual) {
        EscenaGrafo.Constructor c = EscenaGrafo.de(grafo).tabla("v   costo   padre");
        for (int v = 0; v < grafo.n(); v++) {
            Tono tono = v == u ? Tono.ACENTO : enArbol[v] ? Tono.EXITO : costo[v] != INF ? Tono.INFO : Tono.NORMAL;
            c.vertice(v, tono, enArbol[v] ? null : texto(costo[v]));
            if (padre[v] >= 0) {
                c.arista(grafo.indice(padre[v], v), enArbol[v] ? Tono.EXITO : Tono.INFO);
            }
            c.fila(String.format("%-3d %-7s %s", v, texto(costo[v]), padre[v] < 0 ? "-" : String.valueOf(padre[v])),
                    v == u ? Tono.ACENTO : enArbol[v] ? Tono.EXITO : Tono.NORMAL);
        }
        c.arista(actual, Tono.AVISO);
        return c.leyenda(Tono.EXITO, "en el árbol").leyenda(Tono.ACENTO, "elegido").leyenda(Tono.INFO, "candidata")
                .leyenda(Tono.AVISO, "comparando");
    }
}
