package pnni.laboratorio.unidad1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import pnni.laboratorio.nucleo.Escena;
import pnni.laboratorio.ui.Paleta;
import pnni.laboratorio.ui.Tema;

/**
 * Dibuja un árbol binario guardado en un arreglo (base 0) en sus dos vistas a la vez:
 * el árbol y el arreglo secuencial. Cada índice puede tener un rol (color) y punteros
 * con nombres de variables; un intercambio se anima desplazando los dos valores.
 */
public final class EscenaHeap implements Escena {

    public enum Rol {
        NORMAL("", null),
        ACTUAL("nodo actual", Paleta::acento),
        PADRE("padre", Paleta::info),
        HIJO("hijo", Paleta::violeta),
        COMPARA("comparando", Paleta::aviso),
        INTERCAMBIO("intercambio", Paleta::peligro),
        LISTO("en su lugar", Paleta::exito),
        ATENUADO("fuera del heap", Paleta::textoSuave);

        final String leyenda;
        final java.util.function.Function<Paleta, Color> color;

        Rol(String leyenda, java.util.function.Function<Paleta, Color> color) {
            this.leyenda = leyenda;
            this.color = color;
        }
    }

    private final int[] valores;
    private final int tamano;
    private final Rol[] roles;
    private final Map<Integer, String> punteros;
    private final int intercambioA;
    private final int intercambioB;
    private final String insignia;
    private final boolean insigniaPositiva;
    private final String nota;

    private EscenaHeap(Constructor c) {
        valores = c.valores;
        tamano = c.tamano;
        roles = c.roles;
        punteros = c.punteros;
        intercambioA = c.intercambioA;
        intercambioB = c.intercambioB;
        insignia = c.insignia;
        insigniaPositiva = c.insigniaPositiva;
        nota = c.nota;
    }

    /** Toma una copia del arreglo: la escena no cambia aunque el algoritmo siga modificándolo. */
    public static Constructor de(int[] heap, int tamano) {
        return new Constructor(heap, tamano);
    }

    public static final class Constructor {
        private final int[] valores;
        private final int tamano;
        private final Rol[] roles;
        private final Map<Integer, String> punteros = new LinkedHashMap<>();
        private int intercambioA = -1;
        private int intercambioB = -1;
        private String insignia;
        private boolean insigniaPositiva;
        private String nota;

        private Constructor(int[] heap, int tamano) {
            this.valores = java.util.Arrays.copyOf(heap, Math.max(tamano, heap.length));
            this.tamano = tamano;
            this.roles = new Rol[valores.length];
            java.util.Arrays.fill(roles, Rol.NORMAL);
        }

        public Constructor rol(int indice, Rol rol) {
            if (indice >= 0 && indice < roles.length) {
                roles[indice] = rol;
            }
            return this;
        }

        public Constructor todos(Rol rol) {
            for (int k = 0; k < tamano; k++) {
                roles[k] = rol;
            }
            return this;
        }

        public Constructor puntero(int indice, String nombre) {
            if (indice >= 0 && indice < valores.length) {
                punteros.merge(indice, nombre, (a, b) -> a + ", " + b);
            }
            return this;
        }

        /** Los valores en a y b ya están intercambiados; la animación los lleva desde su posición anterior. */
        public Constructor intercambio(int a, int b) {
            intercambioA = a;
            intercambioB = b;
            return rol(a, Rol.INTERCAMBIO).rol(b, Rol.INTERCAMBIO);
        }

        public Constructor insignia(String texto, boolean positiva) {
            insignia = texto;
            insigniaPositiva = positiva;
            return this;
        }

        public Constructor nota(String texto) {
            nota = texto;
            return this;
        }

        public EscenaHeap crear() {
            return new EscenaHeap(this);
        }
    }

    // ------------------------------------------------------------------ dibujo

