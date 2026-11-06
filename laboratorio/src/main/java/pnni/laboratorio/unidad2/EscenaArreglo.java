package pnni.laboratorio.unidad2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pnni.laboratorio.nucleo.Escena;
import pnni.laboratorio.ui.Arbol;
import pnni.laboratorio.ui.Dibujo;
import pnni.laboratorio.ui.Paleta;
import pnni.laboratorio.ui.Tema;
import pnni.laboratorio.ui.Tono;

/**
 * Escena de divide y conquista: arriba, el árbol de llamadas recursivas (si lo hay);
 * abajo, uno o más arreglos con el rango activo resaltado, punteros con nombres de
 * variables y los intercambios animados. Varias filas pueden compartir línea
 * (por ejemplo, los temporales izq y der de merge).
 */
public final class EscenaArreglo implements Escena {

    private final List<List<Fila>> lineas;
    private final List<Arbol.Nodo> arbol;
    private final Map<Tono, String> leyenda;
    private final String nota;
    private final String insignia;
    private final boolean positiva;

    private EscenaArreglo(Constructor c) {
        lineas = c.lineas;
        arbol = c.arbol;
        leyenda = c.leyenda;
        nota = c.nota;
        insignia = c.insignia;
        positiva = c.positiva;
    }

    public static Constructor nueva() {
        return new Constructor();
    }

