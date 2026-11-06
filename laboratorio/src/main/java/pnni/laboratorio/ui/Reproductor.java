package pnni.laboratorio.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import com.formdev.flatlaf.FlatClientProperties;

import pnni.laboratorio.ui.Iconos.Forma;

/** Controles de reproducción de la traza: ir al inicio/fin, paso a paso, reproducir y velocidad. */
public class Reproductor extends JPanel {

    /** Recibe el índice del paso que debe mostrarse. */
    public interface Oyente {
        void mostrarPaso(int indice, boolean animar);
    }

    private static final int[] RETARDOS_MS = {1900, 1300, 850, 520, 300};

    private final JButton inicio = boton(Forma.INICIO, "Ir al primer paso (Inicio)");
    private final JButton anterior = boton(Forma.ANTERIOR, "Paso anterior (←)");
    private final JButton reproducir = new JButton();
    private final JButton siguiente = boton(Forma.SIGUIENTE, "Paso siguiente (→)");
    private final JButton fin = boton(Forma.FIN, "Ir al último paso (Fin)");
    private final JSlider lineaTiempo = new JSlider(0, 0, 0);
    private final JLabel contador = new JLabel();
    private final JSlider velocidad = new JSlider(0, RETARDOS_MS.length - 1, 2);
    private final Timer temporizador = new Timer(RETARDOS_MS[2], e -> avanzarAutomatico());

    private Oyente oyente = (i, a) -> { };
    private int total;
    private int actual;
    private boolean ajustando;

    public Reproductor() {
        super(new BorderLayout(18, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));

        reproducir.setToolTipText("Reproducir / pausar (Espacio)");
        reproducir.setPreferredSize(new Dimension(42, 42));
        reproducir.setFocusable(false);
        reproducir.putClientProperty(FlatClientProperties.STYLE,
                "arc:999; background:$Component.accentColor; foreground:#ffffff; borderWidth:0; focusWidth:0;"
                        + "hoverBackground:lighten($Component.accentColor,6%); pressedBackground:darken($Component.accentColor,6%)");
        actualizarIconoReproducir();

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        botones.setOpaque(false);
        botones.add(inicio);
        botones.add(anterior);
        botones.add(reproducir);
        botones.add(siguiente);
        botones.add(fin);

        lineaTiempo.setOpaque(false);
        lineaTiempo.setFocusable(false);
        lineaTiempo.addChangeListener(e -> {
            if (!ajustando) {
                ir(lineaTiempo.getValue(), !lineaTiempo.getValueIsAdjusting());
            }
        });
        contador.setFont(Tema.fuente(Font.BOLD, 12.5f));
        JPanel centro = new JPanel(new BorderLayout(12, 0));
        centro.setOpaque(false);
        centro.add(lineaTiempo, BorderLayout.CENTER);
        centro.add(contador, BorderLayout.EAST);

        JLabel etiquetaVelocidad = new JLabel("Velocidad");
        etiquetaVelocidad.putClientProperty(FlatClientProperties.STYLE_CLASS, "small");
        velocidad.setOpaque(false);
        velocidad.setFocusable(false);
        velocidad.setPreferredSize(new Dimension(110, 24));
        velocidad.setToolTipText("Velocidad de reproducción automática");
        velocidad.addChangeListener(e -> temporizador.setDelay(RETARDOS_MS[velocidad.getValue()]));
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        derecha.setOpaque(false);
        derecha.add(etiquetaVelocidad);
        derecha.add(velocidad);

        add(botones, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);
        add(derecha, BorderLayout.EAST);

        inicio.addActionListener(e -> ir(0, true));
        anterior.addActionListener(e -> ir(actual - 1, true));
        siguiente.addActionListener(e -> ir(actual + 1, true));
        fin.addActionListener(e -> ir(total - 1, true));
        reproducir.addActionListener(e -> alternar());
        cargar(0);
    }

    public void setOyente(Oyente oyente) {
        this.oyente = oyente;
    }

    /** Reinicia el reproductor para una traza nueva de {@code pasos} pasos. */
    public void cargar(int pasos) {
        detener();
        total = pasos;
        actual = 0;
        ajustando = true;
        lineaTiempo.setMaximum(Math.max(0, pasos - 1));
        lineaTiempo.setValue(0);
        ajustando = false;
        refrescar();
        if (pasos > 0) {
            oyente.mostrarPaso(0, false);
        }
    }

    public void ir(int indice, boolean animar) {
        if (total == 0) {
            return;
        }
        int destino = Math.max(0, Math.min(total - 1, indice));
        if (destino == actual && lineaTiempo.getValue() == destino) {
            return;
        }
        actual = destino;
        ajustando = true;
        lineaTiempo.setValue(actual);
        ajustando = false;
        refrescar();
        oyente.mostrarPaso(actual, animar);
    }

    public void alternar() {
        if (temporizador.isRunning()) {
            detener();
            return;
        }
        if (total == 0) {
            return;
        }
        if (actual >= total - 1) {
            ir(0, true);
        }
        temporizador.start();
        actualizarIconoReproducir();
    }

    public void siguiente() {
        ir(actual + 1, true);
    }

    public void anterior() {
        ir(actual - 1, true);
    }

    public void primero() {
        ir(0, true);
    }

    public void ultimo() {
        ir(total - 1, true);
    }

    public void detener() {
        temporizador.stop();
        actualizarIconoReproducir();
    }

    private void avanzarAutomatico() {
        if (actual >= total - 1) {
            detener();
            return;
        }
        ir(actual + 1, true);
    }

    private void refrescar() {
        contador.setText(total == 0 ? "Sin pasos" : "Paso " + (actual + 1) + " de " + total);
        inicio.setEnabled(actual > 0);
        anterior.setEnabled(actual > 0);
        siguiente.setEnabled(actual < total - 1);
        fin.setEnabled(actual < total - 1);
        reproducir.setEnabled(total > 1);
    }

    private void actualizarIconoReproducir() {
        reproducir.setIcon(Iconos.de(temporizador.isRunning() ? Forma.PAUSA : Forma.PLAY, 18, java.awt.Color.WHITE));
    }

    private static JButton boton(Forma forma, String ayuda) {
        JButton b = new JButton(Iconos.de(forma, 18));
        b.setToolTipText(ayuda);
        b.setFocusable(false);
        b.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_TOOLBAR_BUTTON);
        b.setPreferredSize(new Dimension(36, 36));
        return b;
    }
}