    @Override
    public void pintar(Graphics2D g, int ancho, int alto, double t, Paleta p) {
        int altoArreglo = 108;
        int altoLeyenda = 26;
        int arribaArbol = 40;
        int abajoArbol = alto - altoArreglo - altoLeyenda - 8;

        pintarEtiquetasSuperiores(g, ancho, p);
        if (tamano == 0) {
            g.setFont(Tema.fuente(Font.PLAIN, 14f));
            g.setColor(p.textoSuave());
            centrar(g, "El heap está vacío", ancho / 2.0, (arribaArbol + abajoArbol) / 2.0);
        } else {
            pintarArbol(g, ancho, arribaArbol, abajoArbol, t, p);
        }
        pintarArreglo(g, ancho, alto - altoArreglo - altoLeyenda, t, p);
        pintarLeyenda(g, ancho, alto - altoLeyenda + 6, p);
    }

    private void pintarEtiquetasSuperiores(Graphics2D g, int ancho, Paleta p) {
        g.setFont(Tema.fuente(Font.BOLD, 11.5f));
        if (nota != null) {
            pastilla(g, nota, 4, 4, p.info(), p, false);
        }
        if (insignia != null) {
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(insignia) + 22;
            pastilla(g, insignia, ancho - w - 4, 4, insigniaPositiva ? p.exito() : p.peligro(), p, false);
        }
    }

    private void pintarArbol(Graphics2D g, int ancho, int arriba, int abajo, double t, Paleta p) {
        int niveles = nivel(tamano - 1) + 1;
        double separacion = Math.min(92, (abajo - arriba) / (double) Math.max(1, niveles));
        int hojasMax = 1 << (niveles - 1);
        double margen = ancho > 520 ? 70 : 24;
        double radio = Math.max(11, Math.min(23, Math.min(separacion * 0.34, (ancho - 2 * margen) / (hojasMax * 2.5))));
        double altoArbol = separacion * (niveles - 1);
        double y0 = arriba + radio + Math.max(0, ((abajo - arriba) - altoArbol - 2 * radio) / 2);

        if (ancho > 520) {
            g.setFont(Tema.fuente(Font.PLAIN, 11f));
            g.setColor(Paleta.alfa(p.textoSuave(), 0.8));
            for (int n = 0; n < niveles; n++) {
                g.drawString("Nivel " + n, 6, (float) (y0 + n * separacion + 4));
            }
        }

        g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int k = 1; k < tamano; k++) {
            int padre = (k - 1) / 2;
            Point2D a = posicion(padre, ancho, margen, y0, separacion);
            Point2D b = posicion(k, ancho, margen, y0, separacion);
            boolean resaltada = roles[k] != Rol.NORMAL && roles[padre] != Rol.NORMAL
                    && roles[k] != Rol.ATENUADO && roles[padre] != Rol.ATENUADO
                    && !(roles[k] == Rol.LISTO && roles[padre] == Rol.LISTO);
            g.setColor(resaltada ? Paleta.alfa(p.acento(), 0.7) : p.borde());
            g.draw(new Line2D.Double(a, b));
        }

