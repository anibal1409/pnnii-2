package pnni.laboratorio.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

/** Utilidades de pintura comunes a las escenas: texto centrado, pastillas, celdas por tono y leyenda. */
public final class Dibujo {

    private Dibujo() {
    }

    public static void centrar(Graphics2D g, String texto, double cx, double cy) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, (float) (cx - fm.stringWidth(texto) / 2.0),
                (float) (cy + (fm.getAscent() - fm.getDescent()) / 2.0));
    }

    /** Etiqueta redondeada; devuelve su ancho. */
    public static double pastilla(Graphics2D g, String texto, double x, double y, Color c, Paleta p) {
        g.setFont(Tema.fuente(Font.BOLD, 11.5f));
        double w = g.getFontMetrics().stringWidth(texto) + 22;
        g.setColor(Paleta.alfa(c, p.oscura() ? 0.16 : 0.12));
        g.fill(new RoundRectangle2D.Double(x, y, w, 24, 24, 24));
        g.setColor(c);
        centrar(g, texto, x + w / 2, y + 12);
        return w;
    }

    /** Nota informativa a la izquierda e insignia de resultado a la derecha, en la franja superior. */
    public static void etiquetas(Graphics2D g, int ancho, String nota, String insignia, boolean positiva, Paleta p) {
        if (nota != null) {
            pastilla(g, nota, 4, 4, p.info(), p);
        }
        if (insignia != null) {
            g.setFont(Tema.fuente(Font.BOLD, 11.5f));
            double w = g.getFontMetrics().stringWidth(insignia) + 22;
            pastilla(g, insignia, ancho - w - 4, 4, positiva ? p.exito() : p.peligro(), p);
        }
    }

    /** Rellena y contornea la forma según el tono; devuelve el color de texto adecuado encima. */
    public static Color forma(Graphics2D g, Shape forma, Tono tono, Paleta p) {
        if (tono.solido()) {
            g.setColor(tono.color(p));
            g.fill(forma);
            return Color.WHITE;
        }
        if (tono == Tono.NORMAL || tono == Tono.ATENUADO) {
            g.setColor(p.superficieAlta());
            g.fill(forma);
            g.setColor(p.borde());
            g.setStroke(new BasicStroke(1.4f));
            g.draw(forma);
            return tono == Tono.ATENUADO ? Paleta.alfa(p.textoSuave(), 0.6) : p.texto();
        }
        Color c = tono.color(p);
        g.setColor(Paleta.mezclar(p.superficie(), c, p.oscura() ? 0.22 : 0.14));
        g.fill(forma);
        g.setColor(c);
        g.setStroke(new BasicStroke(1.8f));
        g.draw(forma);
        return textoSobre(c, p);
    }

    /** Color de texto legible sobre un fondo suave del color c. */
    public static Color textoSobre(Color c, Paleta p) {
        return p.oscura() ? Paleta.mezclar(c, Color.WHITE, 0.35) : Paleta.mezclar(c, Color.BLACK, 0.2);
    }

    /** Encabezado pequeño en mayúsculas. */
    public static void rotulo(Graphics2D g, String texto, double x, double y, Paleta p) {
        g.setFont(Tema.fuente(Font.BOLD, 10.5f));
        g.setColor(p.textoSuave());
        g.drawString(texto, (float) x, (float) y);
    }

    /** Leyenda centrada: un punto de color y su significado por cada entrada. */
    public static void leyenda(Graphics2D g, int ancho, int y, Map<Tono, String> entradas, Paleta p) {
        g.setFont(Tema.fuente(Font.PLAIN, 11.5f));
        FontMetrics fm = g.getFontMetrics();
        int total = 0;
        for (String texto : entradas.values()) {
            total += fm.stringWidth(texto) + 30;
        }
        double x = Math.max(4, (ancho - total) / 2.0);
        for (Map.Entry<Tono, String> e : entradas.entrySet()) {
            g.setColor(e.getKey() == Tono.NORMAL ? p.borde() : e.getKey().color(p));
            g.fill(new Ellipse2D.Double(x, y - 9, 10, 10));
            g.setColor(p.textoSuave());
            g.drawString(e.getValue(), (float) (x + 15), y);
            x += fm.stringWidth(e.getValue()) + 30;
        }
    }

    /** Recorta por la derecha con «…» hasta que quepa en el ancho dado. */
    public static String recortar(Graphics2D g, String texto, double ancho) {
        FontMetrics fm = g.getFontMetrics();
        if (fm.stringWidth(texto) <= ancho) {
            return texto;
        }
        String t = texto;
        while (t.length() > 1 && fm.stringWidth(t + "…") > ancho) {
            t = t.substring(0, t.length() - 1);
        }
        return t + "…";
    }

    /** Interpolación suave para animaciones (0..1). */
    public static double suave(double t) {
        double x = Math.max(0, Math.min(1, t));
        return x * x * (3 - 2 * x);
    }
}
