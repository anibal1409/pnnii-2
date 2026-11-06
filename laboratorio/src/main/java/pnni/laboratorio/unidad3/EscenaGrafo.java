package pnni.laboratorio.unidad3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pnni.laboratorio.nucleo.Escena;
import pnni.laboratorio.ui.Dibujo;
import pnni.laboratorio.ui.Paleta;
import pnni.laboratorio.ui.Tema;
import pnni.laboratorio.ui.Tono;

/**
 * Grafo ponderado con los vértices en círculo: cada vértice y cada arista tienen un
 * tono (en el árbol, candidata, descartada...) y los vértices un dato debajo
 * (costo, distancia, representante). A la derecha, una tabla con el estado del algoritmo.
 */
public final class EscenaGrafo implements Escena {

    /** Fila de la tabla lateral. */
    public record Fila(String texto, Tono tono) {
    }

    private final Grafo grafo;
    private final Tono[] tonosVertice;
    private final String[] datos;
    private final Tono[] tonosArista;
    private final String tituloTabla;
    private final List<Fila> tabla;
    private final Map<Tono, String> leyenda;
    private final String nota;
    private final String insignia;
    private final boolean positiva;

    private EscenaGrafo(Constructor c) {
        grafo = c.grafo;
        tonosVertice = c.tonosVertice;
        datos = c.datos;
        tonosArista = c.tonosArista;
        tituloTabla = c.tituloTabla;
        tabla = List.copyOf(c.tabla);
        leyenda = c.leyenda;
        nota = c.nota;
        insignia = c.insignia;
        positiva = c.positiva;
    }

    public static Constructor de(Grafo grafo) {
        return new Constructor(grafo);
    }

    public static final class Constructor {
        private final Grafo grafo;
        private final Tono[] tonosVertice;
        private final String[] datos;
        private final Tono[] tonosArista;
        private String tituloTabla = "";
        private final List<Fila> tabla = new ArrayList<>();
        private final Map<Tono, String> leyenda = new LinkedHashMap<>();
        private String nota;
        private String insignia;
        private boolean positiva;

        private Constructor(Grafo grafo) {
            this.grafo = grafo;
            tonosVertice = new Tono[grafo.n()];
            Arrays.fill(tonosVertice, Tono.NORMAL);
            datos = new String[grafo.n()];
            tonosArista = new Tono[grafo.m()];
            Arrays.fill(tonosArista, Tono.NORMAL);
        }

        public Constructor vertice(int v, Tono tono, String dato) {
            tonosVertice[v] = tono;
            datos[v] = dato;
            return this;
        }

        public Constructor arista(int k, Tono tono) {
            if (k >= 0) {
                tonosArista[k] = tono;
            }
            return this;
        }

        public Constructor tabla(String titulo) {
            tituloTabla = titulo;
            return this;
        }

        public Constructor fila(String texto, Tono tono) {
            tabla.add(new Fila(texto, tono));
            return this;
        }

        public Constructor leyenda(Tono tono, String texto) {
            leyenda.put(tono, texto);
            return this;
        }

        public Constructor nota(String texto) {
            nota = texto;
            return this;
        }

        public Constructor insignia(String texto, boolean esPositiva) {
            insignia = texto;
            positiva = esPositiva;
            return this;
        }

        public EscenaGrafo crear() {
            return new EscenaGrafo(this);
        }
    }

    // ------------------------------------------------------------------ dibujo

