package pnni.laboratorio.unidad3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pnni.laboratorio.nucleo.Escena;
import pnni.laboratorio.ui.Arbol;
import pnni.laboratorio.ui.Dibujo;
import pnni.laboratorio.ui.Paleta;
import pnni.laboratorio.ui.Tema;
import pnni.laboratorio.ui.Tono;

/**
 * Intercalación óptima: arriba, el bosque de fusiones (cada nodo interno es una fusión
 * y su valor el costo); abajo, el Min-Heap en arreglo con los archivos pendientes.
 */
final class EscenaFusion implements Escena {

    private final List<Arbol.Nodo> bosque;
    private final int[] heap;
    private final Tono[] tonosHeap;
    private final String costo;
    private final String nota;
    private final String insignia;

    EscenaFusion(List<Arbol.Nodo> bosque, int[] heap, int n, int resaltado, String costo, String nota, String insignia) {
        this.bosque = List.copyOf(bosque);
        this.heap = Arrays.copyOf(heap, n);
        this.tonosHeap = new Tono[n];
        Arrays.fill(tonosHeap, Tono.NORMAL);
        if (resaltado >= 0 && resaltado < n) {
            tonosHeap[resaltado] = Tono.ACENTO;
        }
        this.costo = costo;
        this.nota = nota;
        this.insignia = insignia;
    }

    @Override
    public void pintar(Graphics2D g, int ancho, int alto, double t, Paleta p) {
        Dibujo.etiquetas(g, ancho, nota, insignia, true, p);
        int altoHeap = 104;
        int arriba = 40;
        int abajoArbol = alto - altoHeap - 30;
        Dibujo.rotulo(g, "ÁRBOL DE FUSIONES (cada nodo interno = una fusión, su valor = su costo)", 12, arriba + 6, p);
        Arbol.pintar(g, bosque, 12, arriba + 14, ancho - 24, abajoArbol - arriba - 14, p);

        double y = abajoArbol + 8;
        Dibujo.rotulo(g, "COLA DE PRIORIDAD (MIN-HEAP EN ARREGLO)", 14, y + 10, p);
        g.setFont(Tema.fuente(Font.BOLD, 13f));
        g.setColor(p.exito());
        String texto = "costo acumulado = " + costo;
        g.drawString(texto, (float) (ancho - 14 - g.getFontMetrics().stringWidth(texto)), (float) (y + 10));
        double lado = Math.min(50, (ancho - 40) / Math.max(1.0, heap.length));
        double x0 = (ancho - lado * heap.length) / 2;
        for (int k = 0; k < heap.length; k++) {
            RoundRectangle2D celda = new RoundRectangle2D.Double(x0 + k * lado + 2, y + 22, lado - 4, 40, 9, 9);
            Color c = Dibujo.forma(g, celda, tonosHeap[k], p);
            g.setFont(Tema.fuente(Font.BOLD, 13.5f));
            g.setColor(c);
            Dibujo.centrar(g, String.valueOf(heap[k]), celda.getCenterX(), celda.getCenterY());
            g.setFont(Tema.mono(10.5f));
            g.setColor(p.textoSuave());
            Dibujo.centrar(g, String.valueOf(k), x0 + k * lado + lado / 2, y + 74);
        }
        if (heap.length == 0) {
            g.setFont(Tema.fuente(Font.PLAIN, 12f));
            g.setColor(p.textoSuave());
            Dibujo.centrar(g, "(vacío)", ancho / 2.0, y + 42);
        }
        Map<Tono, String> leyenda = new LinkedHashMap<>();
        leyenda.put(Tono.AVISO, "extraídos (los dos menores)");
        leyenda.put(Tono.ACENTO, "fusión nueva");
        leyenda.put(Tono.INFO, "ya fusionado");
        leyenda.put(Tono.EXITO, "archivo final");
        Dibujo.leyenda(g, ancho, alto - 8, leyenda, p);
    }
}
