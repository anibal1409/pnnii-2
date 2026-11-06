package pnni.laboratorio.ui;

import java.awt.Color;

/**
 * Colores del diseño. Toda pintura personalizada debe tomar sus colores de
 * {@link #actual()} para que el cambio de tema claro/oscuro sea inmediato.
 */
public record Paleta(
        boolean oscura,
        Color fondo, Color lateral, Color superficie, Color superficieAlta, Color borde,
        Color texto, Color textoSuave,
        Color acento, Color exito, Color aviso, Color peligro, Color info, Color violeta,
        Color sintaxisClave, Color sintaxisTipo, Color sintaxisNumero, Color sintaxisCadena,
        Color sintaxisComentario, Color sintaxisMetodo) {

    public static final Paleta OSCURA = new Paleta(true,
            hex(0x111318), hex(0x0C0E12), hex(0x171A21), hex(0x1F232C), hex(0x272B36),
            hex(0xE7E9EE), hex(0x8A90A0),
            hex(0x7C83FF), hex(0x3DD68C), hex(0xF5B83D), hex(0xF2667A), hex(0x4CC2FF), hex(0xB57BFF),
            hex(0xC792EA), hex(0xFFCB6B), hex(0xF78C6C), hex(0xC3E88D), hex(0x6B7385), hex(0x82AAFF));

    public static final Paleta CLARA = new Paleta(false,
            hex(0xF4F5F8), hex(0xECEEF3), hex(0xFFFFFF), hex(0xF3F4F8), hex(0xE2E5EC),
            hex(0x161922), hex(0x5E6575),
            hex(0x5458E8), hex(0x0F9F6E), hex(0xC98400), hex(0xDC3B55), hex(0x0A8BD1), hex(0x8B4DDB),
            hex(0xA626A4), hex(0xB76B01), hex(0x986801), hex(0x50A14F), hex(0x9DA1AB), hex(0x4078F2));

    private static Paleta actual = OSCURA;

    public static Paleta actual() {
        return actual;
    }

    static void usar(Paleta paleta) {
        actual = paleta;
    }

    /** Mismo color con opacidad 0..1. */
    public static Color alfa(Color c, double opacidad) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) Math.round(255 * opacidad));
    }

    /** Mezcla lineal de dos colores (t = 0 devuelve a, t = 1 devuelve b). */
    public static Color mezclar(Color a, Color b, double t) {
        return new Color(
                (int) Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                (int) Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                (int) Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }

    public static String css(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    private static Color hex(int rgb) {
        return new Color(rgb);
    }
}
