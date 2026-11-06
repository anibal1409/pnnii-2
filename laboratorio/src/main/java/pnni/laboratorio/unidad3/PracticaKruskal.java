package pnni.laboratorio.unidad3;

import java.util.Arrays;
import java.util.List;

import pnni.laboratorio.nucleo.Codigo;
import pnni.laboratorio.nucleo.Entrada;
import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Traza;
import pnni.laboratorio.nucleo.Unidad;
import pnni.laboratorio.ui.Tono;

/** Árbol de costo mínimo con Kruskal y union-búsqueda en arreglos. */
public final class PracticaKruskal implements Practica {

    private static final String CODIGO = """
            static int kruskal(int n, int[] u, int[] v, int[] peso) {
                ordenarPorPeso(u, v, peso);               // inserción, estable @@ordenar
                for (int x = 0; x < n; x++) padre[x] = x; @@init
                for (int k = 0; k < m && aceptadas < n - 1; k++) { @@for
                    int ru = buscar(padre, u[k]); @@buscar
                    int rv = buscar(padre, v[k]); @@buscar
                    if (ru != rv) { @@si
                        if (rango[ru] < rango[rv]) padre[ru] = rv; @@unir
                        else if (rango[ru] > rango[rv]) padre[rv] = ru; @@unir
                        else { padre[rv] = ru; rango[ru]++; } @@unir
                        total += peso[k]; aceptadas++; @@unir
                    } else {
                        // u y v ya están conectados: formaría ciclo @@descartar
                    }
                }
                return total; @@fin
            }

            static int buscar(int[] padre, int x) {        // con compresión de camino
                if (padre[x] != x) padre[x] = buscar(padre, padre[x]);
                return padre[x];
            }
            """;

    @Override
    public Unidad unidad() {
        return Unidad.III;
    }

    @Override
    public String titulo() {
        return "Árbol de costo mínimo: Kruskal";
    }

    @Override
    public String descripcion() {
        return "Recorre las aristas de menor a mayor peso y acepta cada una si une dos componentes distintas (no forma "
                + "ciclo). Las componentes se controlan con union-búsqueda: padre[] con compresión de camino y unión por rango.";
    }

    @Override
    public List<String> complejidad() {
        return List.of("O(E log E)", "Union-find ≈ O(1)");
    }

    @Override
    public List<Parametro> parametros() {
        return List.of(
                Parametro.texto("n", "Vértices", "4", "Entre 2 y 9 (se numeran desde 0)"),
                Parametro.texto("aristas", "Aristas (u-v:peso)", "0-1:1, 0-2:3, 1-2:1, 1-3:4, 2-3:2",
                        "No dirigidas, separadas por coma"));
    }

    @Override
    public String programaConsola() {
        return "KruskalMST.java";
    }

