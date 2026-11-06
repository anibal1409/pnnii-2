package pnni.laboratorio.unidad3;

import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Camino más corto desde un origen único (Dijkstra, matriz de adyacencia). */
public final class PracticaDijkstra implements Practica {

    private static final int INF = Integer.MAX_VALUE;

    private static final String CODIGO = """
            static void dijkstra(int[][] grafo, int origen, int[] dist, int[] prev) {
                for (int v = 0; v < n; v++) { dist[v] = INF; prev[v] = -1; } @@init
                dist[origen] = 0; @@init
                for (int vuelta = 0; vuelta < n; vuelta++) { @@vuelta
                    int u = -1; @@elegir
                    for (int v = 0; v < n; v++)                 // el no visitado más cercano @@elegir
                        if (!visitado[v] && dist[v] != INF && (u == -1 || dist[v] < dist[u])) u = v; @@elegir
                    if (u == -1) return;                        // el resto es inalcanzable @@inalcanzable
                    visitado[u] = true; @@fijar
                    for (int v = 0; v < n; v++) @@relajar
                        if (grafo[u][v] != SIN_ARISTA && !visitado[v]) { @@relajar
                            int nueva = dist[u] + grafo[u][v]; @@nueva
                            if (nueva < dist[v]) { dist[v] = nueva; prev[v] = u; } @@mejora
                        }
                }
            }

            // en main:
            imprimirCamino(prev, destino);                      // sigue prev hacia atrás @@camino
            """;

    @Override
    public Unidad unidad() {
        return Unidad.III;
    }

    @Override
    public String titulo() {
        return "Camino más corto: Dijkstra";
    }

