package pnni.laboratorio.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/** Visor de código Java de solo lectura con resaltado de sintaxis y de las líneas en ejecución. */
public class VisorCodigo extends JComponent implements Scrollable {

    private static final Set<String> PALABRAS_CLAVE = Set.of(
            "public", "private", "protected", "static", "final", "class", "if", "else", "while", "for",
            "do", "return", "new", "break", "continue", "true", "false", "null", "import", "package",
            "switch", "case", "default", "this", "try", "catch", "throw");
    private static final Set<String> PRIMITIVOS = Set.of(
            "int", "long", "double", "float", "boolean", "char", "byte", "short", "void");
    private static final int MARGEN_SUPERIOR = 10;

    private List<String> lineas = List.of();
    private int[] activas = {};
    private float tamanoFuente = 13.5f;

    public VisorCodigo() {
        setOpaque(true);
    }

    public void setCodigo(List<String> nuevasLineas) {
        lineas = nuevasLineas.stream().map(l -> l.replace("\t", "    ")).toList();
        activas = new int[0];
        revalidate();
        repaint();
        scrollRectToVisible(new Rectangle(0, 0, 1, 1));
    }

    public void setActivas(int[] nuevas) {
        activas = nuevas.clone();
        repaint();
        if (activas.length > 0) {
            int alto = altoLinea();
            int y = MARGEN_SUPERIOR + activas[0] * alto;
            scrollRectToVisible(new Rectangle(0, Math.max(0, y - alto * 3), 1, alto * (activas.length + 6)));
        }
    }

    private Font fuente() {
        return Tema.mono(tamanoFuente);
    }

    private int altoLinea() {
        return Math.round(tamanoFuente * 1.7f);
    }

    private int anchoMedianil(FontMetrics fm) {
        return fm.stringWidth(String.valueOf(Math.max(99, lineas.size()))) + 30;
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(fuente());
        int maximo = 0;
        for (String l : lineas) {
            maximo = Math.max(maximo, fm.stringWidth(l));
        }
        return new Dimension(anchoMedianil(fm) + maximo + 40, MARGEN_SUPERIOR * 2 + lineas.size() * altoLinea());
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Paleta p = Paleta.actual();
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setColor(p.superficie());
        g.fillRect(0, 0, getWidth(), getHeight());

        Font base = fuente();
        g.setFont(base);
        FontMetrics fm = g.getFontMetrics();
        int alto = altoLinea();
        int medianil = anchoMedianil(fm);
        int base0 = MARGEN_SUPERIOR + (alto + fm.getAscent() - fm.getDescent()) / 2;
        Rectangle visible = g.getClipBounds();

        boolean enComentario = false;
        for (int i = 0; i < lineas.size(); i++) {
            int y = MARGEN_SUPERIOR + i * alto;
            boolean activa = esActiva(i);
            String linea = lineas.get(i);
            boolean visibleLinea = visible == null || (y + alto >= visible.y && y <= visible.y + visible.height);
            if (visibleLinea) {
                if (activa) {
                    g.setColor(Paleta.alfa(p.acento(), p.oscura() ? 0.17 : 0.11));
                    g.fillRect(0, y, getWidth(), alto);
                    g.setColor(p.acento());
                    g.fillRect(0, y, 3, alto);
                }
                g.setFont(activa ? base.deriveFont(Font.BOLD) : base);
                g.setColor(activa ? p.acento() : Paleta.alfa(p.textoSuave(), 0.6));
                String numero = String.valueOf(i + 1);
                g.drawString(numero, medianil - 16 - g.getFontMetrics().stringWidth(numero), base0 + i * alto);
                g.setFont(base);
            }
            enComentario = pintarLinea(g, linea, medianil, base0 + i * alto, enComentario, p, visibleLinea);
        }
        g.setColor(p.borde());
        g.drawLine(medianil - 6, 0, medianil - 6, getHeight());
        g.dispose();
    }

    private boolean esActiva(int linea) {
        for (int a : activas) {
            if (a == linea) {
                return true;
            }
        }
        return false;
    }

    /** Pinta una línea token a token. Devuelve si al terminar seguimos dentro de un comentario de bloque. */
    private boolean pintarLinea(Graphics2D g, String s, int x, int y, boolean enComentario, Paleta p, boolean pintar) {
        FontMetrics fm = g.getFontMetrics();
        Font normal = g.getFont();
        Font cursiva = normal.deriveFont(Font.ITALIC);
        int i = 0;
        while (i < s.length()) {
            int inicio = i;
            Color color = p.texto();
            Font fuente = normal;
            char c = s.charAt(i);
            if (enComentario) {
                int fin = s.indexOf("*/", i);
                i = fin < 0 ? s.length() : fin + 2;
                enComentario = fin < 0;
                color = p.sintaxisComentario();
                fuente = cursiva;
            } else if (s.startsWith("//", i)) {
                i = s.length();
                color = p.sintaxisComentario();
                fuente = cursiva;
            } else if (s.startsWith("/*", i)) {
                int fin = s.indexOf("*/", i + 2);
                i = fin < 0 ? s.length() : fin + 2;
                enComentario = fin < 0;
                color = p.sintaxisComentario();
                fuente = cursiva;
            } else if (c == '"' || c == '\'') {
                i++;
                while (i < s.length() && s.charAt(i) != c) {
                    i += s.charAt(i) == '\\' ? 2 : 1;
                }
                i = Math.min(i + 1, s.length());
                color = p.sintaxisCadena();
            } else if (Character.isDigit(c)) {
                while (i < s.length() && (Character.isLetterOrDigit(s.charAt(i)) || s.charAt(i) == '_')) {
                    i++;
                }
                color = p.sintaxisNumero();
            } else if (Character.isJavaIdentifierStart(c)) {
                while (i < s.length() && Character.isJavaIdentifierPart(s.charAt(i))) {
                    i++;
                }
                String palabra = s.substring(inicio, i);
                if (PALABRAS_CLAVE.contains(palabra)) {
                    color = p.sintaxisClave();
                } else if (PRIMITIVOS.contains(palabra) || Character.isUpperCase(c)) {
                    color = p.sintaxisTipo();
                } else if (i < s.length() && s.charAt(i) == '(') {
                    color = p.sintaxisMetodo();
                }
            } else {
                i++;
                if ("+-*/%<>=!&|?:".indexOf(c) >= 0) {
                    color = p.sintaxisClave();
                }
            }
            String token = s.substring(inicio, i);
            if (pintar) {
                g.setFont(fuente);
                g.setColor(color);
                g.drawString(token, x, y);
            }
            x += fm.stringWidth(token);
        }
        g.setFont(normal);
        return enComentario;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visible, int orientacion, int direccion) {
        return orientacion == SwingConstants.VERTICAL ? altoLinea() : 24;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visible, int orientacion, int direccion) {
        return orientacion == SwingConstants.VERTICAL ? visible.height - altoLinea() : visible.width / 2;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return getParent() != null && getParent().getWidth() > getPreferredSize().width;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return getParent() != null && getParent().getHeight() > getPreferredSize().height;
    }
}