        Font fuenteValor = Tema.fuente(Font.BOLD, (float) Math.max(11, radio * 0.62));
        Font fuenteIndice = Tema.mono(10f);
        for (int k = 0; k < tamano; k++) {
            Point2D c = posicionAnimada(k, ancho, margen, y0, separacion, radio, t);
            pintarNodo(g, k, c.getX(), c.getY(), radio, fuenteValor, p);
            g.setFont(fuenteIndice);
            g.setColor(Paleta.alfa(p.textoSuave(), 0.9));
            Point2D fija = posicion(k, ancho, margen, y0, separacion);
            centrar(g, "[" + k + "]", fija.getX() + radio + 9, fija.getY() - radio + 4);
        }
    }

    private void pintarNodo(Graphics2D g, int k, double cx, double cy, double r, Font fuente, Paleta p) {
        Rol rol = roles[k];
        Ellipse2D circulo = new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r);
        Color texto;
        if (rol == Rol.ACTUAL || rol == Rol.INTERCAMBIO) {
            Color c = rol.color.apply(p);
            g.setColor(Paleta.alfa(c, 0.25));
            g.fill(new Ellipse2D.Double(cx - r - 5, cy - r - 5, 2 * r + 10, 2 * r + 10));
            g.setColor(c);
            g.fill(circulo);
            texto = Color.WHITE;
        } else if (rol == Rol.NORMAL || rol == Rol.ATENUADO) {
            g.setColor(p.superficieAlta());
            g.fill(circulo);
            g.setColor(p.borde());
            g.setStroke(new BasicStroke(1.6f));
            g.draw(circulo);
            texto = rol == Rol.ATENUADO ? Paleta.alfa(p.textoSuave(), 0.6) : p.texto();
        } else {
            Color c = rol.color.apply(p);
            g.setColor(Paleta.mezclar(p.superficie(), c, p.oscura() ? 0.22 : 0.14));
            g.fill(circulo);
            g.setColor(c);
            g.setStroke(new BasicStroke(2.2f));
            g.draw(circulo);
            texto = p.oscura() ? Paleta.mezclar(c, Color.WHITE, 0.35) : Paleta.mezclar(c, Color.BLACK, 0.2);
        }
        g.setFont(fuente);
        g.setColor(texto);
        centrar(g, String.valueOf(valores[k]), cx, cy);
    }

    private void pintarArreglo(Graphics2D g, int ancho, int arriba, double t, Paleta p) {
        int celdas = Math.max(tamano, 1);
        double margen = 24;
        double lado = Math.min(50, (ancho - 2 * margen) / celdas);
        double x0 = (ancho - lado * celdas) / 2;
        double y = arriba + 22;

        g.setFont(Tema.fuente(Font.BOLD, 10.5f));
        g.setColor(p.textoSuave());
        String encabezado = "ARREGLO SECUENCIAL  ·  padre (i−1)/2  ·  hijos 2i+1, 2i+2";
        if (g.getFontMetrics().stringWidth(encabezado) > ancho - 2 * margen) {
            encabezado = "ARREGLO SECUENCIAL (BASE 0)";
        }
        g.drawString(encabezado, (float) Math.min(x0, (ancho - g.getFontMetrics().stringWidth(encabezado)) / 2.0),
                (float) (arriba + 10));

        Font fuenteValor = Tema.fuente(Font.BOLD, (float) Math.max(10, Math.min(14, lado * 0.32)));
        for (int k = 0; k < tamano; k++) {
            double x = x0 + k * lado;
            double desplazamiento = 0;
            double elevacion = 0;
            if (t < 1 && (k == intercambioA || k == intercambioB)) {
                int origen = k == intercambioA ? intercambioB : intercambioA;
                desplazamiento = (origen - k) * lado * (1 - t);
                elevacion = Math.sin(Math.PI * t) * 16 * (k == intercambioA ? -1 : 1);
            }
            RoundRectangle2D celda = new RoundRectangle2D.Double(x + 2 + desplazamiento, y + elevacion, lado - 4, 40, 9, 9);
            Rol rol = roles[k];
            Color texto = p.texto();
            if (rol == Rol.ACTUAL || rol == Rol.INTERCAMBIO) {
                g.setColor(rol.color.apply(p));
                g.fill(celda);
                texto = Color.WHITE;
            } else if (rol == Rol.NORMAL || rol == Rol.ATENUADO) {
                g.setColor(p.superficieAlta());
                g.fill(celda);
                g.setColor(p.borde());
                g.setStroke(new BasicStroke(1.4f));
                g.draw(celda);
                if (rol == Rol.ATENUADO) {
                    texto = Paleta.alfa(p.textoSuave(), 0.6);
                }
            } else {
                Color c = rol.color.apply(p);
                g.setColor(Paleta.mezclar(p.superficie(), c, p.oscura() ? 0.22 : 0.14));
                g.fill(celda);
                g.setColor(c);
                g.setStroke(new BasicStroke(1.8f));
                g.draw(celda);
                texto = p.oscura() ? Paleta.mezclar(c, Color.WHITE, 0.35) : Paleta.mezclar(c, Color.BLACK, 0.2);
            }
            g.setFont(fuenteValor);
            g.setColor(texto);
            centrar(g, String.valueOf(valores[k]), celda.getCenterX(), celda.getCenterY());

            g.setFont(Tema.mono(10.5f));
            g.setColor(p.textoSuave());
            centrar(g, String.valueOf(k), x + lado / 2, y + 52);
            String puntero = punteros.get(k);
            if (puntero != null) {
                g.setFont(Tema.fuente(Font.BOLD, 11f));
                g.setColor(rol == Rol.NORMAL ? p.acento() : rol.color.apply(p));
                centrar(g, "↑ " + puntero, x + lado / 2, y + 68);
            }
        }
    }

    private void pintarLeyenda(Graphics2D g, int ancho, int y, Paleta p) {
        Set<Rol> presentes = EnumSet.noneOf(Rol.class);
        for (int k = 0; k < tamano; k++) {
            if (roles[k] != Rol.NORMAL) {
                presentes.add(roles[k]);
            }
        }
        g.setFont(Tema.fuente(Font.PLAIN, 11.5f));
        FontMetrics fm = g.getFontMetrics();
        int total = 0;
        for (Rol r : presentes) {
            total += fm.stringWidth(r.leyenda) + 30;
        }
        double x = (ancho - total) / 2.0;
        for (Rol r : presentes) {
            g.setColor(r.color.apply(p));
            g.fill(new Ellipse2D.Double(x, y - 9, 10, 10));
            g.setColor(p.textoSuave());
            g.drawString(r.leyenda, (float) (x + 15), y);
            x += fm.stringWidth(r.leyenda) + 30;
        }
    }

    // ------------------------------------------------------------- geometría

    private Point2D posicionAnimada(int k, int ancho, double margen, double y0, double sep, double r, double t) {
        Point2D destino = posicion(k, ancho, margen, y0, sep);
        if (t >= 1 || (k != intercambioA && k != intercambioB)) {
            return destino;
        }
        Point2D origen = posicion(k == intercambioA ? intercambioB : intercambioA, ancho, margen, y0, sep);
        double x = origen.getX() + (destino.getX() - origen.getX()) * t;
        double y = origen.getY() + (destino.getY() - origen.getY()) * t;
        double dx = destino.getX() - origen.getX();
        double dy = destino.getY() - origen.getY();
        double largo = Math.max(1, Math.hypot(dx, dy));
        double arco = Math.sin(Math.PI * t) * r * 1.3 * (k == intercambioA ? 1 : -1);
        return new Point2D.Double(x - dy / largo * arco, y + dx / largo * arco);
    }

    private static Point2D posicion(int k, int ancho, double margen, double y0, double separacion) {
        int n = nivel(k);
        int enNivel = 1 << n;
        int posicion = k - (enNivel - 1);
        double x = margen + (posicion + 0.5) * (ancho - 2 * margen) / enNivel;
        return new Point2D.Double(x, y0 + n * separacion);
    }

    static int nivel(int indice) {
        return 31 - Integer.numberOfLeadingZeros(indice + 1);
    }

    static void centrar(Graphics2D g, String texto, double cx, double cy) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, (float) (cx - fm.stringWidth(texto) / 2.0),
                (float) (cy + (fm.getAscent() - fm.getDescent()) / 2.0));
    }

    static void pastilla(Graphics2D g, String texto, double x, double y, Color c, Paleta p, boolean solida) {
        FontMetrics fm = g.getFontMetrics();
        double w = fm.stringWidth(texto) + 22;
        RoundRectangle2D forma = new RoundRectangle2D.Double(x, y, w, 24, 24, 24);
        g.setColor(solida ? c : Paleta.alfa(c, p.oscura() ? 0.16 : 0.12));
        g.fill(forma);
        g.setColor(solida ? Color.WHITE : c);
        centrar(g, texto, x + w / 2, y + 12);
    }
}
