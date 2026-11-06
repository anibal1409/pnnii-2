package pnni.laboratorio.unidad3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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
 * Mochila fraccionaria: a la izquierda, los objetos en el orden en que el algoritmo los
 * considera (valor, peso, razón y la fracción tomada); a la derecha, la bolsa con la
 * capacidad ocupada por cada objeto.
 */
final class EscenaMochila implements Escena {

    private static final Tono[] COLORES = {Tono.INFO, Tono.VIOLETA, Tono.EXITO, Tono.AVISO, Tono.ACENTO, Tono.PELIGRO};

    private final int[] valores;
    private final int[] pesos;
    private final int[] orden;
    private final Tono[] tonosFila;
    private final double[] fracciones;
    private final int capacidad;
    private final double total;
    private final String nota;
    private final String insignia;

    EscenaMochila(int[] valores, int[] pesos, int[] orden, Tono[] tonosFila, double[] fracciones, int capacidad,
                  double total, String nota, String insignia) {
        this.valores = valores.clone();
        this.pesos = pesos.clone();
        this.orden = orden.clone();
        this.tonosFila = tonosFila.clone();
        this.fracciones = fracciones.clone();
        this.capacidad = capacidad;
        this.total = total;
        this.nota = nota;
        this.insignia = insignia;
    }

    @Override
    public void pintar(Graphics2D g, int ancho, int alto, double t, Paleta p) {
        Dibujo.etiquetas(g, ancho, nota, insignia, true, p);
        int n = valores.length;
        double anchoBolsa = Math.min(170, ancho * 0.28);
        double anchoTabla = ancho - anchoBolsa - 44;
        double arriba = 44;
        double abajo = alto - 34;
        double altoFila = Math.max(26, Math.min(40, (abajo - arriba - 30) / Math.max(1, n)));
        double x0 = 14;

        String[] cabeceras = {"OBJETO", "VALOR", "PESO", "VALOR/PESO", "FRACCIÓN TOMADA"};
        double[] cols = {0, 0.17, 0.31, 0.45, 0.66};
        for (int c = 0; c < cabeceras.length; c++) {
            Dibujo.rotulo(g, cabeceras[c], x0 + 10 + cols[c] * anchoTabla, arriba + 12, p);
        }
        double y = arriba + 22;
        for (int k = 0; k < n; k++) {
            int i = orden[k];
            Tono tono = tonosFila[k];
            RoundRectangle2D fondo = new RoundRectangle2D.Double(x0, y + 2, anchoTabla, altoFila - 4, 10, 10);
            Color texto = Dibujo.forma(g, fondo, tono, p);
            if (tono.solido()) {
                texto = Color.WHITE;
            }
            g.setFont(Tema.fuente(Font.BOLD, 12.5f));
            g.setColor(texto);
            float base = (float) (y + altoFila / 2 + 5);
            Tono color = COLORES[i % COLORES.length];
            g.setColor(tono.solido() ? Color.WHITE : color.color(p));
            g.fill(new RoundRectangle2D.Double(x0 + 10, y + altoFila / 2 - 5, 10, 10, 4, 4));
            g.setColor(texto);
            g.drawString("obj " + i, (float) (x0 + 26), base);
            g.setFont(Tema.mono(12.5f));
            g.drawString(String.valueOf(valores[i]), (float) (x0 + 10 + cols[1] * anchoTabla), base);
            g.drawString(String.valueOf(pesos[i]), (float) (x0 + 10 + cols[2] * anchoTabla), base);
            g.drawString(String.format("%.2f", (double) valores[i] / pesos[i]), (float) (x0 + 10 + cols[3] * anchoTabla), base);
            double xb = x0 + 10 + cols[4] * anchoTabla;
            double wb = anchoTabla * 0.34 - 60;
            g.setColor(Paleta.alfa(p.textoSuave(), 0.2));
            g.fill(new RoundRectangle2D.Double(xb, y + altoFila / 2 - 4, wb, 8, 8, 8));
            if (fracciones[i] > 0) {
                g.setColor(tono.solido() ? Color.WHITE : color.color(p));
                g.fill(new RoundRectangle2D.Double(xb, y + altoFila / 2 - 4, wb * fracciones[i], 8, 8, 8));
            }
            g.setColor(texto);
            g.drawString(String.format("%.3f", fracciones[i]), (float) (xb + wb + 8), base);
            y += altoFila;
        }

        // Bolsa
        double bx = ancho - anchoBolsa - 18;
        double by = arriba + 22;
        double bh = abajo - by - 30;
        Dibujo.rotulo(g, "BOLSA  C = " + capacidad, bx, arriba + 12, p);
        RoundRectangle2D bolsa = new RoundRectangle2D.Double(bx, by, anchoBolsa, bh, 16, 16);
        g.setColor(p.superficieAlta());
        g.fill(bolsa);
        double ocupado = 0;
        Graphics2D recorte = (Graphics2D) g.create();
        recorte.clip(bolsa);
        for (int k = 0; k < n; k++) {
            int i = orden[k];
            if (fracciones[i] <= 0) {
                continue;
            }
            double peso = fracciones[i] * pesos[i];
            double h = bh * peso / capacidad;
            double yy = by + bh - (ocupado + peso) / capacidad * bh;
            boolean nuevo = tonosFila[k] == Tono.ACENTO;
            double crecer = nuevo ? Dibujo.suave(t) : 1;
            Color c = COLORES[i % COLORES.length].color(p);
            recorte.setColor(Paleta.mezclar(p.superficie(), c, p.oscura() ? 0.45 : 0.35));
            recorte.fill(new Rectangle2D.Double(bx, yy + h * (1 - crecer), anchoBolsa, h * crecer));
            recorte.setColor(c);
            recorte.setStroke(new BasicStroke(1.5f));
            recorte.draw(new Rectangle2D.Double(bx, yy + h * (1 - crecer), anchoBolsa, h * crecer));
            if (h > 18) {
                recorte.setFont(Tema.fuente(Font.BOLD, 11.5f));
                recorte.setColor(Dibujo.textoSobre(c, p));
                String etiqueta = "obj " + i + (fracciones[i] < 1 ? String.format(" (%.2f)", fracciones[i]) : "");
                Dibujo.centrar(recorte, etiqueta, bx + anchoBolsa / 2, yy + h / 2);
            }
            ocupado += peso;
        }
        recorte.dispose();
        g.setColor(p.borde());
        g.setStroke(new BasicStroke(2f));
        g.draw(bolsa);
        g.setFont(Tema.fuente(Font.BOLD, 12f));
        g.setColor(p.textoSuave());
        Dibujo.centrar(g, String.format("ocupado %.2f / %d", ocupado, capacidad), bx + anchoBolsa / 2, by + bh + 14);
        g.setFont(Tema.fuente(Font.BOLD, 15f));
        g.setColor(p.exito());
        Dibujo.centrar(g, String.format("valor = %.2f", total), bx + anchoBolsa / 2, by + bh + 32);

        Map<Tono, String> leyenda = new LinkedHashMap<>();
        leyenda.put(Tono.ACENTO, "objeto en turno");
        leyenda.put(Tono.AVISO, "comparando razones");
        leyenda.put(Tono.ATENUADO, "ya considerado");
        Dibujo.leyenda(g, (int) (anchoTabla + 20), alto - 8, leyenda, p);
    }

    static Tono[] tonos(int n) {
        Tono[] t = new Tono[n];
        Arrays.fill(t, Tono.NORMAL);
        return t;
    }
}
