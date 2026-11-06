package pnni.laboratorio;

import javax.swing.SwingUtilities;

import pnni.laboratorio.ui.Tema;
import pnni.laboratorio.ui.VentanaPrincipal;

/** Punto de entrada del laboratorio interactivo de Programación No Numérica II. */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "lcd");
        Tema.instalarFuentes();
        Tema.aplicar(true);
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
