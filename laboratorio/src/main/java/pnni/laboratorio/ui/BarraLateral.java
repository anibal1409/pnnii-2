package pnni.laboratorio.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.nucleo.Unidad;

/** Navegación principal: unidades del programa y sus prácticas. */
public class BarraLateral extends JPanel {

    private static final int ANCHO = 272;

    private final List<ElementoNav> elementos = new ArrayList<>();

    public BarraLateral(List<Practica> practicas, Consumer<Practica> alElegir) {
        super(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(ANCHO, 0));

        add(new Marca(), BorderLayout.NORTH);

        JPanel lista = new JPanel();
        lista.setOpaque(false);
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBorder(BorderFactory.createEmptyBorder(4, 12, 16, 12));
        for (Unidad unidad : Unidad.values()) {
            lista.add(new CabeceraUnidad(unidad));
            int numero = 1;
            for (Practica practica : practicas) {
                if (practica.unidad() == unidad) {
                    String indice = unidad.ordinal() + 1 + "." + numero++;
                    ElementoNav elemento = new ElementoNav(practica, indice);
                    elemento.addActionListener(e -> alElegir.accept(practica));
                    elementos.add(elemento);
                    lista.add(elemento);
                }
            }
            if (numero == 1) {
                lista.add(new Proximamente());
            }
            lista.add(Box.createVerticalStrut(10));
        }
        JScrollPane desplazable = new JScrollPane(lista);
        desplazable.setBorder(BorderFactory.createEmptyBorder());
        desplazable.setOpaque(false);
        desplazable.getViewport().setOpaque(false);
        desplazable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        desplazable.getVerticalScrollBar().setUnitIncrement(16);
        add(desplazable, BorderLayout.CENTER);

        JLabel atajos = new JLabel("<html>Espacio reproducir · ← → pasos<br>F5 ejecutar · Inicio / Fin</html>");
        atajos.putClientProperty("FlatLaf.styleClass", "small");
        atajos.setBorder(BorderFactory.createEmptyBorder(12, 22, 16, 16));
        add(atajos, BorderLayout.SOUTH);
    }

    public void marcar(Practica practica) {
        for (ElementoNav e : elementos) {
            e.seleccionado = e.practica == practica;
            e.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Paleta p = Paleta.actual();
        g.setColor(p.lateral());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(p.borde());
        g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        for (Component c : getComponents()) {
            if (c instanceof JLabel etiqueta) {
                etiqueta.setForeground(p.textoSuave());
            }
        }
    }

    /** Logotipo y nombre de la aplicación. */
    private static final class Marca extends JComponent {
        Marca() {
            setPreferredSize(new Dimension(ANCHO, 84));
        }

        @Override
        protected void paintComponent(Graphics g0) {
            Paleta p = Paleta.actual();
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int x = 22;
            int y = 24;
            g.setPaint(new GradientPaint(x, y, p.acento(), x + 36, y + 36, p.violeta()));
            g.fill(new RoundRectangle2D.Double(x, y, 36, 36, 11, 11));
            g.setColor(Color.WHITE);
            g.setFont(Tema.fuente(Font.BOLD, 14f));
            String sigla = "PN";
            g.drawString(sigla, x + (36 - g.getFontMetrics().stringWidth(sigla)) / 2, y + 23);
            g.setColor(p.texto());
            g.setFont(Tema.fuente(Font.BOLD, 15f));
            g.drawString("Laboratorio PNN II", x + 48, y + 15);
            g.setColor(p.textoSuave());
            g.setFont(Tema.fuente(Font.PLAIN, 12f));
            g.drawString("Programación No Numérica II", x + 48, y + 33);
            g.dispose();
        }
    }

    private static final class CabeceraUnidad extends JComponent {
        private final Unidad unidad;

        CabeceraUnidad(Unidad unidad) {
            this.unidad = unidad;
            setAlignmentX(LEFT_ALIGNMENT);
            Dimension d = new Dimension(ANCHO - 24, 50);
            setPreferredSize(d);
            setMaximumSize(d);
            setToolTipText(unidad.resumen());
        }

        @Override
        protected void paintComponent(Graphics g0) {
            Paleta p = Paleta.actual();
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setFont(Tema.fuente(Font.BOLD, 10.5f));
            g.setColor(p.acento());
            g.drawString("UNIDAD " + unidad.romano(), 10, 26);
            g.setFont(Tema.fuente(Font.BOLD, 13f));
            g.setColor(p.texto());
            g.drawString(unidad.titulo(), 10, 44);
            g.dispose();
        }
    }

    private static final class Proximamente extends JLabel {
        Proximamente() {
            super("Próximamente");
            setAlignmentX(LEFT_ALIGNMENT);
            setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            setFont(Tema.fuente(Font.ITALIC, 12.5f));
        }

        @Override
        protected void paintComponent(Graphics g) {
            setForeground(Paleta.alfa(Paleta.actual().textoSuave(), 0.7));
            super.paintComponent(g);
        }
    }

    /** Botón de navegación con estados de hover y seleccionado. */
    private static final class ElementoNav extends JButton {
        private final Practica practica;
        private final String indice;
        private boolean seleccionado;

        ElementoNav(Practica practica, String indice) {
            this.practica = practica;
            this.indice = indice;
            setAlignmentX(LEFT_ALIGNMENT);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFocusable(false);
            setRolloverEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setToolTipText(practica.titulo());
            Dimension d = new Dimension(ANCHO - 24, 36);
            setPreferredSize(d);
            setMaximumSize(d);
            setMargin(new Insets(0, 0, 0, 0));
        }

        @Override
        protected void paintComponent(Graphics g0) {
            Paleta p = Paleta.actual();
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            RoundRectangle2D fondo = new RoundRectangle2D.Double(0, 2, getWidth(), getHeight() - 4, 10, 10);
            if (seleccionado) {
                g.setColor(Paleta.alfa(p.acento(), p.oscura() ? 0.18 : 0.12));
                g.fill(fondo);
                g.setColor(p.acento());
                g.fill(new RoundRectangle2D.Double(0, 10, 3, getHeight() - 20, 3, 3));
            } else if (getModel().isRollover()) {
                g.setColor(Paleta.alfa(p.texto(), p.oscura() ? 0.06 : 0.05));
                g.fill(fondo);
            }
            g.setFont(Tema.mono(11f));
            g.setColor(seleccionado ? p.acento() : p.textoSuave());
            int base = getHeight() / 2 + 4;
            g.drawString(indice, 14, base);
            g.setFont(Tema.fuente(seleccionado ? Font.BOLD : Font.PLAIN, 13f));
            g.setColor(seleccionado ? p.texto() : Paleta.mezclar(p.texto(), p.textoSuave(), 0.25));
            String texto = recortar(g, practica.titulo(), getWidth() - 56);
            g.drawString(texto, 46, base);
            g.dispose();
        }

        private static String recortar(Graphics2D g, String texto, int ancho) {
            if (g.getFontMetrics().stringWidth(texto) <= ancho) {
                return texto;
            }
            String t = texto;
            while (t.length() > 1 && g.getFontMetrics().stringWidth(t + "…") > ancho) {
                t = t.substring(0, t.length() - 1);
            }
            return t + "…";
        }
    }
}