    @Override
    public void pintar(Graphics2D g, int ancho, int alto, double t, Paleta p) {
        Dibujo.etiquetas(g, ancho, nota, insignia, positiva, p);
        int altoLeyenda = leyenda.isEmpty() ? 0 : 26;
        boolean conTabla = !tabla.isEmpty() && ancho > 460;
        int anchoTabla = conTabla ? Math.min(230, ancho / 3) : 0;
        int arriba = 36;
        int abajo = alto - altoLeyenda - 6;
        double anchoGrafo = ancho - anchoTabla - (conTabla ? 12 : 0);
        double cx = anchoGrafo / 2;
        double cy = (arriba + abajo) / 2.0;
        double radioCirculo = Math.max(40, Math.min(anchoGrafo / 2 - 48, (abajo - arriba) / 2.0 - 34));
        double r = Math.max(14, Math.min(21, radioCirculo * 0.16));
        Point2D[] pos = new Point2D[grafo.n()];
        for (int v = 0; v < grafo.n(); v++) {
            double angulo = -Math.PI / 2 + 2 * Math.PI * v / grafo.n();
            pos[v] = new Point2D.Double(cx + radioCirculo * Math.cos(angulo), cy + radioCirculo * Math.sin(angulo));
        }
        pintarAristas(g, pos, r, p);
        pintarVertices(g, pos, r, t, p);
        if (conTabla) {
            pintarTabla(g, ancho - anchoTabla - 4, arriba, anchoTabla, abajo - arriba, p);
        }
        if (!leyenda.isEmpty()) {
            Dibujo.leyenda(g, ancho, alto - 8, leyenda, p);
        }
    }

    private void pintarAristas(Graphics2D g, Point2D[] pos, double r, Paleta p) {
        Font fuentePeso = Tema.fuente(Font.BOLD, 11.5f);
        for (int pasada = 0; pasada < 2; pasada++) {
            for (int k = 0; k < grafo.m(); k++) {
                Tono tono = tonosArista[k];
                boolean resaltada = tono != Tono.NORMAL && tono != Tono.ATENUADO;
                if ((pasada == 1) != resaltada) {
                    continue;
                }
                Point2D a = pos[grafo.u(k)];
                Point2D b = pos[grafo.v(k)];
                double dx = b.getX() - a.getX();
                double dy = b.getY() - a.getY();
                double largo = Math.max(1, Math.hypot(dx, dy));
                double ox = 0;
                double oy = 0;
                if (grafo.dirigido() && grafo.indice(grafo.v(k), grafo.u(k)) >= 0) {
                    ox = -dy / largo * 7;
                    oy = dx / largo * 7;
                }
                double x1 = a.getX() + dx / largo * r + ox;
                double y1 = a.getY() + dy / largo * r + oy;
                double x2 = b.getX() - dx / largo * (r + (grafo.dirigido() ? 3 : 0)) + ox;
                double y2 = b.getY() - dy / largo * (r + (grafo.dirigido() ? 3 : 0)) + oy;
                Color color = tono == Tono.NORMAL ? p.borde() : tono == Tono.ATENUADO
                        ? Paleta.alfa(p.textoSuave(), 0.35) : tono.color(p);
                g.setColor(color);
                g.setStroke(tono == Tono.ATENUADO
                        ? new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{5f, 5f}, 0f)
                        : new BasicStroke(resaltada ? 3.6f : 2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.draw(new Line2D.Double(x1, y1, x2, y2));
                if (grafo.dirigido()) {
                    flecha(g, x2, y2, dx / largo, dy / largo);
                }
                String peso = String.valueOf(grafo.peso(k));
                g.setFont(fuentePeso);
                double w = g.getFontMetrics().stringWidth(peso) + 12;
                // Al 42 % de la arista y no en su punto medio: dos aristas que se cruzan en el centro no tapan sus pesos
                double mx = x1 + (x2 - x1) * 0.42 + ox * 0.6;
                double my = y1 + (y2 - y1) * 0.42 + oy * 0.6;
                RoundRectangle2D caja = new RoundRectangle2D.Double(mx - w / 2, my - 10, w, 20, 20, 20);
                g.setColor(p.superficie());
                g.fill(caja);
                g.setColor(resaltada ? color : p.borde());
                g.setStroke(new BasicStroke(1.2f));
                g.draw(caja);
                g.setColor(resaltada ? Dibujo.textoSobre(color, p) : p.textoSuave());
                Dibujo.centrar(g, peso, mx, my);
            }
        }
    }

    private static void flecha(Graphics2D g, double x, double y, double ux, double uy) {
        double l = 10;
        double a = 5;
        Path2D punta = new Path2D.Double();
        punta.moveTo(x + ux * 2, y + uy * 2);
        punta.lineTo(x - ux * l - uy * a, y - uy * l + ux * a);
        punta.lineTo(x - ux * l + uy * a, y - uy * l - ux * a);
        punta.closePath();
        g.fill(punta);
    }

    private void pintarVertices(Graphics2D g, Point2D[] pos, double r, double t, Paleta p) {
        Font fuente = Tema.fuente(Font.BOLD, (float) Math.max(12, r * 0.75));
        Font fuenteDato = Tema.fuente(Font.BOLD, 11f);
        for (int v = 0; v < grafo.n(); v++) {
            Tono tono = tonosVertice[v];
            double x = pos[v].getX();
            double y = pos[v].getY();
            if (tono == Tono.ACENTO) {
                double halo = 5 + 3 * Math.sin(Math.PI * Dibujo.suave(t));
                g.setColor(Paleta.alfa(p.acento(), 0.25));
                g.fill(new Ellipse2D.Double(x - r - halo, y - r - halo, 2 * (r + halo), 2 * (r + halo)));
            }
            Color texto = Dibujo.forma(g, new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r), tono, p);
            g.setFont(fuente);
            g.setColor(texto);
            Dibujo.centrar(g, grafo.nombre(v), x, y);
            if (datos[v] != null) {
                g.setFont(fuenteDato);
                double w = g.getFontMetrics().stringWidth(datos[v]) + 10;
                // Los vértices de la parte alta llevan el dato encima para no chocar con las aristas
                double dy = Math.sin(-Math.PI / 2 + 2 * Math.PI * v / grafo.n()) < -0.5 ? -r - 12 : r + 12;
                g.setColor(Paleta.alfa(p.superficie(), 0.9));
                g.fill(new RoundRectangle2D.Double(x - w / 2, y + dy - 8, w, 16, 8, 8));
                g.setColor(tono == Tono.NORMAL || tono == Tono.ATENUADO ? p.textoSuave() : tono.color(p));
                Dibujo.centrar(g, datos[v], x, y + dy);
            }
        }
    }