    /** Texto como lo imprime el programa de consola: cada valor seguido de un espacio. */
    public static String texto(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int v : arr) {
            sb.append(v).append(' ');
        }
        return sb.toString();
    }

    public static Fila fila(String nombre, int[] valores) {
        return new Fila(nombre, valores);
    }

    /** Un arreglo dibujado como fila de celdas. Copia los valores al crearse. */
    public static final class Fila {
        private final String nombre;
        private final int[] valores;
        private final Tono[] tonos;
        private final Map<Integer, String> punteros = new LinkedHashMap<>();
        private int desde;
        private int hasta;
        private int intercambioA = -1;
        private int intercambioB = -1;

        private Fila(String nombre, int[] valores) {
            this.nombre = nombre;
            this.valores = Arrays.copyOf(valores, valores.length);
            this.tonos = new Tono[valores.length];
            Arrays.fill(tonos, Tono.NORMAL);
            desde = 0;
            hasta = valores.length - 1;
        }

        public Fila tono(int i, Tono t) {
            if (i >= 0 && i < tonos.length) {
                tonos[i] = t;
            }
            return this;
        }

        public Fila tonos(int a, int b, Tono t) {
            for (int i = Math.max(0, a); i <= b && i < tonos.length; i++) {
                tonos[i] = t;
            }
            return this;
        }

        /** Rango activo: lo que queda fuera se atenúa. */
        public Fila rango(int a, int b) {
            desde = a;
            hasta = b;
            return this;
        }

        public Fila puntero(int i, String nombre) {
            punteros.merge(i, nombre, (x, y) -> x + "," + y);
            return this;
        }

        /** Los valores en a y b ya están intercambiados; la animación los trae desde su posición anterior. */
        public Fila intercambio(int a, int b) {
            intercambioA = a;
            intercambioB = b;
            return tono(a, Tono.PELIGRO).tono(b, Tono.PELIGRO);
        }
    }

    public static final class Constructor {
        private final List<List<Fila>> lineas = new ArrayList<>();
        private List<Arbol.Nodo> arbol = List.of();
        private final Map<Tono, String> leyenda = new LinkedHashMap<>();
        private String nota;
        private String insignia;
        private boolean positiva;

        public Constructor linea(Fila... filas) {
            lineas.add(List.of(filas));
            return this;
        }

        public Constructor arbol(List<Arbol.Nodo> nodos) {
            arbol = List.copyOf(nodos);
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

        public EscenaArreglo crear() {
            return new EscenaArreglo(this);
        }
    }

    // ------------------------------------------------------------------ dibujo

    private static final int ALTO_FILA = 96;

    @Override
    public void pintar(Graphics2D g, int ancho, int alto, double t, Paleta p) {
        Dibujo.etiquetas(g, ancho, nota, insignia, positiva, p);
        int altoLeyenda = leyenda.isEmpty() ? 0 : 26;
        int altoFilas = lineas.size() * ALTO_FILA;
        int arriba = 38;
        int yFilas;
        if (arbol.isEmpty()) {
            yFilas = arriba + Math.max(0, (alto - arriba - altoLeyenda - altoFilas) / 2);
        } else {
            int altoArbol = Math.max(90, alto - arriba - altoLeyenda - altoFilas - 10);
            Dibujo.rotulo(g, "ÁRBOL DE LLAMADAS RECURSIVAS", 12, arriba + 6, p);
            Arbol.pintar(g, arbol, 12, arriba + 14, ancho - 24, altoArbol - 14, p);
            yFilas = arriba + altoArbol + 6;
        }
        for (List<Fila> linea : lineas) {
            pintarLinea(g, linea, ancho, yFilas, t, p);
            yFilas += ALTO_FILA;
        }
        if (!leyenda.isEmpty()) {
            Dibujo.leyenda(g, ancho, alto - 8, leyenda, p);
        }
    }

    private void pintarLinea(Graphics2D g, List<Fila> filas, int ancho, int y, double t, Paleta p) {
        int celdas = 0;
        for (Fila f : filas) {
            celdas += Math.max(1, f.valores.length);
        }
        double hueco = 36;
        double disponible = ancho - 32 - hueco * (filas.size() - 1);
        double lado = Math.max(22, Math.min(50, disponible / celdas));
        double total = lado * celdas + hueco * (filas.size() - 1);
        double x = (ancho - total) / 2;
        for (Fila f : filas) {
            pintarFila(g, f, x, y, lado, t, p);
            x += lado * Math.max(1, f.valores.length) + hueco;
        }
    }

    private void pintarFila(Graphics2D g, Fila f, double x0, double y, double lado, double t, Paleta p) {
        Dibujo.rotulo(g, f.nombre, x0 + 2, y + 10, p);
        double yCelda = y + 18;
        if (f.valores.length == 0) {
            g.setFont(Tema.fuente(Font.PLAIN, 11.5f));
            g.setColor(p.textoSuave());
            g.drawString("(vacío)", (float) x0 + 4, (float) yCelda + 24);
            return;
        }
        Font fuenteValor = Tema.fuente(Font.BOLD, (float) Math.max(10.5, Math.min(14, lado * 0.32)));
        double avance = Dibujo.suave(t);
        for (int k = 0; k < f.valores.length; k++) {
            double x = x0 + k * lado;
            double dx = 0;
            double dy = 0;
            if (avance < 1 && (k == f.intercambioA || k == f.intercambioB) && f.intercambioA != f.intercambioB) {
                int origen = k == f.intercambioA ? f.intercambioB : f.intercambioA;
                dx = (origen - k) * lado * (1 - avance);
                dy = Math.sin(Math.PI * avance) * 16 * (k == f.intercambioA ? -1 : 1);
            }
            boolean fuera = k < f.desde || k > f.hasta;
            Tono tono = fuera && f.tonos[k] == Tono.NORMAL ? Tono.ATENUADO : f.tonos[k];
            RoundRectangle2D celda = new RoundRectangle2D.Double(x + 2 + dx, yCelda + dy, lado - 4, 40, 9, 9);
            Color texto = Dibujo.forma(g, celda, tono, p);
            g.setFont(fuenteValor);
            g.setColor(texto);
            Dibujo.centrar(g, String.valueOf(f.valores[k]), celda.getCenterX(), celda.getCenterY());

            g.setFont(Tema.mono(10.5f));
            g.setColor(fuera ? Paleta.alfa(p.textoSuave(), 0.5) : p.textoSuave());
            Dibujo.centrar(g, String.valueOf(k), x + lado / 2, yCelda + 52);
            String puntero = f.punteros.get(k);
            if (puntero != null) {
                g.setFont(Tema.fuente(Font.BOLD, 11f));
                g.setColor(tono == Tono.NORMAL || tono == Tono.ATENUADO ? p.acento() : tono.color(p));
                Dibujo.centrar(g, "↑ " + puntero, x + lado / 2, yCelda + 68);
            }
        }
        if (f.desde <= f.hasta && (f.desde > 0 || f.hasta < f.valores.length - 1)) {
            double xa = x0 + Math.max(0, f.desde) * lado;
            double xb = x0 + (Math.min(f.hasta, f.valores.length - 1) + 1) * lado;
            g.setColor(Paleta.alfa(p.acento(), 0.55));
            g.setStroke(new java.awt.BasicStroke(1.4f, java.awt.BasicStroke.CAP_ROUND,
                    java.awt.BasicStroke.JOIN_ROUND, 1f, new float[]{4f, 4f}, 0f));
            g.draw(new RoundRectangle2D.Double(xa - 1, yCelda - 4, xb - xa + 2, 48, 12, 12));
        }
    }
}
