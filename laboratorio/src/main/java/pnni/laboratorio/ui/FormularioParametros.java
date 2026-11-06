package pnni.laboratorio.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import pnni.laboratorio.nucleo.Parametro;
import pnni.laboratorio.ui.Iconos.Forma;

/** Formulario generado a partir de los {@link Parametro} de la práctica. */
public class FormularioParametros extends JPanel {

    private final JPanel filas = new JPanel(new GridBagLayout());
    private final JLabel error = new JLabel();
    private final JButton ejecutar = new JButton("Ejecutar");
    private final JButton restablecer = new JButton("Restablecer");
    private final Map<String, JComponent> campos = new LinkedHashMap<>();
    private List<Parametro> parametros = List.of();
    private Runnable alEjecutar = () -> { };

    public FormularioParametros() {
        super(new BorderLayout(0, 6));
        setOpaque(false);
        filas.setOpaque(false);

        ejecutar.setIcon(Iconos.de(Forma.PLAY, 14, java.awt.Color.WHITE));
        ejecutar.setToolTipText("Ejecutar con estos datos (F5)");
        ejecutar.setFocusable(false);
        ejecutar.putClientProperty(FlatClientProperties.STYLE,
                "background:$Component.accentColor; foreground:#ffffff; font:bold; borderWidth:0; focusWidth:0;"
                        + "hoverBackground:lighten($Component.accentColor,6%); pressedBackground:darken($Component.accentColor,6%);"
                        + "margin:6,16,6,18");
        ejecutar.addActionListener(e -> alEjecutar.run());

        restablecer.setIcon(Iconos.de(Forma.REINICIAR, 14));
        restablecer.setToolTipText("Volver a los datos de ejemplo");
        restablecer.setFocusable(false);
        restablecer.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        restablecer.addActionListener(e -> {
            cargar(parametros);
            alEjecutar.run();
        });

        error.setFont(Tema.fuente(Font.PLAIN, 12.5f));
        error.setVisible(false);

        add(filas, BorderLayout.CENTER);
        add(error, BorderLayout.SOUTH);
    }

    public void setAlEjecutar(Runnable accion) {
        alEjecutar = accion;
    }

    public void cargar(List<Parametro> nuevos) {
        parametros = nuevos;
        campos.clear();
        filas.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.insets = new Insets(0, 0, 0, 12);
        int columna = 0;
        for (Parametro p : nuevos) {
            JLabel etiqueta = new JLabel(p.etiqueta());
            etiqueta.setFont(Tema.fuente(Font.BOLD, 11.5f));
            etiqueta.setBorder(BorderFactory.createEmptyBorder(0, 2, 5, 0));
            JComponent campo = crearCampo(p);
            campos.put(p.clave(), campo);

            c.gridx = columna++;
            c.gridy = 0;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0;
            filas.add(etiqueta, c);
            c.gridy = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = p.esLista() ? 0 : 1;
            filas.add(campo, c);
        }
        c.gridx = columna++;
        c.gridy = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        filas.add(ejecutar, c);
        c.gridx = columna;
        c.insets = new Insets(0, 0, 0, 0);
        filas.add(restablecer, c);
        limpiarError();
        filas.revalidate();
        filas.repaint();
    }

    private JComponent crearCampo(Parametro p) {
        if (p.esLista()) {
            JComboBox<String> lista = new JComboBox<>(p.opciones().toArray(String[]::new));
            lista.setSelectedItem(p.porDefecto());
            lista.setToolTipText(p.ayuda());
            lista.setFocusable(false);
            lista.addActionListener(e -> alEjecutar.run());
            return lista;
        }
        JTextField texto = new JTextField(p.porDefecto());
        texto.setFont(Tema.mono(13f));
        texto.setToolTipText(p.ayuda());
        texto.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, p.ayuda());
        texto.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        texto.setPreferredSize(new Dimension(Math.max(90, p.porDefecto().length() * 9 + 40), 34));
        texto.addActionListener(e -> alEjecutar.run());
        return texto;
    }

    public Map<String, String> valores() {
        Map<String, String> valores = new LinkedHashMap<>();
        campos.forEach((clave, campo) -> valores.put(clave, campo instanceof JComboBox<?> lista
                ? String.valueOf(lista.getSelectedItem())
                : ((JTextField) campo).getText()));
        return valores;
    }

    public void mostrarError(String mensaje) {
        error.setForeground(Paleta.actual().peligro());
        error.setText("⚠  " + mensaje);
        error.setVisible(true);
    }

    public void limpiarError() {
        error.setVisible(false);
    }
}
