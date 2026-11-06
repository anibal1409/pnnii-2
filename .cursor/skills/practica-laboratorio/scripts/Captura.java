import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pnni.laboratorio.nucleo.Practica;
import pnni.laboratorio.ui.Reproductor;
import pnni.laboratorio.ui.Tema;
import pnni.laboratorio.ui.VentanaPrincipal;

/** Abre el laboratorio, va a una práctica y paso concretos y guarda la ventana como PNG. */
public class Captura {

    public static void main(String[] a) throws Exception {
        boolean oscuro = !a[0].equals("claro");
        int indicePractica = Integer.parseInt(a[1]);
        int paso = Integer.parseInt(a[2]);
        File destino = new File(a[3]);

        Tema.instalarFuentes();
        Tema.aplicar(oscuro);
        VentanaPrincipal[] ventana = new VentanaPrincipal[1];
        SwingUtilities.invokeAndWait(() -> {
            ventana[0] = new VentanaPrincipal();
            ventana[0].setVisible(true);
        });
        Thread.sleep(800);
        SwingUtilities.invokeAndWait(() -> {
            try {
                Method seleccionar = VentanaPrincipal.class.getDeclaredMethod("seleccionar", Practica.class);
                seleccionar.setAccessible(true);
                seleccionar.invoke(ventana[0], ((List<?>) campo(ventana[0], "practicas")).get(indicePractica));
                ((Reproductor) campo(ventana[0], "reproductor")).ir(paso, false);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        });
        Thread.sleep(700);
        SwingUtilities.invokeAndWait(() -> {
            JFrame f = ventana[0];
            BufferedImage imagen = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);
            f.getRootPane().paint(imagen.getGraphics());
            try {
                ImageIO.write(imagen, "png", destino);
            } catch (java.io.IOException e) {
                throw new IllegalStateException(e);
            }
        });
        System.out.println("Captura guardada en " + destino.getAbsolutePath());
        System.exit(0);
    }

    private static Object campo(Object objeto, String nombre) throws ReflectiveOperationException {
        Field f = objeto.getClass().getDeclaredField(nombre);
        f.setAccessible(true);
        return f.get(objeto);
    }
}