    private void pintarTabla(Graphics2D g, double x, double y, double ancho, double alto, Paleta p) {
        RoundRectangle2D fondo = new RoundRectangle2D.Double(x, y, ancho, alto, 14, 14);
        g.setColor(p.superficieAlta());
        g.fill(fondo);
        Dibujo.rotulo(g, tituloTabla, x + 12, y + 20, p);
        double alturaFila = Math.max(16, Math.min(24, (alto - 36) / Math.max(1, tabla.size())));
        g.setFont(Tema.mono((float) Math.min(12.5, alturaFila * 0.6)));
        double yf = y + 34;
        for (Fila f : tabla) {
            if (f.tono() != Tono.NORMAL && f.tono() != Tono.ATENUADO) {
                g.setColor(Paleta.alfa(f.tono().color(p), p.oscura() ? 0.18 : 0.12));
                g.fill(new RoundRectangle2D.Double(x + 6, yf, ancho - 12, alturaFila - 2, 8, 8));
            }
            g.setColor(f.tono() == Tono.NORMAL ? p.texto() : f.tono() == Tono.ATENUADO
                    ? Paleta.alfa(p.textoSuave(), 0.7) : Dibujo.textoSobre(f.tono().color(p), p));
            g.drawString(Dibujo.recortar(g, f.texto(), ancho - 24), (float) (x + 12), (float) (yf + alturaFila * 0.68));
            yf += alturaFila;
        }
    }
}