    @Override
    public String descripcion() {
        return "Fija en cada vuelta el vértice no visitado más cercano al origen (elección voraz) y relaja sus aristas: "
                + "si pasar por él acorta el camino a un vecino, actualiza dist y prev. Requiere pesos no negativos.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(V²) con matriz", "O((V+E) log V) con heap");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.texto("n", "Vértices", "5", "Entre 2 y 9 (se numeran desde 0)"),
                Parametro.texto("aristas", "Aristas dirigidas (u-v:peso)", "0-1:4, 0-2:1, 1-3:1, 2-1:2, 2-3:5, 3-4:3",
                        "u-v significa u → v; pesos ≥ 0"),
                Parametro.texto("origen", "Origen", "0", "Vértice de partida"),
                Parametro.texto("destino", "Destino del camino", "4", "Se reconstruye el camino hasta él"));
    }

    @Override
    public String programaConsola() {
        return "Dijkstra.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        Grafo grafo = Grafo.leer(entrada, "n", "aristas", true);
        int n = grafo.n();
        int origen = entrada.entero("origen", 0, n - 1);
        int destino = entrada.entero("destino", 0, n - 1);
        int[][] mat = grafo.matriz();
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visitado = new boolean[n];
        Traza t = new Traza(new Codigo(CODIGO));
        for (int v = 0; v < n; v++) {
            dist[v] = INF;
            prev[v] = -1;
        }
        dist[origen] = 0;
        t.var("origen", origen);
        t.paso("init", "dist[" + origen + "] = 0, el resto ∞",
                "Solo se conoce la distancia del origen a sí mismo.", escena(grafo, dist, prev, visitado, -1, -1).crear());
        for (int vuelta = 0; vuelta < n; vuelta++) {
            t.var("vuelta", vuelta).quitar("v").quitar("nueva");
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!visitado[v] && dist[v] != INF && (u == -1 || dist[v] < dist[u])) {
                    u = v;
                }
            }
            t.var("u", u);
            if (u == -1) {
                t.paso("inalcanzable", "No quedan vértices alcanzables",
                        "Los vértices que siguen en ∞ no tienen camino desde " + origen + ".",
                        escena(grafo, dist, prev, visitado, -1, -1).crear());
                break;
            }
            t.paso("elegir", "Elección voraz: u = " + u + " (dist " + dist[u] + ")",
                    "Es el no visitado más cercano al origen. Como no hay pesos negativos, ningún otro camino puede "
                            + "llegar a " + u + " con menos de " + dist[u] + ".",
                    escena(grafo, dist, prev, visitado, u, -1).crear());
            visitado[u] = true;
            t.paso("fijar", "dist[" + u + "] = " + dist[u] + " es definitiva",
                    "Se marca " + u + " como visitado y se relajan sus aristas de salida.",
                    escena(grafo, dist, prev, visitado, u, -1).crear());
            for (int v = 0; v < n; v++) {
                if (mat[u][v] == Grafo.SIN_ARISTA || visitado[v]) {
                    continue;
                }
                int nueva = dist[u] + mat[u][v];
                t.var("v", v).var("nueva", nueva);
                boolean mejora = nueva < dist[v];
                int arista = grafo.indice(u, v);
                t.paso("nueva", "Relajar " + u + " → " + v + ": " + dist[u] + " + " + mat[u][v] + " = " + nueva,
                        "Distancia a " + v + " pasando por " + u + ".", escena(grafo, dist, prev, visitado, u, arista).crear());
                if (mejora) {
                    String antes = texto(dist[v]);
                    dist[v] = nueva;
                    prev[v] = u;
                    t.paso("mejora", "¿" + nueva + " < " + antes + "? Sí → dist[" + v + "] = " + nueva + ", prev[" + v + "] = " + u,
                            "Se encontró un camino más corto hasta " + v + ".", escena(grafo, dist, prev, visitado, u, arista).crear());
                } else {
                    t.paso("mejora", "¿" + nueva + " < " + dist[v] + "? No",
                            "El camino que ya se conocía hasta " + v + " es igual o más corto.",
                            escena(grafo, dist, prev, visitado, u, arista).crear());
                }
            }
        }
        t.quitar("v").quitar("nueva").quitar("u").quitar("vuelta");
        StringBuilder linea = new StringBuilder("Distancias desde vertice " + origen + ": ");
        for (int v = 0; v < n; v++) {
            linea.append(dist[v] == INF ? "INF" : String.valueOf(dist[v])).append(' ');
        }
        t.imprimirLinea(linea.toString());
        StringBuilder camino = new StringBuilder();
        java.util.List<Integer> ruta = new java.util.ArrayList<>();
        for (int v = destino; v != -1; v = prev[v]) {
            ruta.add(0, v);
        }
        for (int v : ruta) {
            camino.append(v).append(' ');
        }
        t.imprimirLinea("Camino a " + destino + ": " + camino);
        boolean alcanzable = dist[destino] != INF;
        EscenaGrafo.Constructor c = escena(grafo, dist, prev, visitado, -1, -1);
        for (int k = 1; k < ruta.size(); k++) {
            c.arista(grafo.indice(ruta.get(k - 1), ruta.get(k)), Tono.ACENTO);
        }
        t.paso("camino", alcanzable ? "Camino a " + destino + ": " + camino.toString().trim().replace(" ", " → ")
                        + " (dist " + dist[destino] + ")" : destino + " es inalcanzable",
                alcanzable ? "Se reconstruye siguiendo prev desde " + destino + " hasta el origen y se invierte."
                        : "dist[" + destino + "] = ∞: el programa solo imprime el propio destino porque prev[" + destino + "] = −1.",
                c.insignia(alcanzable ? "dist[" + destino + "] = " + dist[destino] : "Inalcanzable", alcanzable)
                        .leyenda(Tono.ACENTO, "camino al destino").crear());
        return t;
    }

    private static String texto(int d) {
        return d == INF ? "∞" : String.valueOf(d);
    }

    private static EscenaGrafo.Constructor escena(Grafo grafo, int[] dist, int[] prev, boolean[] visitado, int u, int actual) {
        EscenaGrafo.Constructor c = EscenaGrafo.de(grafo).tabla("v   dist   prev");
        for (int v = 0; v < grafo.n(); v++) {
            Tono tono = v == u ? Tono.ACENTO : visitado[v] ? Tono.EXITO : dist[v] != INF ? Tono.INFO : Tono.NORMAL;
            c.vertice(v, tono, "d=" + texto(dist[v]));
            if (prev[v] >= 0) {
                c.arista(grafo.indice(prev[v], v), visitado[v] ? Tono.EXITO : Tono.INFO);
            }
            c.fila(String.format("%-3d %-6s %s", v, texto(dist[v]), prev[v] < 0 ? "-" : String.valueOf(prev[v])),
                    v == u ? Tono.ACENTO : visitado[v] ? Tono.EXITO : Tono.NORMAL);
        }
        c.arista(actual, Tono.AVISO);
        return c.leyenda(Tono.EXITO, "definitivo").leyenda(Tono.ACENTO, "elegido").leyenda(Tono.INFO, "tentativo")
                .leyenda(Tono.AVISO, "relajando");
    }
}
