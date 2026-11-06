package pnni.laboratorio.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.Timer;

import pnni.laboratorio.nucleo.Escena;

/** Superficie donde se dibuja la escena del paso actual, con una animación corta de transición. */
public class LienzoEscena extends JComponent {

    private static final int DURACION_MS = 450;

    private Escena escena;
    private double progreso = 1;
    private long inicio;
    private final Timer animacion = new Timer(15, e -> avanzar());

    public void mostrar(Escena nueva, boolean animar) {
        escena = nueva;
        if (animar) {
            progreso = 0;
            inicio = System.currentTimeMillis();
            animacion.restart();
        } else {
            animacion.stop();
            progreso = 1;
        }
        repaint();
    }

    private void avanzar() {
        double t = (System.currentTimeMillis() - inicio) / (double) DURACION_MS;
        progreso = Math.min(1, t);
        if (progreso >= 1) {
            animacion.stop();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        if (escena == null) {
            return;
        }
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        escena.pintar(g, getWidth(), getHeight(), suavizar(progreso), Paleta.actual());
        g.dispose();
    }

    /** Curva ease-in-out cúbica para que el movimiento se sienta natural. */
    private static double suavizar(double t) {
        return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }
}
