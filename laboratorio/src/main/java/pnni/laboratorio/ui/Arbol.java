package pnni.laboratorio.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Dibuja un bosque de árboles con raíz (árbol de llamadas recursivas, árbol de
 * fusiones de Huffman...). Las hojas se reparten a lo ancho y cada nodo interno
 * queda centrado sobre sus hijos.
 */
public final class Arbol {

    /** Nodo inmutable; {@code padre} es el índice del padre en la lista, o -1 en las raíces. */
    public record Nodo(String texto, String detalle, int padre, Tono tono) {
    }

    private Arbol() {
    }

    public static void pintar(Graphics2D g, List<Nodo> nodos, double x0, double y0, double ancho, double alto, Paleta p) {
        int n = nodos.size();
        if (n == 0) {
            return;
        }
        List<List<Integer>> hijos = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            hijos.add(new ArrayList<>());
        }
        List<Integer> raices = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            int padre = nodos.get(k).padre();
            if (padre < 0) {
                raices.add(k);
            } else {
                hijos.get(padre).add(k);
            }
        }
        int[] nivel = new int[n];
        int niveles = 1;
        for (int r : raices) {
            niveles = Math.max(niveles, asignarNivel(r, 0, hijos, nivel));
        }
        int[] hojas = new int[n];
        int totalHojas = 0;
        for (int r : raices) {
            totalHojas += contarHojas(r, hijos, hojas);
        }
        double[] x = new double[n];
        double paso = ancho / Math.max(1, totalHojas);
        double cursor = x0;
        for (int r : raices) {
            cursor = ubicar(r, hijos, hojas, x, cursor, paso);
        }

        double separacion = Math.min(64, alto / niveles);
        double altoCaja = Math.min(26, separacion * 0.55);
        double yInicio = y0 + Math.max(0, (alto - separacion * niveles) / 2) + altoCaja / 2;
        float tamano = (float) Math.max(10.5, Math.min(12.5, paso / 5.2));
        Font fuente = Tema.mono(tamano);
        Font fuenteDetalle = Tema.fuente(Font.BOLD, Math.max(10.5f, tamano - 1f));

        g.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int k = 0; k < n; k++) {
            int padre = nodos.get(k).padre();
            if (padre >= 0) {
                Tono t = nodos.get(k).tono();
                g.setColor(t == Tono.NORMAL || t == Tono.ATENUADO ? p.borde() : Paleta.alfa(t.color(p), 0.7));
                g.draw(new Line2D.Double(x[padre], yInicio + nivel[padre] * separacion + altoCaja / 2,
                        x[k], yInicio + nivel[k] * separacion - altoCaja / 2));
            }
        }
        for (int k = 0; k < n; k++) {
            Nodo nodo = nodos.get(k);
            double cy = yInicio + nivel[k] * separacion;
            g.setFont(fuente);
            FontMetrics fm = g.getFontMetrics();
            double w = Math.min(Math.max(fm.stringWidth(nodo.texto()) + 14, 30), Math.max(30, paso * 0.95 * hojas[k]));
            RoundRectangle2D caja = new RoundRectangle2D.Double(x[k] - w / 2, cy - altoCaja / 2, w, altoCaja, 10, 10);
            Color texto = Dibujo.forma(g, caja, nodo.tono(), p);
            g.setColor(texto);
            Dibujo.centrar(g, Dibujo.recortar(g, nodo.texto(), w - 6), x[k], cy);
            if (nodo.detalle() != null && !nodo.detalle().isEmpty()) {
                g.setFont(fuenteDetalle);
                g.setColor(nodo.tono() == Tono.NORMAL ? p.textoSuave() : nodo.tono().color(p));
                double espacio = Math.max(paso * hojas[k], w) + 4;
                String detalle = Dibujo.recortar(g, nodo.detalle(), espacio);
                double wd = g.getFontMetrics().stringWidth(detalle) + 6;
                Color color = g.getColor();
                g.setColor(p.superficie());
                g.fill(new RoundRectangle2D.Double(x[k] - wd / 2, cy + altoCaja / 2 + 2, wd, 14, 6, 6));
                g.setColor(color);
                Dibujo.centrar(g, detalle, x[k], cy + altoCaja / 2 + 9);
            }
        }
    }

    /** Devuelve el número de niveles del subárbol. */
    private static int asignarNivel(int k, int n, List<List<Integer>> hijos, int[] nivel) {
        nivel[k] = n;
        int maximo = n + 1;
        for (int h : hijos.get(k)) {
            maximo = Math.max(maximo, asignarNivel(h, n + 1, hijos, nivel));
        }
        return maximo;
    }

    private static int contarHojas(int k, List<List<Integer>> hijos, int[] hojas) {
        if (hijos.get(k).isEmpty()) {
            hojas[k] = 1;
        } else {
            for (int h : hijos.get(k)) {
                hojas[k] += contarHojas(h, hijos, hojas);
            }
        }
        return hojas[k];
    }

    private static double ubicar(int k, List<List<Integer>> hijos, int[] hojas, double[] x, double cursor, double paso) {
        if (hijos.get(k).isEmpty()) {
            x[k] = cursor + paso / 2;
            return cursor + paso;
        }
        double c = cursor;
        for (int h : hijos.get(k)) {
            c = ubicar(h, hijos, hojas, x, c, paso);
        }
        List<Integer> hs = hijos.get(k);
        x[k] = (x[hs.get(0)] + x[hs.get(hs.size() - 1)]) / 2;
        return c;
    }
}
