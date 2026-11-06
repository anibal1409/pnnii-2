package pnni.laboratorio.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Contenedor redondeado con borde sutil y título opcional en versalitas. */
public class Tarjeta extends JPanel {

    private static final int RADIO = 14;

    private final JLabel titulo = new JLabel();
    private final JPanel cabecera = new JPanel(new BorderLayout());

    public Tarjeta(String textoTitulo, JComponent contenido) {
        super(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        cabecera.setOpaque(false);
        cabecera.setBorder(BorderFactory.createEmptyBorder(0, 2, 8, 2));
        if (textoTitulo != null) {
            titulo.setText(textoTitulo.toUpperCase());
            titulo.setFont(Tema.fuente(Font.BOLD, 11f));
            cabecera.add(titulo, BorderLayout.WEST);
            add(cabecera, BorderLayout.NORTH);
        }
        if (contenido != null) {
            add(contenido, BorderLayout.CENTER);
        }
    }

    /** Componente alineado a la derecha del título (p. ej. una insignia o botón). */
    public void accesorio(JComponent componente) {
        cabecera.add(componente, BorderLayout.EAST);
        if (cabecera.getParent() == null) {
            add(cabecera, BorderLayout.NORTH);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (titulo != null) {
            titulo.setFont(Tema.fuente(Font.BOLD, 11f));
        }
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Paleta p = Paleta.actual();
        titulo.setForeground(p.textoSuave());
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D forma = new RoundRectangle2D.Double(0.5, 0.5, getWidth() - 1, getHeight() - 1, RADIO, RADIO);
        g.setColor(p.superficie());
        g.fill(forma);
        g.setColor(p.borde());
        g.draw(forma);
        g.dispose();
    }
}
