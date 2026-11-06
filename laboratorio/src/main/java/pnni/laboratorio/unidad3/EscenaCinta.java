package pnni.laboratorio.unidad3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import pnni.laboratorio.nucleo.Escena;
import pnni.laboratorio.ui.Dibujo;
import pnni.laboratorio.ui.Paleta;
import pnni.laboratorio.ui.Tema;
import pnni.laboratorio.ui.Tono;

/**
 * Cinta magnética: cada archivo es un segmento de ancho proporcional a su longitud.
 * Los primeros {@code llegadas} archivos muestran debajo su tiempo de llegada
 * (longitud acumulada); abajo, la suma que forma el tiempo total.
 */
final class EscenaCinta implements Escena {

    private final int[] cinta;
    private final Tono[] tonos;
    private final Map<Integer, String> punteros;
    private final int llegadas;
    private final String suma;
    private final String referencia;
    private final String nota;
    private final String insignia;
    private final boolean positiva;

    private EscenaCinta(Constructor c) {
        cinta = c.cinta;
        tonos = c.tonos;
        punteros = c.punteros;
        llegadas = c.llegadas;
        suma = c.suma;
        referencia = c.referencia;
        nota = c.nota;
        insignia = c.insignia;
        positiva = c.positiva;
    }

    static Constructor de(int[] cinta) {
        return new Constructor(cinta);
    }

    static final class Constructor {
        private final int[] cinta;
        private final Tono[] tonos;
        private final Map<Integer, String> punteros = new LinkedHashMap<>();
        private int llegadas;
        private String suma;
        private String referencia;
        private String nota;
        private String insignia;
        private boolean positiva;

        private Constructor(int[] cinta) {
            this.cinta = Arrays.copyOf(cinta, cinta.length);
            tonos = new Tono[cinta.length];
            Arrays.fill(tonos, Tono.NORMAL);
        }

        Constructor tono(int i, Tono t) {
            if (i >= 0 && i < tonos.length) {
                tonos[i] = t;
            }
            return this;
        }

        Constructor tonos(int a, int b, Tono t) {
            for (int i = Math.max(0, a); i <= b && i < tonos.length; i++) {
                tonos[i] = t;
            }
            return this;
        }

        Constructor puntero(int i, String nombre) {
            punteros.merge(i, nombre, (x, y) -> x + "," + y);
            return this;
        }

        Constructor llegadas(int cantidad, String textoSuma) {
            llegadas = cantidad;
            suma = textoSuma;
            return this;
        }

        Constructor referencia(String texto) {
            referencia = texto;
            return this;
        }

        Constructor nota(String texto) {
            nota = texto;
            return this;
        }

        Constructor insignia(String texto, boolean esPositiva) {
            insignia = texto;
            positiva = esPositiva;
            return this;
        }

        EscenaCinta crear() {
            return new EscenaCinta(this);
        }
    }

    @Override
    public void pintar(Graphics2D g, int ancho, int alto, double t, Paleta p) {
        Dibujo.etiquetas(g, ancho, nota, insignia, positiva, p);
        int total = 0;
        for (int v : cinta) {
            total += v;
        }
        double margen = 24;
        double util = ancho - 2 * margen;
        double minimo = 34;
        double escala = (util - minimo * cinta.length) / Math.max(1, total);
        double yCinta = Math.max(64, (alto - 250) / 2.0 + 40);
        Dibujo.rotulo(g, "CINTA MAGNÉTICA  ·  lectura secuencial →", margen, yCinta - 12, p);

        g.setColor(p.superficieAlta());
        g.fill(new RoundRectangle2D.Double(margen - 6, yCinta - 4, util + 12, 60, 14, 14));
        double x = margen;
        int acumulado = 0;
        Font fuenteValor = Tema.fuente(Font.BOLD, 13f);
        for (int i = 0; i < cinta.length; i++) {
            double w = minimo + cinta[i] * Math.max(0, escala);
            Tono tono = tonos[i];
            RoundRectangle2D seg = new RoundRectangle2D.Double(x + 2, yCinta, w - 4, 52, 10, 10);
            Color texto = Dibujo.forma(g, seg, tono, p);
            if (tono == Tono.ACENTO && t < 1) {
                g.setColor(Paleta.alfa(Color.WHITE, 0.25 * (1 - Dibujo.suave(t))));
                g.fill(seg);
            }
            g.setFont(fuenteValor);
            g.setColor(texto);
            Dibujo.centrar(g, String.valueOf(cinta[i]), x + w / 2, yCinta + 26);

            g.setFont(Tema.mono(10.5f));
            g.setColor(p.textoSuave());
            Dibujo.centrar(g, String.valueOf(i), x + w / 2, yCinta + 66);
            String puntero = punteros.get(i);
            if (puntero != null) {
                g.setFont(Tema.fuente(Font.BOLD, 11f));
                g.setColor(tono == Tono.NORMAL ? p.acento() : tono.color(p));
                Dibujo.centrar(g, "↑ " + puntero, x + w / 2, yCinta + 82);
            }
            if (i < llegadas) {
                acumulado += cinta[i];
                g.setFont(Tema.fuente(Font.BOLD, 11f));
                g.setColor(p.info());
                Dibujo.centrar(g, "llega en " + acumulado, x + w / 2, yCinta + 100);
            }
            x += w;
        }

        double yResumen = yCinta + 122;
        RoundRectangle2D caja = new RoundRectangle2D.Double(margen - 6, yResumen, util + 12, referencia == null ? 62 : 84, 14, 14);
        g.setColor(p.superficieAlta());
        g.fill(caja);
        Dibujo.rotulo(g, "TIEMPO TOTAL  T = suma de los tiempos de llegada", margen + 8, yResumen + 20, p);
        g.setFont(Tema.fuente(suma == null ? Font.PLAIN : Font.BOLD, suma == null ? 13f : 17f));
        g.setColor(suma == null ? p.textoSuave() : p.exito());
        String texto = suma == null ? "Se calcula cuando toda la cinta esté grabada." : suma;
        g.drawString(Dibujo.recortar(g, texto, util - 16), (float) (margen + 8), (float) (yResumen + 48));
        if (referencia != null) {
            g.setFont(Tema.fuente(Font.PLAIN, 12f));
            g.setColor(p.textoSuave());
            g.drawString(referencia, (float) (margen + 8), (float) (yResumen + 72));
        }
        Map<Tono, String> leyenda = new LinkedHashMap<>();
        leyenda.put(Tono.EXITO, "grabado");
        leyenda.put(Tono.ACENTO, "más corto hasta ahora");
        leyenda.put(Tono.AVISO, "comparando");
        Dibujo.leyenda(g, ancho, alto - 8, leyenda, p);
    }
}
