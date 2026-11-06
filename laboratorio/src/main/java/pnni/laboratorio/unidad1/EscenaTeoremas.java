package pnni.laboratorio.unidad1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import pnni.laboratorio.nucleo.Escena;
import pnni.laboratorio.ui.Paleta;
import pnni.laboratorio.ui.Tema;

/**
 * Árbol binario lleno de altura h: los niveles ya sumados en verde, el nivel en
 * curso resaltado y, a la derecha, el conteo 2^i de cada nivel (Teorema I).
 * Abajo se muestra la suma acumulada y la fórmula del Teorema II.
 */
record EscenaTeoremas(int altura, int nivelActual, int suma, boolean mostrarFormula) implements Escena {

    @Override
    public void pintar(Graphics2D g, int ancho, int alto, double t, Paleta p) {
        int anchoTabla = ancho > 560 ? 190 : 140;
        int anchoArbol = ancho - anchoTabla - 20;
        int arriba = 26;
        int abajo = alto - 110;
        double separacion = (abajo - arriba) / (double) Math.max(1, altura + 1);
        double radio = Math.max(4, Math.min(15, Math.min(separacion * 0.3, anchoArbol / ((1 << altura) * 2.6))));
        double y0 = arriba + separacion / 2;

        g.setStroke(new BasicStroke(1.6f));
        for (int nivel = 1; nivel <= altura; nivel++) {
            int nodos = 1 << nivel;
            for (int k = 0; k < nodos; k++) {
                double x = x(nivel, k, anchoArbol);
                double xp = x(nivel - 1, k / 2, anchoArbol);
                g.setColor(estado(nivel) > 0 ? p.borde() : Paleta.alfa(p.borde(), 0.5));
                g.draw(new Line2D.Double(xp, y0 + (nivel - 1) * separacion, x, y0 + nivel * separacion));
            }
        }
        for (int nivel = 0; nivel <= altura; nivel++) {
            int nodos = 1 << nivel;
            double y = y0 + nivel * separacion;
            int estado = estado(nivel);
            Color color = estado == 2 ? p.acento() : estado == 1 ? p.exito() : p.borde();
            double pulso = estado == 2 ? Math.sin(Math.PI * t) * 2 : 0;
            for (int k = 0; k < nodos; k++) {
                double x = x(nivel, k, anchoArbol);
                double r = radio + pulso;
                Ellipse2D c = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
                if (estado == 0) {
                    g.setColor(p.superficieAlta());
                    g.fill(c);
                    g.setColor(p.borde());
                    g.draw(c);
                } else {
                    g.setColor(estado == 2 ? color : Paleta.mezclar(p.superficie(), color, 0.35));
                    g.fill(c);
                    g.setColor(color);
                    g.draw(c);
                }
            }
            pintarFila(g, nivel, y, anchoArbol + 20, anchoTabla, estado, p);
        }
        pintarResumen(g, ancho, alto - 92, p);
    }

    /** 0 = pendiente, 1 = ya sumado, 2 = nivel en curso. */
    private int estado(int nivel) {
        if (mostrarFormula || nivel < nivelActual) {
            return 1;
        }
        return nivel == nivelActual ? 2 : 0;
    }

    private double x(int nivel, int k, int anchoArbol) {
        int nodos = 1 << nivel;
        return 20 + (k + 0.5) * (anchoArbol - 20) / nodos;
    }

    private void pintarFila(Graphics2D g, int nivel, double y, int x, int ancho, int estado, Paleta p) {
        RoundRectangle2D fondo = new RoundRectangle2D.Double(x, y - 14, ancho - 4, 28, 10, 10);
        if (estado == 2) {
            g.setColor(Paleta.alfa(p.acento(), p.oscura() ? 0.2 : 0.12));
            g.fill(fondo);
        }
        g.setFont(Tema.mono(12.5f));
        g.setColor(estado == 0 ? Paleta.alfa(p.textoSuave(), 0.6) : estado == 2 ? p.acento() : p.texto());
        g.drawString("Nivel " + nivel, x + 10, (float) y + 5);
        String conteo = "2^" + nivel + " = " + (1 << nivel);
        g.setFont(Tema.fuente(Font.BOLD, 12.5f));
        g.drawString(conteo, (float) (x + ancho - 14 - g.getFontMetrics().stringWidth(conteo)), (float) y + 5);
    }

    private void pintarResumen(Graphics2D g, int ancho, int y, Paleta p) {
        RoundRectangle2D caja = new RoundRectangle2D.Double(8, y, ancho - 16, 80, 14, 14);
        g.setColor(p.superficieAlta());
        g.fill(caja);
        g.setFont(Tema.fuente(Font.BOLD, 11f));
        g.setColor(p.textoSuave());
        g.drawString("SUMA POR NIVELES (TEOREMA I)", 24, y + 22);
        g.drawString("FÓRMULA (TEOREMA II)", ancho / 2 + 10, y + 22);

        StringBuilder terminos = new StringBuilder();
        int ultimo = mostrarFormula ? altura : nivelActual;
        for (int n = 0; n <= ultimo && n <= altura; n++) {
            terminos.append(n == 0 ? "" : " + ").append(1 << n);
        }
        g.setFont(Tema.fuente(Font.BOLD, 20f));
        g.setColor(p.exito());
        String texto = ultimo < 0 ? "0" : terminos + " = " + suma;
        g.drawString(recortar(g, texto, ancho / 2 - 40), 24, y + 56);

        String formula = "2^(" + altura + "+1) − 1 = " + ((1 << (altura + 1)) - 1);
        g.setColor(mostrarFormula ? p.acento() : Paleta.alfa(p.textoSuave(), 0.5));
        g.drawString(formula, ancho / 2 + 10, y + 56);
    }

    private static String recortar(Graphics2D g, String texto, int ancho) {
        String t = texto;
        while (t.length() > 4 && g.getFontMetrics().stringWidth(t) > ancho) {
            t = "…" + t.substring(Math.min(t.length(), 3));
        }
        return t;
    }
}
