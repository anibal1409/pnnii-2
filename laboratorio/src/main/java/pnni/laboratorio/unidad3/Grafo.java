package pnni.laboratorio.unidad3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pnni.laboratorio.nucleo.Entrada;

/**
 * Grafo ponderado leído del formulario como texto «u-v:peso, …». Guarda las aristas
 * en el orden escrito (Kruskal las ordena después) y ofrece la matriz de
 * adyacencia que usan Prim y Dijkstra, igual que los programas de consola.
 */
final class Grafo {

    static final int SIN_ARISTA = -1;
    private static final Pattern ARISTA = Pattern.compile("(\\d+)\\s*-\\s*(\\d+)\\s*:\\s*(\\d+)");

    private final int n;
    private final boolean dirigido;
    private final int[] u;
    private final int[] v;
    private final int[] peso;

    private Grafo(int n, boolean dirigido, int[] u, int[] v, int[] peso) {
        this.n = n;
        this.dirigido = dirigido;
        this.u = u;
        this.v = v;
        this.peso = peso;
    }

    static Grafo leer(Entrada entrada, String claveN, String claveAristas, boolean dirigido) {
        int n = entrada.entero(claveN, 2, 9);
        String texto = entrada.texto(claveAristas);
        List<int[]> aristas = new ArrayList<>();
        for (String parte : texto.split("[,;\\n]+")) {
            if (parte.isBlank()) {
                continue;
            }
            Matcher m = ARISTA.matcher(parte.trim());
            if (!m.matches()) {
                throw entrada.error(claveAristas, "tiene «" + parte.trim() + "»; cada arista se escribe como u-v:peso");
            }
            int a = Integer.parseInt(m.group(1));
            int b = Integer.parseInt(m.group(2));
            int w = Integer.parseInt(m.group(3));
            if (a >= n || b >= n) {
                throw entrada.error(claveAristas, "usa el vértice " + Math.max(a, b) + ", pero solo existen 0 a " + (n - 1));
            }
            if (a == b) {
                throw entrada.error(claveAristas, "tiene un lazo " + a + "-" + b + "; no se admiten");
            }
            if (w > 999) {
                throw entrada.error(claveAristas, "solo admite pesos entre 0 y 999");
            }
            for (int[] e : aristas) {
                if ((e[0] == a && e[1] == b) || (!dirigido && e[0] == b && e[1] == a)) {
                    throw entrada.error(claveAristas, "repite la arista " + a + "-" + b);
                }
            }
            aristas.add(new int[]{a, b, w});
        }
        if (aristas.isEmpty() || aristas.size() > 20) {
            throw entrada.error(claveAristas, "debe tener entre 1 y 20 aristas");
        }
        int m = aristas.size();
        int[] u = new int[m];
        int[] v = new int[m];
        int[] p = new int[m];
        for (int k = 0; k < m; k++) {
            u[k] = aristas.get(k)[0];
            v[k] = aristas.get(k)[1];
            p[k] = aristas.get(k)[2];
        }
        return new Grafo(n, dirigido, u, v, p);
    }

    int n() {
        return n;
    }

    int m() {
        return peso.length;
    }

    boolean dirigido() {
        return dirigido;
    }

    int u(int k) {
        return u[k];
    }

    int v(int k) {
        return v[k];
    }

    int peso(int k) {
        return peso[k];
    }

    String nombre(int vertice) {
        return String.valueOf(vertice);
    }

    /** Índice de la arista a-b (o b-a si no es dirigido); -1 si no existe. */
    int indice(int a, int b) {
        for (int k = 0; k < peso.length; k++) {
            if ((u[k] == a && v[k] == b) || (!dirigido && u[k] == b && v[k] == a)) {
                return k;
            }
        }
        return -1;
    }

    int[][] matriz() {
        int[][] mat = new int[n][n];
        for (int[] fila : mat) {
            java.util.Arrays.fill(fila, SIN_ARISTA);
        }
        for (int k = 0; k < peso.length; k++) {
            mat[u[k]][v[k]] = peso[k];
            if (!dirigido) {
                mat[v[k]][u[k]] = peso[k];
            }
        }
        return mat;
    }
}
