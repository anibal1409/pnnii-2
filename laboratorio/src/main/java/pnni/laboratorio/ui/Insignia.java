package pnni.laboratorio.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/** Etiqueta tipo "chip": texto del color de rol sobre un fondo translúcido del mismo color. */
public class Insignia extends JLabel {

    private final Function<Paleta, Color> color;

    public Insignia(String texto, Function<Paleta, Color> color) {
        super(texto);
        this.color = color;
        setFont(Tema.fuente(Font.BOLD, 11f));
        setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Paleta p = Paleta.actual();
        Color c = color.apply(p);
        setForeground(c);
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Paleta.alfa(c, p.oscura() ? 0.16 : 0.11));
        g.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
        g.dispose();
        super.paintComponent(g0);
    }
}
