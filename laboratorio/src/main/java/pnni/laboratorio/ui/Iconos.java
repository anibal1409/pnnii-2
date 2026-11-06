package pnni.laboratorio.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;
import javax.swing.UIManager;

/** Iconos vectoriales dibujados con Java2D (nítidos en cualquier escala y sin archivos externos). */
public final class Iconos {

    public enum Forma { PLAY, PAUSA, SIGUIENTE, ANTERIOR, INICIO, FIN, SOL, LUNA, REINICIAR }

    private Iconos() {
    }

    public static Icon de(Forma forma, int tamano) {
        return new Vectorial(forma, tamano, null);
    }

    public static Icon de(Forma forma, int tamano, Color color) {
        return new Vectorial(forma, tamano, color);
    }

    private record Vectorial(Forma forma, int tamano, Color color) implements Icon {

        @Override
        public void paintIcon(Component c, Graphics g0, int x, int y) {
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            Color tinta = color != null ? color
                    : c.isEnabled() ? c.getForeground() : UIManager.getColor("Label.disabledForeground");
            g.setColor(tinta);
            g.translate(x, y);
            double s = tamano;
            g.setStroke(new BasicStroke((float) (s * 0.11), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            switch (forma) {
                case PLAY -> g.fill(triangulo(s * 0.28, s * 0.18, s * 0.82, s));
                case PAUSA -> {
                    g.fill(new RoundRectangle2D.Double(s * 0.24, s * 0.18, s * 0.18, s * 0.64, s * 0.08, s * 0.08));
                    g.fill(new RoundRectangle2D.Double(s * 0.58, s * 0.18, s * 0.18, s * 0.64, s * 0.08, s * 0.08));
                }
                case SIGUIENTE -> {
                    g.fill(triangulo(s * 0.2, s * 0.2, s * 0.7, s));
                    g.fill(new RoundRectangle2D.Double(s * 0.7, s * 0.2, s * 0.12, s * 0.6, s * 0.06, s * 0.06));
                }
                case ANTERIOR -> {
                    g.fill(triangulo(s * 0.8, s * 0.2, s * 0.3, s));
                    g.fill(new RoundRectangle2D.Double(s * 0.18, s * 0.2, s * 0.12, s * 0.6, s * 0.06, s * 0.06));
                }
                case FIN -> {
                    g.fill(triangulo(s * 0.12, s * 0.22, s * 0.48, s));
                    g.fill(triangulo(s * 0.44, s * 0.22, s * 0.8, s));
                    g.fill(new RoundRectangle2D.Double(s * 0.8, s * 0.22, s * 0.1, s * 0.56, s * 0.05, s * 0.05));
                }
                case INICIO -> {
                    g.fill(triangulo(s * 0.88, s * 0.22, s * 0.52, s));
                    g.fill(triangulo(s * 0.56, s * 0.22, s * 0.2, s));
                    g.fill(new RoundRectangle2D.Double(s * 0.1, s * 0.22, s * 0.1, s * 0.56, s * 0.05, s * 0.05));
                }
                case SOL -> {
                    g.fill(new Ellipse2D.Double(s * 0.32, s * 0.32, s * 0.36, s * 0.36));
                    for (int k = 0; k < 8; k++) {
                        double a = Math.PI / 4 * k;
                        double c1 = s * 0.5;
                        g.draw(new java.awt.geom.Line2D.Double(
                                c1 + Math.cos(a) * s * 0.3, c1 + Math.sin(a) * s * 0.3,
                                c1 + Math.cos(a) * s * 0.42, c1 + Math.sin(a) * s * 0.42));
                    }
                }
                case LUNA -> {
                    java.awt.geom.Area luna = new java.awt.geom.Area(
                            new Ellipse2D.Double(s * 0.16, s * 0.16, s * 0.68, s * 0.68));
                    luna.subtract(new java.awt.geom.Area(
                            new Ellipse2D.Double(s * 0.38, s * 0.04, s * 0.6, s * 0.6)));
                    g.fill(luna);
                }
                case REINICIAR -> {
                    g.draw(new java.awt.geom.Arc2D.Double(s * 0.2, s * 0.2, s * 0.6, s * 0.6, 60, 290,
                            java.awt.geom.Arc2D.OPEN));
                    Path2D flecha = new Path2D.Double();
                    flecha.moveTo(s * 0.62, s * 0.1);
                    flecha.lineTo(s * 0.8, s * 0.26);
                    flecha.lineTo(s * 0.56, s * 0.34);
                    flecha.closePath();
                    g.fill(flecha);
                }
            }
            g.dispose();
        }

        private static Path2D triangulo(double x0, double y0, double x1, double s) {
            Path2D p = new Path2D.Double();
            p.moveTo(x0, y0);
            p.lineTo(x1, s / 2);
            p.lineTo(x0, s - y0);
            p.closePath();
            return p;
        }

        @Override
        public int getIconWidth() {
            return tamano;
        }

        @Override
        public int getIconHeight() {
            return tamano;
        }
    }
}