    @Override
    public Traza ejecutar(Entrada entrada) {
        Grafo grafo = Grafo.leer(entrada, "n", "aristas", false);
        int n = grafo.n();
        int m = grafo.m();
        Traza t = new Traza(new Codigo(CODIGO));
        // orden[k] = arista original en la posición k tras ordenar (inserción estable, como en consola)
        int[] orden = new int[m];
        for (int k = 0; k < m; k++) {
            orden[k] = k;
        }
        for (int k = 1; k < m; k++) {
            int e = orden[k];
            int j = k - 1;
            while (j >= 0 && grafo.peso(orden[j]) > grafo.peso(e)) {
                orden[j + 1] = orden[j];
                j--;
            }
            orden[j + 1] = e;
        }
        int[] estado = new int[m];
        int[] padre = new int[n];
        int[] rango = new int[n];
        for (int x = 0; x < n; x++) {
            padre[x] = x;
        }
        t.paso("ordenar", "Ordenar las aristas por peso", "Kruskal examina primero las aristas más baratas.",
                escena(grafo, orden, estado, padre, -1).crear());
        t.var("padre", Arrays.toString(padre)).var("total", 0);
        t.paso("init", "Cada vértice es su propio conjunto", "padre[x] = x: hay " + n + " componentes separadas.",
                escena(grafo, orden, estado, padre, -1).crear());
        int total = 0;
        int aceptadas = 0;
        for (int k = 0; k < m && aceptadas < n - 1; k++) {
            int e = orden[k];
            int a = grafo.u(e);
            int b = grafo.v(e);
            t.var("k", k).var("arista", a + "-" + b + " (" + grafo.peso(e) + ")");
            t.paso("for", "Siguiente arista: " + a + "-" + b + " (peso " + grafo.peso(e) + ")",
                    "Es la más barata que queda por examinar.", escena(grafo, orden, estado, padre, k).crear());
            int ru = buscar(padre, a);
            int rv = buscar(padre, b);
            t.var("ru", ru).var("rv", rv).var("padre", Arrays.toString(padre));
            t.paso("buscar", "buscar(" + a + ") = " + ru + ", buscar(" + b + ") = " + rv,
                    "Representantes de las componentes de " + a + " y " + b + " (la búsqueda comprime el camino).",
                    escena(grafo, orden, estado, padre, k).crear());
            if (ru != rv) {
                if (rango[ru] < rango[rv]) {
                    padre[ru] = rv;
                } else if (rango[ru] > rango[rv]) {
                    padre[rv] = ru;
                } else {
                    padre[rv] = ru;
                    rango[ru]++;
                }
                total += grafo.peso(e);
                aceptadas++;
                estado[k] = 1;
                t.var("padre", Arrays.toString(padre)).var("total", total);
                t.imprimirLinea("Acepta " + a + "-" + b + " (peso " + grafo.peso(e) + ")");
                t.paso("unir", "Aceptar " + a + "-" + b + ": une dos componentes",
                        ru + " ≠ " + rv + ", así que no hay ciclo. Se unen por rango; total = " + total + ".",
                        escena(grafo, orden, estado, padre, k).crear());
            } else {
                estado[k] = 2;
                t.imprimirLinea("Descarta " + a + "-" + b + " (peso " + grafo.peso(e) + "): forma ciclo");
                t.paso("descartar", "Descartar " + a + "-" + b + ": formaría un ciclo",
                        a + " y " + b + " ya están en la misma componente (representante " + ru + ").",
                        escena(grafo, orden, estado, padre, k).crear());
            }
        }
        t.quitar("k").quitar("arista").quitar("ru").quitar("rv");
        t.imprimirLinea("Peso total MST (Kruskal): " + total);
        boolean conexo = aceptadas == n - 1;
        t.paso("fin", conexo ? "Árbol de costo mínimo: peso " + total + " ✓" : "El grafo no es conexo",
                conexo ? "Se aceptaron " + aceptadas + " = V − 1 aristas." : "Solo se pudieron aceptar " + aceptadas
                        + " aristas: resulta un bosque, no un árbol.",
                escena(grafo, orden, estado, padre, -1).insignia(conexo ? "Peso total = " + total : "No es conexo", conexo)
                        .crear());
        return t;
    }

    private static int buscar(int[] padre, int x) {
        if (padre[x] != x) {
            padre[x] = buscar(padre, padre[x]);
        }
        return padre[x];
    }

    /** estado: 0 pendiente, 1 aceptada, 2 descartada. */
    private static EscenaGrafo.Constructor escena(Grafo grafo, int[] orden, int[] estado, int[] padre, int actual) {
        EscenaGrafo.Constructor c = EscenaGrafo.de(grafo).tabla("ARISTAS ORDENADAS");
        for (int v = 0; v < grafo.n(); v++) {
            c.vertice(v, Tono.NORMAL, "conj. " + raiz(padre, v));
        }
        for (int k = 0; k < orden.length; k++) {
            int e = orden[k];
            Tono tono = k == actual ? Tono.AVISO : estado[k] == 1 ? Tono.EXITO : estado[k] == 2 ? Tono.ATENUADO : Tono.NORMAL;
            c.arista(e, tono);
            String marca = estado[k] == 1 ? "✓" : estado[k] == 2 ? "✗ ciclo" : k == actual ? "←" : "";
            c.fila(String.format("%d-%d  %3d  %s", grafo.u(e), grafo.v(e), grafo.peso(e), marca),
                    k == actual ? Tono.AVISO : estado[k] == 1 ? Tono.EXITO : estado[k] == 2 ? Tono.ATENUADO : Tono.NORMAL);
        }
        c.fila("padre = " + Arrays.toString(padre), Tono.NORMAL);
        if (actual >= 0) {
            int e = orden[actual];
            c.vertice(grafo.u(e), Tono.AVISO, "conj. " + raiz(padre, grafo.u(e)))
                    .vertice(grafo.v(e), Tono.AVISO, "conj. " + raiz(padre, grafo.v(e)));
        }
        return c.leyenda(Tono.EXITO, "aceptada").leyenda(Tono.AVISO, "examinando").leyenda(Tono.ATENUADO, "descartada");
    }

    private static int raiz(int[] padre, int x) {
        while (padre[x] != x) {
            x = padre[x];
        }
        return x;
    }
}
